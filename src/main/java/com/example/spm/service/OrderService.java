package com.example.spm.service;

import com.example.spm.Exception.OutOfStockException;
import com.example.spm.dto.OrderDto;
import com.example.spm.dto.OrderHistDto;
import com.example.spm.dto.OrderItemDto;
import com.example.spm.entity.*;
import com.example.spm.repository.ItemImgRepository;
import com.example.spm.repository.ItemRepository;
import com.example.spm.repository.MemberRepository;
import com.example.spm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;        // 상품을 불러와서 재고를 변경해야함
    private final MemberRepository memberRepository;    // 멤버를 불러와서 연결해야함
    private final OrderRepository orderRepository;      // 주문 객체를 저장해야함
    private final ItemImgRepository itemImgRepository;  // 상품 대표 이미지를 출력해야함

    public Long order(OrderDto orderDto , String email) throws OutOfStockException {
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException :: new);
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();

        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();

    }
    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders){
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem : orderItems){
                ItemImg itemImg = itemImgRepository
                        .findByItemIdAndRepImgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

}
