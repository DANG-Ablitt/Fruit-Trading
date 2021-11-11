create table sys_user
(
  id                   bigint not null comment 'id',
  username             varchar(50) comment '用户名',
  password             varchar(100) comment '密码',
  real_name            varchar(50) comment '姓名',
  head_url             varchar(200) comment '头像',
  gender               tinyint(4) unsigned comment '性别   0：男   1：女    2：保密',
  email                varchar(100) comment '邮箱',
  mobile               varchar(20) comment '手机号',
  dept_id              bigint comment '部门ID',
  super_admin          tinyint unsigned comment '超级管理员   0：否   1：是',
  status               tinyint(4) unsigned comment '状态  0：停用    1：正常',
  remark               varchar(200) comment '备注',
  del_flag             tinyint(4) unsigned comment '删除标识  0：未删除    1：删除',
  creator              bigint comment '创建者',
  create_date          datetime comment '创建时间',
  updater              bigint comment '更新者',
  update_date          datetime comment '更新时间',
  primary key (id),
  unique key uk_username (username),
  key idx_del_flag (del_flag),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='用户管理';

create table sys_dept
(
  id                   bigint not null comment 'id',
  pid                  bigint comment '上级ID',
  pids                 varchar(500) comment '所有上级ID，用逗号分开',
  name                 varchar(50) comment '部门名称',
  sort                 int unsigned comment '排序',
  del_flag             tinyint(4) unsigned comment '删除标识  0：未删除    1：删除',
  creator              bigint comment '创建者',
  create_date          datetime comment '创建时间',
  updater              bigint comment '更新者',
  update_date          datetime comment '更新时间',
  primary key (id),
  key idx_pid (pid),
  key idx_del_flag (del_flag),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='部门管理';

create table sys_menu
(
  id                   bigint not null comment 'id',
  pid                  bigint comment '上级ID，一级菜单为0',
  url                  varchar(200) comment '菜单URL',
  type                 tinyint unsigned comment '类型   0：菜单   1：按钮',
  icon                 varchar(50) comment '菜单图标',
  permissions          varchar(32) comment '权限标识，如：sys:menu:save',
  sort                 int(11) comment '排序',
  del_flag             tinyint(4) unsigned comment '删除标识  0：未删除    1：删除',
  creator              bigint comment '创建者',
  create_date          datetime comment '创建时间',
  updater              bigint comment '更新者',
  update_date          datetime comment '更新时间',
  primary key (id),
  key idx_pid (pid),
  key idx_del_flag (del_flag),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='菜单管理';

create table sys_resource
(
  id                   bigint not null comment 'id',
  resource_code        varchar(32) comment '资源编码，如菜单ID',
  resource_name        varchar(32) comment '资源名称',
  resource_url         varchar(100) comment '资源URL',
  resource_method      varchar(8) comment '请求方式（如：GET、POST、PUT、DELETE）',
  menu_flag            tinyint unsigned comment '菜单标识  0：非菜单资源   1：菜单资源',
  auth_level           tinyint unsigned comment '认证等级   0：权限认证   1：登录认证    2：无需认证',
  creator              bigint comment '创建者',
  create_date          datetime comment '创建时间',
  updater              bigint comment '更新者',
  update_date          datetime comment '更新时间',
  primary key (id),
  key idx_resource_code (resource_code),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='资源管理';

create table sys_role
(
  id                   bigint not null comment 'id',
  name                 varchar(32) comment '角色名称',
  remark               varchar(100) comment '备注',
  del_flag             tinyint(4) unsigned comment '删除标识  0：未删除    1：删除',
  dept_id              bigint comment '部门ID',
  creator              bigint comment '创建者',
  create_date          datetime comment '创建时间',
  updater              bigint comment '更新者',
  update_date          datetime comment '更新时间',
  primary key (id),
  key idx_dept_id (dept_id),
  key idx_del_flag (del_flag),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='角色管理';

create table sys_role_user
(
  id                   bigint not null comment 'id',
  role_id              bigint comment '角色ID',
  user_id              bigint comment '用户ID',
  creator              bigint comment '创建者',
  create_date          datetime comment '创建时间',
  primary key (id),
  key idx_role_id (role_id),
  key idx_user_id (user_id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='角色用户关系';

create table sys_role_menu
(
  id                   bigint not null comment 'id',
  role_id              bigint comment '角色ID',
  menu_id              bigint comment '菜单ID',
  creator              bigint comment '创建者',
  create_date          datetime comment '创建时间',
  primary key (id),
  key idx_role_id (role_id),
  key idx_menu_id (menu_id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='角色菜单关系';

create table sys_role_data_scope
(
  id                   bigint not null comment 'id',
  role_id              bigint comment '角色ID',
  dept_id              bigint comment '部门ID',
  creator              bigint comment '创建者',
  create_date          datetime comment '创建时间',
  primary key (id),
  key idx_role_id (role_id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='角色数据权限';

create table sys_log_login
(
  id                   bigint not null comment 'id',
  operation            tinyint unsigned comment '用户操作   0：用户登录   1：用户退出',
  status               tinyint unsigned not null comment '状态  0：失败    1：成功    2：账号已锁定',
  user_agent           varchar(500) comment '用户代理',
  ip                   varchar(160) comment '操作IP',
  creator_name         varchar(50) comment '用户名',
  creator              bigint comment '创建者',
  create_date          datetime comment '创建时间',
  primary key (id),
  key idx_status (status),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='登录日志';

create table sys_log_operation
(
  id                   bigint not null comment 'id',
  module               varchar(32) comment '模块名称，如：sys',
  operation            varchar(50) comment '用户操作',
  request_uri          varchar(200) comment '请求URI',
  request_method       varchar(20) comment '请求方式',
  request_params       text comment '请求参数',
  request_time         int unsigned not null comment '请求时长(毫秒)',
  user_agent           varchar(500) comment '用户代理',
  ip                   varchar(160) comment '操作IP',
  status               tinyint(4) unsigned not null comment '状态  0：失败   1：成功',
  creator_name         varchar(50) comment '用户名',
  creator              bigint comment '创建者',
  create_date          datetime comment '创建时间',
  primary key (id),
  key idx_module (module),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='操作日志';

create table sys_log_error
(
  id                   bigint not null comment 'id',
  module               varchar(50) comment '模块名称，如：sys',
  request_uri          varchar(200) comment '请求URI',
  request_method       varchar(20) comment '请求方式',
  request_params       text comment '请求参数',
  user_agent           varchar(500) comment '用户代理',
  ip                   varchar(160) comment '操作IP',
  error_info           text comment '异常信息',
  creator              bigint comment '创建者',
  create_date          datetime comment '创建时间',
  primary key (id),
  key idx_module (module),
  key idx_create_date (create_date)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='异常日志';

CREATE TABLE sys_language (
  table_name varchar(32) NOT NULL COMMENT '表名',
  table_id bigint NOT NULL COMMENT '表主键',
  field_name varchar(32) NOT NULL COMMENT '字段名',
  field_value varchar(200) NOT NULL COMMENT '字段值',
  language varchar(10) NOT NULL COMMENT '语言',
  primary key (table_name, table_id, field_name, language),
  key idx_table_id (table_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='国际化';

create table b_order
(
	id                   bigint not null comment '订单id',
	userId               bigint not null comment '用户id',
	shopId               bigint not null comment '商品id',
	orderStatus          tinyint unsigned comment '订单状态   1：订单待支付2：订单待取货3：订单已完成4：订单已过期5：订单已取消',
	address              varchar(500) comment '取货门店',
	goodsPrice			 decimal comment '商品价格',
	goodsAmount          tinyint unsigned comment '商品数量',
	shopma               varchar(500) comment '取货码',
	nowDate              datetime comment '生成时间',
	updateDate           datetime comment '修改时间',
	key idx_table_id (id)
)ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='订单服务';

INSERT INTO sys_user(id, username, password, real_name, head_url, gender, email, mobile, dept_id, super_admin, status, remark, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000001, 'admin', '$2a$10$012Kx2ba5jzqr9gLlG4MX.bnQJTD9UWqF57XDo2N3.fPtLne02u/m', '超级管理员', NULL, 1, 'root@renren.io', '13512345678', NULL, 1, 1, NULL, 0, NULL, now(), NULL, now());

INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000002, 'name', '权限管理', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000002, 'name', '權限管理', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000002, 'name', 'Authority Management', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000003, 'name', '用户管理', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000003, 'name', '用戶管理', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000003, 'name', 'User Management', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000004, 'name', 'View', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000004, 'name', '查看', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000004, 'name', '查看', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000005, 'name', 'Add', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000005, 'name', '新增', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000005, 'name', '新增', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000006, 'name', 'Edit', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000006, 'name', '修改', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000006, 'name', '修改', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000007, 'name', 'Delete', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000007, 'name', '删除', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000007, 'name', '刪除', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000008, 'name', 'Export', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000008, 'name', '导出', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000008, 'name', '導出', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000009, 'name', 'Department Management', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000009, 'name', '部门管理', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000009, 'name', '部門管理', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000010, 'name', 'View', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000010, 'name', '查看', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000010, 'name', '查看', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000011, 'name', 'Add', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000011, 'name', '新增', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000011, 'name', '新增', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000012, 'name', 'Edit', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000012, 'name', '修改', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000012, 'name', '修改', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000013, 'name', 'Delete', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000013, 'name', '删除', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000013, 'name', '刪除', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000014, 'name', 'Role Management', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000014, 'name', '角色管理', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000014, 'name', '角色管理', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000015, 'name', 'View', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000015, 'name', '查看', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000015, 'name', '查看', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000016, 'name', 'Add', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000016, 'name', '新增', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000016, 'name', '新增', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000017, 'name', 'Edit', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000017, 'name', '修改', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000017, 'name', '修改', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000018, 'name', 'Delete', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000018, 'name', '删除', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000018, 'name', '刪除', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000019, 'name', 'Setting', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000019, 'name', '系统设置', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000019, 'name', '系統設置', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000020, 'name', 'Menu Management', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000020, 'name', '菜单管理', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000020, 'name', '菜單管理', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000021, 'name', 'View', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000021, 'name', '查看', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000021, 'name', '查看', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000022, 'name', 'Add', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000022, 'name', '新增', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000022, 'name', '新增', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000023, 'name', 'Edit', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000023, 'name', '修改', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000023, 'name', '修改', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000024, 'name', 'Delete', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000024, 'name', '删除', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000024, 'name', '刪除', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000025, 'name', 'Parameter Management', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000025, 'name', '参数管理', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000025, 'name', '參數管理', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000026, 'name', 'View', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000026, 'name', '查看', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000026, 'name', '查看', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000027, 'name', 'Add', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000027, 'name', '新增', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000027, 'name', '新增', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000028, 'name', 'Edit', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000028, 'name', '修改', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000028, 'name', '修改', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000029, 'name', 'Delete', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000029, 'name', '删除', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000029, 'name', '刪除', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000030, 'name', 'Export', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000030, 'name', '导出', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000030, 'name', '導出', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000031, 'name', 'Dict Management', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000031, 'name', '字典管理', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000031, 'name', '字典管理', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000032, 'name', 'View', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000032, 'name', '查看', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000032, 'name', '查看', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000033, 'name', 'Add', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000033, 'name', '新增', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000033, 'name', '新增', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000034, 'name', 'Edit', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000034, 'name', '修改', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000034, 'name', '修改', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000035, 'name', 'Delete', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000035, 'name', '删除', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000035, 'name', '刪除', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000036, 'name', 'Log Management', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000036, 'name', '日志管理', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000036, 'name', '日誌管理', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000037, 'name', 'Login Log', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000037, 'name', '登录日志', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000037, 'name', '登錄日誌', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000038, 'name', 'Operation Log', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000038, 'name', '操作日志', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000038, 'name', '操作日誌', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000039, 'name', 'Error Log', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000039, 'name', '异常日志', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000039, 'name', '異常日誌', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000040, 'name', 'System Monitoring', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000040, 'name', '系统监控', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000040, 'name', '系統監控', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000041, 'name', 'Service Monitoring', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000041, 'name', '服务监控', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000041, 'name', '服務監控', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000042, 'name', 'Swagger Api', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000042, 'name', '接口文档', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000042, 'name', '接口文檔', 'zh-TW');

INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000002, 0, '', 0, 'icon-lock', '', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000003, 1067246875800000002, 'sys/user', 0, 'icon-user', '', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000004, 1067246875800000003, '', 1, '', 'sys:user:view', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000005, 1067246875800000003, '', 1, '', 'sys:user:save', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000006, 1067246875800000003, '', 1, '', 'sys:user:update', 2, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000007, 1067246875800000003, '', 1, '', 'sys:user:delete', 3, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000008, 1067246875800000003, '', 1, '', 'sys:user:export', 4, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000009, 1067246875800000002, 'sys/dept', 0, 'icon-apartment', '', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000010, 1067246875800000009, '', 1, '', 'sys:dept:view', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000011, 1067246875800000009, '', 1, '', 'sys:dept:save', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000012, 1067246875800000009, '', 1, '', 'sys:dept:update', 2, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000013, 1067246875800000009, '', 1, '', 'sys:dept:delete', 3, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000014, 1067246875800000002, 'sys/role', 0, 'icon-team', '', 2, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000015, 1067246875800000014, '', 1, '', 'sys:role:view', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000016, 1067246875800000014, '', 1, '', 'sys:role:save', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000017, 1067246875800000014, '', 1, '', 'sys:role:update', 2, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000018, 1067246875800000014, '', 1, '', 'sys:role:delete', 3, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000019, 0, '', 0, 'icon-setting', NULL, 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000020, 1067246875800000019, 'sys/menu', 0, 'icon-unorderedlist', NULL, 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000021, 1067246875800000020, NULL, 1, NULL, 'sys:menu:view', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000022, 1067246875800000020, NULL, 1, NULL, 'sys:menu:save', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000023, 1067246875800000020, NULL, 1, NULL, 'sys:menu:update', 2, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000024, 1067246875800000020, NULL, 1, NULL, 'sys:menu:delete', 3, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000025, 1067246875800000019, 'sys/params', 0, 'icon-fileprotect', '', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000026, 1067246875800000025, NULL, 1, NULL, 'sys:params:view', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000027, 1067246875800000025, NULL, 1, NULL, 'sys:params:save', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000028, 1067246875800000025, NULL, 1, NULL, 'sys:params:update', 2, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000029, 1067246875800000025, NULL, 1, NULL, 'sys:params:delete', 3, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000030, 1067246875800000025, '', 1, NULL, 'sys:params:export', 4, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000031, 1067246875800000019, 'sys/dict', 0, 'icon-gold', '', 2, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000032, 1067246875800000031, '', 1, '', 'sys:dict:view', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000033, 1067246875800000031, '', 1, '', 'sys:dict:save', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000034, 1067246875800000031, '', 1, '', 'sys:dict:update', 2, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000035, 1067246875800000031, '', 1, '', 'sys:dict:delete', 3, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000036, 0, '', 0, 'icon-container', '', 4, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000037, 1067246875800000036, 'sys/log-login', 0, 'icon-filedone', '', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000038, 1067246875800000036, 'sys/log-operation', 0, 'icon-solution', '', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000039, 1067246875800000036, 'sys/log-error', 0, 'icon-file-exception', '', 2, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000040, 0, '', 0, 'icon-desktop', '', 5, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000041, 1067246875800000040, '{{ window.SITE_CONFIG["apiURL"] }}/monitor', 0, 'icon-medicinebox', '', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000042, 1067246875800000040, '{{ window.SITE_CONFIG["apiURL"] }}/swagger-ui.html', 0, 'icon-file-word', '', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());

INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000072, 1067246875800000034, '修改', '/sys/dict/{id}', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000073, 1067246875800000017, '修改', '/sys/menu/select', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000074, 'nav', '导航', '/sys/menu/nav', 'GET', 1, 1, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000075, 1067246875800000037, '登录日志', '/sys/log/login/**', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000076, 1067246875800000005, '新增', '/sys/role/list', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000077, 1067246875800000035, '删除', '/sys/dict', 'DELETE', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000078, 1067246875800000026, '查看', '/sys/params/page', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000079, 1067246875800000005, '新增', '/sys/dept/list', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000080, 1067246875800000028, '修改', '/sys/params/{id}', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000081, 1067246875800000018, '删除', '/sys/role', 'DELETE', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000082, 'user_info', '登录账号信息', '/sys/user/info', 'GET', 1, 1, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000083, 1067246875800000022, '新增', '/sys/menu', 'POST', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000084, 1067246875800000030, '导出', '/sys/params/export', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000085, 1067246875800000007, '删除', '/sys/user', 'DELETE', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000086, 1067246875800000034, '修改', '/sys/dict', 'PUT', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000087, 'permissions', '权限', '/sys/menu/permissions', 'GET', 1, 1, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000088, 1067246875800000004, '查看', '/sys/user/page', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000089, 1067246875800000029, '删除', '/sys/params', 'DELETE', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000090, 1067246875800000006, '修改', '/sys/user', 'PUT', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000091, 1067246875800000011, '新增', '/sys/dept', 'POST', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000092, 1067246875800000016, '新增', '/sys/dept/list', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000093, 1067246875800000016, '新增', '/sys/menu/select', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000094, 1067246875800000012, '修改', '/sys/dept', 'PUT', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000095, 1067246875800000023, '修改', '/sys/menu', 'PUT', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000096, 1067246875800000032, '查看', '/sys/dict/list', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000097, 1067246875800000010, '查看', '/sys/dept/list', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000098, 1067246875800000006, '修改', '/sys/user/{id}', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000099, 1067246875800000032, '查看', '/sys/dict/page', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000100, 1067246875800000005, '新增', '/sys/user', 'POST', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000101, 1067246875800000017, '修改', '/sys/dept/list', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000102, 1067246875800000017, '修改', '/sys/role/{id}', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000103, 1067246875800000016, '新增', '/sys/role', 'POST', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000104, 1067246875800000006, '修改', '/sys/dept/list', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000105, 1067246875800000023, '修改', '/sys/menu/{id}', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000106, 1067246875800000017, '修改', '/sys/role', 'PUT', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000107, 1067246875800000021, '查看', '/sys/menu/list', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000108, 1067246875800000013, '删除', '/sys/dept/{id}', 'DELETE', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000109, 1067246875800000028, '修改', '/sys/params/update', 'PUT', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000110, 1067246875800000033, '新增', '/sys/dict', 'POST', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000111, 1067246875800000038, '操作日志', '/sys/log/operation/**', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000112, 1067246875800000008, '导出', '/sys/user/export', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000113, 1067246875800000015, '查看', '/sys/role/page', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000114, 1067246875800000024, '删除', '/sys/menu/{id}', 'DELETE', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000115, 1067246875800000006, '修改', '/sys/role/list', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000116, 1067246875800000012, '修改', '/sys/dept/{id}', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000117, 1067246875800000039, '异常日志', '/sys/log/error/**', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000118, 1067246875800000027, '新增', '/sys/params', 'POST', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());

INSERT INTO sys_dept(id, pid, pids, name, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000061, 1067246875800000062, '1067246875800000065,1067246875800000062', '技术部', 2, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000062, 1067246875800000065, '1067246875800000065', '长沙分公司', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000063, 1067246875800000065, '1067246875800000065', '上海分公司', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000064, 1067246875800000063, '1067246875800000065,1067246875800000063', '市场部', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000065, 0, '0', '人人开源集团', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000066, 1067246875800000063, '1067246875800000065,1067246875800000063', '销售部', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_dept(id, pid, pids, name, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000067, 1067246875800000062, '1067246875800000065,1067246875800000062', '产品部', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
