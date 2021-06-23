package authentication.dao.staff;

import authentication.entity.SysStaffEntity;

public interface SysStaffDao{
    /**
     * 根据职员ID，获取职员信息
     */
    SysStaffEntity getById(Long id);

    /**
     * 根据职员名，获取职员信息
     * @param username  职员名
     */
    SysStaffEntity getByUsername(String username);
}
