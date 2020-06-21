package com.example.jwt.dto;

import com.example.jwt.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResult extends CommonResult {
    private User user;
    private String token;
}
