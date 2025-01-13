package com.microsoft;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestAssistantMessage;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestSystemMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.azure.ai.openai.models.ChatResponseMessage;
import com.azure.ai.openai.models.CompletionsUsage;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Configuration;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        String azureOpenaiKey = Configuration.getGlobalConfiguration().get("AZURE_OPENAI_API_KEY");
        String endpoint = Configuration.getGlobalConfiguration().get("AZURE_OPENAI_ENDPOINT");
        System.out.println(endpoint);
        String deploymentOrModelId = "gpt-4o";

        OpenAIClient client = new OpenAIClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(azureOpenaiKey))
                .buildClient();

        String prompt = "You have been given a G1GC log. The log duration is 117.751 seconds\n" +
                "From the log we know;\n" +
                "1) total bytes allocated 14 TB\n" +
                "2) allocation rate is 124GB/s\n" +
                "3) there was 1 to-space exhausted event but no remark stack overflows nor remark aborts\n" +
                "4) total pause time is 6.192 seconds for an Application throughput of 94.7%\n" +
                "\n" +
                "We also know that there were\n" +
                "- 71  Young collections for a total pause time of 0.429s\n" +
                "- 66 mixed collections with total pause time of 0.304s\n" +
                "- 787 Young Initial Mark collections for a total pause of 4.542s\n" +
                "- 636 remark collections with a total pause time of 0.916s\n" +
                "\n" +
                "Can you provide an assessment or recommend next steps?";

        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage("You are a helpful assistant."));
        chatMessages.add(new ChatRequestUserMessage(prompt));
        //chatMessages.add(new ChatRequestAssistantMessage("Yes, customer managed keys are supported by Azure OpenAI?"));
        //chatMessages.add(new ChatRequestUserMessage("Do other Azure AI services support this too?"));

        ChatCompletions chatCompletions = client.getChatCompletions(deploymentOrModelId, new ChatCompletionsOptions(chatMessages));

        System.out.printf("Model ID=%s is created at %s.%n", chatCompletions.getId(), chatCompletions.getCreatedAt());
        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatResponseMessage message = choice.getMessage();
            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
            System.out.println("Message:");
            System.out.println(message.getContent());
        }

        System.out.println();
        CompletionsUsage usage = chatCompletions.getUsage();
        System.out.printf("Usage: number of prompt token is %d, "
                        + "number of completion token is %d, and number of total tokens in request and response is %d.%n",
                usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
    }
}