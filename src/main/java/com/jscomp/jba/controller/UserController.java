package com.jscomp.jba.controller;

import com.jscomp.jba.entity.Blog;
import com.jscomp.jba.entity.User;
import com.jscomp.jba.service.BlogService;
import com.jscomp.jba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @ModelAttribute("user")
    public User constructUser(){
        return new User();
    }

    @ModelAttribute("blog")
    public Blog constructBlog(){
        return new Blog();
    }

    @RequestMapping(value = "/users")
    public String users(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @RequestMapping(value = "/users/{id}")
    public String detail(Model model, @PathVariable int id){
        model.addAttribute("user", userService.findOneWithBlogs(id));
        return "user-detail";
    }

    @RequestMapping(value = "/register")
    public String showRegister(){
        return "user-register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String doRegister(@ModelAttribute("user") User user){
        userService.save(user);
        return "redirect:/register.html?success=true";
    }

    @RequestMapping(value = "/account")
    public String account(Model model, Principal principal){
        /*Principal - a user session object, contains the user's name if user logged in, if no - returns null*/
        model.addAttribute("user", userService.findOneWithBlogs(principal.getName()));
        return "user-detail";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public String doAddBlog(@ModelAttribute("blog") Blog blog, Principal principal){
        String name = principal.getName();
        blogService.save(blog, name);
        return "redirect:/account.html";
    }

    @RequestMapping(value = "/blog/remove/{id}")
    public String removeBlog(@PathVariable int id) {
        blogService.delete(id);
        return "redirect:/account.html";
    }

    @RequestMapping(value = "/users/remove/{id}")
    public String removeUser(@PathVariable int id){
        userService.delete(id);
        return "redirect:/users.html";
    }
}
