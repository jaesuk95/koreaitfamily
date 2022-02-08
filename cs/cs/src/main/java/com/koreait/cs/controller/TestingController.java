package com.koreait.cs.controller;

import com.koreait.cs.entities.Reply;
import com.koreait.cs.repository.ReplyRepositoryBoardID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class TestingController {

    @Autowired
    ReplyRepositoryBoardID replyRepositoryBoardID;

    @GetMapping("/testing")
    public String testing(Model model) {

        long tweet_board_id = 5;
        String content = "yo";

//        List<Reply> lists = replyRepositoryBoardID.findByTweetBoards(tweet_board_id);
//        List<Reply> lists = replyRepositoryBoardID.findByTweetBoards(tweet_board_id);

//        Reply lists2 = replyRepositoryBoardID.findByContent(content);


//        findById (Long id);
//        TweetBoard findByTweetBoards(Long id);
//        model.addAttribute("a", lists);

        return "views/testing";
    }
}
