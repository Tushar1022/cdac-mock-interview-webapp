//package app.solopdf.com.service;
//
//import app.solopdf.com.entity.Evaluation;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.time.format.DateTimeFormatter;
//
//@Service
//public class PdfServiceImpl implements PdfService {
//
//    // Font definitions
//    private Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.DARK_GRAY);
//    private Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
//    private Font normalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
//    private Font tableHeaderFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE);
//
//    @Override
//    public byte[] generateEvaluationPdf(Evaluation evaluation) {
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//            PdfWriter writer = PdfWriter.getInstance(document, baos);
//
//            document.open();
//
//            // Add content
//            addHeader(document);
//            addTitle(document);
//            addCandidateInfo(document, evaluation);
//            addTechnicalAssessment(document, evaluation);
//            addGeneralAssessment(document, evaluation);
//            addInterviewSummary(document, evaluation);
//            addFinalDecision(document, evaluation);
//            addInterviewerName(document, evaluation);
//
//            document.close();
//            return baos.toByteArray();
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate PDF: " + e.getMessage(), e);
//        }
//    }
//
//    private void addHeader(Document document) throws DocumentException {
//        // Simple header without images to avoid image loading issues
//        Paragraph header = new Paragraph("CDAC MUMBAI", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY));
//        header.setAlignment(Element.ALIGN_CENTER);
//        header.setSpacingAfter(10f);
//        document.add(header);
//    }
//
//    private void addTitle(Document document) throws DocumentException {
//        Paragraph title = new Paragraph("MOCK INTERVIEW EVALUATION FORM – PG-DAC February 2025", titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        title.setSpacingAfter(20f);
//        document.add(title);
//    }
//
//    private void addCandidateInfo(Document document, Evaluation evaluation) throws DocumentException {
//        PdfPTable table = new PdfPTable(2);
//        table.setWidthPercentage(100);
//        table.setWidths(new float[]{1, 3});
//        table.setSpacingAfter(20f);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        String formattedDate = evaluation.getDate() != null ? evaluation.getDate().format(formatter) : "";
//
//        addTableRow(table, "Date:", formattedDate);
//        addTableRow(table, "Candidate Name:", evaluation.getCandidateName() != null ? evaluation.getCandidateName() : "");
//        addTableRow(table, "Roll Number:", evaluation.getRollNumber() != null ? evaluation.getRollNumber() : "");
//
//        document.add(table);
//    }
//
//    private void addTableRow(PdfPTable table, String label, String value) {
//        PdfPCell labelCell = new PdfPCell(new Phrase(label, headerFont));
//        labelCell.setBackgroundColor(new BaseColor(240, 240, 240));
//        labelCell.setBorder(Rectangle.BOX);
//        labelCell.setPadding(5);
//
//        PdfPCell valueCell = new PdfPCell(new Phrase(value, normalFont));
//        valueCell.setBorder(Rectangle.BOX);
//        valueCell.setPadding(5);
//
//        table.addCell(labelCell);
//        table.addCell(valueCell);
//    }
//
//    private void addTechnicalAssessment(Document document, Evaluation evaluation) throws DocumentException {
//        Paragraph title = new Paragraph("Technical area of Assessment:", headerFont);
//        title.setSpacingAfter(10f);
//        document.add(title);
//
//        String[] areas = {
//                "OOPs with Java", "Data Structures", "Databases",
//                "J2EE (Web Java)", "Web Technologies", "MS.NET", "C++ Programming"
//        };
//
//        Integer[] scores = {
//                evaluation.getOopsJava(), evaluation.getDataStructures(), evaluation.getDatabasesTechnologies(),
//                evaluation.getJ2ee(), evaluation.getWebTechnologies(), evaluation.getMsNet(),
//                evaluation.getCppProgramming()
//        };
//
//        String[] comments = {
//                evaluation.getOopsJavaComments(), evaluation.getDataStructuresComments(),
//                evaluation.getDatabasesComments(), evaluation.getJ2eeComments(),
//                evaluation.getWebTechnologiesComments(), evaluation.getMsNetComments(),
//                evaluation.getCppProgrammingComments()
//        };
//
//        createAssessmentTable(document, areas, scores, comments);
//    }
//
//    private void addGeneralAssessment(Document document, Evaluation evaluation) throws DocumentException {
//        Paragraph title = new Paragraph("General areas of Assessment/Skills:", headerFont);
//        title.setSpacingAfter(10f);
//        document.add(title);
//
//        String[] areas = {"Communication", "Confidence", "Attitude", "Ability to Learn"};
//        Integer[] scores = {
//                evaluation.getCommunication(), evaluation.getConfidence(),
//                evaluation.getAttitude(), evaluation.getAbilityToLearn()
//        };
//        String[] comments = {
//                evaluation.getCommunicationComments(), evaluation.getConfidenceComments(),
//                evaluation.getAttitudeComments(), evaluation.getAbilityToLearnComments()
//        };
//
//        createAssessmentTable(document, areas, scores, comments);
//    }
//
//    private void createAssessmentTable(Document document, String[] areas, Integer[] scores, String[] comments) throws DocumentException {
//        PdfPTable table = new PdfPTable(7);
//        table.setWidthPercentage(100);
//        table.setWidths(new float[]{3, 1, 1, 1, 1, 1, 2});
//        table.setSpacingAfter(20f);
//
//        // Table Header
//        String[] headers = {"Area of Assessment", "Excellent (5)", "Very Good (4)", "Good (3)", "Average (2)", "Below Avg. (1)", "Comments"};
//        for (String header : headers) {
//            PdfPCell cell = new PdfPCell(new Phrase(header, tableHeaderFont));
//            cell.setBackgroundColor(new BaseColor(52, 73, 94));
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setPadding(5);
//            table.addCell(cell);
//        }
//
//        // Table Rows
//        for (int i = 0; i < areas.length; i++) {
//            // Area name
//            table.addCell(createTableCell(areas[i], Element.ALIGN_LEFT, false));
//
//            // Scores (radio buttons as checkmarks)
//            Integer score = scores[i] != null ? scores[i] : 0;
//            for (int j = 5; j >= 1; j--) {
//                String mark = score == j ? "✓" : "";
//                table.addCell(createTableCell(mark, Element.ALIGN_CENTER, false));
//            }
//
//            // Comments
//            String comment = comments[i] != null ? comments[i] : "";
//            table.addCell(createTableCell(comment, Element.ALIGN_LEFT, false));
//        }
//
//        document.add(table);
//    }
//
//    private PdfPCell createTableCell(String content, int alignment, boolean isHeader) {
//        Font font = isHeader ? tableHeaderFont : normalFont;
//        PdfPCell cell = new PdfPCell(new Phrase(content, font));
//        cell.setHorizontalAlignment(alignment);
//        cell.setPadding(5);
//        cell.setBorder(Rectangle.BOX);
//        return cell;
//    }
//
//    private void addInterviewSummary(Document document, Evaluation evaluation) throws DocumentException {
//        Paragraph title = new Paragraph("INTERVIEW SUMMARY:", headerFont);
//        title.setSpacingAfter(10f);
//        document.add(title);
//
//        String summary = evaluation.getInterviewSummary() != null ? evaluation.getInterviewSummary() : "";
//        PdfPCell cell = createTableCell(summary, Element.ALIGN_LEFT, false);
//        cell.setMinimumHeight(80);
//
//        PdfPTable table = new PdfPTable(1);
//        table.setWidthPercentage(100);
//        table.addCell(cell);
//        table.setSpacingAfter(20f);
//
//        document.add(table);
//    }
//
//    private void addFinalDecision(Document document, Evaluation evaluation) throws DocumentException {
//        Paragraph title = new Paragraph("FINAL DECISION:", headerFont);
//        title.setSpacingAfter(10f);
//        document.add(title);
//
//        PdfPTable table = new PdfPTable(3);
//        table.setWidthPercentage(60);
//        table.setHorizontalAlignment(Element.ALIGN_LEFT);
//        table.setSpacingAfter(20f);
//
//        String[] decisions = {"SELECT", "ON-HOLD", "REJECT"};
//        String finalDecision = evaluation.getFinalDecision() != null ? evaluation.getFinalDecision() : "";
//
//        for (String decision : decisions) {
//            boolean isSelected = decision.equals(finalDecision);
//            String displayText = decision + (isSelected ? " - ✓" : " - ");
//            PdfPCell cell = createTableCell(displayText, Element.ALIGN_CENTER, false);
//            if (isSelected) {
//                cell.setBackgroundColor(new BaseColor(220, 237, 200));
//            }
//            table.addCell(cell);
//        }
//
//        document.add(table);
//    }
//
//    private void addInterviewerName(Document document, Evaluation evaluation) throws DocumentException {
//        PdfPTable table = new PdfPTable(2);
//        table.setWidthPercentage(100);
//        table.setWidths(new float[]{1, 3});
//
//        table.addCell(createTableCell("Name of the Interviewer:", Element.ALIGN_LEFT, true));
//        String interviewerName = evaluation.getInterviewerName() != null ? evaluation.getInterviewerName() : "";
//        table.addCell(createTableCell(interviewerName, Element.ALIGN_LEFT, false));
//
//        document.add(table);
//    }
//}

