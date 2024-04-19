package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart_item")
@Getter
@Setter
@ToString
public class CartItem  extends BaseEntity  {

    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int count; // 장바구니에 담긴 상품수량

    @ManyToOne(fetch = FetchType.LAZY) // CartItem은 Cart를 참조한다.
    @JoinColumn(name = "cart_id") // cart_id를 FK로 사용한다.
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY) // CartItem은 Item을 참조한다.
    @JoinColumn(name = "item_id") // item_id를 FK로 사용한다.
    private Item item;
}
