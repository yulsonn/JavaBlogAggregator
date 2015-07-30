package com.jscomp.jba.service;

import com.jscomp.jba.entity.Blog;
import com.jscomp.jba.entity.Item;
import com.jscomp.jba.entity.User;
import com.jscomp.jba.exception.RssException;
import com.jscomp.jba.repository.BlogRepository;
import com.jscomp.jba.repository.ItemRepository;
import com.jscomp.jba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RssService rssService;

    private void saveItems(Blog blog){
        try {
            List<Item> items = rssService.getItems(blog.getUrl());
            for (Item item : items){
                Item savedItem = itemRepository.findByBlogAndLink(blog, item.getLink());
                if (savedItem == null){
                    item.setBlog(blog);
                    itemRepository.save(item);
                }
            }
        } catch (RssException e) {
            e.printStackTrace();
        }
    }

    /*first time runs when app is deploying*/
    public void save(Blog blog, String name){
        User user = userRepository.findByName(name);
        blog.setUser(user);
        blogRepository.save(blog);
        saveItems(blog);
    }

    @Scheduled(fixedDelay = 3600000) // 1 hour = 60 sec * 60 min * 1000
    public void reloadBlogs(){
        List<Blog> blogs = blogRepository.findAll();
        for (Blog blog : blogs){
            saveItems(blog);
        }
    }

    public void delete(int id){
        blogRepository.delete(id);
    }

    @PreAuthorize("#blog.user.name == authentication.name or hasRole('ROLE_ADMIN')")
    public void delete(@P("blog")Blog blog){
        blogRepository.delete(blog);
    }

    public Blog findOne(int id) {
        return blogRepository.findOne(id);
    }
}
