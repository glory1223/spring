package com.shopmax.service;

import com.shopmax.dto.ItemFormDto;
import com.shopmax.dto.ItemImgDto;
import com.shopmax.dto.ItemSearchDto;
import com.shopmax.dto.MainItemDto;
import com.shopmax.entity.Item;
import com.shopmax.entity.ItemImg;
import com.shopmax.repository.ItemImgRepository;
import com.shopmax.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    // item 테이블에 상품등록(insert)
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList ) throws Exception {
        // 1. 상품 등록 (insert)
        Item item = itemFormDto.createItem(); // dto -> entity
        itemRepository.save(item); // insert


        // 2. 이미지 등록(5개의 이미지를 등록해야하므로 for문으로 하나씩 저장.)
        for(int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item); // ★itemImg가 item을 참조하므로 insert하기전 반드시 item 객체를 넣어준다.

            // 첫번째 이미지 일때 대표이미지로 지정.
            if(i == 0) {
                itemImg.setRepImgYn("Y");
            } else {
                itemImg.setRepImgYn("N");
            }

            // 이미지 파일을 하나씩 저장.
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId(); // 등록한 상품 id를 리턴.
    }


    // 상품 가져오기
    @Transactional(readOnly = true) // 트랜잭션 읽기전용 (변경감지 수행x) -> 성능 향상.
    public ItemFormDto getItemDtl(Long itemId) {
        //1. item_img 테이블의 이미지를 가저온다.
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        // ItemImg Entity -> Dto 변환. (화면에는 dto로 뿌려줌)
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg); // dto 객체로 바뀜.
            
            itemImgDtoList.add(itemImgDto); // dto객체로 바꾼 객체를 리스트에 넣어준다._
        }

        //2. item 테이블에 있는 데이터를 가져온다.
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new); // 엔티티 못찾으면 throw 처리

        //Item Entity -> Dto 변환. (화면에는 DTo로)
        ItemFormDto itemFormDto = ItemFormDto.of(item);

        //3. ItemFormDto에 itemImgDtoList를 넣어준다 -> 화면단에서는 ItemFormDto에서 이미지 리스트를 가지고 오므로.
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }


    // 상품 수정하기. (엔티티 값만 바꿔줘도 JPA가 알아서 DB에 업데이트해줌.)
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        // 1. item 엔티티 수정
        // ★update를 진행하기 전 반드시 select를 해온다. 영속성 컨텍스트에 item 엔티티가 없을수도 있으므로 그럴경우에는 DB에서 영속성컨텍스트로 가져옴.
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        // update 실행.
        // ★ 한번 select를 진행하면 엔티티가 영속성 컨텍스트에 저장되고, 변경감지 기능으로 인해 트랜잭션 커밋 시점에 엔티티와 DB에 저장된 값이 다른 내용은 JPA에서 업뎃해준다.
        item.updateItem(itemFormDto);

        // 2. itemImg 엔티티 수정
        List<Long> itemImgIds = itemFormDto.getItemImgIds(); // 상품 이미지 아이디 리스트 조회. 아이템이미지 아이디들이 매개변수로 쓰이기떄문에 만들었어.

        // 5개의 이미지 파일을 업로드 했으므로 아래처럼 for문을 이용해 하나씩 이미지 업데이트를 진행.
        for (int i = 0; i < itemImgFileList.size(); i++) {
            //itemImgService.updateItemImg(매개변수:itemImg Id, 이미지파일);
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId(); // 변경한 item의 id리턴.
    }

    @Transactional (readOnly = true)
    public Page<Item> getAdminItemPage (ItemSearchDto itemSearchDto, Pageable pageable) {
        Page<Item> itemPage  = itemRepository.getAdminItemPage(itemSearchDto, pageable);
        return itemPage;
    }

    public Page<MainItemDto> getMainItemPage (ItemSearchDto itemSearchDto, Pageable pageable) {
        Page<MainItemDto> mainItemPage = itemRepository.getMainItemPage(itemSearchDto, pageable);

        return mainItemPage;
    }
}
