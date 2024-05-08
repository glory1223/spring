package com.board.entity;

import com.board.dto.PostFormDto;
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
public class Post extends BaseEntity{
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String name;


    @Lob //clob과 같은 큰타입의 문자타입으로 컬럼을 만든다
    @Column(columnDefinition = "longtext")
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)// Order가 Member를 참조한다/
    @JoinColumn(name = "member_id") // FK로 사용하겠다
    private Member member;



    // post 엔티티 수정하는 메소드.
    public void updatePost(PostFormDto postFormDto) {
        this.title = postFormDto.getTitle();
//       this.name = postFormDto.getName();
        this.content = postFormDto.getContent();

    }
}
