DROP TABLE IF EXISTS `grade`;
DROP TABLE IF EXISTS `student`;
DROP TABLE IF EXISTS `class`;
DROP TABLE IF EXISTS `course`;
DROP TABLE IF EXISTS `teacher`;
DROP TABLE IF EXISTS `manager`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `grade` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `point` int DEFAULT NULL,
    `student_id` bigint(20) DEFAULT NULL,
    `course_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `student` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `sex` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `class_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `class` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `major` varchar(255) DEFAULT NULL,
    `college` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `course` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `teacher_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `teacher` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `sex` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `college` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `manager` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `user`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `username` varchar (255) DEFAULT NULL,
    `password` varchar (255) DEFAULT NULL,
    `role` varchar (255) DEFAULT NULL,
    PRIMARY KEY (`id`)
)
