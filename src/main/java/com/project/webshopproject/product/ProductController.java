package com.project.webshopproject.product;

import com.project.webshopproject.product.dto.ProductAddRequestDto;
import com.project.webshopproject.product.dto.ProductRequestDto;
import com.project.webshopproject.product.dto.ProductFindRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/item") //모든 상품 조회
    public List<ProductRequestDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/item/{itemId}") //단일 상품 조회
    public ProductFindRequestDto getProductById(@PathVariable Long itemId) {
        return productService.getProductById(itemId);
    }
//    @PostMapping("/item") //상품 추가
//    public ResponseEntity<String> addProduct(@RequestPart("dto") ProductAddRequestDto productAddRequestDto,
//                                          @RequestPart("image") final List<MultipartFile> images){
//        productService.addProduct(productAddRequestDto,images);
//        return ResponseEntity.ok("상품추가에 성공하였습니다");
//    }

//    @PatchMapping("/item/{itemId}") // 상품 수정
//    public ResponseEntity<ItemEditDto> editItem(@PathVariable Long itemId,
//                                                @RequestPart("dto") ItemEditDto itemEditDto,
//                                                @RequestPart(value = "image") final List<MultipartFile> images){
//        ItemEditDto updatedItem = itemService.editItem(itemId,itemEditDto, image);
//        return ResponseEntity.ok(updatedItem);
//    }
//    @DeleteMapping("item/{itemId}") // 상품 삭제
//    public ResponseEntity<String> deleteItem(@PathVariable Long itemId){
//        productService.deleteItem(itemId);
//        return ResponseEntity.ok("상품삭제에 성공하였습니다");
//    }
}
