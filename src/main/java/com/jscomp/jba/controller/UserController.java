package com.jscomp.jba.controller;

import com.jscomp.jba.entity.Blog;
import com.jscomp.jba.entity.User;
import com.jscomp.jba.service.BlogService;
import com.jscomp.jba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @ModelAttribute("blog")
    public Blog constructBlog(){
        return new Blog();
    }


    @RequestMapping(value = "/account")
    public String account(Model model, Principal principal){
        /*Principal - a user session object, contains the user's name if user logged in, if no - returns null*/
        model.addAttribute("user", userService.findOneWithBlogs(principal.getName()));
        return "account";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public String doAddBlog(Model model, @Valid @ModelAttribute("blog") Blog blog, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return account(model, principal);
        }
        String name = principal.getName();
        blogService.save(blog, name);
        return "redirect:/account.html";
    }

    @RequestMapping(value = "/blog/remove/{id}")
    public String removeBlog(@PathVariable int id) {
        Blog blog = blogService.findOne(id);
        blogService.delete(blog);
        return "redirect:/account.html";
    }
}
