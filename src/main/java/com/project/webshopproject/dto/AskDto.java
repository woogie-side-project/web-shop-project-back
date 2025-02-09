package com.project.webshopproject.dto;

import com.project.webshopproject.model.Ask;

public class AskDto {
    private Long id;
    private String title;
    private String content;
    private String category;
    private Long itemId;

    public AskDto(Long id, String title, String content, String category, Long itemId) {
    }
    // 필요한 다른 필드들...

    public static AskDto from(Ask ask) {
        return new AskDto(
                ask.getId(),
                ask.getTitle(),
                ask.getContent(),
                ask.getCategory(),
                ask.getItemId()
        );
    }
}
