package security.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import security.bo.ResourceBO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 登录职员信息
 */
@Data
public class StaffDetail implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 职员id
     */
    private Long id;
    /**
     * 职员真实姓名
     */
    private String username;
    /**
     * 职员照片
     */
    private String headUrl;
    /**
     * 职员性别
     */
    private Integer gender;
    /**
     * 职员邮箱
     */
    private String email;
    /**
     * 职员手机号
     */
    private String mobile;
    /**
     * 职员所在部门id
     */
    private Long deptId;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 职员身份
     */
    private Integer status;
    /**
     * 是否为超级管理员
     */
    private Integer superAdmin;
    /**
     * 账号是否正常   0：正常   1：无权调用接口
     */
    private int kill;
    /**
     * 部门数据权限
     */
    private List<Long> deptIdList;
    /**
     * 用户资源列表
     */
    private List<ResourceBO> resourceList;

}