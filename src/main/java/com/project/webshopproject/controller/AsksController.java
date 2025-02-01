package com.project.webshopproject.controller;

import com.project.webshopproject.dto.AsksRequest;
import com.project.webshopproject.model.Asks;
import com.project.webshopproject.service.AsksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/asks")
public class AsksController {
    private final AsksService inquiryService;
    public AsksController(AsksService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @GetMapping("/asks")
    public ResponseEntity<Map<String, Object>> getAllInquiries() {
        List<Asks> inquiries = inquiryService.getAllInquiries();

        Map<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "전체 문의내용 가져오기 성공");
        response.put("data", inquiries);

        return ResponseEntity.ok(response);
    }

    // 사용자 문의사항 전체 조회
    @GetMapping("/user/{userID}")
    public ResponseEntity<Map<String, Object>> getAllInquiriesByUser(@PathVariable Long userID) {
        List<Asks> inquiries = inquiryService.getInquiriesByUserID(userID);

        Map<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "문의내용 가져오기 성공");
        response.put("data", inquiries);

        return ResponseEntity.ok(response);
    }

    // 특정 문의사항 세부 조회
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getInquiryById(
            @PathVariable Long id,
            @RequestParam Long userID) {
        Asks asks = inquiryService.getInquiryByIdAndUserID(id, userID);

        Map<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "문의내용 가져오기 성공");
        response.put("data", asks);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Asks> createInquiry(@RequestBody AsksRequest inquiryRequest) {
        return ResponseEntity.ok(inquiryService.createInquiry(inquiryRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Asks> updateInquiry(@PathVariable Long id, @RequestBody AsksRequest inquiryRequest) {
        Asks updatedInquiry = inquiryService.updateInquiry(id, inquiryRequest);
        return ResponseEntity.ok(inquiryService.updateInquiry(id, inquiryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteInquiry(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {

        // 요청에서 userID 추출
        Long userID = Long.parseLong(requestBody.get("userID"));

        // 삭제 처리
        inquiryService.deleteInquiry(id, userID);

        // 응답 메시지 생성
        Map<String, String> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "삭제 완료");

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/response")
    public ResponseEntity<Map<String, Object>> addAnswerToInquiry(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {

        String answer = requestBody.get("answer");
        if (answer == null || answer.isEmpty()) {
            throw new RuntimeException("답변 내용을 입력해주세요.");
        }

        Asks updatedInquiry = inquiryService.addAnswerToInquiry(id, answer);

        Map<String, Object> response = new HashMap<>();
        response.put("userID", updatedInquiry.getUserID());
        response.put("title", updatedInquiry.getTitle());
        response.put("content", updatedInquiry.getContent());
        response.put("category", updatedInquiry.getCategory());
        response.put("itemId", updatedInquiry.getItemId());
        response.put("answer", updatedInquiry.getAnswer());

        return ResponseEntity.ok(response);
    }
}
