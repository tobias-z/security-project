package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.user.*;
import com.insession.securityproject.infrastructure.entities.UserEntity;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class UserService implements IUserService {
    private final IUserRepository repository;

    public UserService(IUserRepository repository) {
        this.repository = repository;
    }

    // Only used to give example of testing the services

    @Override
    public void sendPinMail(User user) {
        // generates One time pin code and sends i to users email.
        AuthPinCodeService pinCodeService = AuthPinCodeService.getInstance();
        int pinCode = pinCodeService.getNewPinCode(user.getUsername());

        // Recipient's email ID needs to be mentioned.
        String to = user.getUserEmail();

        // Sender's email ID needs to be mentioned
        String from = "pin.insession@gmail.com";

        // Sender's password ID needs to be mentioned ------!!!!!!!!!!!!!
        String password = "PrTzJg<>";

        // Assuming you are sending email from through gmail's smtp
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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Your onetime Pin Code");

            // Now set the actual message
            message.setText("Your one time Pin Code is: " + pinCode);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }


    }

    @Override
    public User login(String username, String password) throws Exception {
        UserEntity user = repository.getUserByUserName(username);
        if (!user.verifyPassword(password)) {
            throw new Exception("Not valid login");
        }
        return new User(user);
    }

    @Override
    public UserRole getUserRole(String username) throws UserNotFoundException {
        UserEntity userEntity = repository.getUserByUserName(username);
        // TODO: Make user entity have a UserRole
        return UserRole.USER;
    }

}
