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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class InitDbService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ItemRepository itemRepository;

    /*PostConstruct annotation means that the method will be called automatically during deployment*/
    @PostConstruct
    public void init(){
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        roleRepository.save(roleUser);

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleRepository.save(roleAdmin);

        User userAdmin = new User();
        userAdmin.setEnabled(true);
        userAdmin.setName("admin");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userAdmin.setPassword(encoder.encode("admin"));
        List<Role> roles = new ArrayList<>();
        roles.add(roleAdmin);
        roles.add(roleUser);
        userAdmin.setRoles(roles);
        userRepository.save(userAdmin);

        User userUser = new User();
        userUser.setEnabled(true);
        userUser.setName("user");
        BCryptPasswordEncoder userEncoder = new BCryptPasswordEncoder();
        userUser.setPassword(userEncoder.encode("user"));
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        userUser.setRoles(userRoles);
        userRepository.save(userUser);

        Blog blogJavavids = new Blog();
        blogJavavids.setName("JavaVids");
        blogJavavids.setUrl("http://feeds.feedburner.com/javavids?format=xml");
        blogJavavids.setUser(userAdmin);
        blogRepository.save(blogJavavids);

        Blog blogJavaWorldCore = new Blog();
        blogJavaWorldCore.setName("JavaWorld");
        blogJavaWorldCore.setUrl("http://www.javaworld.com/category/core-java/index.rss");
        blogJavaWorldCore.setUser(userAdmin);
        blogRepository.save(blogJavaWorldCore);

        Blog blogTomcat = new Blog();
        blogTomcat.setName("Tomcat");
        blogTomcat.setUrl("http://www.tomcatexpert.com/blog/feed");
        blogTomcat.setUser(userUser);
        blogRepository.save(blogTomcat);

        Blog blogMkyong = new Blog();
        blogMkyong.setName("Mkyong");
        blogMkyong.setUrl("http://feeds.feedburner.com/FeedForMkyong");
        blogMkyong.setUser(userUser);
        blogRepository.save(blogMkyong);


/*
        Item item1 = new Item();
        item1.setBlog(blogJavavids);
        item1.setTitle("first");
        item1.setLink("http://www.javavids.com");
        item1.setPublishedDate(new Date());
        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setBlog(blogJavavids);
        item2.setTitle("second");
        item2.setLink("http://www.javavids.com");
        item2.setPublishedDate(new Date());
        itemRepository.save(item2);
*/
    }
}
