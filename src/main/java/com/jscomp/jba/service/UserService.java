package com.jscomp.jba.service;

import com.jscomp.jba.entity.Blog;
import com.jscomp.jba.entity.Item;
import com.jscomp.jba.entity.Role;
import com.jscomp.jba.entity.User;
import com.jscomp.jba.repository.BlogRepository;
import com.jscomp.jba.repository.ItemRepository;
import com.jscomp.jba.repository.RoleRepository;
import com.jscomp.jba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * @return list of all users
     *
     * */
    public List<User> findAll(){
        return userRepository.findAll();
    }

    /**
     * @param id
     * @return user, found by given id
     *
     * */
    public User findOne(int id){
        return userRepository.findOne(id);
    }

    /**
     * To avoid Lazy Initialization Hibernate Exception this method finds a user by given id,
     * then finds blogs of this user, then finds 10 last created items of each blog
     *
     * @param id
     * @return user(found by given id) with info about his blogs with blog items
     *
     * */
    @Transactional
    public User findOneWithBlogs(int id){
        User user = findOne(id);
        List<Blog> blogs = blogRepository.findByUser(user);
        for (Blog blog : blogs){
            List<Item> items = itemRepository.findByBlog(blog, new PageRequest(0,10, Sort.Direction.DESC, "publishedDate"));    /*return first 10 records, ordered by publishedDate in DESC order*/
            blog.setItems(items);
        }
        user.setBlogs(blogs);

        return user;
    }

    /**
     * Save given User into database
     *
     * @param user
     *
     * */
    public void save(User user){
        user.setEnabled(true);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roles);

        userRepository.save(user);
    }
}
