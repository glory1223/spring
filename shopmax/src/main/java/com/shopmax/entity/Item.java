package com.shopmax.entity;

import com.shopmax.constant.ItemSellStatus;
import com.shopmax.dto.ItemFormDto;
import com.shopmax.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item  extends BaseEntity {

    @Id
    @Column(name="item_id") //테이블로 생성될때 컬럼이름을 지정해준다
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키를 자동으로 생성해주는 전략 사용
    private Long id; //상품코드

    @Column(nullable = false, length = 50) //not null여부, 컬럼 크기지정
    private String itemNm; //상품명 -> item_nm

    @Column(nullable = false)
    private int price; //가격 -> price

    @Column(nullable = false)
    private int stockNumber; //재고수량 -> stock_number

    @Lob //clob과 같은 큰타입의 문자타입으로 컬럼을 만든다
    @Column(nullable = false, columnDefinition = "longtext")
    private String itemDetail; //상품상세설명 -> item_detail

    @Enumerated(EnumType.STRING) //enum의 이름을 DB의 저장
    private ItemSellStatus itemSellStatus; //판매상태(SELL, SOLD_OUT) -> item_sell_status


    // item 엔티티 수정하는 메소드.
    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    // 재고 수량 변경.
    public void removeStock(int stockNUmber) {
        int restStock = this.stockNumber - stockNUmber; // 남은 수량 = 상품 제고수량 - 주문수량

        if(restStock <0) { // 더이상 재고가 없다면.
            throw new OutOfStockException("상품의 재고가 부족하빈다. " + "현재수량: " + this.stockNumber);
        }

        this.stockNumber = restStock; // 남은 재고 수량 반영.
    }

    // 재고 증가.
    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }
}
