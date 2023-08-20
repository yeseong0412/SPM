package com.example.spm.repository;

import com.example.spm.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long>, ItemRepositoryCustom {
//    List<Item> findByItemNm(String itemNm);
//
//    @Query(value = "select i from Item i where i.itemDetail" + " like %:itemDetail% order by i.price desc", nativeQuery = true)
//    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
//
//
//    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
