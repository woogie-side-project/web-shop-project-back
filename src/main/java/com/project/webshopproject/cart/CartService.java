package com.project.webshopproject.cart;

import com.project.webshopproject.cart.dto.CartAddRequestDto;
import com.project.webshopproject.cart.entity.Cart;
import com.project.webshopproject.user.UserRepository;
import com.project.webshopproject.user.entity.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "Cart Service")
public class CartService {

    private final CartRepository cartRepository;
    //TODO 제품 브랜치랑 머지 후 수정필요
    //private final ProductRepository ProductRepository;
    private final UserRepository userRepository;

    public void addCart(CartAddRequestDto requestDto, String email) {
        User user = userRepository.findByEmail(email);
        Item item = itemRepository.findById(requestDto.itemId());
        Cart cart = new Cart(user, item, requestDto.quantity());

        cartRepository.save(cart);
    }

    // 장바구니 조회
    public List<Map<String, Object>> getAllCartItem(String email) {
        User user = userRepository.findByEmail(email);
        List<Cart> cartItems = cartRepository.findByUser(user);

        List<Map<String, Object>> cartResponse = new ArrayList<>();
        for (Cart cart : cartItems) {
            Map<String, Object> item = new HashMap<>();
            Map<String, Object> itemDetails = new HashMap<>();
            itemDetails.put("item_name", cart.getItem().getName());
            itemDetails.put("quantity", cart.getQuantity());
            item.put("item", itemDetails);
            cartResponse.add(item);
        }

        return cartResponse;
    }

    // 장바구니 항목 삭제
    public void deleteCart(Long cartId, String email) {
        User user = userRepository.findByEmail(email);
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("장바구니 항목을 찾을 수 없습니다."));

        if (!cart.getUser().equals(user)) {
            throw new RuntimeException("해당 사용자만 삭제할 수 있습니다.");
        }

        cartRepository.delete(cart);
    }

}
