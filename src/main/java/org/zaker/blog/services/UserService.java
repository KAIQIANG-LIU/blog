package org.zaker.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zaker.blog.dao.UserDao;


import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserDao userDao;

    @Autowired
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserDao userDao) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDao = userDao;
    }

    public org.zaker.blog.entity.User getUserByUserName(String username) {
        return userDao.findUserByUserName(username);
    }

    public void insertNewSignUpUser(String username, String password) {
        userDao.insertNewSignUpUser(username, bCryptPasswordEncoder.encode(password));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.zaker.blog.entity.User user = userDao.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "用户不存在");
        }
        return new User(user.getUsername(), user.getEncryptedPassword(), Collections.emptyList());
    }
}
