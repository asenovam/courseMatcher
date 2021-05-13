/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.controllers;

import co.uk.city.london.beans.LoginBean;
import co.uk.city.london.beans.RegisterBean;
import co.uk.city.london.converters.RegisterConverter;
import co.uk.city.london.daos.UserDao;
import co.uk.city.london.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author mar_9
 */
@Controller
public class UserController {
    
     @Autowired
     private UserDao userDao;
     
     @Autowired
     private RegisterConverter registerConverter;
     
    @GetMapping("/register")
    public String loadRegister(Model model) {
        model.addAttribute("register", new RegisterBean());

        return "register";
    }
    
    
    @PostMapping("/register")
    public String executeRegister(@ModelAttribute RegisterBean register, Model model, RedirectAttributes attr) {
        if (register.getEmail() == null || register.getEmail().isEmpty()
                || register.getNickname() == null || register.getNickname().isEmpty()
                || register.getPassword() == null || register.getPassword().isEmpty()
                || register.getConfirmedPassword() == null || register.getConfirmedPassword().isEmpty()) {
            register.setErrorMessage("Fill all mandatory fields!");

            model.addAttribute("register", register);
            return "register";
        }

        if (!register.getPassword().equals(register.getConfirmedPassword())) {
            register.setErrorMessage("Password and confirm password fields should be equals!");

            model.addAttribute("register", register);
            return "register";
        }

        UserEntity user = userDao.findByEmail(register.getEmail());
        if (user != null) {
            register.setErrorMessage("There is already an existing user with that email. "
                    + "Please, use the forgot password link if you need to reset passrd");

            model.addAttribute("register", register);
            return "register";
        }

        user = userDao.findByNickname(register.getNickname());
        if (user != null) {
            register.setErrorMessage("There is already an existing user with that nickname. "
                    + "Please, use the forgot password link if you need to reset passrd");

            model.addAttribute("register", register);
            return "register";
        }

        user = new UserEntity();
        registerConverter.convert(register, user);

        userDao.save(user);

        LoginBean login = new LoginBean();
        login.setName(register.getNickname());
        login.setEmail(register.getEmail());
        model.addAttribute("login", login);

        attr.addAttribute("nickname", login.getName());

        return "redirect:userdashboard";
    }
    
    
    
    @GetMapping("/login")
    public String greeting(Model model) {
        model.addAttribute("login", new LoginBean());

        return "login";
    }

    @PostMapping("/login")
    public String greetingSubmit(@ModelAttribute LoginBean login, Model model, RedirectAttributes attr) {
        UserEntity user = userDao.findByNickname(login.getName());

        if (user == null) {
            login.setErrorMessage("No user with nickname: " + login.getName());
            model.addAttribute("login", login);
            return "login";
        }

        attr.addAttribute("nickname", login.getName());

        return "redirect:/userdashboard";
    }
}
