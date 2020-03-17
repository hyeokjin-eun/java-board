package com.board.java.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("session")
public class SessionController {
    @PostMapping("")
    public ResponseEntity<?> create() {
        return ResponseEntity.ok()
                .body("");
    }
}
