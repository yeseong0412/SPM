package com.example.spm.service;

import com.example.spm.Exception.OutOfStockException;
import com.example.spm.dto.OrderDto;
import com.example.spm.entity.Item;
import com.example.spm.entity.Member;
import com.example.spm.entity.Order;
import com.example.spm.entity.OrderItem;
import com.example.spm.repository.ItemRepository;
import com.example.spm.repository.MemberRepository;
import com.example.spm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

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
}
