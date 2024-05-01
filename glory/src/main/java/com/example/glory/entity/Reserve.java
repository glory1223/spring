package com.example.glory.entity;

import com.example.glory.constant.ReserveStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="reserve")
@Getter
@Setter
@ToString
public class Reserve {

    @Id
    @Column(name = "reserve_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reserveDate; // 예약날짜

    @Enumerated(EnumType.STRING)
    private ReserveStatus reserveStatus; // 예약상태

    @ManyToOne(fetch = FetchType.LAZY) // Reserve테이블이 Member를 참조한다.
    @JoinColumn(name = "member_id") // 포레인키로 사용하겠다.
    private Member member;


    // mappedBy: 연관관계의 주인을 설정한다. 포레인키를 가지고있는 ReserveItem이 연관관계의 주인임.
    @OneToMany(mappedBy = "reserve", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ReserveHamburger> reserveHamburgers = new ArrayList<>();



    public void addReserveHamburger(ReserveHamburger reserveHamburger) {

        reserveHamburgers.add(reserveHamburger); // ★ 양방향 참조시 save를 진행할때는 서로가 참조하는 객체를 꼭 넣어줘야 한다.
        reserveHamburger.setReserve(this); // ★ 양방향 참조관계 일때는 reserveHamburger 객체에도 reserve 객체를 셋팅한다.
    }



}
