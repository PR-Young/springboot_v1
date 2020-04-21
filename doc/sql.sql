/*
Navicat MySQL Data Transfer

Source Server         : springbootV1
Source Server Version : 80019
Source Host           : 127.0.0.1:3306
Source Database       : springbootv1

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-04-21 14:15:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(190) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('AresScheduler', 'TASK_CLASS_NAME_test_TRIGGER', 'DEFAULT_TRIGGER_GROUP', '0 */1 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(190) DEFAULT NULL,
  `JOB_GROUP` varchar(190) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('AresScheduler', 'TASK_CLASS_NAME_test', 'DEFAULT', null, 'com.system.springbootv1.common.quartz.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372002A636F6D2E73797374656D2E737072696E67626F6F7476312E6D6F64656C2E53797351756172747A4A6F6200000000000000010200064C000A636F6E43757272656E747400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00076A6F624E616D6571007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7872002E636F6D2E73797374656D2E737072696E67626F6F7476312E636F6D6D6F6E2E6D6F64656C2E426173654D6F64656C20FC9979122DA2D00200034C0002496471007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000A6D6F6469667954696D6571007E000C78707400123338323338323533383834363530373030387372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000016FEFF386C0787371007E000F7708000001717CAD2FE8787400013174000D30202A2F31202A202A202A203F740039636F6D2E73797374656D2E737072696E67626F6F7476312E636F6D6D6F6E2E71756172747A2E6A6F62732E546573744A6F622E74657374282974000744454641554C5474000474657374737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000007800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('AresScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('AresScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('AresScheduler', 'LAPTOP-M2PJI2L61587448537344', '1587449065019', '10000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint DEFAULT NULL,
  `PREV_FIRE_TIME` bigint DEFAULT NULL,
  `PRIORITY` int DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) DEFAULT NULL,
  `MISFIRE_INSTR` smallint DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('AresScheduler', 'TASK_CLASS_NAME_test_TRIGGER', 'DEFAULT_TRIGGER_GROUP', 'TASK_CLASS_NAME_test', 'DEFAULT', null, '1587449100000', '1587449040000', '5', 'WAITING', 'CRON', '1587448547000', '0', null, '0', '');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `Id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `HostIP` varchar(255) DEFAULT NULL,
  `UserName` varchar(255) DEFAULT NULL,
  `Url` varchar(255) DEFAULT NULL,
  `Notes` text,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `Id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限名称',
  `Description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限描述',
  `Url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权链接',
  `IsBlank` int DEFAULT '0' COMMENT '是否跳转 0 不跳转 1跳转',
  `PId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父节点id',
  `Icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单图标',
  `Order` int DEFAULT NULL COMMENT '排序',
  `Visible` int DEFAULT NULL COMMENT '是否可见',
  `Perms` varchar(255) DEFAULT NULL,
  `Type` int DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '系统管理', '#', '0', '0', 'fa fa-gear', '1', '0', 'sys', '0', '2020-01-23 18:17:06', null);
INSERT INTO `sys_menu` VALUES ('2', '菜单管理', '菜单管理', '/sys/menuIndex', '0', '1', 'fa fa-list-ul', '1', '0', 'menu:list', '1', '2020-01-23 18:17:39', null);
INSERT INTO `sys_menu` VALUES ('3', '用户管理', '用户管理', '/sys/userIndex', '0', '1', 'fa fa-user', '2', '0', 'user:list', '1', '2020-01-24 15:27:25', null);
INSERT INTO `sys_menu` VALUES ('381295505541566464', '新增/修改', '新增/修改', '#', null, '2', '', '1', '1', 'menu:edit', '2', '2020-01-26 06:19:03', '2020-01-29 05:41:40');
INSERT INTO `sys_menu` VALUES ('381296514569474048', '定时任务', '定时任务', '/sys/jobIndex', null, '1', 'fa fa-calendar-o', '4', '0', 'quartz:list', '1', '2020-01-26 06:23:03', '2020-01-29 06:07:30');
INSERT INTO `sys_menu` VALUES ('382382361008017408', '新增/修改', '新增/修改', '', null, '381296514569474048', '', '1', '1', 'quartz:edit', '2', '2020-01-29 06:17:49', null);
INSERT INTO `sys_menu` VALUES ('382736944293089280', '任务日志', '任务日志', '/sys/jobLogIndex', null, '1', 'fa fa-calendar', '5', '0', 'quartz:logList', '1', '2020-01-30 05:46:49', '2020-01-30 06:35:20');
INSERT INTO `sys_menu` VALUES ('382743905826902016', '页面示例', '页面示例', '#', null, '1', 'fa fa-bars', '10', '0', '#', '1', '2020-01-30 06:14:28', '2020-01-30 06:34:41');
INSERT INTO `sys_menu` VALUES ('382744141844582400', '字体图标', '字体图标', '/sys/fontawesome', null, '382743905826902016', 'fa fa-font', '1', '0', '#', '1', '2020-01-30 06:15:25', '2020-01-30 06:36:53');
INSERT INTO `sys_menu` VALUES ('383822600511557632', '删除', '删除', '#', null, '2', '', '2', '1', 'menu:delete', '2', '2020-02-02 05:40:49', null);
INSERT INTO `sys_menu` VALUES ('383822867416092672', '新增/修改', '新增/修改', '#', null, '3', '', '1', '1', 'user:edit', '2', '2020-02-02 05:41:53', null);
INSERT INTO `sys_menu` VALUES ('383822981555687424', '删除', '删除', '#', null, '3', '', '2', '1', 'user:delete', '2', '2020-02-02 05:42:20', '2020-02-02 05:42:27');
INSERT INTO `sys_menu` VALUES ('383823166725820416', '新增/修改', '新增/修改', '#', null, '4', '', '1', '1', 'role:edit', '2', '2020-02-02 05:43:04', null);
INSERT INTO `sys_menu` VALUES ('383823257045962752', '删除', '删除', '#', null, '4', '', '2', '1', 'role:delete', '2', '2020-02-02 05:43:26', null);
INSERT INTO `sys_menu` VALUES ('383823416903471104', '删除', '删除', '#', null, '381296514569474048', '', '2', '1', 'quartz:delete', '2', '2020-02-02 05:44:04', null);
INSERT INTO `sys_menu` VALUES ('383838051287306240', '通用工具', '通用工具', '#', null, '0', 'fa fa-wrench', '2', '0', '#', '1', '2020-02-02 06:42:13', '2020-02-02 06:46:04');
INSERT INTO `sys_menu` VALUES ('383839399340806144', 'python编辑', 'python编辑', '/tool/python', null, '383838051287306240', 'fa fa-link', '1', '0', '#', '1', '2020-02-02 06:47:34', null);
INSERT INTO `sys_menu` VALUES ('392513990858772480', '消息测试', '消息测试', '/sys/message', null, '383838051287306240', 'fa fa-bell-o', '2', '0', '#', '1', '2020-02-26 05:17:18', '2020-02-26 05:18:28');
INSERT INTO `sys_menu` VALUES ('4', '角色管理', '角色管理', '/sys/roleIndex', '0', '1', 'fa fa-users', '3', '0', 'role:list', '1', '2020-01-24 15:27:27', null);
INSERT INTO `sys_menu` VALUES ('405938773428408320', '服务器信息类', '服务器信息类', '/sys/service', null, '383838051287306240', 'fa fa-server', '3', '0', '#', '1', '2020-04-03 06:22:36', '2020-04-03 06:25:05');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `Id` varchar(255) NOT NULL,
  `RoleId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `MenuId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('383826456633020416', '381303138021412864', '1');
