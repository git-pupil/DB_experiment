DROP TABLE IF EXISTS `score`;
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `point` int DEFAULT NULL,
    `student_id` bigint(20) DEFAULT NULL,
    `course_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `sex` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `class_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `major` varchar(255) DEFAULT NULL,
    `college` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `teacher_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `sex` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `college` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

/*ALTER TABLE `student` ADD CONSTRAINT `FK_class`
    FOREIGN KEY (`class_id`) REFERENCES `class` (`id`);
ALTER TABLE `course` ADD CONSTRAINT `FK_teacher`
    FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`);
ALTER TABLE `grade` ADD CONSTRAINT `FK_student`
    FOREIGN KEY (`student_id`) REFERENCES `student` (`id`);
ALTER TABLE `grade` ADD CONSTRAINT `FK_course`
    FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);*/


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `username` varchar (255) DEFAULT NULL,
    `password` varchar (255) DEFAULT NULL,
    `role` varchar (255) DEFAULT NULL,
    PRIMARY KEY (`id`)
)
/*DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
)*/