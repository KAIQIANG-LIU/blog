package org.zaker.blog.controller;

import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zaker.blog.entity.*;
import org.zaker.blog.services.AuthService;
import org.zaker.blog.services.BlogService;


import java.util.Map;

@RestController
public class BlogController {
    private final BlogService blogService;
    private final AuthService authService;

    @Autowired
    public BlogController(BlogService blogService, AuthService authService) {
        this.blogService = blogService;
        this.authService = authService;
    }

    @GetMapping("/blog")
    @ResponseBody
    public BlogListResult getBlogs(@RequestParam("page") Integer page, @RequestParam(value = "userId", required = false) Integer userId) {
        if (page == null || page < 0) {
            page = 1;
        }
        return blogService.getBlogs(page, 10, userId);
    }

    @GetMapping("/blog/{blogId}")
    @ResponseBody
    public BlogResult getBlog(@PathVariable("bolgId") Long blogId) {
        return blogService.getBlogById(blogId);
    }

    @PostMapping("/blog")
    @ResponseBody
    public BlogResult newBlog(@RequestBody Map<String, String> param) {
        try {
            return authService.getCurrentUser().map(user -> blogService.insertBlog(fromParam(param, user))).orElse(BlogResult.failure("登录后才能操作"));
        } catch (IllegalArgumentException e) {
            return BlogResult.failure(e);
        }
    }

    @PatchMapping("/blog/{blogId}")
    @ResponseBody
    public BlogResult updateBlog(@PathVariable("blogId") Long blogId, @RequestBody Map<String, String> param) {
        try {
            return authService.getCurrentUser()
                    .map(user -> blogService.updateBlog(blogId, fromParam(param, user)))
                    .orElse(BlogResult.failure("登录后才能操作"));
        } catch (IllegalArgumentException e) {
            return BlogResult.failure(e);
        }
    }

    @DeleteMapping("/blog/{blogId}")
    @ResponseBody
    public Result deleteBlog(@PathVariable("blogId") Long blogId) {
        try {
            return authService.getCurrentUser()
                    .map(user -> blogService.deleteBlog(blogId, user))
                    .orElse(BlogResult.failure("登录后才能操作"));
        } catch (IllegalArgumentException e) {
            return BlogResult.failure(e);
        }
    }


    private Blog fromParam(Map<String, String> params, User user) {
        Blog blog = new Blog();
        String title = params.get("title");
        String content = params.get("content");
        String description = params.get("description");

        if (StringUtils.isBlank(description)) {
            description = content.substring(0, Math.min(content.length(), 10)) + "...";
        }

        blog.setTittle(title);
        blog.setContent(content);
        blog.setDescription(description);
        blog.setUser(user);
        return blog;
    }
}

