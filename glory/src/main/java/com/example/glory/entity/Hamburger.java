package com.example.glory.entity;

import com.example.glory.constant.HamburgerCategory;
import com.example.glory.constant.HamburgerStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="hamburger")
@Getter
@Setter
@ToString
public class Hamburger extends BaseEntity {

    @Id
    @Column(name="hamburger_id") //테이블로 생성될때 컬럼이름을 지정해준다
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키를 자동으로 생성해주는 전략 사용
    private Long id; //상품코드

    @Column(nullable = false, length = 50) //not null여부, 컬럼 크기지정
    private String hamburgerNm; //상품명 -> hamburger_nm

    @Column(nullable = false)
    private int price; //가격 -> price

    @Column(nullable = false)
    private int stockNumber; //재고수량 -> stock_number

    @Lob //clob과 같은 큰타입의 문자타입으로 컬럼을 만든다
    @Column(nullable = false, columnDefinition = "longtext")
    private String hamburgerDetail; //상품상세설명 -> hamburger_detail

    @Enumerated(EnumType.STRING) //enum의 이름을 DB에 저장
    private HamburgerStatus hamburgerStatus; //판매상태(SELL, SOLD_OUT) -> item_sell_status

    @Enumerated(EnumType.STRING) //enum의 이름을 DB에 저장.
    private HamburgerCategory hamburgerCategory;


    // 관리자가 hamburger 엔티티 수정하는 메소드.
//    public void updateHamburger(HamburgerFormDto hamburgerFormDto) {
//        this.hamburgerNm = hamburgerFormDto.getHamburgerNm();
//        this.price = hamburgerFormDto.getPrice();
//        this.stockNumber = hamburgerFormDto.getStockNumber();
//        this.hamburgerDetail = hamburgerFormDto.getHamburgerDetail();
//        this.hamburgerStatus = hamburgerFormDto.getHamburgerStatus();
//    }
}
