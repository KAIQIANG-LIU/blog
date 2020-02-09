package org.zaker.blog.entity;

public class BlogResult extends Result<Blog>{
    protected BlogResult(ResultStatus status, String msg, Blog data) {
        super(status, msg, data);
    }

    public static BlogResult failure(String message) {
        return new BlogResult(ResultStatus.fail, message, null);
    }

    public static BlogResult failure(Exception e) {
        return new BlogResult(ResultStatus.fail, e.getMessage(), null);
    }

    public static BlogResult success(String msg) {
        return new BlogResult(ResultStatus.ok, msg, null);
    }

    public static BlogResult success(String msg, Blog blog) {
        return new BlogResult(ResultStatus.ok, msg, blog);
    }
}
