package com.example.springbasic2.entity;

import com.example.springbasic2.constant.ItemSellStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDateTime;

//DTO(Data Transfer Object): 데이터 전송객체
//Entity 클래스는 DTO클래스와 다르다.
//Entity 클래스는 데이터베이스와 대응되는 클래스  ===>>> 엔티티 클래스를 통해서 JPA는 테이블을 생성하고 insert, update, delete, select..
@Entity // 현재 클래스를 엔티티 클래스로 사용하곘다고 지정하는 어노테이션.
@Table(name="item") // 테이블이름 지정
@Getter
@Setter
@ToString // Object객체의 toString 메소드를 오버라이드 하지 않아도 객체 정보를 출력.
public class Item {

    @Id // 현재 이속성을 테이블의 PK로 사용
    @Column(name="item_id") //테이블로 생성될때 컬럼이름을 지정해준다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT 지정.
    private Long id; //상품코드, PK

    @Column(nullable = false, length = 50) // not null 제약조건지정. 컬럼 크기 지정.
    private String itemNm; //상품명

    @Column(nullable = false)
    private int price; //가격
    

    @Column(nullable = false)
    private  int stockNumber; //재고수량

    @Lob // 큰타입의 문자 타입을 지정
    @Column(nullable = false, columnDefinition = "longtext") // 컬럼의 타입을 별도로 지정.
    private  String itemDetail; //상품상세설명

    @Enumerated(EnumType.STRING) //enum클래스에 저장된 상수를 item_sell_status 컬럼에 문자타입으로 저장.
    private ItemSellStatus itemSellStatus; //판매상태(SELL, SOLD_OUT)

    private LocalDateTime regTime; //상품등록시간

    private LocalDateTime updateTime; //상품수정시간


}