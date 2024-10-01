package base64.base64.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base64.base64.Entity.Entity;


@Component
public class base64Service {

 

    private static final char[] BASE64_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final int[] BASE64_REVERSE_LOOKUP = new int[256];

    // Initialize the reverse lookup table for Base64 decoding
    static {
        for (int i = 0; i < BASE64_CHARSET.length; i++) {
            BASE64_REVERSE_LOOKUP[BASE64_CHARSET[i]] = i;
        }
        BASE64_REVERSE_LOOKUP['='] = 0; // Padding character
    }

    // Method to manually encode the username and password
    public String encodeString(String username, String password) {
        String credentials = username + ":" + password;
        byte[] bytes = credentials.getBytes();
        StringBuilder encodedString = new StringBuilder();
        int paddingCount = 0;

        // Process the input bytes in chunks of 3 bytes (24 bits)
        for (int i = 0; i < bytes.length; i += 3) {
            int b = 0;

            // Combine up to 3 bytes into one 24-bit integer
            b |= (bytes[i] & 0xFF) << 16;  // First byte (8 bits)

            if (i + 1 < bytes.length) {
                b |= (bytes[i + 1] & 0xFF) << 8;  // Second byte (8 bits)
            } else {
                paddingCount++;  // Add padding if fewer than 3 bytes
            }

            if (i + 2 < bytes.length) {
                b |= (bytes[i + 2] & 0xFF);  // Third byte (8 bits)
            } else {
                paddingCount++;  // Add padding if fewer than 3 bytes
            }

            // Break the 24-bit integer into four 6-bit chunks
            encodedString.append(BASE64_CHARSET[(b >> 18) & 0x3F]);
            encodedString.append(BASE64_CHARSET[(b >> 12) & 0x3F]);
            encodedString.append((paddingCount < 2) ? BASE64_CHARSET[(b >> 6) & 0x3F] : '=');
            encodedString.append((paddingCount < 1) ? BASE64_CHARSET[b & 0x3F] : '=');
        }

        return encodedString.toString();
    }

    // Method to manually decode a Base64 encoded string
    public String decodeString(String encodedString) {
        if (encodedString == null || encodedString.isEmpty()) {
            throw new IllegalArgumentException("Encoded string cannot be null or empty");
        }

        StringBuilder decodedString = new StringBuilder();

        // Process the input string in chunks of 4 characters (each 6 bits)
        for (int i = 0; i < encodedString.length(); i += 4) {
            int b = 0;

            // Combine up to 4 Base64 characters into a 24-bit integer
            b |= BASE64_REVERSE_LOOKUP[encodedString.charAt(i)] << 18;
            b |= BASE64_REVERSE_LOOKUP[encodedString.charAt(i + 1)] << 12;

            if (encodedString.charAt(i + 2) != '=') {
                b |= BASE64_REVERSE_LOOKUP[encodedString.charAt(i + 2)] << 6;
            }

            if (encodedString.charAt(i + 3) != '=') {
                b |= BASE64_REVERSE_LOOKUP[encodedString.charAt(i + 3)];
            }

            // Extract the original bytes (8-bit chunks)
            decodedString.append((char) ((b >> 16) & 0xFF)); // First byte
            if (encodedString.charAt(i + 2) != '=') {
                decodedString.append((char) ((b >> 8) & 0xFF)); // Second byte
            }
            if (encodedString.charAt(i + 3) != '=') {
                decodedString.append((char) (b & 0xFF)); // Third byte
            }
        }

        return decodedString.toString();
    }


    public String encodedString(String username,String password){
        String encodeGenerateString=null;
        if(username!=null && password!=null){

             encodeGenerateString=encodeString(username,password);
             return encodeGenerateString;
        }


        return encodeGenerateString;

    }



   
}
