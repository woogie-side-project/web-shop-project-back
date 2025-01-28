package com.project.webshopproject.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InquiryRequest {
    @NotNull
    private Long userID;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String category;

    private String itemId;

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