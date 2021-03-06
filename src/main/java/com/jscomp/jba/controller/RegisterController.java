package com.jscomp.jba.controller;

import com.jscomp.jba.entity.User;
import com.jscomp.jba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    /*creates the form*/
    @ModelAttribute("user")
    public User constructUser(){
        return new User();
    }

    /*shows the register page*/
    @RequestMapping
    public String showRegister(){
        return "user-register";
    }

    /*receives form from the user and saves to DB*/
    @RequestMapping(method = RequestMethod.POST)
    public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "user-register";
        }
        userService.save(user);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/register.html";
    }

    @RequestMapping(value = "/available")
    @ResponseBody
    public String available(@RequestParam String username){
        Boolean available = userService.findOne(username) == null;
        return available.toString();
    }
}
