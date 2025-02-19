package com.project.webshopproject.dto;

public class AskResponseDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String category;
    private Long itemId;

    public AskResponseDto(Long id, Long userId, String title, String content, String category, Long itemId) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.itemId = itemId;
    }
}