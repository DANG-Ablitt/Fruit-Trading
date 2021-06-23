package authentication.dao.user;

import security.user.UserDetail;

public interface UserDao  {
    /**
     * 根据手机号查询用户信息
     * @param mobile 手机号
     * @return  UserDetail
     */
    UserDetail getUserByMobile(String mobile);

    /**
     * 根据用户id查询用户信息
     * @param userId 手机号
     * @return  UserDetail
     */
    UserDetail getUserByUserId(Long userId);
}
