package database.part.db2.controller;

import database.part.db2.dto.CourseInfo;
import database.part.db2.dto.StudentInfo;
import database.part.db2.entity.Course;
import database.part.db2.entity.Student;
import database.part.db2.entity.Teacher;
import database.part.db2.entity.auth.User;
import database.part.db2.mapper.auth.UserMapper;
import database.part.db2.service.impl.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/managerHome")
public class ManagerController {

    @Autowired
    ManagerService service;
    @Autowired
    UserMapper userMapper;

    //学院列表
    List<String> collegeList = Arrays.asList(
            "信息与通信工程","电子工程","计算机",
            "网络空间安全","人工智能","软件","自动化",
            "现代邮政","光电信息","经济管理","理","人文",
            "数字媒体与设计艺术","马克思主义","国际",
            "网络教育","继续教育","叶培大创新创业");

    /**
     * 准备信息，并进入学生管理界面
     * @param model
     * @return managerStudent 学生管理界面
     */

    @GetMapping("/managerStudent")
    public String getStudentList(Model model) {
        //为学生管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);

        //初始化已选学院为“全部学院”，并将其存入model，使课程管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有学生的信息，并将其存入model
        List<StudentInfo> studentList = service.getStudentInfoList();
        model.addAttribute("studentList", studentList);

        return "managerStudent";
    }

    /**
     * 根据选择的学院筛选学生信息
     * @param model
     * @param selectCollege 所选学院
     * @return managerStudent 学生管理界面
     */

    @PostMapping("/managerSelectStudent")
    public String getStudentListByCollege(Model model,@RequestParam String selectCollege) {
        //为学生管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);

        //将已选学院设为选择的学院，并将其存入model，使学院多选框显示为所选学院
        model.addAttribute("selectedCollege",selectCollege);

        //获取所选学院所有学生的信息
        List<StudentInfo> studentList = service.getStudentListByCollege(selectCollege);

        //若选择的是“全部学院”，则获取所有学生的信息
        if(selectCollege.equals("全部学院")){
            studentList = service.getStudentInfoList();
        }

        //将以上获取的的学生信息存入model
        model.addAttribute("studentList", studentList);

        return "managerStudent";
    }

    /**
     * 获取所选学生信息，并进入修改学生信息界面
     * @param id    所选学生的id
     * @param model
     * @return managerChangeStudent 修改学生信息的界面
     */

    @GetMapping("/managerBeginChangeStudent")
    public String beginChangeStudent(@RequestParam Long id, Model model){
        //根据id返回student实体，并将其存入model
        StudentInfo student = service.getStudentInfoById(id);
        model.addAttribute("student",student);

        return "managerChangeStudent";
    }

    /**
     * 修改学生的信息
     * @param request
     * @param model
     * @param id        所选学生的id
     * @param classId   所选学生的班级号
     * @param name      所选学生的名字
     * @param sex       所选学生的性别
     * @param email     所选学生的邮箱
     * @return 1.输入不符合要求    managerChangeStudent     进入修改学生信息界面，重新输入
     *         2.输入符合要求      managerStudent          修改学生信息后直接返回学生管理界面
     */

    @PostMapping("/managerChangeStudent")
    public String changeStudent(HttpServletRequest request, Model model,
                                @RequestParam Long id,
                                @RequestParam Long classId,
                                @RequestParam String name,
                                @RequestParam String sex,
                                @RequestParam String email) {
        //“existedClass”标识班级号是否存在，1标识存在
        request.setAttribute("existedClass","1");

        //创建Student实体，并将修改后学生的id、班级号、名字、性别以及邮箱存入其中
        Student student = new Student();
        student.setId(id);
        student.setClassId(classId);
        student.setName(name);
        student.setSex(sex);
        student.setEmail(email);

        //若此班级号代表的班级不存在，做相应错误提示处理，并返回录入学生界面
        if(service.getClassById(classId) == null){
            //根据学生id搜索学生信息，并将其存入model，为“managerChangeStudent”页面做准备
            model.addAttribute("student", service.getStudentInfoById(id));

            //则将“existedClass”设为0，弹出班级不存在警告
            request.setAttribute("existedClass","0");

            //将班级号之后的错误提示信息设为"您输入的班级不存在！"
            model.addAttribute("ErrorMsg1","您输入的班级不存在！");

            return "managerChangeStudent";
        }

        //将修改过的学生信息存入
        service.changeStudent(student);

        //为学生管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);

        //初始化已选学院为“全部学院”，并将其存入model，使课程管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有学生的信息，并将其存入model
        List<StudentInfo> studentList = service.getStudentInfoList();
        model.addAttribute("studentList", studentList);

        return "managerStudent";
    }

