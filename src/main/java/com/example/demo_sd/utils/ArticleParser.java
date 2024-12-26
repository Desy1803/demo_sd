package com.example.demo_sd.utils;

import com.example.demo_sd.dto.ArticleDtoParser;

public class ArticleParser {

    private static final String ERROR_RESPONSE = "I'm sorry. I only help with financial analysis. Please try again.";

    public static ArticleDtoParser parseArticle(String inputText) {
        // Input validation
        if (inputText == null || inputText.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: The input text is null or empty.");
        }

        if (inputText.contains(ERROR_RESPONSE)) {
            throw new IllegalArgumentException("Error: The AI response is not related to financial analysis.");
        }

        // Extract title
        String title = extractTitle(inputText);
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Error: The title is missing in the AI response.");
        }

        // Extract description
        String description = extractDescription(inputText);
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Error: The description is missing in the AI response.");
        }

        return new ArticleDtoParser(title, description);
    }

    private static String extractTitle(String inputText) {
        String[] titleRegexes = {
                "\\*\\*Title:\\s*(.+?)\\n", // Matches: **Title: Title text
                "\\*\\*Title\\*\\*:\\s*(.+?)\\s*\\*\\*", // Matches: **Title**: Title text **
                "\\*\\*\\*Title: (.+?)\\*\\*\\*" // Matches: ***Title: Title text***
        };

        for (String regex : titleRegexes) {
            String title = extractData(inputText, regex);
            if (!title.isEmpty()) {
                return title;
            }
        }

        return "";
    }

    private static String extractDescription(String inputText) {
        String descriptionRegex = "\\*\\*Title[:\\*]*.*?\\n([\\s\\S]+)";
        return extractData(inputText, descriptionRegex);
    }

    private static String extractData(String inputText, String regex) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex, java.util.regex.Pattern.DOTALL);
        java.util.regex.Matcher matcher = pattern.matcher(inputText);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }
    public static String removeAsterisks(String text) {
        return text.replaceAll("^\\*+|\\*+$", "").trim();
    }
}