package app.solopdf.com.service;

import app.solopdf.com.entity.Evaluation;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfServiceImpl implements PdfService {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.DARK_GRAY);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
    private static final Font TABLE_HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE);

    @Override
    public byte[] generateEvaluationPdf(Evaluation evaluation) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 40, 40, 40, 40);
            PdfWriter.getInstance(document, baos);

            document.open();

            addHeader(document);
            addTitle(document);
            addCandidateInfo(document, evaluation);
            addTechnicalAssessment(document, evaluation);
            addGeneralAssessment(document, evaluation);
            addInterviewSummary(document, evaluation);
            addFinalDecision(document, evaluation);
            addInterviewerName(document, evaluation);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF: " + e.getMessage(), e);
        }
    }

    private void addHeader(Document document) throws DocumentException {
        // Create header table with logos
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{2, 3, 2});

        // Left logo placeholder
        Paragraph leftLogo = new Paragraph("[LEFT LOGO]", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.GRAY));
        PdfPCell leftCell = new PdfPCell(leftLogo);
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);

        // Center title
        Paragraph centerTitle = new Paragraph("CDAC MUMBAI", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK));
        PdfPCell centerCell = new PdfPCell(centerTitle);
        centerCell.setBorder(Rectangle.NO_BORDER);
        centerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        // Right logo placeholder
        Paragraph rightLogo = new Paragraph("[RIGHT LOGO]", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.GRAY));
        PdfPCell rightCell = new PdfPCell(rightLogo);
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        headerTable.addCell(leftCell);
        headerTable.addCell(centerCell);
        headerTable.addCell(rightCell);
        headerTable.setSpacingAfter(20f);

        document.add(headerTable);
    }

    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("MOCK INTERVIEW EVALUATION FORM – PG-DAC February 2025", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(25f);
        document.add(title);
    }

    private void addCandidateInfo(Document document, Evaluation evaluation) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 3});
        table.setSpacingAfter(20f);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = evaluation.getDate() != null ? evaluation.getDate().format(formatter) : "N/A";

        addInfoRow(table, "Date:", formattedDate);
        addInfoRow(table, "Candidate Name:", evaluation.getCandidateName() != null ? evaluation.getCandidateName() : "N/A");
        addInfoRow(table, "Roll Number:", evaluation.getRollNumber() != null ? evaluation.getRollNumber() : "N/A");

        document.add(table);
    }

    private void addInfoRow(PdfPTable table, String label, String value) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, HEADER_FONT));
        labelCell.setBackgroundColor(new BaseColor(240, 240, 240));
        labelCell.setBorder(Rectangle.BOX);
        labelCell.setPadding(8);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, NORMAL_FONT));
        valueCell.setBorder(Rectangle.BOX);
        valueCell.setPadding(8);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addTechnicalAssessment(Document document, Evaluation evaluation) throws DocumentException {
        Paragraph title = new Paragraph("Technical area of Assessment:", HEADER_FONT);
        title.setSpacingAfter(15f);
        document.add(title);

        String[] areas = {"OOPs with Java", "Data Structures", "Databases", "J2EE (Web Java)",
                "Web Technologies", "MS.NET", "C++ Programming"};
        Integer[] scores = {evaluation.getOopsJava(), evaluation.getDataStructures(), evaluation.getDatabasesTechnologies(),
                evaluation.getJ2ee(), evaluation.getWebTechnologies(), evaluation.getMsNet(),
                evaluation.getCppProgramming()};
        String[] comments = {evaluation.getOopsJavaComments(), evaluation.getDataStructuresComments(),
                evaluation.getDatabasesComments(), evaluation.getJ2eeComments(),
                evaluation.getWebTechnologiesComments(), evaluation.getMsNetComments(),
                evaluation.getCppProgrammingComments()};

        createAssessmentTable(document, areas, scores, comments);
    }

    private void addGeneralAssessment(Document document, Evaluation evaluation) throws DocumentException {
        Paragraph title = new Paragraph("General areas of Assessment/Skills:", HEADER_FONT);
        title.setSpacingAfter(15f);
        document.add(title);

        String[] areas = {"Communication", "Confidence", "Attitude", "Ability to Learn"};
        Integer[] scores = {evaluation.getCommunication(), evaluation.getConfidence(),
                evaluation.getAttitude(), evaluation.getAbilityToLearn()};
        String[] comments = {evaluation.getCommunicationComments(), evaluation.getConfidenceComments(),
                evaluation.getAttitudeComments(), evaluation.getAbilityToLearnComments()};

        createAssessmentTable(document, areas, scores, comments);
    }

    private void createAssessmentTable(Document document, String[] areas, Integer[] scores, String[] comments) throws DocumentException {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 1, 1, 1, 1, 1, 2});
        table.setSpacingAfter(20f);

        // Table Header
        String[] headers = {"Area of Assessment", "Excellent (5)", "Very Good (4)", "Good (3)", "Average (2)", "Below Avg. (1)", "Comments"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, TABLE_HEADER_FONT));
            cell.setBackgroundColor(new BaseColor(52, 73, 94));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(6);
            table.addCell(cell);
        }

        // Table Rows
        for (int i = 0; i < areas.length; i++) {
            // Area
            table.addCell(createCell(areas[i], Element.ALIGN_LEFT, false));

            // Scores
            Integer score = scores[i] != null ? scores[i] : 0;
            for (int j = 5; j >= 1; j--) {
                String mark = score == j ? "✓" : "";
                table.addCell(createCell(mark, Element.ALIGN_CENTER, false));
            }

            // Comments
            String comment = comments[i] != null && !comments[i].isEmpty() ? comments[i] : "-";
            table.addCell(createCell(comment, Element.ALIGN_LEFT, false));
        }

        document.add(table);
    }

    private PdfPCell createCell(String content, int alignment, boolean isHeader) {
        Font font = isHeader ? TABLE_HEADER_FONT : NORMAL_FONT;
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(6);
        cell.setBorder(Rectangle.BOX);
        return cell;
    }

    private void addInterviewSummary(Document document, Evaluation evaluation) throws DocumentException {
        Paragraph title = new Paragraph("INTERVIEW SUMMARY:", HEADER_FONT);
        title.setSpacingAfter(10f);
        document.add(title);

        String summary = evaluation.getInterviewSummary() != null && !evaluation.getInterviewSummary().isEmpty()
                ? evaluation.getInterviewSummary()
                : "No summary provided.";

        PdfPCell cell = createCell(summary, Element.ALIGN_LEFT, false);
        cell.setMinimumHeight(100);

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.addCell(cell);
        table.setSpacingAfter(20f);

        document.add(table);
    }

    private void addFinalDecision(Document document, Evaluation evaluation) throws DocumentException {
        Paragraph title = new Paragraph("FINAL DECISION:", HEADER_FONT);
        title.setSpacingAfter(10f);
        document.add(title);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(60);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setSpacingAfter(20f);

        String[] decisions = {"SELECT", "ON-HOLD", "REJECT"};
        String finalDecision = evaluation.getFinalDecision() != null ? evaluation.getFinalDecision() : "";

        for (String decision : decisions) {
            boolean isSelected = decision.equals(finalDecision);
            String displayText = decision + (isSelected ? " - ✓" : " - ");
            PdfPCell cell = createCell(displayText, Element.ALIGN_CENTER, false);
            if (isSelected) {
                cell.setBackgroundColor(new BaseColor(220, 237, 200));
            }
            table.addCell(cell);
        }

        document.add(table);
    }

    private void addInterviewerName(Document document, Evaluation evaluation) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 3});

        table.addCell(createCell("Name of the Interviewer:" , Element.ALIGN_LEFT, true));
        String interviewerName = evaluation.getInterviewerName() != null ? evaluation.getInterviewerName() : "N/A";
        table.addCell(createCell(interviewerName, Element.ALIGN_LEFT, false));

        document.add(table);
    }
}