    /**
     * 进入录入学生界面
     * @return managerInputStudent 录入学生界面
     */

    @GetMapping("/managerBeginInputStudent")
    public String beginInputStudent(){
        return "managerInputStudent";
    }

    /**
     * 录入学生，包括录入学生数据库和用户数据库
     * @param request
     * @param model
     * @param id        新学生的id
     * @param classId   新学生的班级号
     * @param name      新学生的名字
     * @param sex       新学生的性别
     * @param email     新学生的邮箱
     * @return 1.输入不符合要求    managerInputStudent     进入录入学生界面，重新输入
     *         2.输入符合要求      managerStudent         录入学生后直接返回学生管理界面
     */

    @PostMapping("/managerInputStudent")
    public String inputStudent(HttpServletRequest request, Model model,
                               @RequestParam Long id,
                               @RequestParam Long classId,
                               @RequestParam String name,
                               @RequestParam String sex,
                               @RequestParam String email) {
        //“existedStudent”标识学生id是否存在，0标识不存在
        //“existedClass”标识班级号是否存在，1标识存在
        //“existedTeacher”标识学生id是否已被教师占用，0标识没有
        request.setAttribute("existedStudent", "0");
        request.setAttribute("existedClass","1");
        request.setAttribute("existedTeacher", "0");

        //若输入的新学生id与已存在的某一教师id相同，做相应错误提示处理，并返回录入学生界面
        if(service.getTeacherById(id) != null){
            //则将“existedTeacher”设为1，弹出此id被某一教师占用警告
            request.setAttribute("existedTeacher", "1");

            //将学生id之后的错误提示信息设为"您输入的工号已被教师占用！"
            model.addAttribute("ErrorMsg1","您输入的学号已被教师占用！");

            return "managerInputStudent";
        }

        //若输入的班级号代表的班级不存在，做相应错误提示处理，并返回录入学生界面
        if(service.getClassById(classId) == null){
            //则将“existedClass”设为0，弹出班级不存在警告
            request.setAttribute("existedClass","0");

            //将班级号之后的错误提示信息设为"您输入的班级不存在！"
            model.addAttribute("ErrorMsg2","您输入的班级不存在！");

            return "managerInputStudent";
        }

        //创建Student实体，并将新学生的id、班级号、名字、性别以及邮箱存入其中
        Student student = new Student();
        student.setId(id);
        student.setClassId(classId);
        student.setName(name);
        student.setSex(sex);
        student.setEmail(email);

        //录入学生，包括录入学生数据库和用户数据库，若返回值为null，代表输入的新学生id已存在
        //若输入的新学生id已存在，做相应错误提示处理，并返回录入学生界面
        if(service.inputStudent(student) == null){
            //则将“existedStudent”设为1，弹出学生已存在警告
            request.setAttribute("existedStudent", "1");

            //将学生id之后的错误提示信息设为"你输入的学号已被其他学生占用！"
            model.addAttribute("ErrorMsg1","你输入的学号已被其他学生占用！");

            return "managerInputStudent";
        }

        //为学生管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);

        //初始化已选学院为“全部学院”，并将其存入model，使课程管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有学生的信息，并将其存入model
        List<StudentInfo> studentList = service.getStudentInfoList();
        model.addAttribute("studentList", studentList);

        return "managerStudent";
    }

    /**
     * 将所选的学生的相关信息删除，包括用户、学生以及学生成绩数据库
     * @param model
     * @param id    所选学生的id
     * @return managerStudent 学生管理界面
     */

    @GetMapping("/managerDeleteStudent")
    public String deleteStudent(Model model, @RequestParam Long id) {
        //将所选的学生的相关信息删除，包括用户、学生以及学生成绩数据库
        service.deleteStudent(id);

        //为学生管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);

        //初始化已选学院为“全部学院”，并将其存入model，使课程管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有学生的信息，并将其存入model
        List<StudentInfo> studentList = service.getStudentInfoList();
        model.addAttribute("studentList", studentList);

        return "managerStudent";
    }

