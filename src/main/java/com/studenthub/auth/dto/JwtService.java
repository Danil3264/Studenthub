package com.studenthub.auth.dto;

import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class JwtService {

    private String secretPassword = "super-secret-key-that-must-be-at-least-32-bytes-long!!!";


    public String createToken(String email, String role, String id){

        String headerJson = """
        {
        "alg": "HS256",
        "typ": "JWT"
        }
        """;


        String payloadJson = """
        {
        "sub": "%s",
        "role": "%s",
        "id": "%s"
        }
        """.formatted(email, role, id);


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

        String token = encodedHeader + "." + encodedPayload + "." + encodedSignature;

        return token;
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



    public boolean validateToken(String token){


        //    byte[] decodedBytes = Base64.getUrlDecoder().decode(encoded);
        //    String decodedText = new String(decodedBytes, StandardCharsets.UTF_8);
        //
        //        System.out.println("Decoded Original String: " + decodedText);

        //decode -> check if still valid terms -> true/false

        return true;
    }


    public String extractUsername(String token){

        //

        return "name";
    }
}
