package com.project.webshopproject.service;

import com.project.webshopproject.dto.*;
import com.project.webshopproject.entity.Items;
import com.project.webshopproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemDto> getAllItem(){
        List<Items> items = itemRepository.findAll();
        return items.stream()
                .map(item -> new ItemDto(
                        item.getItemName(),
                        "/images/" + item.getItemImg(),
                        item.getItemPrice(),
                        item.getItemCount()
                ))
                .collect(Collectors.toList());
    }
    public ItemFindRequestDto getItemById(Long itemId) {
        Items item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품이 존재하지 않습니다: " + itemId));

        return new ItemFindRequestDto(
                item.getItemName(),
                "/images/" + item.getItemImg(),
                item.getItemPrice(),
                item.getItemCount()
        );
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    public void addItem(ItemRequestDto itemRequestDto,final MultipartFile image){
        try{
            String imagePath = saveImage(image);
            Items items = new Items();
            items.setItemName(itemRequestDto.getItemName());
            items.setItemImg(imagePath);
            items.setItemPrice(itemRequestDto.getItemPrice());
            items.setItemCount(itemRequestDto.getItemCount());
            itemRepository.save(items);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // 이미지를 서버에 저장하고 경로를 반환하는 메서드
    public String saveImage(MultipartFile image) throws IOException {
        // 파일 이름 생성 (현재 시간 + 원본 파일 이름)
        String fileName = System.currentTimeMillis() + "-" + image.getOriginalFilename();
        // 저장 경로 생성
        Path filePath = Paths.get(uploadDir, fileName);
        // 경로가 존재하지 않으면 디렉토리 생성
        Files.createDirectories(filePath.getParent());
        // 파일 저장
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // 저장된 파일의 상대 경로 반환
        return fileName;
    }

    public ItemEditDto editItem(Long itemId,ItemEditDto itemEditDto,MultipartFile image){
        Items editItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + itemId));

        if (itemEditDto.getItemName() != null) {
            editItem.setItemName(itemEditDto.getItemName());
        }

        if ((itemEditDto.getItemPrice() != null)) {
            editItem.setItemPrice(itemEditDto.getItemPrice());
        }

        if (itemEditDto.getItemCount() != null) {
            editItem.setItemCount(itemEditDto.getItemCount());
        }

        if (image != null ) {
            try{
                String imagePath = saveImage(image);
                editItem.setItemImg(imagePath);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Items updatedItem = itemRepository.save(editItem);

        return new ItemEditDto(
                updatedItem.getItemName(),
                "/images/" + updatedItem.getItemImg(),
                updatedItem.getItemPrice(),
                updatedItem.getItemCount()
        );
    }
    public void deleteItem(Long itemId){
        Items deleteItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + itemId));
        itemRepository.delete(deleteItem);
    }
}
