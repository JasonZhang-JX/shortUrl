DROP TABLE
    IF
        EXISTS `short_url_lists`;
CREATE TABLE `short_url_lists` (
   `id` INT ( 0 ) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `qr_code_url` VARCHAR ( 255 ) NOT NULL COMMENT '二维码对应的url',
   `qr_code_short_url` VARCHAR ( 255 )  COMMENT '二维码对应的url(短链)预留字段 目前使用的是根据id进行64进制转换',
   `create_time` TIMESTAMP ( 0 ) NOT NULL DEFAULT CURRENT_TIMESTAMP ( 0 ) COMMENT '创建时间',
   `update_time` TIMESTAMP ( 0 ) NOT NULL DEFAULT CURRENT_TIMESTAMP ( 0 ) COMMENT '更新时间',
   PRIMARY KEY ( `id` ) USING BTREE,
   UNIQUE INDEX `udx_short_url_lists_qg`(`qr_code_url`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER
    SET = utf8mb4 COMMENT = '二维码短链生成表' ROW_FORMAT = Dynamic;