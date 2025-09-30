package app.solopdf.com.service;

import app.solopdf.com.entity.Evaluation;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;

@Service
public class WordDocumentService {

    public byte[] generateEvaluationWord(Evaluation evaluation) {
        try {
            XWPFDocument document = new XWPFDocument();

            // Set page margins
            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            CTPageMar pageMar = sectPr.addNewPgMar();
            pageMar.setLeft(BigInteger.valueOf(720));  // 0.5 inch
            pageMar.setRight(BigInteger.valueOf(720)); // 0.5 inch
            pageMar.setTop(BigInteger.valueOf(720));   // 0.5 inch
            pageMar.setBottom(BigInteger.valueOf(720)); // 0.5 inch

            addHeader(document);
            addTitle(document);
            addCandidateInfo(document, evaluation);
            addTechnicalAssessment(document, evaluation);
            addGeneralAssessment(document, evaluation);
            addInterviewSummary(document, evaluation);
            addFinalDecision(document, evaluation);
            addInterviewerName(document, evaluation);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.write(baos);
            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Word document: " + e.getMessage(), e);
        }
    }

    private void addHeader(XWPFDocument document) {
        // Create header table
        XWPFTable headerTable = document.createTable(1, 3);
        headerTable.setWidth("100%");

        // Left logo placeholder
        headerTable.getRow(0).getCell(0).setText("[LEFT LOGO]");
        headerTable.getRow(0).getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

        // Center title
        XWPFRun centerRun = headerTable.getRow(0).getCell(1).getParagraphs().get(0).createRun();
        centerRun.setText("CDAC MUMBAI");
        centerRun.setBold(true);
        centerRun.setFontSize(14);
        centerRun.setColor("2C3E50");
        headerTable.getRow(0).getCell(1).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);

        // Right logo placeholder
        headerTable.getRow(0).getCell(2).setText("[RIGHT LOGO]");
        headerTable.getRow(0).getCell(2).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        headerTable.getRow(0).getCell(2).getParagraphs().get(0).setAlignment(ParagraphAlignment.RIGHT);

