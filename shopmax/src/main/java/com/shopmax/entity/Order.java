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
    @Column(name = "oreder_id")
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
    
}
