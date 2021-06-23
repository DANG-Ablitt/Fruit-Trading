package authentication.dao.user;


import security.user.UserDetail;

public interface RegisterDao {
    /**
     * 插入一条记录
     */
    boolean insert(UserDetail user);
}
