package com.example.spm.controller;

import com.example.spm.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafExController {
    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("제품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/thymeleafEx01";
    }
    //여러 제품 확인용
    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();

        for(int i=1; i<=10; i++) {
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("제품 상세 설명" + i);
            itemDto.setItemNm("테스트 상품1" + i);
            itemDto.setPrice(10000 * i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx02";
    }
    //param 확인용
    @GetMapping(value = "/ex06")
    public String thymeleafExample06(String param1, String param2, Model model){
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleafEx/thymeleafEx06";
    }
    //레이아웃 확인
    @GetMapping(value = "/layout1")
    public String thymeleafExample07(){
        return "layouts/layout1";
    }

}
