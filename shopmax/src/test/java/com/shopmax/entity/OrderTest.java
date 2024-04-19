package com.shopmax.entity;

import com.shopmax.constant.ItemSellStatus;
import com.shopmax.repository.ItemRepository;
import com.shopmax.repository.MemberRepository;
import com.shopmax.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class OrderTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    public Item createItem(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        //item.setRegTime(LocalDateTime.now());
        //item.setUpdateTime(LocalDateTime.now());
        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {
        Order order = new Order();

        for(int i= 0; i<3; i++) {
            Item item = createItem();
            itemRepository.save(item); // 3개의 item생성 후 insert

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);

            // 양방향은 서로가 서로를 참조하므로 insert 할때 두 객체를 다 넣어준다. (3개씩 생성)
            orderItem.setOrder(order); // OrderItem은 Order를 참조.
            order.getOrderItems().add(orderItem); //Order는 OrderItem을 참조.


        }

        // 부모객체만 insert한다.
        orderRepository.save(order); // insert // cascade(영속성전이상태)를 설정해놓았기때문에 자식인 orderItem까진 save안해도된다.
                                     // 단, order객체에 order.getOrderItems().add(orderItem); orderitem값을 넣어줘야함!
        em.clear(); //select쿼리문 보려고. (DB에서 select하는 쿼리문을 실행시키기위해 영속성컨텍스트에서 분리한다. 영속성컨텍스트에서 있으면 select안함.)


        // 양방향 + 영속성 전이 상태를 만들었기 때문에 부모 객체만 select한다.
        // select * from orders where order_id = ?
        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);


        //System.out.println(savedOrder); //메모리 부족에러..
        List<OrderItem> orderItems = savedOrder.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            System.out.println("orderItem Id: " + orderItem.getId());
        }

        // 저장된 orderItems의 갯수가 3개면 테스트 통과
        Assertions.assertEquals(3, savedOrder.getOrderItems().size());

    }


    public Order createOrder() {
        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item); // orderItem 객체가 참조하고있는 item 넣어줌.
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order); // orderItem 객체가 참조하는 order 넣어줌.
            order.getOrderItems().add(orderItem); // order 객체가 참조하는 orderItem 여러개 넣어줌.

        }
        Member member = new Member();
        memberRepository.save(member); // insert 이떄는 일단 pk값만 들어갈것임.

        order.setMember(member); // order가 참조하고있는 member를 넣어줌.
        orderRepository.save(order); //최종적으로는 insert하게되면  order, member, orderItem(양방향) , item에  insert시킴.

        return order;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest() {
        Order order = createOrder();
        List<OrderItem> orderItems = order.getOrderItems();

        //고아객체로 만들어서 자식레코드만 따로 제거할수 있어요.
        orderItems.remove(0);

        em.flush(); // 커밋.쿼리문 실행보려고
    }
}