INSERT INTO `sys_permission` VALUES ('383826457195057152', '381303138021412864', '382743905826902016');
INSERT INTO `sys_permission` VALUES ('383826457761288192', '381303138021412864', '382744141844582400');

-- ----------------------------
-- Table structure for sys_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job`;
CREATE TABLE `sys_quartz_job` (
  `Id` varchar(255) NOT NULL COMMENT '日志id',
  `JobName` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `JobGroup` varchar(255) DEFAULT NULL COMMENT '任务组名',
  `Description` varchar(255) DEFAULT NULL COMMENT '描述',
  `InvokeTarget` varchar(255) DEFAULT NULL COMMENT '调用目标字符串',
  `CronExpression` varchar(255) DEFAULT NULL COMMENT 'cron执行表达式',
  `ConCurrent` varchar(255) DEFAULT NULL COMMENT '是否并发执行（0允许 1禁止）',
  `Status` int DEFAULT NULL COMMENT '任务状态（0正常 1暂停）',
  `CreateTime` datetime DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';

-- ----------------------------
-- Records of sys_quartz_job
-- ----------------------------
INSERT INTO `sys_quartz_job` VALUES ('382382538846507008', 'test', 'DEFAULT', '测试', 'com.system.springbootv1.common.quartz.jobs.TestJob.test()', '0 */1 * * * ?', '1', '0', '2020-01-29 06:18:32', '2020-04-15 07:10:57');

-- ----------------------------
-- Table structure for sys_quartz_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job_log`;
CREATE TABLE `sys_quartz_job_log` (
  `Id` varchar(255) NOT NULL COMMENT '主键',
  `JobName` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `JobGroup` varchar(255) DEFAULT NULL COMMENT '任务组名',
  `InvokeTarget` varchar(255) DEFAULT NULL COMMENT '调用目标字符串',
  `JobMessage` varchar(255) DEFAULT NULL COMMENT '日志信息',
  `Status` int DEFAULT NULL COMMENT '执行状态（0正常 1失败）',
  `ExceptionInfo` varchar(255) DEFAULT NULL COMMENT '异常信息',
  `StartTime` datetime DEFAULT NULL COMMENT '开始时间',
  `EndTime` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';

-- ----------------------------
-- Records of sys_quartz_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `Id` varchar(255) NOT NULL,
  `RoleName` varchar(255) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'gly', '超级管理员', '2020-01-25 09:09:51', '2020-01-26 06:49:34');
INSERT INTO `sys_role` VALUES ('381303138021412864', 'user', '用户', '2020-01-26 06:49:23', null);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `Id` varchar(255) NOT NULL,
  `UserId` varchar(255) DEFAULT NULL COMMENT '用户id',
  `RoleId` varchar(255) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色中间表';

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '1', '1');
INSERT INTO `sys_role_user` VALUES ('383826455240511488', '380942348554735616', '381303138021412864');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `Id` varchar(255) NOT NULL,
  `Account` varchar(255) DEFAULT NULL,
  `UserName` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '管理员', '21232f297a57a5a743894a0e4a801fc3', '2020-01-23 08:51:42', null);
INSERT INTO `sys_user` VALUES ('380942348554735616', 'user', '用户1', 'e10adc3949ba59abbe56e057f20f883e', '2020-01-25 06:55:44', '2020-01-25 07:01:23');
INSERT INTO `sys_user` VALUES ('383826362324094976', 'tttt', 'tttt', 'e10adc3949ba59abbe56e057f20f883e', '2020-02-02 05:55:46', null);

-- ----------------------------
-- Table structure for test_v
-- ----------------------------
DROP TABLE IF EXISTS `test_v`;
CREATE TABLE `test_v` (
  `Id` varchar(255) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `test_name` varchar(255) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test_v
-- ----------------------------
