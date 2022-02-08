package com.koreait.cs.controller;

import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.User;
import com.koreait.cs.mapper.TweetMapper;
import com.koreait.cs.mapper.UserProfileMapper;
import com.koreait.cs.repository.TweetRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @Controller annotation will return information on view information on html
// however this is not the case for restful api case, in this case you just return an XML content
// or JSON data, you don't need to worry about to views, it just responds your data

/*
    사람들 중에 하나의 개인 정보를 출력할 수 있게 해주는 것이 REST API 작업니다
    예를 들어 localhost:8080/users/userDetail
 */

@RestController
public class ApiGenerator {

    private UserProfileMapper mapper;
    private TweetMapper tweetMapper;


    public ApiGenerator(UserProfileMapper mapper, TweetMapper tweetMapper) {
        this.mapper = mapper;
        this.tweetMapper = tweetMapper;
    }


//    @GetMapping(value = "/{email}", produces = "application/json")
//    public User getUserProfile(@PathVariable("email") String email, Model model){
////        model.addAttribute("other", mapper.getUserProfile(email))
//        return mapper.getUserProfile(email);
//    }

    @GetMapping(path = "/allinfo", produces = "application/json")
    public List<TweetBoard> allInfo(){
        return tweetMapper.getTweetInfo();
    }

}
