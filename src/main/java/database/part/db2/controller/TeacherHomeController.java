package database.part.db2.controller;

import database.part.db2.entity.Teacher;
import database.part.db2.service.ITeacherService;
import database.part.db2.service.impl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TeacherHomeController {
        @Autowired
        ITeacherService service;
        //获取相关权限
        Authentication au = SecurityContextHolder.getContext().getAuthentication();

        @GetMapping("/teacherHome")
        String teacherHome(Model model, HttpServletRequest request) {

            Teacher t_info = service.getInfo(au);
            String username = t_info.getName();
            HttpSession session = request.getSession();
            model.addAttribute("username", username);
            //上传个人信息
            session.setAttribute("teacher", t_info);
            model.addAttribute("teacher", t_info);

            return "teacherHome";
        }

}
