package service;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendOTPService {

    // Method to send OTP to the provided email
    public static void sendOTP(String email, String otp) {
        // Sender's email credentials
        final String from = "shikhars496@gmail.com";
        final String password = "xcft frmc xolh esmj";

        // SMTP server configuration for Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Creating a Session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        // Enable debug mode
        session.setDebug(true);

        try {
            // Creating a message object
            Message message = new MimeMessage(session);
            // Setting sender email
            message.setFrom(new InternetAddress(from));
            // Setting recipient email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            // Setting email subject
            message.setSubject("OTP Verification");
            // Setting email content
            message.setText("Your OTP is: " + otp);

            // Sending the email
            Transport.send(message);

            System.out.println("OTP sent successfully to " + email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
