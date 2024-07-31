package com.example.demo.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sendHtmlMessage(String to, String subject, String htmlBody);
    void sendDailyReminderEmail(String to);  // 新添加的方法

    String generateDailyReminderContent();
}