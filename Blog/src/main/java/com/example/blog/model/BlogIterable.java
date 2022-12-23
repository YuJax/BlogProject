package com.example.blog.model;

import org.springframework.data.domain.Page;

public class BlogIterable {
    private int totalPage;
    private int currentPage;
    private Page<Blog> blogs;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Iterable<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(Page<Blog>  blogs) {
        this.blogs = blogs;
    }
}
