package com.example.glory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})  // audit 기능을 사용하기 위해 작성.
@MappedSuperclass // 엔티티에서 부모클래스로 사용하려면 해당 어노테이션을 사용해야한다.
@Setter
@Getter
public class BaseTimeEntity {

    @CreatedDate // 게시물 최초로 등록한 날짜를 저장 및 감지.
    @Column(updatable = false) // 해당 컬럼에 대한 값은 업데이트 금지.
    private LocalDateTime regTime; // 등록날짜

    @LastModifiedDate // 게시물을 수정한 날짜를 저장 및 감지.
    private LocalDateTime updateTime; // 수정날짜
}
