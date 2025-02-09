package com.project.webshopproject.controller;

import com.project.webshopproject.dto.AskRequestDto;
import com.project.webshopproject.model.Ask;
import com.project.webshopproject.service.AskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AskController {
    private final AskService askService;

    @GetMapping("/{askId}")
    public ResponseEntity<Map<String, Object>> getInquiryById(
            @PathVariable Long askId,
            @RequestParam Long userID) {
        Ask asks = askService.getAsksByIdAndUserID(askId, userID);

        AskRequestDto askRequestDto = new AskRequestDto(userID, "문의내용 가져오기 성공", asks.getTitle(), asks.getContent(), asks.getCategory(), asks.getItemId());

        Map<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", askRequestDto.getTitle()); // 메시지로 타이틀을 사용
        response.put("data", askRequestDto);

        return ResponseEntity.ok(response);
    }

    // 사용자 문의사항 전체 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getAllAsksByUser(@PathVariable Long userId) {
        List<Ask> asks = askService.getAsksByUserID(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "문의내용 가져오기 성공");
        response.put("data", asks);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/ask")
    public ResponseEntity<Ask> createInquiry(@RequestBody AskRequestDto inquiryRequest) {
        return ResponseEntity.ok(askService.createAsk(inquiryRequest));
    }

    @PatchMapping("/{askId}")
    public ResponseEntity<Ask> updateInquiry(@PathVariable Long askId, @RequestBody AskRequestDto inquiryRequest) {
        Ask updatedAsk = askService.updateAsk(askId, inquiryRequest);
        return ResponseEntity.ok(updatedAsk);
    }

    @DeleteMapping("/{askId}")
    public ResponseEntity<Map<String, String>> deleteInquiry(
            @PathVariable Long askId,
            @RequestBody Map<String, String> requestBody) {

        // 요청에서 userID 추출
        Long userID = Long.parseLong(requestBody.get("userID"));

        // 삭제 처리
        askService.deleteAsk(askId, userID);

        // 응답 메시지 생성
        Map<String, String> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "삭제 완료");

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{askId}/response")
    public ResponseEntity<Map<String, Object>> addAnswerToInquiry(
            @PathVariable Long askId,
            @RequestBody Map<String, String> requestBody) {

        String answer = requestBody.get("answer");
        if (answer == null || answer.isEmpty()) {
            throw new RuntimeException("답변 내용을 입력해주세요.");
        }

        Ask updatedAsk = askService.addAnswerToAsk(askId, answer);

        Map<String, Object> response = new HashMap<>();
        response.put("userID", updatedAsk.getUserID());
        response.put("title", updatedAsk.getTitle());
        response.put("content", updatedAsk.getContent());
        response.put("category", updatedAsk.getCategory());
        response.put("itemId", updatedAsk.getItemId());
        response.put("answer", updatedAsk.getAnswer());

        return ResponseEntity.ok(response);
    }
}
