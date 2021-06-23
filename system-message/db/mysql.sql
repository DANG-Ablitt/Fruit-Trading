-- 短信
CREATE TABLE sys_sms (
  id bigint NOT NULL COMMENT 'id',
  platform tinyint unsigned NOT NULL COMMENT '平台类型',
  mobile varchar(20) COMMENT '手机号',
  params_1 varchar(50) COMMENT '参数1',
  params_2 varchar(50) COMMENT '参数2',
  params_3 varchar(50) COMMENT '参数3',
  params_4 varchar(50) COMMENT '参数4',
  status tinyint unsigned COMMENT '发送状态  0：失败  1：成功',
  creator bigint COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  PRIMARY KEY (id),
  key idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='短信';


-- 邮件模板
CREATE TABLE sys_mail_template (
  id bigint NOT NULL COMMENT 'id',
  name varchar(100) COMMENT '模板名称',
  subject varchar(200) COMMENT '邮件主题',
  content text COMMENT '邮件正文',
  creator bigint COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  updater bigint COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  PRIMARY KEY (id),
  key idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='邮件模板';


-- 邮件发送记录
CREATE TABLE sys_mail_log (
  id bigint NOT NULL COMMENT 'id',
  template_id bigint NOT NULL COMMENT '邮件模板ID',
  mail_from varchar(200) COMMENT '发送者',
  mail_to varchar(400) COMMENT '收件人',
  mail_cc varchar(400) COMMENT '抄送者',
  subject varchar(200) COMMENT '邮件主题',
  content text COMMENT '邮件正文',
  status tinyint unsigned COMMENT '发送状态  0：失败  1：成功',
  creator bigint COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  PRIMARY KEY (id),
  key idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COMMENT='邮件发送记录';

INSERT INTO sys_params(id, param_code, param_value, param_type, remark, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000145, 'MAIL_CONFIG_KEY', '{"smtp":"smtp.163.com","port":25,"username":"renrenio_test@163.com","password":"renren123456"}', 0, '邮件配置信息', 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_params(id, param_code, param_value, param_type, remark, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000146, 'SMS_CONFIG_KEY', '{"platform":1,"aliyunAccessKeyId":"1","aliyunAccessKeySecret":"1","aliyunSignName":"1","aliyunTemplateCode":"1","qcloudAppId":2,"qcloudAppKey":"2","qcloudSignName":"2","qcloudTemplateId":"2"}', 0, '短信配置信息', 0, 1067246875800000001, now(), 1067246875800000001, now());

INSERT INTO sys_mail_template(id, name, subject, content, create_date) VALUES (1067246875800000147, '验证码模板', '人人开源注册验证码', '<p>人人开源注册验证码：${code}</p>', now());


INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000056, 0, '', 0, 'icon-message', '', 3, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000057, 1067246875800000056, 'message/sms', 0, 'icon-layout', '', 0, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000058, 1067246875800000056, 'message/mail-template', 0, 'icon-appstore', '', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_menu(id, pid, url, type, icon, permissions, sort, del_flag, creator, create_date, updater, update_date) VALUES (1067246875800000059, 1067246875800000056, 'message/mail-log', 0, 'icon-save', '', 2, 0, 1067246875800000001, now(), 1067246875800000001, now());

INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000056, 'name', 'Message Management', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000056, 'name', '消息管理', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000056, 'name', '消息管理', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000057, 'name', 'SMS Service', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000057, 'name', '短信服务', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000057, 'name', '短信服務', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000058, 'name', 'Mail Template', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000058, 'name', '邮件模板', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000058, 'name', '郵件模板', 'zh-TW');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000059, 'name', 'Mail Log', 'en-US');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000059, 'name', '邮件发送记录', 'zh-CN');
INSERT INTO sys_language(table_name, table_id, field_name, field_value, language) VALUES ('sys_menu', 1067246875800000059, 'name', '郵件發送記錄', 'zh-TW ');

INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000148, 1067246875800000057, ' 短信服务', '/message/sms/send', 'POST', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000149, 1067246875800000057, ' 短信服务', '/message/sms', 'DELETE', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000150, 1067246875800000057, ' 短信服务', '/message/sms/config', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000151, 1067246875800000057, ' 短信服务', '/message/sms/page', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000152, 1067246875800000057, ' 短信服务', '/message/sms/saveConfig', 'POST', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000153, 1067246875800000058, ' 邮件模板', '/message/mailtemplate/page', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000154, 1067246875800000058, ' 邮件模板', '/message/mailtemplate', 'DELETE', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000155, 1067246875800000058, ' 邮件模板', '/message/mailtemplate/saveConfig', 'POST', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000156, 1067246875800000058, ' 邮件模板', '/message/mailtemplate', 'POST', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000157, 1067246875800000058, ' 邮件模板', '/message/mailtemplate/{id}', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000158, 1067246875800000058, ' 邮件模板', '/message/mailtemplate', 'PUT', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000159, 1067246875800000058, ' 邮件模板', '/message/mailtemplate/config', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000160, 1067246875800000058, ' 邮件模板', '/message/mailtemplate/send', 'POST', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000161, 1067246875800000059, ' 邮件发送记录', '/message/maillog', 'DELETE', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
INSERT INTO sys_resource(id, resource_code, resource_name, resource_url, resource_method, menu_flag, auth_level, creator, create_date, updater, update_date) VALUES (1067246875800000162, 1067246875800000059, ' 邮件发送记录', '/message/maillog/page', 'GET', 1, 0, 1067246875800000001, now(), 1067246875800000001, now());
