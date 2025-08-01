package br.com.karine.front_gestao_vagas.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FormatErrorMessage {

    public static String formatErrorMessage(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(message);
            if (rootNode.isArray()) {
                return formatArrayErrorMessage(rootNode);
            }
            return rootNode.asText();
        } catch (Exception e) {
            return message;
        }
}

    public static String formatArrayErrorMessage(JsonNode arrayNode) {
        StringBuilder formattedMessage = new StringBuilder();
        for (JsonNode node : arrayNode) {
            formattedMessage.append(node.get("message").asText()).append("\n");
        }
        return formattedMessage.toString();

    }
}
