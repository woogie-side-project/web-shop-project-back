package com.project.webshopproject.service;

import com.project.webshopproject.model.Ask;
import com.project.webshopproject.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.project.webshopproject.dto.AskRequestDto;
import com.project.webshopproject.repository.AskRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AskService {
    private final AskRepo askRepo;

    public Ask createAsk(AskRequestDto askRequest) {
        Ask ask = new Ask();
        ask.setUserID(askRequest.getUserID());
        ask.setTitle(askRequest.getTitle());
        ask.setContent(askRequest.getContent());
        ask.setCategory(askRequest.getCategory());
        ask.setItemId(askRequest.getItemId());
        return askRepo.save(ask);
    }

    public Ask updateAsk(Long id, AskRequestDto askRequest) {
        Ask ask = askRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의가 존재하지 않습니다."));
        ask.setTitle(askRequest.getTitle());
        ask.setContent(askRequest.getContent());
        return askRepo.save(ask);
    }

    public void deleteAsk(Long id, Long userID) {
        Ask ask = askRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의가 존재하지 않습니다."));
        if (!ask.getUserID().equals(userID)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        askRepo.deleteById(id);
    }

    public Ask addAdminResponse(Long id, String responese) {
        Ask ask = askRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의가 존재하지 않습니다."));
        ask.setAdminResponse(responese);
        ask.setStatus(Status.valueOf(String.valueOf(Status.ANSWERED)));
        return askRepo.save(ask);
    }

    public List<Ask> getAsksByUserID(Long userID) {
        return askRepo.findByUserID(userID);
    }

    // 특정 문의사항 세부 조회
    public Ask getAsksByIdAndUserID(Long id, Long userID) {
        return askRepo.findByIdAndUserID(id, userID)
                .orElseThrow(() -> new RuntimeException("문의사항이 존재하지 않습니다."));
    }

    public Ask addAnswerToAsk(Long id, String answer) {
        // ID로 문의사항 조회
        Ask ask = askRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("문의사항이 존재하지 않습니다."));

        // 답변 설정
        ask.setAnswer(answer);

        // 상태를 답변 완료로 변경
        ask.setStatus(Status.valueOf("ANSWERED"));

        // 저장 후 반환
        return askRepo.save(ask);
    }

    public List<Ask> getAllasks() {
        return askRepo.findAll();
    }
}
