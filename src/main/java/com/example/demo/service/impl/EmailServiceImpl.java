package com.example.demo.service.impl;

import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendDailyReminderEmail(String to) {
        String subject = "每日学习提醒";
        String content = generateDailyReminderContent();
        sendHtmlMessage(to, subject, content);
    }


    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            logger.info("准备发送简单邮件到: {}", to);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            logger.info("简单邮件已成功发送到: {}", to);
        } catch (Exception e) {
            logger.error("发送简单邮件时发生错误: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void sendHtmlMessage(String to, String subject, String htmlBody) {
        try {
            logger.info("准备发送HTML邮件到: {}", to);
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);  // 第二个参数true表示这是HTML内容

            emailSender.send(message);
            logger.info("HTML邮件已成功发送到: {}", to);
        } catch (MessagingException e) {
            logger.error("发送HTML邮件时发生错误: {}", e.getMessage(), e);
            throw new RuntimeException("发送HTML邮件失败", e);
        }
    }

    @Override
    public String generateDailyReminderContent() {
        String htmlContent = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                "h1 { color: #2c3e50; font-size: 24px; }" +
                "h2 { color: #2980b9; font-size: 20px; }" +
                ".highlight { background-color: #f1c40f; padding: 5px; border-radius: 3px; }" +
                ".question { color: #2980b9; font-weight: bold; }" +
                ".motivation { font-size: 18px; color: #e74c3c; font-weight: bold; }" +
                ".quote { font-style: italic; color: #27ae60; }" +
                ".interview-question { margin-bottom: 10px; }" +
                ".company { font-weight: bold; color: #8e44ad; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1>📚 每日学习提醒</h1>" +
                "<p>亲爱的同学，这是你的AI学习助理。现在是复习和学习的黄金时间哦！</p>" +
                "<p>今天是 <span class='highlight'>%s</span>，距离你的目标校招还有 <span class='highlight'>%d 天</span>。</p>" +
                "<p>🎯 <strong>今天的学习任务完成了吗？</strong></p>" +
                "<p class='question'>1. 📖 算法题目练习完成了吗？</p>" +
                "<p class='question'>2. 📝 专业课复习进度如何？</p>" +
                "<p class='question'>3. 🖥️ 项目经验整理得怎么样了？</p>" +
                "<p class='question'>4. 🤔 遇到什么困难了吗？需要我的帮助吗？</p>" +
                "<p>💡 记得劳逸结合，适当休息能提高学习效率哦！</p>" +
                "<h2>📝 今日面经精选</h2>" +
                "%s" +
                "<p class='quote'>%s</p>" +
                "<p class='motivation'>加油！相信自己，你一定能成功！🚀</p>" +
                "</body>" +
                "</html>";

        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));

        LocalDate recruitmentDate = LocalDate.of(2024, 9, 1);
        long daysUntilRecruitment = ChronoUnit.DAYS.between(today, recruitmentDate);

        String dailyQuote = "成功的秘诀是坚持不懈的努力。 - 爱迪生";

        String interviewQuestions = generateInterviewQuestions();

        return String.format(htmlContent, formattedDate, daysUntilRecruitment, interviewQuestions, dailyQuote);
    }

    private String generateInterviewQuestions() {
        List<String> allQuestions = new ArrayList<>();
        allQuestions.add("<div class='interview-question'><span class='company'>【阿里巴巴】</span> 请解释一下什么是线程安全，如何实现线程安全？</div>");
        allQuestions.add("<div class='interview-question'><span class='company'>【腾讯】</span> 描述一下Java中的垃圾回收机制。</div>");
        allQuestions.add("<div class='interview-question'><span class='company'>【字节跳动】</span> 什么是死锁？如何避免死锁？</div>");
        allQuestions.add("<div class='interview-question'><span class='company'>【百度】</span> 请解释一下HTTP和HTTPS的区别。</div>");
        allQuestions.add("<div class='interview-question'><span class='company'>【美团】</span> 什么是分布式事务？如何实现？</div>");
        allQuestions.add("<div class='interview-question'><span class='company'>【华为】</span> 请描述一下TCP的三次握手过程。</div>");
        allQuestions.add("<div class='interview-question'><span class='company'>【京东】</span> 什么是ORM？它有什么优点和缺点？</div>");
        allQuestions.add("<div class='interview-question'><span class='company'>【网易】</span> 请解释一下什么是MVCC（多版本并发控制）。</div>");
        allQuestions.add("<div class='interview-question'><span class='company'>【小米】</span> 什么是AOP（面向切面编程）？它有什么用途？</div>");
        allQuestions.add("<div class='interview-question'><span class='company'>【拼多多】</span> 请描述一下什么是CAP定理。</div>");
        allQuestions.add("<div class='interview-question'><span class='company'>【滴滴】</span> 什么是Redis的持久化？有哪些方式？</div>");
        allQuestions.add("<div class='interview-question'><span class='company'>【快手】</span> 请解释一下Java中的volatile关键字。</div>");

        Collections.shuffle(allQuestions);
        List<String> selectedQuestions = allQuestions.subList(0, Math.min(10, allQuestions.size()));

        StringBuilder questionHtml = new StringBuilder();
        for (String question : selectedQuestions) {
            questionHtml.append(question);
        }

        return questionHtml.toString();
    }
}