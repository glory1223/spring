package com.shopmax.repository;


import com.shopmax.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    // select * from item_img where item_id = ? order by item_id asc(오름차순);
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    // select * from item_img where item_id = ? and rep_img_Yn = ?
    ItemImg  findByItemIdAndRepImgYn(Long itemId, String repImgYn);
}
