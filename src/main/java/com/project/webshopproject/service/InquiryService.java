package com.project.webshopproject.service;

import org.springframework.stereotype.Service;
import com.project.webshopproject.model.Inquiry;
import com.project.webshopproject.dto.InquiryRequest;
import com.project.webshopproject.repository.InquiryRepo;

import java.util.List;

@Service
public class InquiryService {
    private final InquiryRepo inquiryRepo;

    public InquiryService(InquiryRepo inquiryRepo) {
        this.inquiryRepo = inquiryRepo;
    }

    public Inquiry createInquiry(InquiryRequest inquiryRequest) {
        Inquiry inquiry = new Inquiry();
        inquiry.setUserID(inquiryRequest.getUserID());
        inquiry.setTitle(inquiryRequest.getTitle());
        inquiry.setContent(inquiryRequest.getContent());
        inquiry.setCategory(inquiryRequest.getCategory());
        inquiry.setItemId(inquiryRequest.getItemId());
        return inquiryRepo.save(inquiry);
    }

    public Inquiry updateInquiry(Long id, InquiryRequest inquiryRequest) {
        Inquiry inquiry = inquiryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의가 존재하지 않습니다."));
        inquiry.setTitle(inquiryRequest.getTitle());
        inquiry.setContent(inquiryRequest.getContent());
        return inquiryRepo.save(inquiry);
    }

    public void deleteInquiry(Long id, Long userID) {
        Inquiry inquiry = inquiryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의가 존재하지 않습니다."));
        if (!inquiry.getUserID().equals(userID)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        inquiryRepo.deleteById(id);
    }

    public Inquiry addAdminResponse(Long id, String responese) {
        Inquiry inquiry = inquiryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의가 존재하지 않습니다."));
        inquiry.setAdminResponse(responese);
        inquiry.setStatus(Inquiry.Status.valueOf(String.valueOf(Inquiry.Status.ANSWERED)));
        return inquiryRepo.save(inquiry);
    }

    public List<Inquiry> getInquiriesByUserID(Long userID) {
        return inquiryRepo.findByUserID(userID);
    }

    // 특정 문의사항 세부 조회
    public Inquiry getInquiryByIdAndUserID(Long id, Long userID) {
        return inquiryRepo.findByIdAndUserID(id, userID)
                .orElseThrow(() -> new RuntimeException("문의사항이 존재하지 않습니다."));
    }

    public Inquiry addAnswerToInquiry(Long id, String answer) {
        // ID로 문의사항 조회
        Inquiry inquiry = inquiryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의사항이 존재하지 않습니다."));

        // 답변 설정
        inquiry.setAnswer(answer);

        // 상태를 답변 완료로 변경
        inquiry.setStatus(Inquiry.Status.valueOf("ANSWERED"));

        // 저장 후 반환
        return inquiryRepo.save(inquiry);
    }

    public List<Inquiry> getAllInquiries() {
        return inquiryRepo.findAll();
    }
}
