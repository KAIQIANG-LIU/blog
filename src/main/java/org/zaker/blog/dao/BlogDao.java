package org.zaker.blog.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zaker.blog.entity.Blog;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BlogDao {
    private SqlSession sqlSession;

    @Autowired
    public BlogDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Blog> getBlogs(Integer page, Integer pageSize, Integer userId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", userId);
        parameters.put("offset", (page - 1) * pageSize);
        parameters.put("limit", pageSize);
        return sqlSession.selectList("BlogMapper.selectBlog", parameters);
    }

    public Blog getBlogById(Long id) {
        return sqlSession.selectOne("BlogMapper.selectBlogById", id);
    }

    public void deleteBlogById(Long id) {
        sqlSession.delete("BlogMapper.deleteBlogById", id);
    }

    public int count(Integer usrId) {
        Map<String,Object> params = new HashMap<>();
        params.put("userId",usrId);
        return sqlSession.selectOne("BlogMapper.countBlog", params);
    }

    public Blog insertBlog(Blog newBlog) {
        sqlSession.insert("BlogMapper.insertBlog", newBlog);
        return getBlogById(newBlog.getId());
    }

    public Blog updateBlog(Blog targetBlog) {
        sqlSession.update("BlogMapper.updateBlog", targetBlog);
        return getBlogById(targetBlog.getId());
    }
}
