package mybatis_plus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import mybatis_plus.enums.DelFlagEnum;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import security.user.SecurityStaff;
import security.user.StaffDetail;
import java.util.Date;

/**
 * 公共字段，自动填充值
 */
@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_DATE = "createDate";
    private final static String CREATOR = "creator";
    private final static String UPDATE_DATE = "updateDate";
    private final static String UPDATER = "updater";
    private final static String DEL_FLAG = "delFlag";
    private final static String DEPT_ID = "deptId";

    @Override
    public void insertFill(MetaObject metaObject) {
        StaffDetail staff = SecurityStaff.getStaff();
        if(staff == null){
            return;
        }
        Date date = new Date();
        //创建者
        setInsertFieldValByName(CREATOR, staff.getId(), metaObject);
        //创建时间
        setInsertFieldValByName(CREATE_DATE, date, metaObject);
        //创建者所属部门
        setInsertFieldValByName(DEPT_ID, staff.getDeptId(), metaObject);
        //更新者
        setInsertFieldValByName(UPDATER, staff.getId(), metaObject);
        //更新时间
        setInsertFieldValByName(UPDATE_DATE, date, metaObject);
        //删除标识
        setInsertFieldValByName(DEL_FLAG, DelFlagEnum.NORMAL.value(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新者
        setUpdateFieldValByName(UPDATER, SecurityStaff.getStaffId(), metaObject);
        //更新时间
        setUpdateFieldValByName(UPDATE_DATE, new Date(), metaObject);
    }
}