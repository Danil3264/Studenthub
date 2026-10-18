package com.studenthub.auth;

import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class JwtService {

    private final String secretPassword = "super-secret-key-that-must-be-at-least-32-bytes-long!!!";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final long TOKEN_LIFETIME_SECONDS = 3600;

    public String createToken(String email, String role, String id){

        String headerJson = """
        {
        "alg": "HS256",
        "typ": "JWT"
        }
        """;

        long now = System.currentTimeMillis() / 1000;
        long expirationDate = now + TOKEN_LIFETIME_SECONDS;

        String payloadJson = """
        {
        "sub": "%s",
        "role": "%s",
        "id": "%s",
        "exp": %d
        }
        """.formatted(email, role, id, expirationDate); // WHY sub - email ?? sub - subject -> user ID or unique identifier


        String encodedHeader = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));

        String encodedPayload = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));

        String signingInput = encodedHeader + "." + encodedPayload;

        byte[] signatureBytes = HMAC_SHA256(signingInput, secretPassword);

        String encodedSignature = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(signatureBytes);

        return encodedHeader + "." + encodedPayload + "." + encodedSignature;
    }

    public boolean validateToken(String token){

        String[] parts = token.split("\\.");

        if (parts.length != 3) {
            return false;
        }


        String signingInput = parts[0] + "." + parts[1];

        byte[] expectedSignatureBytes = HMAC_SHA256(signingInput, secretPassword);

        String expectedSignature = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(expectedSignatureBytes);

        if(!expectedSignature.equals(parts[2])) return false;

        byte[] decodedBytes =
                Base64.getUrlDecoder().decode(parts[1]);

        String payloadJson = new String(decodedBytes, StandardCharsets.UTF_8);

        JsonNode payload = objectMapper.readTree(payloadJson);

        long expirationDate = payload.get("exp").asLong();
        long now = System.currentTimeMillis() / 1000;

        if (expirationDate <= now) {
            return false;
        }

        return true;
    }


    public String extractEmail(String token){

        JsonNode payload = extractPayload(token);

        return payload.get("sub").asText();
    }

    public String extractRole(String token){

        JsonNode payload = extractPayload(token);

        return payload.get("role").asText();
    }

    public String extractId(String token){

        JsonNode payload = extractPayload(token);

        return payload.get("id").asText();
    }

    public JsonNode extractPayload(String token){
        String[] parts = token.split("\\.");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT format");
        }

        byte[] decodedBytes =
                Base64.getUrlDecoder().decode(parts[1]);

        String payloadJson = new String(decodedBytes, StandardCharsets.UTF_8);

        return  objectMapper.readTree(payloadJson);
    }


    private byte[] HMAC_SHA256(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec secretKey = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );

            mac.init(secretKey);

            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to create HMAC-SHA256 signature", e);
        }
    }
}
