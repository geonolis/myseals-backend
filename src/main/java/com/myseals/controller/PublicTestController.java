package com.myseals.controller;

import com.myseals.service.SealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/public")
public class PublicTestController {

    @Autowired
    private SealService sealService;

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("message", "The mySeals backend is running and connected to Supabase!");
        try {
            status.put("totalSealsInDb", sealService.findAll().size());
            status.put("databaseConnection", "SUCCESS");
        } catch (Exception e) {
            status.put("databaseConnection", "FAILED: " + e.getMessage());
        }
        return status;
    }
}
