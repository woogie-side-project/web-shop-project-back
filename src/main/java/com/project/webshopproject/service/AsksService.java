package com.project.webshopproject.service;

import org.springframework.stereotype.Service;
import com.project.webshopproject.model.Asks;
import com.project.webshopproject.dto.AsksRequestDto;
import com.project.webshopproject.repository.InquiryRepo;

import java.util.List;

@Service
public class AsksService {
    private final InquiryRepo inquiryRepo;

    public AsksService(InquiryRepo inquiryRepo) {
        this.inquiryRepo = inquiryRepo;
    }

    public Asks createInquiry(AsksRequestDto inquiryRequest) {
        Asks inquiry = new Asks();
        inquiry.setUserID(inquiryRequest.getUserID());
        inquiry.setTitle(inquiryRequest.getTitle());
        inquiry.setContent(inquiryRequest.getContent());
        inquiry.setCategory(inquiryRequest.getCategory());
        inquiry.setItemId(inquiryRequest.getItemId());
        return inquiryRepo.save(inquiry);
    }

    public Asks updateInquiry(Long id, AsksRequestDto inquiryRequest) {
        Asks inquiry = inquiryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의가 존재하지 않습니다."));
        inquiry.setTitle(inquiryRequest.getTitle());
        inquiry.setContent(inquiryRequest.getContent());
        return inquiryRepo.save(inquiry);
    }

    public void deleteInquiry(Long id, Long userID) {
        Asks inquiry = inquiryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의가 존재하지 않습니다."));
        if (!inquiry.getUserID().equals(userID)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        inquiryRepo.deleteById(id);
    }

    public Asks addAdminResponse(Long id, String responese) {
        Asks inquiry = inquiryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의가 존재하지 않습니다."));
        inquiry.setAdminResponse(responese);
        inquiry.setStatus(Asks.Status.valueOf(String.valueOf(Asks.Status.ANSWERED)));
        return inquiryRepo.save(inquiry);
    }

    public List<Asks> getInquiriesByUserID(Long userID) {
        return inquiryRepo.findByUserID(userID);
    }

    // 특정 문의사항 세부 조회
    public Asks getInquiryByIdAndUserID(Long id, Long userID) {
        return inquiryRepo.findByIdAndUserID(id, userID)
                .orElseThrow(() -> new RuntimeException("문의사항이 존재하지 않습니다."));
    }

    public Asks addAnswerToInquiry(Long id, String answer) {
        // ID로 문의사항 조회
        Asks inquiry = inquiryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의사항이 존재하지 않습니다."));

        // 답변 설정
        inquiry.setAnswer(answer);

        // 상태를 답변 완료로 변경
        inquiry.setStatus(Asks.Status.valueOf("ANSWERED"));

        // 저장 후 반환
        return inquiryRepo.save(inquiry);
    }

    public List<Asks> getAllInquiries() {
        return inquiryRepo.findAll();
    }
}
