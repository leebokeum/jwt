package com.example.jwt.controller.v1;

import java.util.Collections;
import javax.jws.soap.SOAPBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.jwt.advice.exception.CEmailSigninFailedException;
import com.example.jwt.config.security.JwtTokenProvider;
import com.example.jwt.dto.CommonResult;
import com.example.jwt.dto.LoginResult;
import com.example.jwt.dto.SingleResult;
import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin("*")
@Api(tags = {"1. Sign"})
@RestController
@RequestMapping(value = "/v1")
public class SignController {
    private final UserRepository userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignController(UserRepository userJpaRepo, JwtTokenProvider jwtTokenProvider,
        ResponseService responseService, PasswordEncoder passwordEncoder) {
        this.userJpaRepo = userJpaRepo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.responseService = responseService;
        this.passwordEncoder = passwordEncoder;
    }

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public LoginResult signin(@ApiParam(value = "회원ID, 비밀번호") @RequestBody User paramUser) {
        User user = userJpaRepo.findByUserName(paramUser.getUserName()).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(paramUser.getPassword(), user.getPassword()))
            throw new CEmailSigninFailedException();
        return responseService.getLoginSuccessResult(user, jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()));
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "회원ID : 이메일, 회원 비밀번호, 별명", required = true) @RequestBody User user) {
        userJpaRepo.save(User.builder()
            .userName(user.getUsername())
            .password(passwordEncoder.encode(user.getPassword()))
            .nickName(user.getNickName())
            .roles(Collections.singletonList("ROLE_USER"))
            .build());
        return responseService.getSuccessResult();
    }
}