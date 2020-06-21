package com.example.jwt.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.jwt.dto.ListResult;
import com.example.jwt.dto.PostsResult;
import com.example.jwt.dto.SingleResult;
import com.example.jwt.entity.Post;
import com.example.jwt.entity.User;
import com.example.jwt.repository.PostRepository;
import com.example.jwt.service.ResponseService;
import io.swagger.annotations.Api;

@CrossOrigin("*")
@Api(tags = {"3. Post"})
@RestController
@RequestMapping(value = "/v1")
public class PostController {
    private final PostRepository postRepository;
    private final ResponseService responseService; // 결과를 처리할 Service

    @Autowired
    public PostController(PostRepository postRepository, ResponseService responseService) {
        this.postRepository = postRepository;
        this.responseService = responseService;
    }

    @GetMapping(value = "/posts")
    public PostsResult findAllPost() {
        // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
        return responseService.getPostsResult(postRepository.findAll());
    }

    @GetMapping(value = "/posts/{id}")
    public SingleResult findPost(@PathVariable("id") long id) {
        return responseService.getSingleResult(postRepository.findById(id));
    }

    @PutMapping(value = "/posts/{id}")
    public ListResult<Post> modifyPost(@PathVariable("id") long id) {
        //TODO 회원정보 수정
        return responseService.getListResult(postRepository.findAll());
    }

    @PostMapping(value = "/posts")
    public SingleResult savePost(@RequestBody Post post) {
        return responseService.getSingleResult(postRepository.save(post));
    }

    @DeleteMapping(value = "/posts/{id}")
    public SingleResult deletePosts(@PathVariable("id") long id) {
        postRepository.deleteById(id);
        return responseService.getSingleResult("..deleted");
    }
}
