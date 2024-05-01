package com.example.glory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="reserve_hamburger")
@Getter
@Setter
@ToString
public class ReserveHamburger {

    @Id
    @Column(name = "reserve_hamburger_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private int reservePrice; // 주문가격

    private int count; // 주문수량

    @ManyToOne(fetch = FetchType.LAZY) // ReserveHamburger이 reserve를 참조한다.
    @JoinColumn (name = "reserve_id")
    private Reserve reserve;


//    public static ReserveHamburger createReserveHamburger (Hamburger Hamburger, int count) {
//        ReserveHamburger reserveHamburger = new ReserveHamburger();
//        reserveHamburger.setReserve(Reserve);
//        reserveHamburger.setCount(count);
//        reserveHamburger.setReservePrice(getPrice());
//
//        hamburger.removeStock(count); // item 객체 안의 재고변경
//        return orderItem;
//    }

}
