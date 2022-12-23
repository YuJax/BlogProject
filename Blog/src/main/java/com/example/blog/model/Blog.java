package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;


/*
create table db.blog
(
    title     varchar(100)   not null,
    content   varchar(10000) not null,
    post_date datetime       not null,
    author    varchar(100)   not null,
    end_date  datetime       not null,
    id        int auto_increment
        primary key
);

*/
@Entity
@Table(name = "blog")
public class Blog {

    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "post_date")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date post_date;
    @Column(name = "author")
    private String author;
    @Column(name = "end_date")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date end_date;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