    /**
     * 重置所选学生的密码为其id
     * @param id    所选学生的id
     * @param model
     * @return managerStudent 学生管理界面
     */
    @RequestMapping("/managerChangeStudentPassword")
    public String ChangeStudentPassword(@RequestParam Long id, Model model){
        //将此学生的账户密码改为其id号
        service.changePassword(id,String.valueOf(id));

        //为学生管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);

        //初始化已选学院为“全部学院”，并将其存入model，使课程管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有学生的信息，并将其存入model
        List<StudentInfo> studentList = service.getStudentInfoList();
        model.addAttribute("studentList", studentList);

        return "managerStudent";
    }

    /**
     * 准备信息，并进入教师管理界面
     * @param model
     * @return managerTeacher 教师管理界面
     */

    @GetMapping("/managerTeacher")
    public String getTeacherList(Model model) {
        //为教师管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);

        //初始化已选学院为“全部学院”，并将其存入model，使课程管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有教师的信息，并将其存入model
        List<Teacher> teacherList = service.getTeacherList();
        model.addAttribute("teacherList", teacherList);

        return "managerTeacher";
    }

    /**
     * 根据所选学院筛选教师
     * @param selectCollege 所选择的学院
     * @param model
     * @return managerTeacher 教师管理界面
     */

    @PostMapping("/managerSelectTeacher")
    public String getTeacherListByCollege(@RequestParam String selectCollege, Model model) {
        //为教师管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);

        //将已选学院设为选择的学院，并将其存入model，使学院多选框显示为所选学院
        model.addAttribute("selectedCollege",selectCollege);

        //获取所选学院所有教师的信息
        List<Teacher> teacherList = service.getTeacherListByCollege(selectCollege);

        //若选择的是“全部学院”，则获取所有教师的信息
        if(selectCollege.equals("全部学院")){
            teacherList = service.getTeacherList();
        }

        //将之前获取的所有教师信息存入model
        model.addAttribute("teacherList", teacherList);

        return "managerTeacher";
    }

    /**
     * 获取所选教师的信息，并进入修改教师信息的界面
     * @param id    所选教师的id
     * @param model
     * @return managerChangeTeacher 修改教师信息的界面
     */

    @GetMapping("/managerBeginChangeTeacher")
    public String beginChangeTeacher(@RequestParam Long id, Model model){
        //根据id返回Teacher实体,并将其存入model
        Teacher teacher = service.getTeacherById(id);
        model.addAttribute("teacher",teacher);

        //将所有学院的列表存入model，为修改教师信息界面准备信息
        model.addAttribute("collegeList", collegeList);

        //将已选学院设为所选教师的所属学院，并存入model，使修改教师信息界面的学院多选框显示为所选教师的学院
        model.addAttribute("selectedCollege",teacher.getCollege());

        return "managerChangeTeacher";
    }

    /**
     * 修改教师信息
     * @param model
     * @param id        所选教师的id
     * @param college   所选教师的所属学院
     * @param name      所选教师的名称
     * @param sex       所选教师的性别
     * @param email     所选教师的邮箱
     * @return managerTeacher 修改教师信息，并返回教师管理界面
     */

    @PostMapping("/managerChangeTeacher")
    public String changeTeacher(Model model, @RequestParam Long id,
                                @RequestParam String college,
                                @RequestParam String name,
                                @RequestParam String sex,
                                @RequestParam String email) {
        //创建Teacher实体，将修改过的教师的id、名称、性别、邮箱以及所属学院存入其中
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setCollege(college);
        teacher.setName(name);
        teacher.setSex(sex);
        teacher.setEmail(email);

        //将修改过的教师信息存入
        service.changeTeacher(teacher);

        //为教师管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);

        //初始化已选学院为“全部学院”，并将其存入model，使课程管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有教师的信息，并将其存入model
        List<Teacher> teacherList = service.getTeacherList();
        model.addAttribute("teacherList", teacherList);

        return "managerTeacher";
    }

    /**
     * 进入录入教师界面
     * @param model
     * @return managerInputTeacher 录入教师界面
     */

    @GetMapping("/managerBeginInputTeacher")
    public String beginInputTeacher(Model model){
        //将所有学院的列表存入model，为录入教师界面准备信息
        model.addAttribute("collegeList", collegeList);

        //初始化已选学院为“信息与通信工程”，并将其存入model，使录入教师界面的学院多选框显示为"信息与通信工程"
        model.addAttribute("selectedCollege","信息与通信工程");

        return "managerInputTeacher";
    }

