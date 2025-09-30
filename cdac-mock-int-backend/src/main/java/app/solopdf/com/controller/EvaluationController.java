//package app.solopdf.com.controller;
//
//import app.solopdf.com.entity.Evaluation;
//import app.solopdf.com.repository.EvaluationRepository;
//import app.solopdf.com.service.PdfService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/evaluations")
//@CrossOrigin(origins = "http://localhost:3000")
//public class EvaluationController {
//
//    private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);
//
//    @Autowired
//    private EvaluationRepository evaluationRepository;
//
//    @Autowired
//    private PdfService pdfService;
//
//    @GetMapping("/{id}/download")
//    public ResponseEntity<byte[]> downloadEvaluation(@PathVariable Long id) {
//        try {
//            logger.info("Download request received for evaluation ID: {}", id);
//
//            Optional<Evaluation> evaluationOpt = evaluationRepository.findById(id);
//            if (evaluationOpt.isEmpty()) {
//                logger.warn("Evaluation not found for ID: {}", id);
//                return ResponseEntity.notFound().build();
//            }
//
//            Evaluation evaluation = evaluationOpt.get();
//            logger.info("Generating PDF for candidate: {}", evaluation.getCandidateName());
//
//            byte[] pdfBytes = pdfService.generateEvaluationPdf(evaluation);
//
//            if (pdfBytes == null || pdfBytes.length == 0) {
//                logger.error("Generated PDF is empty for evaluation ID: {}", id);
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(("PDF generation failed").getBytes());
//            }
//
//            String filename = "MockInterviewEvaluation_" +
//                    (evaluation.getCandidateName() != null ? evaluation.getCandidateName().replace(" ", "_") : "Unknown") +
//                    ".pdf";
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDispositionFormData("attachment", filename);
//            headers.setContentLength(pdfBytes.length);
//            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//
//            logger.info("PDF generated successfully for evaluation ID: {}, file size: {} bytes", id, pdfBytes.length);
//
//            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
//
//        } catch (Exception e) {
//            logger.error("Error generating PDF for evaluation ID: {}", id, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
//        }
//    }
//
//    // Keep your other endpoints as they were
//    @GetMapping
//    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
//        try {
//            List<Evaluation> evaluations = evaluationRepository.findAll();
//            return ResponseEntity.ok(evaluations);
//        } catch (Exception e) {
//            logger.error("Error fetching evaluations", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<Evaluation> createEvaluation(@RequestBody Evaluation evaluation) {
//        try {
//            Evaluation savedEvaluation = evaluationRepository.save(evaluation);
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvaluation);
//        } catch (Exception e) {
//            logger.error("Error creating evaluation", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable Long id) {
//        try {
//            Optional<Evaluation> evaluation = evaluationRepository.findById(id);
//            return evaluation.map(ResponseEntity::ok)
//                    .orElse(ResponseEntity.notFound().build());
//        } catch (Exception e) {
//            logger.error("Error fetching evaluation with ID: {}", id, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
//        try {
//            if (!evaluationRepository.existsById(id)) {
//                return ResponseEntity.notFound().build();
//            }
//            evaluationRepository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            logger.error("Error deleting evaluation with ID: {}", id, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}
//

package app.solopdf.com.controller;

import app.solopdf.com.entity.Evaluation;
import app.solopdf.com.repository.EvaluationRepository;
import app.solopdf.com.service.PdfService;
import app.solopdf.com.service.WordDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluations")
@CrossOrigin(origins = "http://localhost:3000")
public class EvaluationController {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private WordDocumentService wordDocumentService;

    @GetMapping("/{id}/download-pdf")
    public ResponseEntity<byte[]> downloadEvaluationPdf(@PathVariable Long id) {
        try {
            logger.info("PDF download request received for evaluation ID: {}", id);

            Optional<Evaluation> evaluationOpt = evaluationRepository.findById(id);
            if (evaluationOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Evaluation evaluation = evaluationOpt.get();
            byte[] pdfBytes = pdfService.generateEvaluationPdf(evaluation);

            String filename = evaluation.getRollNumber() + "_" + evaluation.getCandidateName() + ".pdf";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", filename);
            headers.setContentLength(pdfBytes.length);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            logger.info("PDF generated successfully for evaluation ID: {}", id);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error generating PDF for evaluation ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/download-word")
    public ResponseEntity<byte[]> downloadEvaluationWord(@PathVariable Long id) {
        try {
            logger.info("Word document download request received for evaluation ID: {}", id);

            Optional<Evaluation> evaluationOpt = evaluationRepository.findById(id);
            if (evaluationOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Evaluation evaluation = evaluationOpt.get();
            byte[] wordBytes = wordDocumentService.generateEvaluationWord(evaluation);

            String filename = evaluation.getRollNumber() + "_" + evaluation.getCandidateName() + ".docx";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);
            headers.setContentLength(wordBytes.length);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            logger.info("Word document generated successfully for evaluation ID: {}", id);
            return new ResponseEntity<>(wordBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error generating Word document for evaluation ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String safeFileName(String name) {
        if (name == null) return "Unknown";
        return name.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    // Keep your other endpoints (GET, POST, DELETE) as they were
    @GetMapping
    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
        try {
            List<Evaluation> evaluations = evaluationRepository.findAll();
            return ResponseEntity.ok(evaluations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Evaluation> createEvaluation(@RequestBody Evaluation evaluation) {
        try {
            Evaluation savedEvaluation = evaluationRepository.save(evaluation);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvaluation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable Long id) {
        try {
            Optional<Evaluation> evaluation = evaluationRepository.findById(id);
            return evaluation.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        try {
            if (!evaluationRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            evaluationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}