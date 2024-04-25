//package com.shopmax.repository;
//
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.core.types.dsl.Wildcard;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.shopmax.constant.ItemSellStatus;
//import com.shopmax.dto.ItemSearchDto;
//import com.shopmax.entity.Item;
//import com.shopmax.entity.QItem;
//import jakarta.persistence.EntityManager;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.thymeleaf.util.StringUtils;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class ItemRepositoryCustomImpl implements ItemRepositoryCustom { // 사용자 정의 인터페이스 작성.
//
//    //이안에 querydsl을 작성.
//    private JPAQueryFactory queryFactory;
//
//    public ItemRepositoryCustomImpl(EntityManager em) { // 쿼리펙토리는 엔티티매니저로 이용.
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//
//
//    // 현재 날짜로부터 이전날짜를 구해주는 메소드.
//    private BooleanExpression regDtsAfter(String searchDateType) {
//        LocalDateTime dateTime = LocalDateTime.now(); // 현재 날짜, 시간
//        if(StringUtils.equals("all", searchDateType) || searchDateType == null) return null; // 타임리프에서 스트링타입 비교해줌.
//        else if (StringUtils.equals("1d", searchDateType))
//            dateTime = dateTime.minusDays(1); // 1일전.
//        else if (StringUtils.equals("1w", searchDateType))
//            dateTime = dateTime.minusWeeks(1); // 1주일전.
//        else if (StringUtils.equals("1m", searchDateType))
//            dateTime = dateTime.minusMonths(1); // 1개월전.
//        else if (StringUtils.equals("6m", searchDateType))
//            dateTime = dateTime.minusMonths(6); // 6개월전.
//        return QItem.item.regTime.after(dateTime);
//    }
//
//
//    // 상태를 전체로 했을때 null이 들어있으므로 처리를 한번 해준다.
//    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
//        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
//    }
//
//    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
//        if(StringUtils.equals("itemNm",searchBy)){ //상품명으로 검색시
//            return QItem.item.itemNm.like("%"+searchBy+"%");
//        } else if(StringUtils.equals("createdBy",searchBy)){ //등록자 검색시
//            return QItem.item.createdBy.like("%"+searchBy+"%");
//        }
//        return null;
//    }
//
//
//    @Override
//    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
//        /*
//        select * from item where reg_time = ? and item_sell_status = ? and item_nm(create_by) like %검색어% order by item_id desc;
//                                                                            ㄴ> item_nm으로도 검색할수있고 creat_by로도 검색할수있음.
//         */
//        List<Item> content = queryFactory.selectFrom(QItem.item)
//                .where(regDtsAfter(itemSearchDto.getSearchDateType()))
//                .where(searchSellStatusEq(itemSearchDto.getSearchSellStatus()))
//                .where(searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
//                .orderBy(QItem.item.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//        long total = queryFactory.select(Wildcard.count).from(QItem.item)
//                .where(regDtsAfter(itemSearchDto.getSearchDateType()))
//                .where(searchSellStatusEq(itemSearchDto.getSearchSellStatus()))
//                .where(searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
//                .fetchOne();
//
//
//        return new PageImpl<>(content, pageable, total); // pagealbe 객체: 한페이지당 게시물 몇개, 시작페이지 번호에 대한 정보를 가지고있는 객체.
//    }
//
//
//}


package com.shopmax.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shopmax.constant.ItemSellStatus;
import com.shopmax.dto.ItemSearchDto;
import com.shopmax.dto.MainItemDto;
import com.shopmax.dto.QMainItemDto;
import com.shopmax.entity.Item;
import com.shopmax.entity.QItem;
import com.shopmax.entity.QItemImg;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //현재 날짜로부터 이전날짜를 구해주는 메소드
    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now(); //현재시간

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1); //1일전
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1); //1주일 전
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1); //1개월 전
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6); //6개월 전
        }
        return QItem.item.regTime.after(dateTime);
    }

    //상태를 전체로 했을 때 null이 들어있으므로 처리를 한번 해준다.
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("itemNm", searchBy)) { //상품명으로 검색 시
            return QItem.item.itemNm.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)) { //등록자 검색 시
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }
    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        /*
        select * from item
        where reg_time = ?
        and item_sell_status = ?
        and item_nm(create_by) like '%검색어%'
        order by item_id desc;
         */

        List<Item> content = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        /*
        select count(*) from item
        where reg_time = ?
        and item_sell_status = ?
        and item_nm(create_by) like '%검색어%'
         */
        long total = queryFactory
                .select(Wildcard.count).from(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .fetchOne();

        //pageable 객체: 한 페이지의 게시물을 보여줄지, 시작 페이지 번호에 대한 정보를 가지고 있따.
        return new PageImpl<>(content, pageable, total);
    }

    // 검색어가 빈 문자열일때를 대비
    private BooleanExpression itemNmLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ?
                null : QItem.item.itemNm.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
     // select item.id, item.item_nm, item.item_detail, item_img.img_url, item.price
        // from item, item_img
        // where item.item_id = item_img.item_id
        //and item_img.rep_img_yn = 'Y'
        //and item.item_nm like '%검색어%'
        //order by item.item_id desc;

        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        List<MainItemDto> content = queryFactory
                .select( new QMainItemDto(
                        item.id,
                        item.itemNm,
                        item.itemDetail,
                        itemImg.imgUrl,
                        item.price)
                )
                .from(itemImg) // item으로 기준잡아도상관업음
                .join(itemImg.item, item)   // where item.item_id = item_img.item_id
                .where(itemImg.repImgYn.eq("Y"))
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable. getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(itemImg) // item으로 기준잡아도상관업음
                .join(itemImg.item, item)   // where item.item_id = item_img.item_id
                .where(itemImg.repImgYn.eq("Y"))
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }


}
