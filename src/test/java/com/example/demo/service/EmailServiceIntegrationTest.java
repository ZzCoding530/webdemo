package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;

@SpringBootTest
public class EmailServiceIntegrationTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendDailyReminderEmail() {
        String to = "rookiezc@163.com";  // 使用一个测试用的学生邮箱

        try {
            System.out.println("正在发送每日学习提醒邮件...");
            emailService.sendDailyReminderEmail(to);
            System.out.println("每日学习提醒邮件发送成功。");
            Assertions.assertTrue(true, "每日学习提醒邮件发送成功");
        } catch (MailException e) {
            System.out.println("发送每日学习提醒邮件失败: " + e.getMessage());
            Assertions.fail("发送每日学习提醒邮件失败: " + e.getMessage());
        }
    }
}