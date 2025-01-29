package com.project.webshopproject.service;

import com.project.webshopproject.dto.*;
import com.project.webshopproject.entity.ItemImg;
import com.project.webshopproject.entity.Items;
import com.project.webshopproject.repository.ItemImgRepository;
import com.project.webshopproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;

    // 전체 상품 조회
    public List<ItemDto> getAllItem(){
        List<Items> items = itemRepository.findAll();

        return items.stream()
                .map(item -> new ItemDto(
                        item.getItemName(),
                        "/images/" + item.getItemMainImg(),
                        item.getItemPrice(),
                        item.getItemStock()
                ))
                .collect(Collectors.toList());
    }

    // 세부 상품 조회
//    public ItemFindRequestDto getItemById(Long itemId) {
//        Items item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품이 존재하지 않습니다: " + itemId));
//
//        return new ItemFindRequestDto(
//                item.getItemName(),
//                "/images/" + item.getItemImg(),
//                item.getItemPrice(),
//                item.getItemStock()
//        );
//    }

    @Value("${file.upload-dir}")
    private String uploadDir; // 이미지 파일 저장 되는 경로

    // 상품 추가
    @Transactional
    public void addItem(ItemAddRequestDto itemAddRequestDto,List<MultipartFile> images){
        try{
            List<String> savedImageUrls = saveImage(images);

            // 첫 번째 이미지는 메인 이미지로 item 테이블에 저장
            String mainImageUrl = savedImageUrls.get(0);  // 첫 번째 이미지를 대표 이미지로 사용
            Items item = new Items();
            item.setItemName(itemAddRequestDto.getItemName());
            item.setItemPrice(itemAddRequestDto.getItemPrice());
            item.setItemStock(itemAddRequestDto.getItemStock());
            item.setItemMainImg(mainImageUrl);  // 첫 번째 이미지를 메인 이미지로 설정
            itemRepository.save(item);

            // 나머지 이미지는 itemImg 테이블에 저장 (상품 이미지 테이블에 저장)
            for (int i = 1; i < savedImageUrls.size(); i++) {
                String itemImgUrl = savedImageUrls.get(i);
                ItemImg itemImg = new ItemImg();
                itemImg.setItems(item);  // item과 연관 관계 설정
                itemImg.setItemImg(itemImgUrl);  // 이미지 URL 설정
                itemImgRepository.save(itemImg);  // itemImg 테이블에 저장
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // 이미지를 서버에 저장하고 경로를 반환하는 메서드
    public List<String> saveImage(List<MultipartFile> images) throws IOException {
        List<String> savedImageUrls = new ArrayList<>();

        for(int i = 0; i < images.size(); i++){
            MultipartFile itemImg = images.get(i);
            // 파일 이름 생성 (현재 시간 + 원본 파일 이름)
            String fileName = System.currentTimeMillis() + "-" + itemImg.getOriginalFilename();
            // 저장 경로 생성
            Path filePath = Paths.get(uploadDir, fileName);
            // 경로가 존재하지 않으면 디렉토리 생성
            Files.createDirectories(filePath.getParent());
            // 파일 저장
            Files.copy(itemImg.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            // 저장된 파일의 상대 경로 반환
            savedImageUrls.add(fileName);
        }
        return savedImageUrls;
    }

    // 상품 수정
//    public ItemEditDto editItem(Long itemId,ItemEditDto itemEditDto,MultipartFile image){
//        Items editItem = itemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + itemId));
//
//        if (itemEditDto.getItemName() != null) {
//            editItem.setItemName(itemEditDto.getItemName());
//        }
//
//        if ((itemEditDto.getItemPrice() != null)) {
//            editItem.setItemPrice(itemEditDto.getItemPrice());
//        }
//
//        if (itemEditDto.getItemStock() != null) {
//            editItem.setItemStock(itemEditDto.getItemStock());
//        }
//
//        if (image != null ) {
//            try{
//                String imagePath = saveImage(image);
//                editItem.setItemImg(imagePath);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        Items updatedItem = itemRepository.save(editItem);
//
//        return new ItemEditDto(
//                updatedItem.getItemName(),
//                "/images/" + updatedItem.getItemImg(),
//                updatedItem.getItemPrice(),
//                updatedItem.getItemStock()
//        );
//    }

    // 상품 삭제
    public void deleteItem(Long itemId){
        Items deleteItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + itemId));
        itemRepository.delete(deleteItem);
    }
}
