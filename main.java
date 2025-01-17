import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.openai.api.*;
import com.openai.models.*;

public class FinanceDocumentAnalyzer {

    // Function to read the contents of a PDF file and return it as a string
    public static String readPdf(String filePath) {
        StringBuilder fullText = new StringBuilder();
        try {
            PdfDocument pdf = PdfDocument.load(Paths.get(filePath));
            for (int i = 0; i < pdf.getPages().size(); i++) {
                String pageText = pdf.getPage(i).getText();
                fullText.append(pageText).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullText.toString();
    }

    public static void main(String[] args) {
        // Read the first PDF document (replace with the actual file path)
        String document1 = readPdf("findocs/payslip.pdf");
        String document2 = readPdf("findocs/statement.pdf");

        // Define the system and user prompts
        String systemMessage = "Your task is to function as an advanced finance documents analyser and investigator";
        String userPrompt1 = String.format(
            "You will analyze two consecutive financial documents: a pay slip and a bank statement. Follow these steps to extract key details and assess their correspondence:\n\n" +
            "### Document Details Extraction:\n\n" +
            "1. Pay Slip: Extract and summarize:\n" +
            "   - Name of the employee\n" +
            "   - Name of employer\n" +
            "   - Date of issuance\n" +
            "   - Net salary amount\n" +
            "   - Deductions and breakdown (if available)\n" +
            "   - Any other relevant details\n\n" +
            "2. Bank Statement: Extract and summarize:\n" +
            "   - Name of the account holder\n" +
            "   - Bank name\n" +
            "   - Statement period\n" +
            "   - Salary deposit date(s)\n" +
            "   - Amount deposited\n" +
            "   - Any additional relevant transactions\n\n" +
            "### Accuracy:\n\n" +
            "3. Both Documents: Generate:\n" +
            "   - A simple yes or no confirmation if the documents corresponds\n" +
            "   - A score out of 100% based on how well the pay slip corresponds with the bank statement.\n\n" +
            "4. Both Documents: Generate:\n" +
            "   - A Short justification on the score by explaining areas of agreement or discrepancy.\n\n" +
            "Financial Documents:\n" +
            "I will attach the first document here (Pay Slip):\n" +
            "%s\n\n" +
            "The second document also document here (Bank Statement):\n" +
            "%s",
            document1, document2
        );

        // Initialize OpenAI client
        OpenAIClient client = new OpenAIClient(System.getenv("OPENAI_API_KEY"));

        // Set up request payload
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("model", "gpt-4");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", systemMessage));
        messages.add(Map.of("role", "user", "content", userPrompt1));
        requestPayload.put("messages", messages);

        // Send the request to OpenAI API
        try {
            CompletionResponse response = client.chatCompletions().create(requestPayload);
            String responseMessage = response.getChoices().get(0).getMessage().getContent();
            System.out.println(responseMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
