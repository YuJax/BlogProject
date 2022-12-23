package com.example.blog.service;

import com.example.blog.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

@Component
@CrossOrigin
public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        String token = request.getHeader("Authorization");
        System.out.printf("token: %s\n", token);
        return true;
//        if(token == null) {
//            response.setStatus(401);
//            return false;
//        }
//        try {
//            String key = "secret";
//            String username =  Jwts.parser()
//                .setSigningKey(key)
//                .parseClaimsJws(token.replace("Bearer ", ""))
//                .getBody()
//                .getSubject();
//            if (username == null) {
//                response.setStatus(401);
//                return false;
//            }
//
//            if (userRepository.findByUsername(username) == null) {
//                response.setStatus(401);
//                return false;
//            }
//
//
//        }catch (Exception e) {
//            response.setStatus(401);
//            return false;
//        }
//
//
//        return true;
    }
}
