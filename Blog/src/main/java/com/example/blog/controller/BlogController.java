package com.example.blog.controller;

import com.example.blog.model.Blog;
import com.example.blog.model.BlogIterable;
import com.example.blog.repository.BlogRepository;
import com.example.blog.service.Message;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @GetMapping("/blog")
    public ResponseEntity<BlogIterable> getPageBlog(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        // order by post_date desc
        Page<Blog> blogPage = blogRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
        BlogIterable blogIterable = new BlogIterable();
        blogIterable.setBlogs(blogPage);
        blogIterable.setCurrentPage(page);
        blogIterable.setTotalPage(blogPage.getTotalPages());
        return ResponseEntity.ok(blogIterable);
    }


    @PostMapping("/blog")
    @CrossOrigin
    public ResponseEntity<Message> blog(@RequestBody Blog blog, @RequestHeader("Authorization") String token) {
        String username = getUsernameFromToken(token);
        blog.setAuthor(username);
        blogRepository.save(blog);
        Message message = new Message();
        message.setMessage("Post success");
        return ResponseEntity.ok(message);
    }

    private String getUsernameFromToken(String token) {
        String key = "secret";
        String username =  Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody()
                .getSubject();
        return  username;
    }

    @PutMapping("/blog/{id}")
    @CrossOrigin
    public ResponseEntity<Message> blogPut(@PathVariable Integer id, @RequestBody Blog blog, @RequestHeader("Authorization") String token) {
        String username = getUsernameFromToken(token);
        Blog tmp = blogRepository.findById(id).get();
        Message message = new Message();
        if (!tmp.getAuthor().equals(username)) {
            message.setMessage("You can't edit this post");
            return ResponseEntity.status(403).body(message);
        }

        if(tmp != null) {
            tmp.setTitle(blog.getTitle());
            tmp.setContent(blog.getContent());
            tmp.setPost_date(blog.getPost_date());
            tmp.setAuthor(blog.getAuthor());
            tmp.setEnd_date(blog.getEnd_date());
            blogRepository.save(tmp);
            message.setMessage("Update success");
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(404).body(message);
    }

    @DeleteMapping("/blog/{id}")
    public ResponseEntity<Message> deleteBlog(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String username = getUsernameFromToken(token);
        Blog tmp = blogRepository.findById(id).get();
        Message message = new Message();
        if (!tmp.getAuthor().equals(username)) {
            message.setMessage("You can't delete this post");
            return ResponseEntity.status(403).body(message);
        }

        blogRepository.deleteById(id);
        message.setMessage("Delete success");
        return ResponseEntity.ok(message);
    }
}
