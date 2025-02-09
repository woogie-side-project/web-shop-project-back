package com.project.webshopproject.product;

import com.project.webshopproject.product.dto.ProductAddRequestDto;
import com.project.webshopproject.product.dto.ProductRequestDto;
import com.project.webshopproject.product.dto.ProductFindRequestDto;
import com.project.webshopproject.product.dto.ProductEditRequestDto;
import com.project.webshopproject.product.entity.ProductImg;
import com.project.webshopproject.product.entity.Product;
import com.project.webshopproject.product.entity.SubCategory;
import com.project.webshopproject.product.repository.ProductImgRepository;
import com.project.webshopproject.product.repository.ProductRepository;
import com.project.webshopproject.product.repository.ProductRepositoryCustom;
import com.project.webshopproject.product.repository.SubCategoryRepository;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImgRepository productImgRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductRepositoryCustom productRepositoryCustom;

    // 전체 상품 조회
    public List<ProductRequestDto> getAllProducts(){
        List<ProductRequestDto> allProducts = productRepositoryCustom.findAllProducts();
        return allProducts;
    }
    //카테고리별 조회 api 추가

     // 세부 상품 조회
    public ProductFindRequestDto getProductById(Long itemId) {
        ProductFindRequestDto productById = productRepositoryCustom.findProductById(itemId);
        return productById;
    }

    @Value("${file.upload-dir}")
    private String uploadDir; // 이미지 파일 저장 되는 경로

    // 상품 추가
//    @Transactional
//    public void addProduct(ProductAddRequestDto productAddRequestDto, List<MultipartFile> images){
//        try{
//            List<String> savedImageUrls = saveImage(images);
//            // 서브카테고리 id 받아와서 체크하고
//            SubCategory subCategory =
//                    subCategoryRepository.findById(productAddRequestDto.getSubCategory())
//                            .orElseThrow(()->new IllegalArgumentException("존재하지 않는 서브 카테고리입니다."));
//
//            Product product = Product.builder()
//                    .name(productAddRequestDto.getProductName())
//                    .price(productAddRequestDto.getProductPrice())
//                    .stock(productAddRequestDto.getProductStock())
//                    .subCategory(subCategory)
//                    .build();
//            productRepository.save(product);
//
//            for (int i = 0; i < savedImageUrls.size(); i++) {
//                if(i == 0){
//
//                }
//                else{
//
//                }
//
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
    // 이미지를 서버에 저장하고 경로를 반환하는 메서드
    public List<String> saveImage(List<MultipartFile> images) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        List<String> savedImageUrls = new ArrayList<>();

        for(int i = 0; i < images.size(); i++){
            MultipartFile itemImg = images.get(i);
            // 파일 이름 생성 (현재 시간 + 원본 파일 이름)
            String currentTime = LocalDateTime.now().format(formatter);
            String fileName = currentTime + "-" + itemImg.getOriginalFilename();
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
//    public ProductEditRequestDto editProduct(Long itemId, ProductEditRequestDto productEditRequestDto, List<MultipartFile> images){
//        try{
//            List<String> savedImageUrls = saveImage(images);
//            productEditRequestDto.setItemImg(savedImageUrls);
//            editItem.updateItems(
//                    productEditRequestDto.getItemName(),
//                    productEditRequestDto.getItemImg().get(0),
//                    productEditRequestDto.getItemPrice(),
//                    productEditRequestDto.getItemStock());
//            //서브 이미지들을 담아야해
//            for(int i = 1; i < savedImageUrls.size(); i++){
//                String subImg = savedImageUrls.get(i);
//                ProductImg productImg = ProductImg.builder()
//                        .items(editItem)
//                        .itemImg(subImg)
//                        .build();
//                productImgRepository.save(productImg);
//            }
//        }
//        catch (Exception e){
//
//        }


//
//        if (images != null ) {
//            try{
//                // 받아온 이미지들 리스트에 담아서 순서대로 1번은 메인,나머지는 서브이미지테이블로
//                List<String> imagePath = saveImage(images);
//                editItem.setItemImg(imagePath);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        Items updatedItem = itemRepository.save(editItem);
//
//        return new ItemEditRequestDto(
//                updatedItem.getItemName(),
//                "/images/" + updatedItem.getItemImg(),
//                updatedItem.getItemPrice(),
//                updatedItem.getItemStock()
//        );
//    }

    // 상품 삭제
//    public void deleteItem(Long itemId){
//        Product deleteItem = productRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + itemId));
//        productRepository.delete(deleteItem);
//    }
}
