package org.zaker.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.zaker.blog.entity.LoginResult;
import org.zaker.blog.entity.User;
import org.zaker.blog.services.AuthService;
import org.zaker.blog.services.UserService;


import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    public AuthController(AuthService authService, UserService userService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }
    private final AuthService authService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/auth")
    @ResponseBody
    public LoginResult auth() {
        //使用COOKIES拿到用户名
        return authService.getCurrentUser().map(LoginResult::success).orElse(LoginResult.success("用户没有登录",false));
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public LoginResult logout() {
        SecurityContextHolder.clearContext();
        return authService.getCurrentUser()
                .map(user -> LoginResult.success("success", false))
                .orElse(LoginResult.failure("用户没有登录"));
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public LoginResult register(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        if (username == null || password == null) {
            return LoginResult.failure("username/password == null");
        } else if (username.length() < 1 || username.length() > 15) {
            return LoginResult.failure("invalid username");
        } else if (password.length() < 1 || password.length() > 15) {
            return LoginResult.failure("invalid password");
        }
        try {
            userService.insertNewSignUpUser(username, password);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
        } catch (DuplicateKeyException e) {
            return LoginResult.failure("user already exists");
        }
        return LoginResult.success("注册成功", userService.getUserByUserName(username));
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Object login(@RequestBody Map<String, Object> userNameAndPassword) {
        String userName = userNameAndPassword.get("username").toString();
        String passwd = userNameAndPassword.get("password").toString();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(userName);
        } catch (UsernameNotFoundException e) {
            return LoginResult.failure("用户不存在");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, passwd);
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return LoginResult.success("登录成功",userService.getUserByUserName(userName));
        } catch (BadCredentialsException e) {
            return LoginResult.failure("密码不正确");
        }
    }


}
