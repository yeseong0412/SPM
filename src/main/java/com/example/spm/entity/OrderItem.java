package com.example.spm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_it")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //가격
    private int count; //수량
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}