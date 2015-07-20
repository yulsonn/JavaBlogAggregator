package com.jscomp.jba.service;

import com.jscomp.jba.entity.Blog;
import com.jscomp.jba.entity.Item;
import com.jscomp.jba.entity.User;
import com.jscomp.jba.repository.BlogRepository;
import com.jscomp.jba.repository.ItemRepository;
import com.jscomp.jba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findOne(int id){
        return userRepository.findOne(id);
    }

    @Transactional
    public User findOneWithBlogs(int id){
        User user = findOne(id);
        List<Blog> blogs = blogRepository.findByUser(user);
        for (Blog blog : blogs){
            List<Item> items = itemRepository.findByBlog(blog, new PageRequest(0,10, Sort.Direction.DESC, "publishedDate"));
            blog.setItems(items);
        }
        user.setBlogs(blogs);

        return user;
    }

    public void save(User user){
        userRepository.save(user);
    }
}