        // Add spacing
        document.createParagraph();
    }

    private void addTitle(XWPFDocument document) {
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("MOCK INTERVIEW EVALUATION FORM – PG-DAC February 2025");
        titleRun.setBold(true);
        titleRun.setFontSize(16);
        titleRun.setColor("2C3E50");

        // Add spacing
        document.createParagraph();
        document.createParagraph();
    }

    private void addCandidateInfo(XWPFDocument document, Evaluation evaluation) {
        XWPFTable table = document.createTable(3, 2);
        table.setWidth("100%");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = evaluation.getDate() != null ? evaluation.getDate().format(formatter) : "N/A";

        addWordTableRow(table, 0, "Date:", formattedDate);
        addWordTableRow(table, 1, "Candidate Name:", evaluation.getCandidateName() != null ? evaluation.getCandidateName() : "N/A");
        addWordTableRow(table, 2, "Roll Number:", evaluation.getRollNumber() != null ? evaluation.getRollNumber() : "N/A");

        // Add spacing
        document.createParagraph();
        document.createParagraph();
    }

    private void addWordTableRow(XWPFTable table, int row, String label, String value) {
        // Label cell
        XWPFRun labelRun = table.getRow(row).getCell(0).getParagraphs().get(0).createRun();
        labelRun.setText(label);
        labelRun.setBold(true);
        table.getRow(row).getCell(0).setColor("F0F0F0");

        // Value cell
        table.getRow(row).getCell(1).setText(value);
    }

    private void addTechnicalAssessment(XWPFDocument document, Evaluation evaluation) {
        addSectionTitle(document, "Technical area of Assessment:");

        String[] areas = {"OOPs with Java", "Data Structures", "Databases", "J2EE (Web Java)",
                "Web Technologies", "MS.NET", "C++ Programming"};
        Integer[] scores = {evaluation.getOopsJava(), evaluation.getDataStructures(), evaluation.getDatabasesTechnologies(),
                evaluation.getJ2ee(), evaluation.getWebTechnologies(), evaluation.getMsNet(),
                evaluation.getCppProgramming()};
        String[] comments = {evaluation.getOopsJavaComments(), evaluation.getDataStructuresComments(),
                evaluation.getDatabasesComments(), evaluation.getJ2eeComments(),
                evaluation.getWebTechnologiesComments(), evaluation.getMsNetComments(),
                evaluation.getCppProgrammingComments()};

        createWordAssessmentTable(document, areas, scores, comments);
    }

    private void addGeneralAssessment(XWPFDocument document, Evaluation evaluation) {
        addSectionTitle(document, "General areas of Assessment/Skills:");

        String[] areas = {"Communication", "Confidence", "Attitude", "Ability to Learn"};
        Integer[] scores = {evaluation.getCommunication(), evaluation.getConfidence(),
                evaluation.getAttitude(), evaluation.getAbilityToLearn()};
        String[] comments = {evaluation.getCommunicationComments(), evaluation.getConfidenceComments(),
                evaluation.getAttitudeComments(), evaluation.getAbilityToLearnComments()};

        createWordAssessmentTable(document, areas, scores, comments);
    }

    private void addSectionTitle(XWPFDocument document, String title) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(title);
        run.setBold(true);
        run.setFontSize(12);

        document.createParagraph();
    }

    private void createWordAssessmentTable(XWPFDocument document, String[] areas, Integer[] scores, String[] comments) {
        XWPFTable table = document.createTable(areas.length + 1, 7); // +1 for header row
        table.setWidth("100%");

        // Table Header
        String[] headers = {"Area of Assessment", "Excellent (5)", "Very Good (4)", "Good (3)", "Average (2)", "Below Avg. (1)", "Comments"};
        for (int i = 0; i < headers.length; i++) {
            XWPFRun headerRun = table.getRow(0).getCell(i).getParagraphs().get(0).createRun();
            headerRun.setText(headers[i]);
            headerRun.setBold(true);
            headerRun.setColor("FFFFFF");
            table.getRow(0).getCell(i).setColor("34495E");
        }

        // Table Rows
        for (int i = 0; i < areas.length; i++) {
            // Area
            table.getRow(i + 1).getCell(0).setText(areas[i]);

            // Scores
            Integer score = scores[i] != null ? scores[i] : 0;
            for (int j = 0; j < 5; j++) {
                String mark = score == (5 - j) ? "✓" : "";
                table.getRow(i + 1).getCell(j + 1).setText(mark);
                table.getRow(i + 1).getCell(j + 1).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            }

            // Comments
            String comment = comments[i] != null && !comments[i].isEmpty() ? comments[i] : "-";
            table.getRow(i + 1).getCell(6).setText(comment);
        }

        // Add spacing
        document.createParagraph();
        document.createParagraph();
    }

    private void addInterviewSummary(XWPFDocument document, Evaluation evaluation) {
        addSectionTitle(document, "INTERVIEW SUMMARY:");

        XWPFTable table = document.createTable(1, 1);
        table.setWidth("100%");

        String summary = evaluation.getInterviewSummary() != null && !evaluation.getInterviewSummary().isEmpty()
                ? evaluation.getInterviewSummary()
                : "No summary provided.";

        table.getRow(0).getCell(0).setText(summary);

        // Add spacing
        document.createParagraph();
        document.createParagraph();
    }

    private void addFinalDecision(XWPFDocument document, Evaluation evaluation) {
        addSectionTitle(document, "FINAL DECISION:");

        XWPFTable table = document.createTable(1, 3);
        table.setWidth("60%");

        String[] decisions = {"SELECT", "ON-HOLD", "REJECT"};
        String finalDecision = evaluation.getFinalDecision() != null ? evaluation.getFinalDecision() : "";

        for (int i = 0; i < decisions.length; i++) {
            String displayText = decisions[i] + (decisions[i].equals(finalDecision) ? " - ✓" : " - ");
            table.getRow(0).getCell(i).setText(displayText);
            table.getRow(0).getCell(i).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);

            if (decisions[i].equals(finalDecision)) {
                table.getRow(0).getCell(i).setColor("DCEFD4");
            }
        }

        // Add spacing
        document.createParagraph();
        document.createParagraph();
    }

    private void addInterviewerName(XWPFDocument document, Evaluation evaluation) {
        XWPFTable table = document.createTable(1, 2);
        table.setWidth("100%");

        // Label
        XWPFRun labelRun = table.getRow(0).getCell(0).getParagraphs().get(0).createRun();
        labelRun.setText("Name of the Interviewer:");
        labelRun.setBold(true);

        // Value
        String interviewerName = evaluation.getInterviewerName() != null ? evaluation.getInterviewerName() : "N/A";
        table.getRow(0).getCell(1).setText(interviewerName);
    }
}