    /**
     * 录入教师，包括录入教师数据库和用户数据库
     * @param request
     * @param model
     * @param id        新教师的id
     * @param college   新教师所属的学院
     * @param name      新教师的名字
     * @param sex       新教师的性别
     * @param email     新教师的邮箱
     * @return 1.输入不符合要求    managerInputTeacher     进入录入教师界面，重新输入
     *         2.输入符合要求      managerTeacher          录入教师后直接返回教师管理界面
     */

    @PostMapping("/managerInputTeacher")
    public String inputTeacher(HttpServletRequest request, Model model,
                               @RequestParam Long id,
                               @RequestParam String college,
                               @RequestParam String name,
                               @RequestParam String sex,
                               @RequestParam String email) {
        //“existedTeacher”标识教师id是否存在，0标识不存在
        //“existedStudent”标识此教师id是否被某一学生占用，0标识没有
        request.setAttribute("existedTeacher", "0");
        request.setAttribute("existedStudent", "0");

        //新建Teacher实体，并将新教师的id、名字、性别、所属学院、邮箱存入其中
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setCollege(college);
        teacher.setName(name);
        teacher.setSex(sex);
        teacher.setEmail(email);

        //将所有学院的列表存入model，为录入教师界面或教师管理界面准备信息
        model.addAttribute("collegeList", collegeList);

        //若输入的新教师id与已存在的某一学生id相同，做相应错误提示处理，并返回录入教师界面
        if(service.getStudentInfoById(id) != null){
            //初始化已选学院为“信息与通信工程”，并将其存入model，使录入教师界面的学院多选框显示为"信息与通信工程"
            model.addAttribute("selectedCollege","信息与通信工程");

            //则将“existedStudent”设为1，弹出此id被某一学生占用警告
            request.setAttribute("existedStudent", "1");

            //将教师id之后的错误提示信息设为"您输入的工号已被学生占用！"
            model.addAttribute("ErrorMsg1","您输入的工号已被学生占用！");

            return "managerInputTeacher";
        }

        //录入教师，包括录入教师数据库和用户数据库，若返回值为null，代表输入的新教师id已存在
        //若输入的新教师id已存在，做相应错误提示处理，并返回录入教师界面
        if(service.inputTeacher(teacher) == null){
            //初始化已选学院为“信息与通信工程”，并将其存入model，使录入教师界面的学院多选框显示为"信息与通信工程"
            model.addAttribute("selectedCollege","信息与通信工程");

            //则将“existedTeacher”设为1，弹出教师已存在警告
            request.setAttribute("existedTeacher", "1");

            //将教师id之后的错误提示信息设为"您输入的工号已被其他教师占用！"
            model.addAttribute("ErrorMsg1","您输入的工号已被其他教师占用！");

            return "managerInputTeacher";
        }

        //为教师管理界面准备信息
        //初始化已选学院为“全部学院”，并将其存入model，使教师管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有教师的信息，并将其存入model
        List<Teacher> teacherList = service.getTeacherList();
        model.addAttribute("teacherList", teacherList);

        return "managerTeacher";
    }

    /**
     * 删除教师相关信息，包括用户、所教课程、所教课程相关成绩以及教师数据库
     * @param model
     * @param id    所选教师id
     * @return managerTeacher 教师管理界面
     */

    @GetMapping("/managerDeleteTeacher")
    public String deleteTeacher(Model model, @RequestParam Long id) {
        //将所选教师的相关信息删除，包括用户、所教课程、所教课程相关成绩以及教师数据库
        service.deleteTeacher(id);

        //为教师管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);
        //初始化已选学院为“全部学院”，并将其存入model，使教师管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有教师的信息，并将其存入model
        List<Teacher> teacherList = service.getTeacherList();
        model.addAttribute("teacherList", teacherList);

        return "managerTeacher";
    }

    /**
     * 重置教师密码为其id号
     * @param id    要重置密码的教师id
     * @param model
     * @return managerTeacher 教师管理界面
     */

    @RequestMapping("/managerChangeTeacherPassword")
    public String ChangeTeacherPassword(@RequestParam Long id, Model model){
        //将此教师的账户密码改为其id号
        service.changePassword(id,String.valueOf(id));

        //为教师管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);
        //初始化已选学院为“全部学院”，并将其存入model，使教师管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有教师的信息，并将其存入model
        List<Teacher> teacherList = service.getTeacherList();
        model.addAttribute("teacherList", teacherList);

        return "managerTeacher";
    }

    /**
     * 准备信息，并进入课程管理界面
     * @param model
     * @return managerCourse 课程管理界面
     */

