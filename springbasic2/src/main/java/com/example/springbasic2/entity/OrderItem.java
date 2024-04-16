package com.example.springbasic2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity // 현재 클래스를 엔티티 클래스로 사용하곘다고 지정하는 어노테이션.
@Table(name="order_item") // 테이블이름 지정
@Getter
@Setter
public class OrderItem {

    @Id
    @Column(name="order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_price", nullable = false)
    private int price;

    @Column(nullable = false)
    private int count;

    private long orderId;
}
