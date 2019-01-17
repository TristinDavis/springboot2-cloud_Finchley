DROP TABLE t_user;

CREATE TABLE `t_user` (
  `user_id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT "" COMMENT '应用名称',
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


insert into t_user (user_id,name,created_at,updated_at) values (1,'name_1',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (2,'name_2',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (3,'name_3',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (4,'name_4',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (5,'name_5',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (6,'name_6',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (7,'name_7',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (8,'name_8',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (9,'name_9',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (10,'name_10',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (11,'name_11',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (12,'name_12',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (13,'name_13',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (14,'name_14',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (15,'name_15',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (16,'name_16',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (17,'name_17',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (18,'name_18',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (19,'name_19',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (20,'name_20',now(),now());
insert into t_user (user_id,name,created_at,updated_at) values (21,'name_21',now(),now());


## 另一个数据源
DROP TABLE t_read;

CREATE TABLE `t_read` (
  `user_id` bigint(20) NOT NULL,
  `age` integer  DEFAULT 99 COMMENT 'nianling ',
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into t_read (user_id,age,created_at,updated_at) values (1,12,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (2,22,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (3,33,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (4,44,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (5,55,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (6,66,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (7,77,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (8,88,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (9,99,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (10,112,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (11,23,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (12,98,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (13,40,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (14,41,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (15,42,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (16,47,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (17,21,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (18,22,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (19,27,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (20,28,now(),now());
insert into t_read (user_id,age,created_at,updated_at) values (21,29,now(),now());
