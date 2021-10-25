package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;

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
    public User someMethod(String username,String userEmail) {
        return repository.someMethod(username,userEmail);
    }


    @Override
    public void sendPinMail( User user) {
    // generates One time pin cocde and sends i to users email.
        Random rand = new Random();
        int pinCode= rand.nextInt(10000);

        // Recipient's email ID needs to be mentioned.
        String to = "jensgelbek@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "pin.insession@gmail.com";

        // Sender's password ID needs to be mentioned ------!!!!!!!!!!!!!
        String password="PrTzJg<>";

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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

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
}
