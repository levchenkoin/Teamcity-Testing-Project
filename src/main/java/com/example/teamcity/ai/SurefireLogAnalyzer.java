package com.example.teamcity.ai;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class SurefireLogAnalyzer {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public static void main(String[] args) {
        try {
            String logs = collectLogData("target/surefire-reports");

            String apiKey = System.getenv("OPENAI_API_KEY");
            String slackWebhook = System.getenv("SLACK_WEBHOOK_URL");
            String runUrl = System.getenv("GITHUB_RUN_URL");

            if (apiKey == null || apiKey.isEmpty()) {
                throw new IllegalStateException("API ключ не найден в переменных среды (OPENAI_API_KEY)");
            }

            String aiAnalysis = sendToOpenAI(apiKey, logs);

            // Добавляем ссылку на GitHub Actions run
            if (runUrl != null && !runUrl.isEmpty()) {
                aiAnalysis += "\n\n👉 [View this run on GitHub](" + runUrl + ")";
            }

            System.out.println("🔍 AI-анализ логов:\n" + aiAnalysis);

            Files.write(Paths.get("ai-analysis.txt"), aiAnalysis.getBytes());

            // Отправка в Slack
            if (slackWebhook != null && !slackWebhook.isEmpty()) {
                sendToSlack(aiAnalysis, slackWebhook);
            } else {
                System.out.println("⚠️ Slack webhook не задан в переменной SLACK_WEBHOOK_URL");
            }

        } catch (IOException e) {
            System.err.println("❌ Ошибка: " + e.getMessage());
        }
    }

    private static String collectLogData(String folderPath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths.filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".txt") || p.toString().endsWith(".xml"))
                .forEach(p -> {
                    try {
                        content.append("\n=== ").append(p.getFileName()).append(" ===\n");
                        content.append(Files.readString(p));
                    } catch (IOException e) {
                        System.err.println("⚠️ Ошибка чтения файла: " + p.getFileName());
                    }
                });
        }

        String result = content.toString();
        return result.length() > 12000 ? result.substring(result.length() - 12000) : result;
    }

    private static String sendToOpenAI(String apiKey, String logs) throws IOException {
        JSONObject json = new JSONObject();
        json.put("model", "gpt-4o"); // можешь заменить на свою модель
        json.put("messages", new org.json.JSONArray()
            .put(new JSONObject().put("role", "system").put("content",
                "Ты — AI-тестировщик. Проанализируй логи автотестов (Surefire Reports), определи нестабильные тесты, ошибки и возможные улучшения."))
            .put(new JSONObject().put("role", "user").put("content", logs))
        );

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
            .url(API_URL)
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Ошибка запроса: " + response);

            String responseBody = response.body().string();
            JSONObject result = new JSONObject(responseBody);
            return result
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
        }
    }

    public static void sendToSlack(String message, String webhookUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        json.put("text", message);

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
            .url(webhookUrl)
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Ошибка отправки в Slack: " + response);
            }
        }
    }
}