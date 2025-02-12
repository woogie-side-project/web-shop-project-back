package com.project.webshopproject.categories;

import com.project.webshopproject.categories.dto.CategoryAddRequestDto;
import com.project.webshopproject.categories.dto.CategoryEditRequestDto;
import com.project.webshopproject.categories.dto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final  CategoryService categoryService;

    // 카테고리 전체 조회
    @GetMapping("/categories")
    public ResponseEntity<String> getAllCategories(){
         categoryService.getAllCategories();
        return ResponseEntity.ok("카테고리조회에 성공하였습니다");
    }
    // 카테고리 추가
    @PostMapping("/categories")
    public ResponseEntity<String> addCategory(@RequestBody CategoryAddRequestDto categoryAddRequestDto){
        categoryService.addCategory(categoryAddRequestDto);
        return ResponseEntity.ok("카테고리추가에 성공하였습니다");
    }

    // 카테고리 수정
    @PatchMapping("/categories/{categoryId}")
    public ResponseEntity<String> editCategory(@PathVariable("categoryId") Long categoryId,
                                               @RequestBody CategoryEditRequestDto categoryEditRequestDto){
        categoryService.editCategory(categoryId,categoryEditRequestDto);
        return ResponseEntity.ok("카테고리수정에 성공하였습니다");
    }

    // 카테고리 삭제

}


