package com.example.spm.repository;

import com.example.spm.dto.ItemSearchDto;
import com.example.spm.dto.MainItemDto;
import com.example.spm.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    // @QueryProjection 을 이용하여 바로 Dto 객체 반환
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}