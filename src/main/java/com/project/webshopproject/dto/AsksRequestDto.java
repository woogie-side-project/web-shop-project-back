package com.project.webshopproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class AsksRequestDto {

    @NotNull(message = "User ID는 null일 수 없습니다.")
    private Long userID;

    @NotBlank(message = "Title은 비어 있을 수 없습니다.")
    @Size(max = 100, message = "Title의 최대 길이는 100자입니다.")
    private String title;

    @NotBlank(message = "Content는 비어 있을 수 없습니다.")
    private String content;

    @NotBlank(message = "Category는 비어 있을 수 없습니다.")
    private String category;

    @Pattern(regexp = "^[A-Za-z0-9]{3,10}$", message = "Item ID는 3자에서 10자 사이의 영숫자여야 합니다.")
    private String itemId;

    // Getters and Setters
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}



/*{
        "userID" : "{user_id}",
        "title" : "{title}",
        "content" : "{content}",
        "category" : "{category}",
        "itemId" : "{item_id}"
        }*/