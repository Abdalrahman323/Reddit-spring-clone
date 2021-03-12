package com.springredditclone.redditclone.service;

import com.springredditclone.redditclone.dao.entities.NotificationEmail;
import com.springredditclone.redditclone.exceptions.SpringRedditException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j // will create instact of slf4j logger object & inject it to our class
public class MailService {

    private final JavaMailSender  javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("springReddit@email.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
           mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Activation email sent !! ");

        } catch (MailException e){
//            System.out.println(e);
            log.info(e.toString());
            throw new SpringRedditException("Exception occured when sending mail to " +notificationEmail.getRecipient(),e);
        }
    }
}
