package com.example.springbasic2.repository;

import com.example.springbasic2.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

//JPA에서는 Repository클래스가 Model(데이터베이스와 대화를 한다.)의 역할을 한다.
//Repository클래스는 JpaRepository<사용할 엔티티 클래스, 해당 엔티티의 PK타입> 인터페이스를 반드시 상속받아야한다.
public interface ItemRepository extends JpaRepository<Item, Long> { 
}
