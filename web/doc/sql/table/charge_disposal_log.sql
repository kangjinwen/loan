SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for charge_disposal_log
-- ----------------------------
CREATE TABLE `charge_disposal_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creator` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程实例ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='押品处置记录';
