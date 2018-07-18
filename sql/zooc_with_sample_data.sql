/*
Navicat MySQL Data Transfer

Source Server         : Lab
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : zooc

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-07-18 15:12:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `administrator_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(16) COLLATE utf8mb4_general_ci NOT NULL,
  `type` enum('SYSTEM','ENTERPRISE') COLLATE utf8mb4_general_ci NOT NULL,
  `enterprise_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`administrator_id`),
  UNIQUE KEY `unique_administrator_pk` (`administrator_id`),
  UNIQUE KEY `unique_administrator_enterprise_id` (`enterprise_id`),
  CONSTRAINT `fk_administrator_enterprise_id` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('1', '1', '123', 'ENTERPRISE', '1');

-- ----------------------------
-- Table structure for branch
-- ----------------------------
DROP TABLE IF EXISTS `branch`;
CREATE TABLE `branch` (
  `branch_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) unsigned NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(127) COLLATE utf8mb4_general_ci NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `telephone` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `is_disabled` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`branch_id`),
  UNIQUE KEY `unique_branch_pk` (`branch_id`),
  KEY `fk_branch_enterprise_id` (`enterprise_id`) USING BTREE,
  CONSTRAINT `fk_branch_enterprise_id` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of branch
-- ----------------------------
INSERT INTO `branch` VALUES ('1', '1', '东软实训中心（浑南）', '东软睿道第一分部', '41.70361400', '123.43128900', '13084859683', '\0');
INSERT INTO `branch` VALUES ('2', '1', '东软第二分部', '沈阳市浑南区营盘北街5号兴隆大奥莱B座5楼(近奥体中心)', '41.74851750', '123.46253111', '18375928573', '\0');
INSERT INTO `branch` VALUES ('3', '1', '东软第三分部', '东软睿道', '41.70739500', '123.43822000', '12345678910', '\0');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) unsigned NOT NULL,
  `name` varchar(63) COLLATE utf8mb4_general_ci NOT NULL,
  `detail` text COLLATE utf8mb4_general_ci NOT NULL,
  `img_url` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `category_id` bigint(20) unsigned NOT NULL,
  `release_time` datetime NOT NULL,
  `price` decimal(10,2) unsigned NOT NULL,
  `status` enum('AVAILABLE','OFF','IN_REVIEW') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'AVAILABLE',
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `unique_course_pk` (`course_id`),
  KEY `idx_suit_available_course_by_category` (`status`,`category_id`) USING BTREE,
  KEY `fk_course_category_id` (`category_id`),
  KEY `fk_course_enterprise_id` (`enterprise_id`),
  CONSTRAINT `fk_course_category_id` FOREIGN KEY (`category_id`) REFERENCES `course_category` (`category_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_course_enterprise_id` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '1', 'Vue.js 源码全方位深入解析', '<p>本课程从基础的 Vue源码目录设计、源码构建开始讲起，包括数据驱动，响应式原理，让同学们深入全面理解Vue的实现原理，掌握源码分析技巧，牢固对Vue的使用，斩断BAT进阶拦路虎，快人一步进名企。</p>\n', 'https://img3.mukewang.com/szimg/5b17bad10001535305400300.jpg', '7', '2018-06-26 15:59:18', '488.00', 'AVAILABLE');
INSERT INTO `course` VALUES ('2', '1', 'React 全家桶 + AntD 共享单车后台管理系统开发', '在日常的开发中，我们可能会遇到项目工程化、地图开发、后台图表等各类问题。本项目针对此类问题设计，结合“共享单车”真实项目，开发高水准的通用后台管理系统。使用最流行的React全家桶技术，同时囊括了大部分常用前沿技术，从项目架构、项目工程化、基于React的UI框架、Map地图、Echarts图表等各个维度进行详细介绍。真正实现举一反三，助你吃透技术，独立处理各类项目问题。', 'https://img4.mukewang.com/szimg/5b28a9cf00017c9a05400300.jpg', '1', '2018-07-12 15:32:15', '388.00', 'AVAILABLE');
INSERT INTO `course` VALUES ('3', '1', 'TestNG 测试框架入门到实战', '关于TestNG的资料网上有许多，但并不系统和完善，本课程将给你最系统的TestNG知识，由浅入深，通过详细的基础知识讲解， 清晰的场景应用式案例实战，助你掌握这一火热的软测框架，并最终实现出自己的自动化测试框架', 'https://img1.mukewang.com/szimg/5b39eb5a00016c4205400300.jpg', '9', '2018-07-15 23:35:46', '199.00', 'AVAILABLE');
INSERT INTO `course` VALUES ('4', '1', 'SpringBoot 仿抖音短视频小程序开发 全栈式实战项目', '本课程基于微信小程序和目前主流的后端技术SpringBoot/SpringMvc来实现一个完整的短视频小程序App。通过对本套课程的学习，可以使你独立开发一个短视频小程序并部署到腾讯云上，掌握全栈式开发，更是毕业设计利器！', 'https://img2.mukewang.com/szimg/5afb8aa900014cc705400300.jpg', '1', '2018-07-15 23:38:07', '348.00', 'AVAILABLE');
INSERT INTO `course` VALUES ('5', '1', 'Google 资深工程师深度讲解 Go 语言', 'Go作为专门为并发和大数据设计的语言，在编程界占据越来越重要的地位！不论是c/c++，php，java，重构首选语言就是Go~本次课程特邀谷歌资深工程师，将Go语言使用经验总结归纳，从Go语言基本语法到函数式编程、并发编程，最后构建分布式爬虫系统，步步深入，带你快速掌握Go语言！', 'https://img2.mukewang.com/szimg/5a7127370001a8fa05400300.jpg', '10', '2018-07-15 23:41:55', '399.00', 'AVAILABLE');

-- ----------------------------
-- Table structure for course_category
-- ----------------------------
DROP TABLE IF EXISTS `course_category`;
CREATE TABLE `course_category` (
  `category_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `unique_course_category_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of course_category
-- ----------------------------
INSERT INTO `course_category` VALUES ('5', 'Android');
INSERT INTO `course_category` VALUES ('10', 'Go');
INSERT INTO `course_category` VALUES ('3', 'HTML');
INSERT INTO `course_category` VALUES ('1', 'Java');
INSERT INTO `course_category` VALUES ('4', 'Javascript');
INSERT INTO `course_category` VALUES ('2', 'Python');
INSERT INTO `course_category` VALUES ('8', 'Swift');
INSERT INTO `course_category` VALUES ('7', 'Vue.js');
INSERT INTO `course_category` VALUES ('6', '分布式系统');
INSERT INTO `course_category` VALUES ('9', '软件测试');

-- ----------------------------
-- Table structure for course_offering
-- ----------------------------
DROP TABLE IF EXISTS `course_offering`;
CREATE TABLE `course_offering` (
  `course_offering_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `course_id` bigint(20) unsigned NOT NULL,
  `branch_id` bigint(20) unsigned NOT NULL,
  `lecturer_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`course_offering_id`),
  KEY `fk_course_offering` (`course_id`),
  KEY `fk_course_offering_branch_id` (`branch_id`),
  KEY `fk_course_offering_lecturer_id` (`lecturer_id`),
  CONSTRAINT `fk_course_offering` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_course_offering_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_course_offering_lecturer_id` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`lecturer_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of course_offering
-- ----------------------------
INSERT INTO `course_offering` VALUES ('1', '1', '1', '1');
INSERT INTO `course_offering` VALUES ('2', '1', '2', '2');
INSERT INTO `course_offering` VALUES ('3', '4', '2', '3');
INSERT INTO `course_offering` VALUES ('4', '4', '1', '3');
INSERT INTO `course_offering` VALUES ('5', '5', '1', '4');
INSERT INTO `course_offering` VALUES ('6', '5', '2', '4');
INSERT INTO `course_offering` VALUES ('7', '1', '2', '5');

-- ----------------------------
-- Table structure for enterprise
-- ----------------------------
DROP TABLE IF EXISTS `enterprise`;
CREATE TABLE `enterprise` (
  `enterprise_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `img_url` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `introduction` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `video_url` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `detail` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`enterprise_id`),
  UNIQUE KEY `unique_enterprise_pk` (`enterprise_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of enterprise
-- ----------------------------
INSERT INTO `enterprise` VALUES ('1', '东软睿道', 'https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=d2a5ac492934349b600b66d7a8837eab/ac345982b2b7d0a2fb7c49cecaef76094b369a28.jpg', '东软睿道教育信息技术有限公司（简称东软睿道）由东软创办，是东软基于20年来对IT产业实践的理解和对IT教育实践的洞察，整合国内外众多优秀合作伙伴的教育资源和产品，依托信息与通信技术，通过线上与线下服务模式的组合，基于互联网和云计算来实现交互式与实践式学习的教育与人才服务提供商。', 'http://www.w3school.com.cn/i/movie.ogg', '<p>东软睿道教育信息技术有限公司（简称东软睿道）由东软创办，是东软基于20年来对IT产业实践的理解和对IT教育实践的洞察，整合国内外众多优秀合作伙伴的教育资源和产品，依托信息与通信技术，通过线上与线下服务模式的组合，基于互联网和云计算来实现交互式与实践式学习的教育与人才服务提供商。 东软睿道倡导&ldquo;信息技术服务教育未来&rdquo;的经营理念，将科学的学习方法与先进的信息通信技术相结合，致力于成为中国领先的工程教育服务的提供者。目前，公司在沈阳、大连、南京、成都、无锡、天津、郑州、南昌、青岛、广州建立了10个分布式的人才基地，与全球500所高校、400家企业建立了持续稳定的深度合作。 东软睿道工程教育是大学生及大学后提高职业技能的平台。我们面向高校、个人、政府提供以东软知识体系为核心的人才培养解决方案；面向企业构建以&ldquo;高绩效金字塔&rdquo;为基础，以建立高绩效组织为目标的解决方案，并提供咨询、培训、人才服务及IT信息系统等产品和服务。 在产品领域，我们提供国内领先的学习管理系统Skillbase及依托东软20余年IT行业技术与经验积淀的数字内容；针对人才的评测与成长，我们提供基于云平台的睿云评测服务系统、睿鼎实训平台、睿博IT人才技能测评系统、分布式考试系统等系列产品。 东软睿道人才服务是专业的人力资源解决方案提供者，萃取东软专业化人力资源管理精髓，为中国软件行业提供高质量、规范化、专业化的人力资源服务。东软睿道人才服务总部设于沈阳，已在大连、北京、天津、青岛、南京、无锡、上海、武汉、成都、广州设有分支机构，提供近岸外包、人才猎聘、定制培养、招聘代理等服务形式。我们以覆盖全国的业务布局，优化的全国性IT类资源配比，快速准确地对应中国软件行业的人力资源服务需求，为成为中国最大最先进的IT人力资源供应者而不断完善努力。 东软睿道将高效、合作、负责、坚韧、诚信作为核心价值观，以改善教育绩效，降低教育成本，促进教育公平，服务于教育未来为己任，通过组织的持续改进，教育资源的不断整合，联盟与开放式创新，持续为社会、客户、股东和员工创造更大的价值。</p>\n');
INSERT INTO `enterprise` VALUES ('2', '中软', 'https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike220%2C5%2C5%2C220%2C73/sign=afa4b6e7982397ddc274905638ebd9d2/91ef76c6a7efce1b578a47caa651f3deb48f6509.jpg', '中软国际有限公司是国内大型综合性软件与信息服务企业，具有极高的市场感召力和客户忠诚度，以领先的技术、丰富的经验、精湛的服务在中国 IT 行业享有极高的声誉。', 'http://www.w3school.com.cn/i/movie.ogg', '主导产品\nResourceOne（简称R1）是基于构件及服务技术的开发、整合、项目工程化管理的支撑平台，是集支撑快速应用开发、应用整合及分发管理、业务流程控制及管理、信息集成交换及业务协同于一体的完整信息化支撑平台，于2008年发布的V4第四代创新产品线包括：　多年来，中软国际精准把握客户需求，凭借自主研发的应用整合和业务支撑中间件产品ResourceOne，帮助用户实现信息化工程建设全生命周期的最佳操控，并一向致力于实现企业级信息系统的业务应用创建支撑、集成、管理、运维服务及业务优化，并在制造业（烟草工业及整个行业）、零售业（烟草销售）、电子政务工程（多个国家金字号工程、政府机关、经济技术开发区）中都已有广泛的应用和大量成功案例。　ResourceOne平台产品在中软国际多年的行业经验和技术积累基础上，从工程建设角度提供了项目开发、整合、管理三位一体的配套支撑环境，保障和提升大型软件工程项目成功交付能力：\n一、R1提供IT整合支撑的环境　 　1、以R1构件和服务模型为核心，提供支撑R1构件及服务的基础架构；　 　2、以R1 DE-Integration为核心的R1交换集成平台，遵循面向服务的技术架构（SOA），提供更为丰富的企业级支撑能力，以及提供对整个企业IT系统服务消息处理调度中枢的能力；　 　3、R1 StarFlow业务流程管理平台，提供跨应用的业务服务协同和流程管理的能力，帮助客户实现业务流程的集成；　 　4、R1 Portal/Framework集成门户，基于构件技术，提供构件注册、装配功能和资产化管理能力，并提供各类Web应用系统的统一访问集成，构建具有高度可伸缩性的企业级应用集成门户　 　5、R1 Adapter Framework，提供统一的适配框架，帮助客户实现对异构、遗留系统的连接集成。\n二、R1提供快速开发业务应用的工具与支撑平台　1、ResourceOne 提供以构件方式快速实现应用封装与应用装配管理的工具，通过实现应用及服务松散耦合集成，帮助客户有效的管理、重组、更新应用和对新系统的再造；　2、R1 StarFlow结合R1Studio提供快速定制开发流程化业务应用的能力，业务应用中的数据和服务可与R1 DE-I及其他企业服务总线进行交互；　3、基于Eclipse的全生命周期集成化设计开发工具 R1 Studio，提供应用快速辅助开发及部署、R1工程资源管理、团队协作、集成场景的可视化设计编排、调试、仿真等功能。\n三、提供项目过程管理及质量控制环境　ResourceOne是中软国际基于平台进行项目开发与管理的基础，并且在Studio中内置集成了对R1工程建设方法的多项支持。\nResourceOne DE-Integration\nResourceOne DE-Integration（简称R1 DE-I）是ResourceOne家族中提供面向服务（SOA）技术支撑能力的基础设施，提供企业服务总线（ESB）所需的核心功能，同时完全兼容和保留传统的企业应用集成(EAI），拥有强大的企业级的信息交换、应用及服务集成能力，是实现企业面向服务(SOA）的服务调度、交换中枢和业务协同的重要产品。　R1 DE-I是集多种功能于一体的集成骨架，实现基于SOA的企业应用集成，连接器/适配器接入；拥有企业服务总线ESB的强大功能，支持各种通讯协议转换、消息格式转换以及服务中介调用，用于在异构服务与应用系统之间连接、调节和管理交互。\n1) 以信息交互、数据共享为切入点，构建应用系统间的集成中枢，整合IT环境中信息共享与管理；\n2) 扮演企业服务总线（ESB）的角色，提供IT环境中应用/服务间的服务 ，帮助用户构建面向服务（SOA）的IT架构；\n3) 灵活的伸缩性和适应性，可以实现跨地域的信息或服务交互；提供可靠的信息传输架构；帮助用户实现全国、甚至全球范围内的信息整合；\n4) 整合IT环境中应用间的相互交互关系，增强可扩展性、可管理性，增强IT环境对业务的适应性。　ResourceOne额外提供适配器框架产品ResourceOne AdapterFramework（简称 R1 AF），为R1适配器提供一个松散耦合、标准、稳定、易于扩展、具有良好可管理能力的运行环境，在R1 SOA架构中，实现应用系统间非侵入式交互、连接。\nResourceOne GlobalRepository\nResourceOne GlobalRepository（简称：R1 GLR）是ResourceOne SOA架构中存储和管理R1整合资源的元数据管理系统，提供了企业级的Web服务及其它资源注册、存储、发现、检索及调用等服务。提供对资源依赖关系的清晰可见，促进重用，并且提供了与第三方同类产品和R1Studio的集成能力。\n1) 通过使用各种管理控制、降低风险以及提高投资回报率的方法，使机构能够更轻松地转变到面向服务的架构；\n2) 通过对IT资产的持续控制、分析来确保SOA始终有效提供业务价值，为整个面向服务架构生命周期的治理提供了坚实基础；\n3) 通过展现IT资产的可视性统一视图来促进复用、消除冗余和优化使用，从而保护已有的IT投资的价值；\n4) 除Webservice之外，还提供更丰富的IT资产的管理，如R1适配器，并且可以通过标准的接口访问。\nResourceOne StarFlow\nResourceOne Starflow（简称：R1 StarFlow）是ResourceOne业务流程管理基础平台，可支持快速流程化应用开发与部署独立流程管理BPM服务器，以业务流程为核心，将参与流程活动的人员、信息、数据等实现整合管理，提高业务流程、组织及IT架构的灵活性和敏捷性，可与R1 DE-I或第三方ESB产品集成，提供完整的基于SOA的BPM解决方案。\n1) 提供全生命周期的业务流程管理；\n2) 提供快速构建流程化业务应用的能力；\n3) “流程即服务”轻松实现基于SOA的BMP解决方案；\n4) 完备的多层次的权限控制体系。\nResourceOne Portal/Framework\nResourceOne Portal/Framework（简称R1 Portal/Framework）通过整合人员、业务应用和服务，其采用多种先进技术带来前所未有的用户体验。\n1) “融合”的设计理念，融信息门户和应用集成门户于一体，快速集成不同资源，为用户提供差异化服务与体验；\n2) 融入中软国际多年行业项目经验，为用户提供即插即用的实用功能，帮助用户有效解决系统建设中的问题；\n3) 弹性灵活，能够根据实际业务场景和变化快速定制优化，紧跟业务步伐。\n软件开发\nResourceOne Studio（简称：R1 Studio）是ResourceOne的统一设计开发工具平台，提供对项目整个交付生命周期的全面支持，包括工程资源与Team协作统一管理，快速应用构件设计开发，中介服务、信息交换、业务流程、数据主题、映射的可视化设计，流程调试及仿真模拟，快速部署及多国语言支持等，为集成商、开发商提供了一个快速而强大的工具环境。\n1) 基于ResourceOne工程建设方法论，对项目的设计、开发、集成等各个阶段提供支持，帮助设计人员、应用开发人员、系统集成人员、质量管理人员等从不同层面对项目进行开发与管理；\n2) 结合中软国际项目管理办法（如QA规范制度），针对在项目开发过程中各阶段，提供项目Review管理，实现对Review结果管理、Review协作沟通，保障软件项目设计、开发质量；\n3) 提供流程模板复用、表单模板复用、代码段复用等复用机制，通过复用机制可以迅速开发设计应用组件，随着项目可重用资源不断提炼积累，能为新项目提供复用资源和快速解决方案，从而提高企业核心竞争力；\n4) 提供以脑图方式对原始需求进行功能分解的功能，能够形成功能分解图，根据功能分解图，基于R1工程建设方法论进行应用构件切分、功能设计、应用角色设计、菜单项设计；支持基于R1应用开发框架进行面向MVC模式的J2EE应用开发。');

-- ----------------------------
-- Table structure for lecturer
-- ----------------------------
DROP TABLE IF EXISTS `lecturer`;
CREATE TABLE `lecturer` (
  `lecturer_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) unsigned NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `photo_url` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `introduction` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `is_disabled` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`lecturer_id`),
  KEY `fk_lecturer_enterprise_id` (`enterprise_id`),
  CONSTRAINT `fk_lecturer_enterprise_id` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of lecturer
-- ----------------------------
INSERT INTO `lecturer` VALUES ('1', '1', '比尔·盖茨', 'https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=a5b20d7aed1190ef01fb95d9f620fa2b/4bed2e738bd4b31ca786492c8dd6277f9f2ff8eb.jpg', '一个在东软的老师', '\0');
INSERT INTO `lecturer` VALUES ('2', '1', '康愈圆', 'https://img.mukewang.com/user/5a0c5df20001a1cb05800580-100-100.jpg', '也是一个在东软的老师', '\0');
INSERT INTO `lecturer` VALUES ('3', '1', '风间影月', 'https://img.mukewang.com/user/5a0c5df20001a1cb05800580-100-100.jpg', 'imooc.com 后端架构师', '\0');
INSERT INTO `lecturer` VALUES ('4', '1', 'ccmouse', 'https://img.mukewang.com/user/598bcaf70001f13309600960-100-100.jpg', 'Google 高级软件工程师', '\0');
INSERT INTO `lecturer` VALUES ('5', '1', '陈子康', 'https://img.moegirl.org/common/e/e0/9694490.jpg', '陈子康牛逼', '\0');

-- ----------------------------
-- Table structure for moment
-- ----------------------------
DROP TABLE IF EXISTS `moment`;
CREATE TABLE `moment` (
  `moment_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) unsigned NOT NULL,
  `content` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`moment_id`),
  KEY `fk_moment_enterprise_id` (`enterprise_id`),
  CONSTRAINT `fk_moment_enterprise_id` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of moment
-- ----------------------------

-- ----------------------------
-- Table structure for moment_comment
-- ----------------------------
DROP TABLE IF EXISTS `moment_comment`;
CREATE TABLE `moment_comment` (
  `moment_comment_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `moment_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `content` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`moment_comment_id`),
  KEY `fk_moment_comment_moment_id` (`moment_id`),
  KEY `fk_moment_comment_user_id` (`user_id`),
  CONSTRAINT `fk_moment_comment_moment_id` FOREIGN KEY (`moment_id`) REFERENCES `moment` (`moment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_moment_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of moment_comment
-- ----------------------------

-- ----------------------------
-- Table structure for moment_img
-- ----------------------------
DROP TABLE IF EXISTS `moment_img`;
CREATE TABLE `moment_img` (
  `moment_img_index` tinyint(20) unsigned NOT NULL,
  `moment_id` bigint(20) unsigned NOT NULL,
  `img_url` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`moment_img_index`,`moment_id`),
  KEY `fk_moment_img_moment_id` (`moment_id`),
  CONSTRAINT `fk_moment_img_moment_id` FOREIGN KEY (`moment_id`) REFERENCES `moment` (`moment_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of moment_img
-- ----------------------------

-- ----------------------------
-- Table structure for moment_like
-- ----------------------------
DROP TABLE IF EXISTS `moment_like`;
CREATE TABLE `moment_like` (
  `moment_like_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `moment_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`moment_like_id`),
  KEY `fk_moment_like_moment_id` (`moment_id`),
  KEY `fk_moment_like_user_id` (`user_id`),
  CONSTRAINT `fk_moment_like_moment_id` FOREIGN KEY (`moment_id`) REFERENCES `moment` (`moment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_moment_like_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of moment_like
-- ----------------------------

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `course_id` bigint(20) unsigned NOT NULL,
  `time` datetime NOT NULL,
  `status` enum('PENDING','CANCELED','AVAILABLE','REFUND_REQUESTED','REFUNDED') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`order_id`),
  KEY `fk_order_user_id` (`user_id`),
  KEY `fk_order_course_id` (`course_id`),
  CONSTRAINT `fk_order_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('12', '1', '2', '2018-07-15 15:27:53', 'AVAILABLE');
INSERT INTO `order` VALUES ('14', '1', '1', '2018-07-16 11:12:25', 'REFUNDED');

-- ----------------------------
-- Table structure for refund
-- ----------------------------
DROP TABLE IF EXISTS `refund`;
CREATE TABLE `refund` (
  `refund_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) unsigned NOT NULL,
  `time` datetime NOT NULL,
  `reason` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`refund_id`),
  UNIQUE KEY `unique_refund_order_id` (`order_id`),
  CONSTRAINT `fk_refund_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of refund
-- ----------------------------

-- ----------------------------
-- Table structure for trial
-- ----------------------------
DROP TABLE IF EXISTS `trial`;
CREATE TABLE `trial` (
  `trial_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(63) COLLATE utf8mb4_general_ci NOT NULL,
  `detail` text COLLATE utf8mb4_general_ci NOT NULL,
  `img_url` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `category_id` bigint(20) unsigned NOT NULL,
  `branch_id` bigint(20) unsigned NOT NULL,
  `lecturer_id` bigint(20) unsigned NOT NULL,
  `release_time` datetime NOT NULL,
  `status` enum('AVAILABLE','OFF','IN_REVIEW') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'IN_REVIEW',
  PRIMARY KEY (`trial_id`),
  KEY `fk_trial_branch_id` (`branch_id`),
  KEY `fk_trial_category_id` (`category_id`),
  KEY `fk_trial_lecturer_id` (`lecturer_id`),
  CONSTRAINT `fk_trial_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_trial_category_id` FOREIGN KEY (`category_id`) REFERENCES `course_category` (`category_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_trial_lecturer_id` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`lecturer_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of trial
-- ----------------------------
INSERT INTO `trial` VALUES ('1', '全网最热 Python 3 入门', '<p>Python教程很多，但能做到如此全面系统，带你入门并打牢基础的视频类课程，全网都是稀缺的，你可以自行比较一下</p>\n', 'https://img3.mukewang.com/szimg/59b8a486000107fb05400300.jpg', '6', '1', '1', '2018-07-11 23:24:39', 'AVAILABLE');
INSERT INTO `trial` VALUES ('2', '分布式事务实践', '基于微服务架构的分布式系统应用越来越多，而分布式系统下的事务，一直没有一个简单统一的实现方案。本课程从本地事务出发，介绍了分布式系统和Spring cloud框架及其使用，以及分布式事务的几种实现模式。课程中还提供了大量的实例，让同学们在实战过程中，掌握分布式事务实现方式与思路。', 'https://img4.mukewang.com/szimg/5b2a29d50001bf4605400300.jpg', '6', '1', '1', '2018-07-11 23:27:56', 'AVAILABLE');
INSERT INTO `trial` VALUES ('3', 'Python 3 入门机器学习 经典算法与应用', 'bobo老师特为机器学习初学者量身打造，使用最新的python3语言和最流行的scikit-learn框架，算法与编程两翼齐飞，由浅入深，一步步的进入机器学习的世界。学到的不只是一门课程，更是不断思考的能力。', 'https://img2.mukewang.com/szimg/5a39cd3f0001c09805400300.jpg', '2', '2', '2', '2018-07-11 23:29:25', 'AVAILABLE');
INSERT INTO `trial` VALUES ('4', 'Python 最火爬虫框架 Scrapy 入门与实践', 'Scrapy，Python开发的一个快速,高层次的web抓取框架，用于抓取web站点并从页面中提取结构化的数据。Scrapy用途广泛，可以用于数据挖掘、监测和自动化测试。任何人都可以根据需求方便的修改。它也提供了多种类型爬虫的基类，如BaseSpider、sitemap爬虫等，最新版本又提供了web2.0爬虫的支持。本课程将带你入门并实践Scrapy框架！ ', 'https://img3.mukewang.com/5b39cfff0001a2ed06000338-240-135.jpg', '2', '2', '2', '2018-07-12 18:40:59', 'AVAILABLE');
INSERT INTO `trial` VALUES ('5', 'Swift 之基于 CALayer 的图形绘制', '本课主要讲解CoreAnimation框架，让大家掌握CALayer绘制实现方式。同时，根据所需知识完成一些圆形进度条的绘制。', 'https://img.mukewang.com/5b3061210001550306000338-240-135.jpg', '8', '1', '2', '2018-07-12 18:42:56', 'AVAILABLE');
INSERT INTO `trial` VALUES ('6', 'Java 生产环境下性能监控与调优详解', '本课程将为你讲解如何在生产环境下对Java应用做性能监控与调优；通过本课程，你将掌握多种性能监控工具应用，学会定位并解决诸如内存溢出、cpu负载飙高等问题；学会线上代码调试，Tomcat、Nginx，GC调优等手段； 读懂JVM字节码指令，分析源码背后原理，提升应对线上突发状况的能力', 'https://img3.mukewang.com/szimg/5b384772000132c405400300.jpg', '1', '1', '1', '2018-07-12 18:45:14', 'AVAILABLE');

-- ----------------------------
-- Table structure for trial_reservation
-- ----------------------------
DROP TABLE IF EXISTS `trial_reservation`;
CREATE TABLE `trial_reservation` (
  `reservation_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `trial_id` bigint(20) unsigned NOT NULL,
  `time` datetime NOT NULL,
  `message` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` enum('PENDING','CANCELED','AVAILABLE','USED','EXPIRED') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`reservation_id`),
  KEY `fk_trial_reservation` (`user_id`),
  KEY `fk_trial_reservation_trial_id` (`trial_id`),
  CONSTRAINT `fk_trial_reservation` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_trial_reservation_trial_id` FOREIGN KEY (`trial_id`) REFERENCES `trial` (`trial_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of trial_reservation
-- ----------------------------
INSERT INTO `trial_reservation` VALUES ('1', '1', '1', '2018-07-16 11:11:20', '销毁', 'PENDING');
INSERT INTO `trial_reservation` VALUES ('2', '1', '2', '2018-07-16 11:13:50', '销毁', 'PENDING');
INSERT INTO `trial_reservation` VALUES ('3', '1', '3', '2018-07-16 11:13:59', '销毁', 'AVAILABLE');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(16) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `avatar_url` varchar(511) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zenas', 'zzzz', 'czyczk@qq.com', '12345678901', null);

-- ----------------------------
-- View structure for view_available_branch
-- ----------------------------
DROP VIEW IF EXISTS `view_available_branch`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_available_branch` AS select `branch`.`branch_id` AS `branch_id`,`branch`.`enterprise_id` AS `enterprise_id`,`branch`.`name` AS `name`,`branch`.`address` AS `address`,`branch`.`latitude` AS `latitude`,`branch`.`longitude` AS `longitude`,`branch`.`telephone` AS `telephone`,`branch`.`is_disabled` AS `is_disabled` from `branch` where (`branch`.`is_disabled` = 0x00) ;

-- ----------------------------
-- View structure for view_available_course
-- ----------------------------
DROP VIEW IF EXISTS `view_available_course`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_available_course` AS select `course`.`course_id` AS `course_id`,`course`.`enterprise_id` AS `enterprise_id`,`course`.`name` AS `name`,`course`.`detail` AS `detail`,`course`.`img_url` AS `img_url`,`course`.`category_id` AS `category_id`,`course`.`release_time` AS `release_time`,`course`.`price` AS `price`,`course`.`status` AS `status` from `course` where (`course`.`status` = 'AVAILABLE') ;

-- ----------------------------
-- View structure for view_available_lecturer
-- ----------------------------
DROP VIEW IF EXISTS `view_available_lecturer`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_available_lecturer` AS select `lecturer`.`lecturer_id` AS `lecturer_id`,`lecturer`.`enterprise_id` AS `enterprise_id`,`lecturer`.`name` AS `name`,`lecturer`.`photo_url` AS `photo_url`,`lecturer`.`introduction` AS `introduction`,`lecturer`.`is_disabled` AS `is_disabled` from `lecturer` where (`lecturer`.`is_disabled` = 0) ;

-- ----------------------------
-- View structure for view_course_detail
-- ----------------------------
DROP VIEW IF EXISTS `view_course_detail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_course_detail` AS select `course`.`course_id` AS `course_id`,`course`.`enterprise_id` AS `enterprise_id`,`course`.`name` AS `name`,`course`.`detail` AS `detail`,`course`.`img_url` AS `img_url`,`course`.`category_id` AS `category_id`,`course_category`.`name` AS `category_name`,`course`.`release_time` AS `release_time`,`course`.`price` AS `price`,`course`.`status` AS `status` from (`course` join `course_category` on((`course`.`category_id` = `course_category`.`category_id`))) ;

-- ----------------------------
-- View structure for view_course_offering_detail
-- ----------------------------
DROP VIEW IF EXISTS `view_course_offering_detail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_course_offering_detail` AS select `course_offering`.`course_offering_id` AS `course_offering_id`,`course_offering`.`course_id` AS `course_id`,`course_offering`.`branch_id` AS `branch_id`,`branch`.`name` AS `branch_name`,`course_offering`.`lecturer_id` AS `lecturer_id`,`lecturer`.`name` AS `lecturer_name` from ((`course_offering` join `branch` on((`course_offering`.`branch_id` = `branch`.`branch_id`))) join `lecturer` on((`course_offering`.`lecturer_id` = `lecturer`.`lecturer_id`))) ;

-- ----------------------------
-- View structure for view_order_detail
-- ----------------------------
DROP VIEW IF EXISTS `view_order_detail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_order_detail` AS select `order`.`order_id` AS `order_id`,`course`.`enterprise_id` AS `enterprise_id`,`order`.`user_id` AS `user_id`,`user`.`username` AS `username`,`user`.`email` AS `user_email`,`user`.`mobile` AS `user_mobile`,`order`.`course_id` AS `course_id`,`course`.`name` AS `course_name`,`course`.`price` AS `course_price`,`order`.`time` AS `time`,`order`.`status` AS `status`,`refund`.`refund_id` AS `refund_id`,`refund`.`time` AS `refund_time`,`refund`.`reason` AS `refund_reason` from (((`order` join `user` on((`order`.`user_id` = `user`.`user_id`))) join `course` on((`order`.`course_id` = `course`.`course_id`))) left join `refund` on((`refund`.`order_id` = `order`.`order_id`))) ;

-- ----------------------------
-- View structure for view_refund_detail
-- ----------------------------
DROP VIEW IF EXISTS `view_refund_detail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_refund_detail` AS select `view_order_detail`.`order_id` AS `order_id`,`view_order_detail`.`enterprise_id` AS `enterprise_id`,`view_order_detail`.`user_id` AS `user_id`,`view_order_detail`.`username` AS `username`,`view_order_detail`.`user_email` AS `user_email`,`view_order_detail`.`user_mobile` AS `user_mobile`,`view_order_detail`.`course_id` AS `course_id`,`view_order_detail`.`course_name` AS `course_name`,`view_order_detail`.`course_price` AS `course_price`,`view_order_detail`.`time` AS `time`,`view_order_detail`.`status` AS `status`,`view_order_detail`.`refund_id` AS `refund_id`,`view_order_detail`.`refund_time` AS `refund_time`,`view_order_detail`.`refund_reason` AS `refund_reason` from `view_order_detail` where ((`view_order_detail`.`status` = 'REFUND_REQUESTED') or (`view_order_detail`.`status` = 'REFUNDED')) ;

-- ----------------------------
-- View structure for view_trial_detail
-- ----------------------------
DROP VIEW IF EXISTS `view_trial_detail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_trial_detail` AS select `trial`.`trial_id` AS `trial_id`,`trial`.`name` AS `name`,`trial`.`detail` AS `detail`,`trial`.`img_url` AS `img_url`,`trial`.`category_id` AS `category_id`,`course_category`.`name` AS `category_name`,`branch`.`enterprise_id` AS `enterprise_id`,`enterprise`.`name` AS `enterprise_name`,`trial`.`branch_id` AS `branch_id`,`branch`.`name` AS `branch_name`,`trial`.`lecturer_id` AS `lecturer_id`,`lecturer`.`name` AS `lecturer_name`,`trial`.`release_time` AS `release_time`,`trial`.`status` AS `status` from ((((`trial` join `course_category` on((`trial`.`category_id` = `course_category`.`category_id`))) join `branch` on((`trial`.`branch_id` = `branch`.`branch_id`))) join `lecturer` on((`trial`.`lecturer_id` = `lecturer`.`lecturer_id`))) join `enterprise` on((`branch`.`enterprise_id` = `enterprise`.`enterprise_id`))) ;
SET FOREIGN_KEY_CHECKS=1;
