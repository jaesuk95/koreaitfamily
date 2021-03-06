package com.koreait.cs.controller;

import com.koreait.cs.entities.Reply;
import com.koreait.cs.entities.TweetBoard;
import com.koreait.cs.entities.User;
import com.koreait.cs.repository.*;
import com.koreait.cs.service.ReplyService;
import com.koreait.cs.service.TweetSearchService;
import com.koreait.cs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Controller
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyRepositoryBoardID replyRepositoryBoardID;

    @Autowired
    private TweetRepositorySearch tweetRepositorySearch;

    @Autowired
    private TweetSearchService tweetSearchService;

    @Autowired
    private UserRepositoryProfile userRepositoryProfile;

    @GetMapping("/tweet")                        // shows all the lists of the tweets
    public String listsOfTweets(Model model, Principal principal) {
        List<TweetBoard> boards = tweetRepository.findAll();
        String email = principal.getName();                         // added late
        User user = userService.findOneByEmail(email);              // added late
        String diffuse = user.getEmail();                           // added late
        model.addAttribute("profile", userRepositoryProfile.findByEmail(diffuse));                  // added late
        model.addAttribute("tweets", boards);
        return "views/tweetindex";
    }

    @GetMapping("/form")                        // shows the tweet form you want to submit
    public String tweetForm(Model model, Principal principal) {
        model.addAttribute("tweet", new TweetBoard());
        return "views/tweetForm";
    }


    //    @GetMapping("/tweet/{id}")                                                           // ???????????? ????????? ??????
    @GetMapping("/profile/{email}/comment/{id}")
    // ???????????? ????????? ??????
    public String showSpecificComment(@PathVariable("email") String email, @PathVariable("id") Long id, Model model, Principal principal) {

        // PRINCIPAL DETAIL
        String principalName = principal.getName();                         // principalName = jaesuk95@outlook.com or etc... current principal user
        User principalUser = userService.findOneByEmail(principalName);              // added late
        String principalEmail = principalUser.getEmail();                            // principal email


        if (principalEmail.equals(email)){
            List<TweetBoard> userAuthority = tweetRepositorySearch.findByUser(principalUser);   // brings you all the lists from tweet board under same principal same username
            TweetBoard userAuthority2 = tweetRepository.getById(id);
            Collections.reverse(userAuthority);
            model.addAttribute("userAuthority",userAuthority2);

            TweetBoard sneakyTweetBoard = tweetRepository.getById(id);
            model.addAttribute("comm", sneakyTweetBoard);           // this section is made due to form submit.
                                                                                // the form requires the current page email, not principal email
            model.addAttribute("response", tweetSearchService.viewComments(id));    // ?????? ?????? ?????? ??? ?????? ??????
        } else {
            TweetBoard tweetBoard = tweetRepository.getById(id);
            model.addAttribute("comments", tweetBoard);     // "comments ??? ????????? ??????"

//            model.addAttribute("response", tweetSearchService.viewComments(id));
        }

        User otherUser = userService.findOneByEmail(email);          // ?????? ?????? ???????????? USER ??? ????????????, ????????? ?????????

        model.addAttribute("reply", new Reply());       // "for new sub-comment(reply messages)
        model.addAttribute("response", tweetSearchService.viewComments(id));
//        User boardUserDetail = tweetBoard.getUser();
//        String boardUserEmail = boardUserDetail.getEmail();
        model.addAttribute("profile", userRepositoryProfile.findByEmail(principalEmail));                  // user profile data
        return "views/reply/viewResponse";
    }



//    @PostMapping("/tweet/reply")
    @PostMapping("profile/{email}/comment/{id}/reply")
    public String commentInserted(Model model, @RequestParam(value = "content", required = false) String content,
                                               @RequestParam(value = "id", required = false) Long id,
                                               @PathVariable("email") String email,
                                               @RequestParam(value = "image", required = false) MultipartFile multipartFile,
                                                Principal principal) throws IOException {

//        http://localhost:8080/profile/martin@gmail.com/comment/18/reply?content=hello&id=18

        System.out.println("------------------------------ content ---------" + content + " ------------------------");
        System.out.println("------------------------------ id ---------" + id + " ------------------------");
        System.out.println("------------------------------ email ---------" + email + " ------------------------");

        String user = principal.getName();      // gets the name of the user
        User userInfo = userService.findOneByEmail(user);      // gets the email by specifying the current user name
        String principalEmail = userInfo.getEmail();

//        TweetBoard tweetBoardIdentity = tweetRepository.findById(id).orElse(null);          // finds the tweet board through ID
//        Reply replyRepositoryId = replyRepositoryBoardID.findByTweetBoards(tweetBoardIdentity);     //
//        String replyImage = replyRepositoryId.getReplyImage();                                       // you do not need it because there is no default setting

        String originalName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
        String dateStr = dateFormat.format(calendar.getTime());

        if (originalName == ""){

        } else {
            String fileName = dateStr+originalName;
            String uploadDirectory = "./response-image/" + principalEmail + "/";

            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);                                    // ?????? ??????
            } try (InputStream inputStream = multipartFile.getInputStream()){
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e){
//            throw new IOException("could not save uploaded file: " + fileName);
            }
        }

        Reply reply = new Reply();          // create a new
        reply.setContent(content);          // save the content to reply

//        if (fileName == null || fileName.isEmpty()){        // ?????? ????????? ????????? ??? ????????? ??????, ?????? ?????? ???????????? ????????????
//            reply.setReplyImage(replyImage);
//        } else {
//            reply.setReplyImage(fileName);
//        }
        replyService.addResponse(userInfo, id, reply, multipartFile);
        return "redirect:/profile/" + email + "/comment/" + id;         // received email and id parameters from form
    }
}




// old method
//    @PostMapping("/form")                       // receives 'form' from tweetForm
//    public String submittedForm(@Valid TweetBoard tweetBoard, BindingResult bindingResult, Principal principal) {
//        if (bindingResult.hasErrors()) {
//            return "views/taskForm";
//        }
//        String user = principal.getName();      // gets the name of the user
//        User email = userService.findOneByEmail(user);      // gets the email by specifying the current user name
//        String diffuse = email.getEmail();
//        System.out.println("----------------------------------" + email + " 3333333333333333333333333333333333");
//        tweetSearchService.addTweet(tweetBoard, userService.findOneByEmail(diffuse));
//
//        return "redirect:/home";
//    }
