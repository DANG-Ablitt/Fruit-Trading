package platform.service;


import security.user.StaffDetail;

/**
 * 资源服务
 */
public interface ResourceService {

    /**
     * 是否有资源访问权限
     * @param token   token
     * @param url     资源URL
     * @param method  请求方式
     *
     * @return 有访问权限，则返回用户信息
     */
    StaffDetail resource(String token, String url, String method);

}