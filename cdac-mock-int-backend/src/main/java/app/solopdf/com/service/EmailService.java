package app.solopdf.com.service;

public interface EmailService {
    void sendEvaluationEmail(String toEmail, String candidateName, byte[] pdfAttachment);
    void sendEvaluationWithAttachment(String toEmail, String subject, String body, byte[] attachment, String filename);
}