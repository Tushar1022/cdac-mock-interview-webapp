package app.solopdf.com.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEvaluationEmail(String toEmail, String candidateName, byte[] pdfAttachment) {
        String subject = "Mock Interview Evaluation - " + candidateName;
        String body = "Dear Student,\n\nPlease find attached your mock interview evaluation form.\n\nBest regards,\nEvaluation Team";
        String filename = "MockInterviewEvaluation_" + candidateName + ".pdf";

        sendEvaluationWithAttachment(toEmail, subject, body, pdfAttachment, filename);
    }

    @Override
    public void sendEvaluationWithAttachment(String toEmail, String subject, String body, byte[] attachment, String filename) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreply@cdac.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

            // Attach PDF
            helper.addAttachment(filename, new ByteArrayResource(attachment));

            mailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}