package cn.edu.seu.historycontest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class HistoryContestApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

}
