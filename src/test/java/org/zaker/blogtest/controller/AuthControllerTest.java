package org.zaker.blogtest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.zaker.blog.controller.AuthController;
import org.zaker.blog.services.AuthService;
import org.zaker.blog.services.UserService;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//为MVC编写单元测试
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    private MockMvc mvc;
    @Mock
    private AuthService authService;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new AuthController(authService,userService,authenticationManager)).build();
    }
    @Test
    void returnNotLoginByDefault() throws Exception {
        mvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(mvcResult -> System.out.println(mvcResult.getResponse().getContentAsString()));
    }
}
