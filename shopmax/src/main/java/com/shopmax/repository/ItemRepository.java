package com.shopmax.repository;

import com.shopmax.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemRepository extends JpaRepository<Item, Long>
        ,QuerydslPredicateExecutor<Item>, ItemRepositoryCustom { // Spring Data JPA 레파지토리에서 사용자 정의 인터페이스 상속.: QuerydslPredicateExecutor<Item>, ItemRepositoryCustom


}
