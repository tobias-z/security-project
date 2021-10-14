package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;

//imports for sending mail
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserRepository implements IUserRepository {
    @Override
    public User someMethod(String username,String userEmail) {
        // Only done to show how to test services
        // Would be a db call or something
        return new User(username, UserRole.USER, userEmail);
    }



}
