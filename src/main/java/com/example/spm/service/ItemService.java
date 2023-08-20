package com.example.spm.service;

import com.example.spm.dto.ItemFormDto;
import com.example.spm.dto.ItemImgDto;
import com.example.spm.dto.ItemSearchDto;
import com.example.spm.dto.MainItemDto;
import com.example.spm.entity.Item;
import com.example.spm.entity.ItemImg;
import com.example.spm.repository.ItemImgRepository;
import com.example.spm.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto,
                         List<MultipartFile> itemImgFileList) throws Exception {

        // 상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if (i == 0) {
                itemImg.setRepImgYn("y");
            } else {
                itemImg.setRepImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }
    // 상품 조회
    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {

        // 상품 이미지 엔티티들을 itemImgDto 객체로 변환하여 itemImgDtoList 에 담음
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        // 상품 id를 기반으로 상품 엔티티 객체 가져오기
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException :: new);

        // 상품 엔티티 객체를 DTO 객체로 변환
        ItemFormDto itemFormDto = ItemFormDto.of(item) ;
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;


    }
    public Long updateItem(ItemFormDto itemFormDto , List<MultipartFile> itemImgFileList) throws Exception {

        // 상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException :: new);
        item.updateItem(itemFormDto);

        // 상품 이미지 수정
        List<Long> itemImgIds = itemFormDto.getItemImgIds();
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId();
    }
    // 메인 페이지 상품 목록 조회
    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }


}
