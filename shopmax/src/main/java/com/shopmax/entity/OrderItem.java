package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="order_item")
@Getter
@Setter
@ToString
public class OrderItem  extends BaseEntity {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private int orderPrice; // 주문가격

    private int count; // 주문수량

    @ManyToOne(fetch = FetchType.LAZY) // OrderItem이 order을 참조한다.
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY) // OrderItem이 item을 참조한다.
    @JoinColumn(name = "item_id")
    private Item item;
}
