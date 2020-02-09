package org.zaker.blogtest.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.zaker.blog.dao.UserDao;
import org.zaker.blog.services.UserService;


//为功能模块编写单元测试
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mocEncoder;
    @Mock
    UserDao mockDao;
    @InjectMocks
    UserService userSerivce;

    Logger logger = LoggerFactory.getLogger(getClass());
    //基于模拟的单元测试
    @Test
    public void testSave(){
        Mockito.when(mocEncoder.encode("mypassword")).thenReturn("myencodedpassword");
        userSerivce.insertNewSignUpUser("myUser","mypassword");
        Mockito.verify(mockDao).insertNewSignUpUser("myUser","myencodedpassword");
    }
    @Test
    public void testGetUserByUserName(){
        userSerivce.getUserByUserName("myUser");
        Mockito.verify(mockDao).findUserByUserName("myUser");
    }
    @Test
    public void throwExceptionWhenUserNotFound(){
        Mockito.when(mockDao.findUserByUserName("myUser")).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class,()->userSerivce.loadUserByUsername("myUser"));
    }

    @Test
    public void contextLoads(){
        logger.info("开始测试");
    }


}
