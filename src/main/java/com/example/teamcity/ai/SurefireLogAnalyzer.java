package com.example.teamcity.ai;

import okhttp3.*;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;
import java.util.stream.Stream;

public class SurefireLogAnalyzer {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String CONFIG_PATH = "src/main/resources/config.properties";

    public static void main(String[] args) {
        try {
            String logs = collectLogData("target/surefire-reports");
            String apiKey = resolveApiKey();
            String aiAnalysis = sendToOpenAI(apiKey, logs);

            System.out.println("🔍 AI-анализ логов:\n" + aiAnalysis);

            // Сохраняем результат в файл
            Files.write(Paths.get("ai-analysis.txt"), aiAnalysis.getBytes());

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
        return result.length() > 12000 ? result.substring(result.length() - 12000) : result; // защита от слишком длинного текста
    }

    private static String resolveApiKey() throws IOException {
        String key = System.getenv("OPENAI_API_KEY");

        if (key == null || key.isEmpty()) {
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
                props.load(fis);
                key = props.getProperty("openaiApiKey");
            }
        }

        if (key == null || key.isEmpty()) {
            throw new IllegalStateException("API ключ не найден ни в переменных среды, ни в config.properties");
        }

        return key;
    }

    private static String sendToOpenAI(String apiKey, String logs) throws IOException {
        JSONObject json = new JSONObject();
        json.put("model", "gpt-4");
        json.put("messages", new org.json.JSONArray()
            .put(new JSONObject().put("role", "system").put("content",
                "Ты — AI-тестировщик. Проанализируй тестовые логи (Surefire Reports) и предложи улучшения или гипотезы для нестабильных тестов."))
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
            if (!response.isSuccessful()) {
                throw new IOException("OpenAI API error: " + response);
            }
            return response.body().string();
        }
    }
}
