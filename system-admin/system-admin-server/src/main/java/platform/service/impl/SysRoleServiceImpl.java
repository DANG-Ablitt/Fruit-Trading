package platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import constant.Constant;
import enums.SuperAdminEnum;
import mybatis_plus.enums.DelFlagEnum;
import mybatis_plus.service.impl.BaseServiceImpl;
import page.PageData;
import platform.dao.SysRoleDao;
import platform.dto.SysRoleDTO;
import platform.entity.SysRoleEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import platform.service.*;
import security.user.SecurityStaff;
import security.user.StaffDetail;
import utils.ConvertUtils;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDataScopeService sysRoleDataScopeService;
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public PageData<SysRoleDTO> page(Map<String, Object> params) {
        IPage<SysRoleEntity> page = baseDao.selectPage(
            getPage(params, Constant.CREATE_DATE, false),
            getWrapper(params)
        );

        return getPageData(page, SysRoleDTO.class);
    }

    @Override
    public List<SysRoleDTO> list(Map<String, Object> params) {
        List<SysRoleEntity> entityList = baseDao.selectList(getWrapper(params));

        return ConvertUtils.sourceToTarget(entityList, SysRoleDTO.class);
    }

    private QueryWrapper<SysRoleEntity> getWrapper(Map<String, Object> params){
        String name = (String)params.get("name");

        QueryWrapper<SysRoleEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Constant.DEL_FLAG, DelFlagEnum.NORMAL.value());
        wrapper.like(StringUtils.isNotBlank(name), "name", name);

        //普通管理员，只能查询所属部门及子部门的数据
        StaffDetail user = SecurityStaff.getStaff();
        if(user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            List<Long> deptIdList = sysDeptService.getSubDeptIdList(user.getDeptId());
            wrapper.in(deptIdList != null, "dept_id", deptIdList);
        }

        return wrapper;
    }

    @Override
    public SysRoleDTO get(Long id) {
        SysRoleEntity entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, SysRoleDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleDTO dto) {
        SysRoleEntity entity = ConvertUtils.sourceToTarget(dto, SysRoleEntity.class);
        //保存角色
        insert(entity);
        //保存角色菜单关系
        sysRoleMenuService.saveOrUpdate(entity.getId(), dto.getMenuIdList());
        //保存角色数据权限关系
        sysRoleDataScopeService.saveOrUpdate(entity.getId(), dto.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleDTO dto) {
        SysRoleEntity entity = ConvertUtils.sourceToTarget(dto, SysRoleEntity.class);

        //更新角色
        updateById(entity);

        //更新角色菜单关系
        sysRoleMenuService.saveOrUpdate(entity.getId(), dto.getMenuIdList());

        //更新角色数据权限关系
        sysRoleDataScopeService.saveOrUpdate(entity.getId(), dto.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        //逻辑删除角色
        logicDelete(ids, SysRoleEntity.class);

        //删除角色用户关系
        sysRoleUserService.deleteByRoleIds(ids);

        //[角色菜单关系、角色数据权限关系]，需要保留，不然逻辑删除就变成物理删除了
    }

}