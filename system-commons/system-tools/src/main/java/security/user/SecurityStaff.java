package security.user;

import constant.Constant;
import org.apache.commons.lang3.StringUtils;
import redis.StaffDetailRedis;
import utils.HttpContextUtils;
import utils.SpringContextUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * 职员
 */
public class SecurityStaff {
    private static StaffDetailRedis staffDetailRedis;
    static {
        staffDetailRedis = SpringContextUtils.getBean(StaffDetailRedis.class);
    }

    /**
     * 获取职员信息
     */
    public static StaffDetail getStaff(){
        //从HTTP请求流中获取职员标识
        Long staffId = getStaffId();
        if(staffId == null){
            return null;
        }
        //从缓存中加载职员信息（根据职员标识）
        StaffDetail staff = staffDetailRedis.get(staffId);
        return staff;
    }

    /**
     * 获取职员ID
     */
    public static Long getStaffId() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if(request == null){
            return null;
        }
        String staffId = request.getHeader(Constant.USER_KEY);
        //检查返回的职员标识是否为空
        if(StringUtils.isBlank(staffId)){
            return null;
        }
        //将 string 转化成 long 的基本类型
        //  Long.ValueOf("String") 返回Long包装类型
        return Long.parseLong(staffId);
    }

    /**
     * 获取部门ID
     */
    public static Long getDeptId() {
        StaffDetail staff = getStaff();
        if(staff == null){
            return null;
        }
        return staff.getDeptId();
    }
}