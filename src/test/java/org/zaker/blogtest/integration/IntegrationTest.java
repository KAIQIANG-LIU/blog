package org.zaker.blogtest.integration;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zaker.blog.BlogApplication;


import java.io.IOException;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BlogApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
class IntegrationTest {
    @Autowired
    Environment environment;
    @Test
    void mytest(){
        OkHttpClient okHttpClient = new OkHttpClient();
        String port = environment.getProperty("local.server.port");
        Request request = new Request.Builder().url("http://localhost:" + port + "/auth").build();
        try{
            Response response = okHttpClient.newCall(request).execute();
            Assertions.assertEquals(200,response.code());
            Assertions.assertTrue(Objects.requireNonNull(response.body()).string().contains("用户没有登录"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
