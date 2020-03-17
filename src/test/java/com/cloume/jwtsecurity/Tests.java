package com.cloume.jwtsecurity;

import com.cloume.jwtsecurity.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests {
    @Test
    public void addUser() {
        User addUser = new User();
        addUser.setUsername("testUser");
        addUser.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        System.out.println(addUser);
    }

    @Test
    public void addTime() {
        long today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long seven_day_start = LocalDateTime.of(LocalDate.now().plusDays(-7), LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(today_end);
        System.out.println(seven_day_start);
    }
}
