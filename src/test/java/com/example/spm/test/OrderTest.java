package com.example.spm.test;

import com.example.spm.constant.ItemSellStatus;
import com.example.spm.entity.Item;
import com.example.spm.entity.Member;
import com.example.spm.entity.Order;
import com.example.spm.entity.OrderItem;
import com.example.spm.repository.ItemRepository;
import com.example.spm.repository.MemberRepository;
import com.example.spm.repository.OrderRepository;
import com.example.spm.test.annotation.TestInit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInit
class OrderTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    public Item createItem() {
        Item item = new Item();
        item.setItemNm("테스트상품");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    @Transactional  // 트랜잭션 추가
    public void cascadeTest() {
        // 1. Order 생성 (초기화X)
        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            // 2. Item 생성 및 저장 (초기화X)
            Item item = createItem();
            itemRepository.save(item);

            // 3. OrderItem 생성 및 초기화
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(100);
            orderItem.setOrder(order);

            // 4. Order 객체에 OrderItem 추가
            order.getOrderItems().add(orderItem);
        }

        // 5. Order 객체 저장
        orderRepository.saveAndFlush(order);
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
    }

    @Autowired
    MemberRepository memberRepository;

    public Order createOrder(){
        Order order = new Order();
        for(int i=0; i<3; i++){
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount (10);
            orderItem.setOrderPrice (1000) ;
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        Member member = new Member();
        memberRepository.save(member);
        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("연관 관계가 끊어진 자식 엔티티 삭제 테스트")
    public void orphanRemovalTest(){
        Order order = this.createOrder();
        Long orderItem_id = order.getOrderItems().get(0).getId();
        order.getOrderItems().remove(0);
        em.flush(); // 디비에 처리
        assertEquals(Optional.empty(), orderRepository.findById(orderItem_id));
    }
}
