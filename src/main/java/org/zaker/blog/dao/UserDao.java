package org.zaker.blog.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zaker.blog.entity.User;


import java.util.HashMap;
import java.util.Map;

@Component
public class UserDao {
    private SqlSession sqlSession;

    @Autowired
    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public User findUserByUserName(String username) {
        return sqlSession.selectOne("UserMapper.findUserByUserName", username);
    }

    public void insertNewSignUpUser(String username, String password) {
        Map<String, String> user = new HashMap<>();
        user.put("username", username);
        user.put("password", password);
        sqlSession.insert("UserMapper.insertNewSignUpUser", user);
    }
}
