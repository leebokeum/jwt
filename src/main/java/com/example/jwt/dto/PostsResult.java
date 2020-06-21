package com.example.jwt.dto;

import java.util.List;
import com.example.jwt.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostsResult extends CommonResult{
    private List<Post> posts;
}
