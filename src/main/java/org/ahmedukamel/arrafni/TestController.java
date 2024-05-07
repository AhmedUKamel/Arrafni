package org.ahmedukamel.arrafni;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(value = "public/test")
    public ResponseEntity<?> test(HttpServletRequest request) {
        return ResponseEntity.ok("Done");
    }
}