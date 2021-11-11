package mybatis_plus.aspect;

import cn.hutool.core.collection.CollUtil;
import constant.Constant;
import enums.SuperAdminEnum;
import exception.ErrorCode;
import exception.RenException;
import mybatis_plus.annotation.DataFilter;
import mybatis_plus.entity.DataScope;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import security.user.SecurityStaff;
import security.user.StaffDetail;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 数据过滤，切面处理类
 */
@Aspect
@Component
public class DataFilterAspect {

    /**
     * 通过ThreadLocal记录权限相关的属性值
     */
    ThreadLocal<DataScope> threadLocal = new ThreadLocal<>();

    public ThreadLocal<DataScope> getThreadLocal() {
        return threadLocal;
    }

    @Pointcut("@annotation(mybatis_plus.annotation.DataFilter)")
    public void dataFilterCut() {

    }

    @Before("dataFilterCut()")
    public Map dataFilter(JoinPoint point) throws Exception {
        Object params = point.getArgs()[0];
        if(params != null && params instanceof Map){
            StaffDetail staff = SecurityStaff.getStaff();
            //如果是超级管理员，则不进行数据过滤
            if(staff.getSuperAdmin() == SuperAdminEnum.YES.value()) {
                return null;
            }
            //否则进行数据过滤
            Map map = (Map)params;
            String sqlFilter = getSqlFilter(staff, point);
            map.put(Constant.SQL_FILTER, new DataScope(sqlFilter));
            //从本地线程池中获取拼接SQL
            threadLocal.set(new DataScope(sqlFilter));
            return map;
        }

        throw new RenException(ErrorCode.DATA_SCOPE_PARAMS_ERROR);
    }

    /**
     * 获取数据过滤的SQL
     */
    private String getSqlFilter(StaffDetail staff, JoinPoint point) throws Exception {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        DataFilter dataFilter = method.getAnnotation(DataFilter.class);
        //获取表的别名
        String tableAlias = dataFilter.tableAlias();
        if(StringUtils.isNotBlank(tableAlias)){
            tableAlias +=  ".";
        }
        StringBuilder sqlFilter = new StringBuilder();
        //查询条件前缀
        String prefix = dataFilter.prefix();
        if(StringUtils.isNotBlank(prefix)){
            sqlFilter.append(" ").append(prefix);
        }
        sqlFilter.append(" (");
        //部门ID列表
        List<Long> deptIdList = staff.getDeptIdList();
        if(CollUtil.isNotEmpty(deptIdList)){
            sqlFilter.append(tableAlias).append(dataFilter.deptId());
            sqlFilter.append(" in(").append(StringUtils.join(deptIdList, ",")).append(")");
        }
        //查询本人数据
        if(CollUtil.isNotEmpty(deptIdList)){
            sqlFilter.append(" or ");
        }
        sqlFilter.append(tableAlias).append(dataFilter.userId()).append("=").append(staff.getId());
        sqlFilter.append(")");
        return sqlFilter.toString();
    }
}