package com.koreait.cs.repository;

import com.koreait.cs.entities.Reply;
import com.koreait.cs.entities.TweetBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepositoryBoardID extends JpaRepository<Reply, Long> {
//    List<TweetBoard> findByTweetBoards(Long id);
//    List<Reply> findByTweetBoards(Long id);
//    Reply findByTweetBoards(Long id);
//        Reply findByContent(String content);          // 사용 가능
//    Reply findByTweetBoards(TweetBoard tweetBoards);
//    Reply deleteByTweetBoards(TweetBoard tweetBoards);
    Reply findByTweetBoards (TweetBoard tweetBoards);
}


//    List<Reply> findByTweetBoardId(TweetBoard tweetBoards);
//    List<Reply> findByUserId(Long id);
//    List<Reply> findById(Long id);