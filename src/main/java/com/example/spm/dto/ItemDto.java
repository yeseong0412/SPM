package com.example.spm.dto;

import com.example.spm.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id; // 상품코드
    private String itemNm; // 상품명
    private int price ; //가격
    private int stockNumber; // 재고 수량
    private String itemDetail; // 상품 상세 상태
    private ItemSellStatus itemSellStatus; // 상품 판매 상태
    private LocalDateTime regTime; // 상품 등록 시간
    private LocalDateTime updateTime; // 상품 수정 시간
}
