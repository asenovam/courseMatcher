/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.controllers;

import co.uk.city.london.beans.UserDashboardBean;
import co.uk.city.london.daos.CourseDao;
import co.uk.city.london.daos.UserDao;
import co.uk.city.london.entities.CourseEntity;
import co.uk.city.london.entities.UserEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author mar_9
 */
@Controller
public class UserDashboardController {
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private CourseDao courseDao;
    
    @GetMapping("/userdashboard")
    public String loadUserDashoboard(Model model, String nickname) {
          populateUserDashboard(model, nickname, null, null);

            return "userdashboard";
    }

    private void populateUserDashboard(Model model, String nickname, Object object, Object object0) {
            UserEntity user = userDao.findByNickname(nickname);

            UserDashboardBean userDashboardBean = new UserDashboardBean();
             userDashboardBean.setUserName(user.getFirstName() + " " + user.getLastName());
             userDashboardBean.setNickname(nickname);
             
            for (String courseUrl : user.getCourseUrls()) {
                  List<CourseEntity> courses = courseDao.findByUrl(courseUrl);
                  if (courses != null && !courses.isEmpty()) {
                        userDashboardBean.getMyCourses().add(courses.get(0));
                  }
            }
             
        
            model.addAttribute("userDashboard", userDashboardBean);
    }
}
