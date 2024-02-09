/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : db_2024

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 16/11/2023 18:50:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '9bb9670d6329be2a5c0c1a70750f1c30', 12, '男', '湖南长沙', 'sixan9986@gamil.com', '13226152046', '1696463659912');
INSERT INTO `admin` VALUES (2, 'admin1', '5067fd79fc1227221dd2c507c6a34e72', 22, '男', '湖南长沙', 'sixan9986@gamil.com', '13226152046', '1695808769030');
INSERT INTO `admin` VALUES (3, 'admin2', '3bd1078edfe8ca7ef39dd9a9645b13aa', 22, '男', '湖南长沙', 'sixan9986@gamil.com', '13226152046', '1695809289362');
INSERT INTO `admin` VALUES (4, 'admin3', '5067fd79fc1227221dd2c507c6a34e72', 22, '男', '湖南长沙', 'sixan9986@gamil.com', '13226152046', '1695810167066');
INSERT INTO `admin` VALUES (5, 'aaa', '5067fd79fc1227221dd2c507c6a34e72', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin` VALUES (6, 'test', '5067fd79fc1227221dd2c507c6a34e72', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for audit
-- ----------------------------
DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '请假原由',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '请假日期',
  `day` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '请假天数',
  `userId` int(11) NULL DEFAULT NULL COMMENT '请假用户ID',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核状态',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '请假审核表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of audit
-- ----------------------------
INSERT INTO `audit` VALUES (18, '探亲', '2023-10-18', '12', 1, '审核通过', '好的');

-- ----------------------------
-- Table structure for hotel
-- ----------------------------
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '酒店名称',
  `price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '入住价格',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '酒店图片',
  `num` int(10) NULL DEFAULT NULL COMMENT '剩余间数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '酒店信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hotel
-- ----------------------------
INSERT INTO `hotel` VALUES (10, '哈哈哈', '123', '1697012256797', 3);
INSERT INTO `hotel` VALUES (11, '不是', '123', '1697012153026', 6);
INSERT INTO `hotel` VALUES (12, '如家酒店', '123', '1698096702502', 20);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作时间',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作人',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作人IP',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 607 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (574, '条件查询系统公告', '2023-10-24 05:32:06', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (575, '获取系统top3公告记录', '2023-10-24 05:32:07', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (576, '获取所有用户角色', '2023-10-24 05:32:22', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (577, '条件查询角色信息', '2023-10-24 05:32:22', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (578, '条件查询系统公告', '2023-10-24 05:32:27', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (579, '获取系统top3公告记录', '2023-10-24 05:32:29', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (580, '获取系统top3公告记录', '2023-10-24 05:33:57', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (581, '获取系统top3公告记录', '2023-10-24 05:35:23', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (582, '获取所有用户角色', '2023-10-24 05:35:34', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (583, '获取所有用户角色', '2023-10-24 05:37:49', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (584, '获取系统top3公告记录', '2023-10-24 05:37:50', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (585, '进行登录', '2023-10-24 10:12:04', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (586, '获取系统top3公告记录', '2023-10-24 10:12:05', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (587, '条件查询系统公告', '2023-10-24 10:12:48', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (588, '获取系统top3公告记录', '2023-10-24 10:12:54', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (589, '获取所有用户角色', '2023-10-24 10:12:59', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (590, '条件查询角色信息', '2023-10-24 10:13:27', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (591, '获取系统top3公告记录', '2023-10-24 10:16:03', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (592, '获取系统top3公告记录', '2023-10-24 10:50:00', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (593, '进行登录', '2023-10-24 11:22:35', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (594, '获取系统top3公告记录', '2023-10-24 11:22:35', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (595, '获取所有用户角色', '2023-10-24 11:23:51', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (596, '条件查询角色信息', '2023-10-24 11:23:58', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (597, '获取系统top3公告记录', '2023-10-24 11:24:01', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (598, '条件查询系统公告', '2023-10-24 11:24:02', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (599, '获取系统top3公告记录', '2023-10-24 11:24:03', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (600, '进行登录', '2023-11-16 18:42:15', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (601, '获取系统top3公告记录', '2023-11-16 18:42:17', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (602, '条件查询系统公告', '2023-11-16 18:42:20', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (603, '获取所有用户角色', '2023-11-16 18:42:23', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (604, '条件查询角色信息', '2023-11-16 18:42:23', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (605, '条件查询角色信息', '2023-11-16 18:42:34', 'admin', '0:0:0:0:0:0:0:1');
INSERT INTO `log` VALUES (606, '获取所有用户角色', '2023-11-16 18:42:35', 'admin', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '公告内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公告时间',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '是否公开',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (53, '📢 系统维护通知 📢', '亲爱的用户：\n\n为了给大家提供更好的服务，我们的系统将于2023年10月7日 22:00 - 23:00进行暂时维护。在此期间，您可能无法正常使用我们的服务。给您带来的不便，我们深感抱歉，并感谢您的理解和支持！\n\n祝您生活愉快！\n\n系统团队 敬上\n', '2023-10-06 09:38:24', 1);
INSERT INTO `notice` VALUES (54, '🎉 新功能发布 🎉', '\n亲爱的用户：\n\n我们很高兴地宣布，新的图片编辑功能现已上线！现在，您可以在上传图片后进行基础的裁剪、旋转和调整操作。\n\n感谢您的持续支持，我们将继续努力为您提供更优质的服务。\n\n祝您使用愉快！\n\n产品团队 敬上\n', '2023-10-17 05:57:17', 1);
INSERT INTO `notice` VALUES (55, '🚨 重要安全通知 🚨', '亲爱的用户：\n\n我们最近发现并修复了一个系统安全漏洞，该漏洞可能会影响到部分用户的账户安全。为了确保您的账户安全，请您尽快登录并更改密码。\n\n我们深感抱歉给您带来的不便，并已采取必要的措施确保此类事件不再发生。\n\n如果您在更改密码或登录过程中遇到任何问题，请联系我们的支持团队。\n\n保护您的信息安全是我们的首要任务，感谢您的理解和支持。\n\n安全团队 敬上\n', '2023-10-09 13:28:34', 1);
INSERT INTO `notice` VALUES (56, '🎈 五一特惠活动 🎈', '亲爱的用户：\n\n五一劳动节即将到来，为答谢大家的持续支持，从4月28日至5月2日，我们的所有服务享受8折优惠！\n\n别错过这次大促，祝您购物愉快！\n\n市场团队 敬上\n\n', '2023-10-11 16:21:21', 1);

-- ----------------------------
-- Table structure for reserve
-- ----------------------------
DROP TABLE IF EXISTS `reserve`;
CREATE TABLE `reserve`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hotelId` int(10) NULL DEFAULT NULL COMMENT '酒店ID',
  `userId` int(10) NULL DEFAULT NULL COMMENT '用户ID',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '预订时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预订信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reserve
-- ----------------------------
INSERT INTO `reserve` VALUES (1, 10, 1, '2023-10-24 05:30:32');
INSERT INTO `reserve` VALUES (2, 10, 1, '2023-10-24 05:30:32');
INSERT INTO `reserve` VALUES (3, 11, 1, '2023-10-24 05:30:33');
INSERT INTO `reserve` VALUES (4, 11, 1, '2023-10-24 05:30:33');
INSERT INTO `reserve` VALUES (5, 12, 1, '2023-10-24 05:31:54');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '教师');
INSERT INTO `type` VALUES (2, '学生');
INSERT INTO `type` VALUES (3, '员工');
INSERT INTO `type` VALUES (4, '待选择');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `introduce` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '个人简介',
  `typeId` int(10) NULL DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1023 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (961, '常云熙', '6QHTUaA0Xv', 'yunxi723', '10-3014-5616', 'yuchang@gmail.com', '中国北京市延庆区028县道876号35号楼', '1696542341850', '', 4);
INSERT INTO `user` VALUES (965, '武詩涵', '7yocfuUaiJ', 'wushihan', '147-2803-5430', 'shihanwu@gmail.com', '中国北京市東城区東直門內大街966号37楼', '1696542349546', '', 2);
INSERT INTO `user` VALUES (973, '顾云熙', 'etmsvTCv4w', 'gyun04', '158-4480-8978', 'yunxgu@gmail.com', '中国广州市海珠区江南西路216号3栋', '1696542314083', '', 3);
INSERT INTO `user` VALUES (1012, 'SixAn', NULL, NULL, '132-2615-2046', '2634598683@qq.com', '广东深圳', '1696656649177', '<h1 id=\"hr8mn\">个人简介</h1><p>你好，我是一名Java开发工程师<span style=\"font-size: 14px;\">🤑</span></p><p><br/></p><p>我来自广东省揭阳市普宁市赤岗镇</p><h2 id=\"xk5ls\">图片</h2><h2 id=\"xk5ls\"><u></u><img src=\"http://localhost:9090/api/files/1696441781651\" data-href=\"http%3A%2F%2Flocalhost%3A9090%2Fapi%2Ffiles%2F1696441781651\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\" width=\"30%\"/></h2><p>这是我的头像，是一个阳光男孩~<span style=\"font-size: 14px;\">🤗</span></p><h2><br/></h2><h2>代码块</h2><pre><code class=\"Java\"><span class=\"hljs-keyword\">package</span> com.example.springboot.entity;\n\n<span class=\"hljs-keyword\">import</span> cn.hutool.core.<span class=\"hljs-keyword\">annotation</span>.Alias;\n<span class=\"hljs-keyword\">import</span> lombok.Data;\n\n<span class=\"hljs-comment\">/**\n * 功能：\n * 作者：SixAn\n * 日期：2023-09-20 22:50\n */</span>\n<span class=\"hljs-meta\">@Data</span>\n<span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">class</span> <span class=\"hljs-title class_\">User</span> {\n    <span class=\"hljs-keyword\">private</span> Integer id;\n    <span class=\"hljs-meta\">@Alias(<span class=\"hljs-string\">\"姓名\"</span>)</span>\n    <span class=\"hljs-keyword\">private</span> String username;\n    <span class=\"hljs-keyword\">private</span> String password;\n    <span class=\"hljs-keyword\">private</span> String name;\n    <span class=\"hljs-meta\">@Alias(<span class=\"hljs-string\">\"电话\"</span>)</span>\n    <span class=\"hljs-keyword\">private</span> String phone;\n    <span class=\"hljs-meta\">@Alias(<span class=\"hljs-string\">\"邮箱\"</span>)</span>\n    <span class=\"hljs-keyword\">private</span> String email;\n    <span class=\"hljs-meta\">@Alias(<span class=\"hljs-string\">\"地址\"</span>)</span>\n    <span class=\"hljs-keyword\">private</span> String address;\n    <span class=\"hljs-keyword\">private</span> String avatar;\n    <span class=\"hljs-keyword\">private</span> String introduce;\n}</code></pre><h2 id=\"z2zrl\">上传视频测试</h2><p><video src=\"http://localhost:9090/api/files/1696453565119\" controls=\"controls\" style=\"max-width:100%\" __idm_id__=\"1458179\" draggable=\"true\" width=\"50%\"></video></p>', 1);
INSERT INTO `user` VALUES (1021, '测试', NULL, NULL, '132-4839-2729', '21222121@qq.com', '湖南长沙', '1696848408486', '<p>🖖<br/></p>', 2);
INSERT INTO `user` VALUES (1022, '张三', NULL, NULL, NULL, NULL, '广东省', '1700131384646', '<p>😄<img src=\"http://localhost:9090/api/files/1700131417968\" data-href=\"http%3A%2F%2Flocalhost%3A9090%2Fapi%2Ffiles%2F1700131417968\" contenteditable=\"false\" style=\"font-size: 14px; text-align: initial; max-width: 100%;\"/></p><video src=\"http://localhost:9090/api/files/1700131432045\" controls=\"controls\" style=\"max-width:100%\" __idm_id__=\"409602\"></video>', 2);

SET FOREIGN_KEY_CHECKS = 1;
