package com.cloume.jwtsecurity;

import com.cloume.jwtsecurity.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PsyApiApplicationTests {
    @Test
    public void addUser() {
        User addUser = new User();
        addUser.setUsername("testUser");
        addUser.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        System.out.println(addUser);
    }
}
