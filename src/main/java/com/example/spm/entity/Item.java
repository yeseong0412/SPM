package com.example.spm.entity;

import com.example.spm.Exception.OutOfStockException;
import com.example.spm.constant.ItemSellStatus;
import com.example.spm.dto.ItemFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Entity
@Table(name = "item")
@Getter @Setter
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id; // 상품코드

    @Column(nullable = false, length = 50)
    private String itemNm; // 상품명

    @Column(name = "price", nullable = false)
    private int price ; //가격

    @Column(nullable = false)
    private int stockNumber; // 재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 상태

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    private LocalDateTime regTime; // 상품 등록 시간
    private LocalDateTime updateTime; // 상품 수정 시간

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm() ;
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber () ;
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }
    public void removeStock(int stockNumber) throws OutOfStockException {
        int restStock = this.stockNumber - stockNumber;
        if(restStock > 0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량 : " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }
}
