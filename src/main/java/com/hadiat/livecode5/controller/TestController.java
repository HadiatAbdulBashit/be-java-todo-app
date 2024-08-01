package com.hadiat.livecode5.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/1")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>("hehe", HttpStatus.OK);
    }
}
