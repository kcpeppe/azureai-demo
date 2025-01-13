package com.microsoft.ai;

//import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
//import com.azure.core.credential.AzureKeyCredential;

public class AzureAIClient implements AzureAI {

    String endPoint;
    String apiKey;

    public AzureAIClient(String endPoint, String apiKey) {
        this.endPoint = endPoint;
        this.apiKey = apiKey;
    }

    @Override
    public void analyzeSentiment(String text) {
//        OpenAIClient openAIClient = new OpenAIClientBuilder()
//                .credential(new AzureKeyCredential(apiKey))
//                .endpoint(endPoint)
//                .buildClient();
//
//        var client = new TextAnalyticsClientBuilder()
//                .credential(new AzureKeyCredential(apiKey))
//                .endpoint(endPoint)
//                .buildClient();
//        var response = client.analyzeSentiment(text);
//        System.out.println("sentiment: " + response.getSentiment());
    }

    @Override
    public void extractKeyPhrases(String text) {
//        var client = new TextAnalyticsClientBuilder()
//                .credential(new AzureKeyCredential(apiKey))
//                .endpoint(endPoint)
//                .buildClient();
//        var response = client.extractKeyPhrases(text);
//        System.out.println("key phrases: ");
//        response.stream().forEach(System.out::println);
    }
}
