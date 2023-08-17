package com.example.spm.repository;

import com.example.spm.dto.ItemSearchDto;
import com.example.spm.dto.MainItemDto;
import com.example.spm.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemNm(String itemNm);

    @Query(value = "select i from Item i where i.itemDetail" + " like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);


    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
