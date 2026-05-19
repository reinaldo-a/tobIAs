package com.tobias.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.tobias.model.Activity;
import com.tobias.model.ActivitySubmission;
import com.tobias.model.Report;

public class PdfReportService {
    private static final float MARGIN = 50;
    private static final float FONT_SIZE = 11;
    private static final float TITLE_SIZE = 16;
    private static final float LEADING = 15;
    private static final float MAX_WIDTH = PDRectangle.A4.getWidth() - (MARGIN * 2);

    public byte[] generatePdf(Report report, Activity activity, ActivitySubmission submission) throws IOException {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(document);
            writer.writeLine(report.getTitle(), PDType1Font.HELVETICA_BOLD, TITLE_SIZE);
            writer.writeLine("Atividade: " + activity.getTitle(), PDType1Font.HELVETICA, FONT_SIZE);
            writer.writeLine("Aluno: " + submission.getStudentName(), PDType1Font.HELVETICA, FONT_SIZE);
            if (report.getDate() != null) {
                writer.writeLine("Data: " + report.getDate(), PDType1Font.HELVETICA, FONT_SIZE);
            }
            writer.writeBlankLine();

            for (String line : normalizeMarkdown(report.getAssessment())) {
                if (line.isBlank()) {
                    writer.writeBlankLine();
                    continue;
                }

                boolean heading = line.startsWith("#");
                String cleanLine = cleanMarkdownLine(line);
                writer.writeWrappedLine(cleanLine, heading ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA, heading ? 13 : FONT_SIZE);
            }

            writer.close();
            document.save(output);
            return output.toByteArray();
        }
    }

    private List<String> normalizeMarkdown(String text) {
        String[] lines = text == null ? new String[0] : text.split("\\R");
        List<String> normalized = new ArrayList<>();
        for (String line : lines) {
            normalized.add(line == null ? "" : line.stripTrailing());
        }
        return normalized;
    }

    private String cleanMarkdownLine(String line) {
        return line.replaceFirst("^#{1,6}\\s*", "")
                .replace("**", "")
                .replace("__", "")
                .replace("`", "");
    }

    private static class PdfWriter {
        private final PDDocument document;
        private PDPage page;
        private PDPageContentStream content;
        private float y;

        PdfWriter(PDDocument document) throws IOException {
            this.document = document;
            addPage();
        }

        void writeLine(String text, PDType1Font font, float fontSize) throws IOException {
            ensureSpace(LEADING);
            content.beginText();
            content.setFont(font, fontSize);
            content.newLineAtOffset(MARGIN, y);
            content.showText(sanitize(text));
            content.endText();
            y -= LEADING;
        }

        void writeWrappedLine(String text, PDType1Font font, float fontSize) throws IOException {
            for (String line : wrap(text, font, fontSize)) {
                writeLine(line, font, fontSize);
            }
        }

        void writeBlankLine() throws IOException {
            ensureSpace(LEADING);
            y -= LEADING;
        }

        void close() throws IOException {
            if (content != null) {
                content.close();
            }
        }

        private void addPage() throws IOException {
            if (content != null) {
                content.close();
            }

            page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            content = new PDPageContentStream(document, page);
            y = page.getMediaBox().getHeight() - MARGIN;
        }

        private void ensureSpace(float requiredHeight) throws IOException {
            if (y - requiredHeight < MARGIN) {
                addPage();
            }
        }

        private List<String> wrap(String text, PDType1Font font, float fontSize) throws IOException {
            List<String> lines = new ArrayList<>();
            String[] words = sanitize(text).split("\\s+");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                String candidate = currentLine.length() == 0 ? word : currentLine + " " + word;
                float width = font.getStringWidth(candidate) / 1000 * fontSize;

                if (width > MAX_WIDTH && currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    currentLine = new StringBuilder(candidate);
                }
            }

            if (currentLine.length() > 0) {
                lines.add(currentLine.toString());
            }

            return lines;
        }

        private String sanitize(String text) {
            return text == null ? "" : text.replace('\t', ' ').replaceAll("[^\\x20-\\x7E\\u00C0-\\u00FF]", "");
        }
    }
}
