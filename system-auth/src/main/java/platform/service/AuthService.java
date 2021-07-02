package platform.service;

import platform.dto.AuthorizationDTO;
import platform.dto.LoginDTO;

/**
 * 认证服务
 */
public interface AuthService {

    /**
     * 登录
     */
    AuthorizationDTO login(LoginDTO login);

    /**
     * 退出
     */
    void logout(Long userId);
}