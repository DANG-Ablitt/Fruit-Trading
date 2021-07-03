package platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import constant.Constant;
import enums.SuperAdminEnum;
import mybatis_plus.service.impl.BaseServiceImpl;
import page.PageData;
import platform.dao.SysUserDao;
import platform.dto.SysUserDTO;
import platform.entity.SysUserEntity;
import platform.service.SysDeptService;
import platform.service.SysRoleUserService;
import platform.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.password.PasswordUtils;
import security.user.SecurityStaff;
import security.user.StaffDetail;
import utils.ConvertUtils;

import java.util.List;
import java.util.Map;

/**
 * 用户管理
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public PageData<SysUserDTO> page(Map<String, Object> params) {
        //转换成like
        paramsToLike(params, "username");
        //分页
        IPage<SysUserEntity> page = getPage(params, Constant.CREATE_DATE, false);
        //普通管理员，只能查询所属部门及子部门的数据
        StaffDetail user = SecurityStaff.getStaff();
        if(user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            params.put("deptIdList", sysDeptService.getSubDeptIdList(user.getDeptId()));
        }
        //查询
        List<SysUserEntity> list = baseDao.getList(params);
        return getPageData(list, page.getTotal(), SysUserDTO.class);
    }

    @Override
    public List<SysUserDTO> list(Map<String, Object> params) {
        //普通管理员，只能查询所属部门及子部门的数据
        StaffDetail user = SecurityStaff.getStaff();
        if(user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            params.put("deptIdList", sysDeptService.getSubDeptIdList(user.getDeptId()));
        }
        List<SysUserEntity> entityList = baseDao.getList(params);
        return ConvertUtils.sourceToTarget(entityList, SysUserDTO.class);
    }

    @Override
    public SysUserDTO get(Long id) {
        SysUserEntity entity = baseDao.getById(id);
        return ConvertUtils.sourceToTarget(entity, SysUserDTO.class);
    }

    @Override
    public SysUserDTO getByUsername(String username) {
        SysUserEntity entity = baseDao.getByUsername(username);
        return ConvertUtils.sourceToTarget(entity, SysUserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserDTO dto) {
        SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);
        //密码加密
        String password = PasswordUtils.encode(entity.getPassword());
        entity.setPassword(password);
        //保存用户
        entity.setSuperAdmin(SuperAdminEnum.NO.value());
        insert(entity);
        //保存角色用户关系
        sysRoleUserService.saveOrUpdate(entity.getId(), dto.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserDTO dto) {
        SysUserEntity entity = ConvertUtils.sourceToTarget(dto, SysUserEntity.class);
        //密码加密
        if(StringUtils.isBlank(dto.getPassword())){
            entity.setPassword(null);
        }else{
            String password = PasswordUtils.encode(entity.getPassword());
            entity.setPassword(password);
        }
        //更新用户
        updateById(entity);
        //更新角色用户关系
        sysRoleUserService.saveOrUpdate(entity.getId(), dto.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        //逻辑删除
        logicDelete(ids, SysUserEntity.class);
        //角色用户关系，需要保留，不然逻辑删除就变成物理删除了
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long id, String newPassword) {
        newPassword = PasswordUtils.encode(newPassword);
        baseDao.updatePassword(id, newPassword);
    }

    @Override
    public int getCountByDeptId(Long deptId) {
        return baseDao.getCountByDeptId(deptId);
    }
}