package com.project.webshopproject.categories;

import com.project.webshopproject.categories.dto.CategoryAddRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final  CategoryService categoryService;
    //카테고리 추가
    @PostMapping("/categories")
    public ResponseEntity<String> addCategory(@RequestBody CategoryAddRequestDto categoryAddRequestDto){
        categoryService.addCategory(categoryAddRequestDto);
        return ResponseEntity.ok("카테고리추가에 성공하였습니다");
    }

}


