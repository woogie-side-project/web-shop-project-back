package com.project.webshopproject.cart.dto;

/**
 * @param itemName final 선언
 * @param quantity final 선언
 */
public record CartResponseDto(
        String itemName,
        int quantity
) {

}
