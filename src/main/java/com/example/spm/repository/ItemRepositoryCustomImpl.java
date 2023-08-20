package com.example.spm.repository;

import com.example.spm.dto.ItemSearchDto;
import com.example.spm.dto.MainItemDto;
import com.example.spm.entity.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private JPAQueryFactory queryFactory;
    @PersistenceContext
    private EntityManager em;

    // 생성자 DI를 통해서 JPAQueryFactory(EntityManager) 주입
//    public ItemRepositoryCustomImpl(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory((javax.persistence.EntityManager) em);
//    }

//    // 상품 등록일에 대한 조회 조건 BooleanExpression
//    private BooleanExpression regDtsAfter(String searchDateType) {
//
//
//        return QItem.item.regTime.after(dateTime);
//    }
//
//    // 상품 상태에 대한 조회 조건 BooleanExpression
//    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
//        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
//    }
//
//    // 상품명 또는 등록자 아이디에 대한 조회 조건 BooleanExpression
//    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
//        if (StringUtils.equals("itemName", searchBy)) {
//            return QItem.item.itemName.like("%" + searchQuery + "%");
//        } else if (StringUtils.equals("createdBy", searchBy)) {
//            return QItem.item.createdBy.like("%" + searchQuery + "%");
//        }
//        return null;
//    }


    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        StringBuilder jpql = new StringBuilder("SELECT i FROM Item i WHERE 1 = 1");

        // 검색 조건 추가 (예시)
        if (itemSearchDto.getSearchSellStatus() != null) {
            jpql.append(" AND i.itemSellStatus = :sellStatus");
        }
        if (StringUtils.isEmpty(itemSearchDto.getSearchQuery())) {
            jpql.append(" AND i.itemNm LIKE :searchQuery");
        }

        // 정렬 조건 추가
        jpql.append(" ORDER BY i.id DESC");

        TypedQuery<Item> query = em.createQuery(jpql.toString(), Item.class);

        // 검색 조건에 매개변수 설정 (예시)
        if (itemSearchDto.getSearchSellStatus() != null) {
            query.setParameter("sellStatus", itemSearchDto.getSearchSellStatus());
        }
        if (StringUtils.isEmpty(itemSearchDto.getSearchQuery())) {
            query.setParameter("searchQuery", "%" + itemSearchDto.getSearchQuery() + "%");
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Item> content = query.getResultList();

        // 전체 카운트 조회
        String countJpql = "SELECT COUNT(i) FROM Item i WHERE 1 = 1";

        // 검색 조건 추가 (예시)
        if (itemSearchDto.getSearchSellStatus() != null) {
            countJpql += " AND i.itemSellStatus = :sellStatus";
        }
        if (StringUtils.isEmpty(itemSearchDto.getSearchQuery())) {
            countJpql += " AND i.itemNm LIKE :searchQuery";
        }

        Long total = em.createQuery(countJpql, Long.class)
                .setParameter("sellStatus", itemSearchDto.getSearchSellStatus())
                .setParameter("searchQuery", "%" + itemSearchDto.getSearchQuery() + "%")
                .getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        StringBuilder jpql = new StringBuilder("SELECT NEW com.example.spm.dto.MainItemDto(i.id, i.itemName, i.itemDetail, img.imgUrl, i.price) " +
                "FROM ItemImg img JOIN img.item i WHERE img.repImgYn = 'Y'");

        if (itemSearchDto.getSearchQuery() != null && !itemSearchDto.getSearchQuery().isEmpty()) {
            jpql.append(" AND i.itemName LIKE :searchQuery");
        }

        jpql.append(" ORDER BY i.id DESC");

        TypedQuery<MainItemDto> query = em.createQuery(jpql.toString(), MainItemDto.class);

        if (itemSearchDto.getSearchQuery() != null && !itemSearchDto.getSearchQuery().isEmpty()) {
            query.setParameter("searchQuery", "%" + itemSearchDto.getSearchQuery() + "%");
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<MainItemDto> content = query.getResultList();

        // 전체 카운트 조회
        String countJpql = "SELECT COUNT(i) FROM ItemImg img JOIN img.item i WHERE img.repImgYn = 'Y'";

        if (itemSearchDto.getSearchQuery() != null && !itemSearchDto.getSearchQuery().isEmpty()) {
            countJpql += " AND i.itemName LIKE :searchQuery";
        }

        long total = em.createQuery(countJpql, Long.class).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }
}