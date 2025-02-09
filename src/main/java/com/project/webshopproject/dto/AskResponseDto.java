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

    // Getter 추가
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public Long getItemId() {
        return itemId;
    }
}
