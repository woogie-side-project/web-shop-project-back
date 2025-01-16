package com.project.webshopproject.controller;

import com.project.webshopproject.entity.Items;
import com.project.webshopproject.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;

    @GetMapping("/item")
    String item(Model model){
        List<Items> result = itemRepository.findAll();
        model.addAttribute("items",result);
        return "list.html";
    }

}
