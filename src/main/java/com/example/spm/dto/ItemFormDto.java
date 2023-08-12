package com.example.spm.dto;

import com.example.spm.constant.ItemSellStatus;
import com.example.spm.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {
    private Long id;
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;
    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;
    @NotBlank(message = "상품 상세는 필수 입력값입니다.")
    private String itemDetail;
    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;
    private ItemSellStatus itemSellStatus;

    // 상품 수정 시 사용되는 멤버변수들
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();
    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    // DTO -> Entity
    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }
    // Entity -> DTO
    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
}