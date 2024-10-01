package base64.base64.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import base64.base64.Entity.Entity;
import base64.base64.service.base64Service;

@RestController
@RequestMapping("/base")
@CrossOrigin(origins = "http://localhost:3000") // Allow CORS for the frontend
public class Base64Controller {

    @Autowired
    private base64Service base;

    @GetMapping("/decode/{encodedString}")
    public ResponseEntity<?> decodeString(@PathVariable String encodedString) {
        try {
            String requestString = base.decodeString(encodedString);
            if (requestString != null) {
                String[] parts = requestString.split(":", 2);
                String user = parts[0];
                String password = parts[1];

                return new ResponseEntity<>("Username: " + user + "  Password: " + password, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to decode the string: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Unable to decode the provided string", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/encode")
    public ResponseEntity<?> encodePassword(@RequestBody Entity entity) {
        try {
            String newEntity = base.encodedString(entity.getUsername(),entity.getPassword());
            if (newEntity != null) {
                return new ResponseEntity<>(newEntity, HttpStatus.OK);
            }
            return new ResponseEntity<>("Username or Password is null", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
