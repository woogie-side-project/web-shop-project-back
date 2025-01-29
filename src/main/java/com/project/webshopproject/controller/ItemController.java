package com.project.webshopproject.controller;

import com.project.webshopproject.dto.*;
import com.project.webshopproject.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/item") //모든 상품 조회
    public List<ItemDto> getAllItem(){
        return itemService.getAllItem();
    }

//    @GetMapping("/item/{itemId}") //단일 상품 조회
//    public ResponseEntity<ItemFindRequestDto> getItemById(@PathVariable Long itemId) {
//        ItemFindRequestDto itemDto = itemService.getItemById(itemId);
//        return ResponseEntity.ok(itemDto);
//    }
    @PostMapping("/item") //상품 추가
    public ResponseEntity<String> addItem(@RequestPart("dto") ItemAddRequestDto itemAddRequestDto,
                                          @RequestPart("image") final List<MultipartFile> images){
        itemService.addItem(itemAddRequestDto,images);
        return ResponseEntity.ok("상품추가에 성공하였습니다");
    }

//    @PatchMapping("/item/{itemId}") // 상품 수정
//    public ResponseEntity<ItemEditDto> editItem(@PathVariable Long itemId,
//                                                @RequestPart("dto") ItemEditDto itemEditDto,
//                                                @RequestPart(value = "image") final List<MultipartFile> images){
//        ItemEditDto updatedItem = itemService.editItem(itemId,itemEditDto, image);
//        return ResponseEntity.ok(updatedItem);
//    }
    @DeleteMapping("item/{itemId}") // 상품 삭제
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId){
        itemService.deleteItem(itemId);
        return ResponseEntity.ok("상품삭제에 성공하였습니다");
    }
}
