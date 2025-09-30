package app.solopdf.com.service;

import app.solopdf.com.entity.Evaluation;

public interface PdfService {
    byte[] generateEvaluationPdf(Evaluation evaluation) throws Exception;
}