    @GetMapping("/managerCourse")
    public String getCourseList(Model model) {
        //为课程管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);
        //初始化已选学院为“全部学院”，并将其存入model，使教师管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有课程的信息，并将其存入model
        List<CourseInfo> courseList = service.getCourseInfoList();
        model.addAttribute("courseList", courseList);

        return "managerCourse";
    }

    /**
     * 根据所选学院筛选课程
     * @param model
     * @param selectCollege 所选择的学院
     * @return managerCourse 课程管理界面
     */

    @PostMapping("/managerSelectCourse")
    public String getCourseListByCollege (Model model,@RequestParam String selectCollege){
        //为课程管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);

        //将已选学院设为选择的学院，并将其存入model，使学院多选框显示为所选学院
        model.addAttribute("selectedCollege",selectCollege);

        //获取所选学院所有课程的信息
        List<CourseInfo> courseList = service.getCourseListByCollege(selectCollege);

        //若选择的是“全部学院”，则获取所有课程的信息
        if(selectCollege.equals("全部学院")){
            courseList = service.getCourseInfoList();
        }

        //将之前获取的所有课程信息存入model
        model.addAttribute("courseList", courseList);

        return "managerCourse";
    }

    /**
     * 获取所选课程的信息，并进入修改课程信息的界面
     * @param id    所选课程的id
     * @param model
     * @return managerChangeCourse 修改课程信息的界面
     */

    @GetMapping("/managerBeginChangeCourse")
    public String beginChangeCourse(@RequestParam Long id, Model model){
        //根据id返回CourseInfo实体，并将其存入model
        CourseInfo course = service.getCourseInfoById(id);
        model.addAttribute("course",course);

        return "managerChangeCourse";
    }

    /**
     * 修改课程信息
     * @param request
     * @param model
     * @param id        新课程id
     * @param name      新课程名称
     * @param teacherId 新课程教师id
     * @return 1.输入不符合要求    managerChangeCourse     进入修改课程信息界面，重新输入
     *         2.输入符合要求      managerCourse          修改课程信息后直接返回课程管理界面
     */

    @PostMapping("/managerChangeCourse")
    public String changeCourse(HttpServletRequest request, Model model,
                               @RequestParam Long id,
                               @RequestParam String name,
                               @RequestParam Long teacherId) {
        //“existedTeacher”标识教师id是否存在，1标识存在
        request.setAttribute("existedTeacher","1");

        //若教师id不存在，做相应错误提示处理，并返回录入课程界面
        if(service.getTeacherById(teacherId) == null){
            //根据课程id搜索课程信息，并将其存入model，为“managerChangeCourse”页面做准备
            model.addAttribute("course",service.getCourseInfoById(id));

            //则将“existedTeacher”设为0，弹出教师不存在警告
            request.setAttribute("existedTeacher","0");

            //将教师id之后的错误提示信息设为"您输入的教师工号不存在！"
            model.addAttribute("ErrorMsg1","您输入的教师工号不存在！");

            return "managerChangeCourse";
        }

        //创建Course实体，将修改过的课程的id、名称以及教师id存入其中
        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setTeacherId(teacherId);

        //将修改过的课程信息存入
        service.changeCourse(course);

        //为课程管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);
        //初始化已选学院为“全部学院”，并将其存入model，使课程管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有课程的信息，并将其存入model
        List<CourseInfo> courseList = service.getCourseInfoList();
        model.addAttribute("courseList", courseList);

        return "managerCourse";
    }

    /**
     * 进入录入课程的界面
     * @return managerInputCourse 录入课程界面
     */

    @GetMapping("/managerBeginInputCourse")
    public String beginInputCourse(){
        return "managerInputCourse";
    }

    /**
     * 录入课程
     * @param request
     * @param model
     * @param id        新课程id
     * @param teacherId 新课程教师id
     * @param name      新课程名称
     * @return 1.输入不符合要求    managerInputCourse     进入录入课程界面，重新输入
     *         2.输入符合要求      managerCourse          录入课程后直接返回课程管理界面
     */

