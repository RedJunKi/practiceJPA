package com.jpa.practiceJPA.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/new")
    public String createForm() {
        return "items/createItemForm";
    }

    @PostMapping("/new")
    public String create(Book item) {
        itemService.saveItem(item);
        return "redirect:/items";
    }

    @GetMapping
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);
        model.addAttribute("item", item);
        return "items/updateItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@ModelAttribute("item") Book item) {
        itemService.saveItem(item);
        return "redirect:/items";
    }
}
