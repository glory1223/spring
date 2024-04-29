package com.shopmax.entity;

import com.shopmax.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") // 테이블명을 order이라고 하면 에러가남.
@Getter
@Setter
@ToString
public class Order { // 테이블명을 order이라고 하면 에러가남(order by 때문) 하지만 클래스 명은 복수형으로 할수없음.
    
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime orderDate; // 주문일
    
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태


    @ManyToOne(fetch = FetchType.LAZY)// Order가 Member를 참조한다/
    @JoinColumn(name = "member_id") // FK로 사용하겠다
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) // mappedBy: 연관관계의 주인을 설정 (FK를 가지고있는 OrderItem이 연관관계의 주인임.)
    private List<OrderItem> orderItems = new ArrayList<>();


    //★ 양방향 참조시 save를 진행할때는 서로가 참조하는 객체를 꼭 넣어주어야 한다.
    public void addOrderItem(OrderItem orderItem) {

        orderItems.add(orderItem);
        orderItem.setOrder(this); //★ 양방향 참조관계 일떄는 orderItem 객체에도 order객체를 세팅
        
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);

        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // 총 주문 금액
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem: orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return  totalPrice;
    }


    // 주문 취소
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;

        // 재고를 원래대로 돌려놓는다.
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }



}
