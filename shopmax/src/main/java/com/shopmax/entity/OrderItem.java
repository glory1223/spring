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

    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());

        item.removeStock(count); // item 객체 안의 재고변경
        return orderItem;
    }

    public int getTotalPrice() {
        return orderPrice * count; // 총 가격.
    }

    // 재고를 원래대로 돌리는 메소드
    public void cancel() {
        // 이전에 주문을 한만큼(count) 다시 item의 stock을 더해준다.
        this.getItem().addStock(count);
    }

}