    @PostMapping("/managerInputCourse")
    public String inputCourse(HttpServletRequest request, Model model,
                              @RequestParam Long id,
                              @RequestParam Long teacherId,
                              @RequestParam String name) {
        //"existedCourse"标识课程id是否已经存在，0标识不存在
        //“existedTeacher”标识教师id是否存在，1标识存在
        request.setAttribute("existedCourse", "0");
        request.setAttribute("existedTeacher","1");

        //若教师id不存在，做相应错误提示处理，并返回录入课程界面
        if(service.getTeacherById(teacherId) == null){
            //则将“existedTeacher”设为0，弹出教师不存在警告
            request.setAttribute("existedTeacher","0");

            //将教师id之后的错误提示信息设为"您输入的教师工号不存在！"
            model.addAttribute("ErrorMsg2","您输入的教师工号不存在！");

            return "managerInputCourse";
        }

        //创建Course实体，将新课程的id、名称以及教师id存入其中
        Course course = new Course();
        course.setId(id);
        course.setTeacherId(teacherId);
        course.setName(name);

        //录入课程，若返回值为null，代表输入的新课程id已存在
        //若输入的新课程id已存在，做相应错误提示处理，并返回录入课程界面
        if(service.inputCourse(course) == null){
            //则将“existedCourse”设为1，弹出课程已存在警告
            request.setAttribute("existedCourse", "1");

            //将课程id之后的错误提示信息设为"您输入的课程号已存在！"
            model.addAttribute("ErrorMsg1","您输入的课程号已存在！");

            return "managerInputCourse";
        }

        //为课程管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);
        //初始化已选学院为“全部学院”，并将其存入model，使课程管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有课程的信息，并将其存入model
        List<CourseInfo> courseList = service.getCourseInfoList();
        model.addAttribute("courseList", courseList);

        return "managerCourse";
    }

    /**
     * 删除指定课程相关信息，包括课程与课程相关成绩数据库
     * @param model
     * @param id 要删除的课程的课程号
     * @return managerCourse 课程管理界面
     */

    @GetMapping("/managerDeleteCourse")
    public String deleteCourse(Model model, @RequestParam Long id) {
        //删除所选课程相关信息，包括课程与课程相关成绩数据库
        service.deleteCourse(id);

        //为课程管理界面准备信息
        //将所有学院的列表存入model
        model.addAttribute("collegeList", collegeList);
        //初始化已选学院为“全部学院”，并将其存入model，使课程管理界面的学院多选框显示为"全部学院"
        model.addAttribute("selectedCollege","全部学院");

        //获取所有课程的信息，并将其存入model
        List<CourseInfo> courseList = service.getCourseInfoList();
        model.addAttribute("courseList", courseList);

        return "managerCourse";
    }

    /**
     * 进入修改管理员自己密码的界面
     * @return userChangePassword 修改密码界面
     */

    @RequestMapping("/changePassword")
    public String ChangePassword() {
        return "userChangePassword";
    }

    /**
     * 修改管理员自己账户的密码
     * @param request
     * @param model
     * @param password 输入的原始密码
     * @param password1 第一遍输入的新密码
     * @param password2 第二遍输入的新密码
     * @return 1.输入不符合要求    userChangePassword     进入输入密码界面，重新输入
     *         2.输入符合要求      logout                 修改密码后直接登出
     */

    @RequestMapping("/userChangePassword")
    public String changeManagerPassword(HttpServletRequest request, Model model,
                                        @RequestParam String password,
                                        @RequestParam String password1,
                                        @RequestParam String password2) {
        //“changefailure”标识当前输入信息是否符合要求，符合为0，不符合为1
        //初始化“changefailure”为0，标识输入符合要求
        request.setAttribute("changefailure","0");

        //获取管理员当前的用户信息
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        String username = au.getName();
        User user = userMapper.findByUsername(username);

        //比较输入的原始密码与当前密码，相同则表示密码输入正确
        //若输入的原始密码正确
        if (user.getPassword().equals(password)) {

            //比较两次输入的新密码，相同则表示新密码输入正确
            //若新密码输入正确，则修改管理员密码，并登出账号，返回登陆页面
            if (password1.equals(password2)) {
                user.setPassword(password1);
                userMapper.update(user);
                return "redirect:/logout";
            }

            //若新密码输入错误，则标识输入不符合要求，并提示"两次密码输入不一致"
            else {
                request.setAttribute("changefailure","1");
                model.addAttribute("ErrorMsg3", "两次密码输入不一致");
                return "userChangePassword";
            }
        }

        //若输入的密码错误，则标识输入不符合要求，并提示"原始密码错误"
        else {
            request.setAttribute("changefailure","1");
            model.addAttribute("ErrorMsg1", "原始密码错误");
            return "userChangePassword";
        }
    }


}

