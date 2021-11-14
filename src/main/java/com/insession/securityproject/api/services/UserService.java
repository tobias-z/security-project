package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.infrastructure.entities.UserEntity;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;
public class UserService implements IUserService {
    private final IUserRepository repository;

    public UserService(IUserRepository repository) {
        this.repository = repository;
    }

    // Only used to give example of testing the services

    @Override
    public void sendPinMail( User user) {

        AuthPinCodeService authPinCodeService=AuthPinCodeService.getInstance();
        int pinCode=authPinCodeService.getNewPinCode(user.getUsername());

        // Recipient's email ID needs to be mentioned.
        String to = user.getUserEmail();

        // Sender's email ID needs to be mentioned
        String from = System.getenv("SEC_USERNAME");

        // Sender's password ID needs to be mentioned ------!!!!!!!!!!!!!
        String password=System.getenv("SEC_PASSWORD");

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Your onetime Pin Code");

            // Now set the actual message
            message.setText("Your one time Pin Code is: "+pinCode);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }



    }

    @Override
    public void sendPinSMS(User user) {

        String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
        String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
        String TWILIO_NUMBER=System.getenv("TWILIO_NUMBER");
        AuthPinCodeService authPinCodeService=AuthPinCodeService.getInstance();
        int pinCode=authPinCodeService.getNewPinCode(user.getUsername());
        System.out.println(ACCOUNT_SID+" "+AUTH_TOKEN);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber("+4542733385"),
                        new PhoneNumber(TWILIO_NUMBER),
                        "Your one time Pin Code is: "+pinCode)
                  .create();

    }

    @Override
    public User login(String username, String password) throws Exception {
        UserEntity user = repository.getUserByUserName(username);
        if (!user.verifyPassword(password)) {
            throw new Exception("Not valid login");
        }
        return new User(user);
    }


}
