package org.zaker.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zaker.blog.dao.BlogDao;
import org.zaker.blog.entity.Blog;
import org.zaker.blog.entity.BlogListResult;
import org.zaker.blog.entity.BlogResult;
import org.zaker.blog.entity.User;

import java.util.List;
import java.util.Objects;

@Service
public class BlogService {
    private BlogDao blogDao;

    @Autowired
    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public BlogListResult getBlogs(Integer page, Integer pageSize, Integer userId) {
        try {
            List<Blog> blogs = blogDao.getBlogs(page, pageSize, userId);
            int count = blogDao.count(userId);
            int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            return BlogListResult.success(blogs, count, page, pageCount);
        } catch (Exception e) {
            return BlogListResult.failure("系统异常");
        }
    }

    public BlogResult getBlogById(Long blogId) {
        try {
            Blog blog = blogDao.getBlogById(blogId);
            return BlogResult.success("获取成功",blogDao.getBlogById(blogId));
        } catch (Exception e) {
            return BlogResult.failure(e);
        }
    }

    public BlogResult insertBlog(Blog blog) {
        try {
            return BlogResult.success( "创建成功", blogDao.insertBlog(blog));
        } catch (Exception e) {
            return BlogResult.failure(e);
        }
    }

    public BlogResult updateBlog(Long blogId, Blog targetBlog) {
        Blog blogById = blogDao.getBlogById(blogId);
        if (blogById == null) {
            return BlogResult.failure("博客不存在");
        }
        if (!Objects.equals(blogId, blogById.getId())) {
            return BlogResult.failure("无法修改别人的博客");
        }
        try {
            targetBlog.setId(blogId);
            return BlogResult.success("修改成功",blogDao.updateBlog(targetBlog));
        } catch (Exception e) {
            return BlogResult.failure(e);
        }
    }

    public BlogResult deleteBlog(Long blogId, User user) {
        Blog blogById = blogDao.getBlogById(blogId);
        if (blogById == null) {
            return BlogResult.failure("博客不存在");
        }
        if (!Objects.equals(user.getId(), blogById.getUser().getId())) {
            return BlogResult.failure("无法修改别人的博客");
        }
        try {
            blogDao.deleteBlogById(blogId);
            return BlogResult.success( "修改成功");
        } catch (Exception e) {
            return BlogResult.failure(e);
        }

    }

}
