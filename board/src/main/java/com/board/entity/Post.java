package com.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@Setter
@ToString
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;



    @Lob //clob과 같은 큰타입의 문자타입으로 컬럼을 만든다
    @Column(columnDefinition = "longtext")
    private String content;

}
