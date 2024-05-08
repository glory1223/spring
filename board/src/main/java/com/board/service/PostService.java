package com.board.service;

import com.board.dto.PostFormDto;
import com.board.dto.PostSearchDto;
import com.board.entity.Member;
import com.board.entity.Post;
import com.board.repository.MemberReposiotry;
import com.board.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberReposiotry memberReposiotry;

    // post 테이블에 상품등록(insert)
    public Long savePost(PostFormDto postFormDto) throws Exception {

        //1. 게시글 등록 (insert)
        Post post = postFormDto.createPost(); // 엔티티 객체 리턴.
        postRepository.save(post); // insert

        //2. 이미지 등록은 일단 보류.


        return post.getId(); // 게시글 등록 후 게시물ID 리턴.
    }


    @Transactional (readOnly = true)
    public Page<Post>getAdminPostPage(PostSearchDto postSearchDto, Pageable pageable) {
        Page<Post> postPage  = postRepository.getAdminPostPage(postSearchDto, pageable);
       return postPage;

    }

    // 게시물 가져오기(상세)
    @Transactional(readOnly = true) // 트랜잭션 읽기전용 (변경감지 수행x) -> 성능 향상.
    public PostFormDto getPostDtl(Long postId) {
        //1. item_img 테이블의 이미지를 가저온다.
        // List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        // ItemImg Entity -> Dto 변환. (화면에는 dto로 뿌려줌)
        //List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        //for (ItemImg itemImg : itemImgList) {
//            ItemImgDto itemImgDto = ItemImgDto.of(itemImg); // dto 객체로 바뀜.
//
//            itemImgDtoList.add(itemImgDto); // dto객체로 바꾼 객체를 리스트에 넣어준다._
//        }

        //2. post테이블에 있는 데이터를 가져온다.
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new); // 엔티티 못찾으면 throw 처리

        //Post Entity -> Dto 변환. (화면에는 DTo로)
        PostFormDto postFormDto = PostFormDto.of(post);

        //3. ItemFormDto에 itemImgDtoList를 넣어준다 -> 화면단에서는 ItemFormDto에서 이미지 리스트를 가지고 오므로.
        //itemFormDto.setItemImgDtoList(itemImgDtoList);

        return postFormDto;
    }


    // 게시물 수정. (엔티티 값만 바꿔줘도 JPA가 알아서 DB에 업데이트해줌.)
    public Long updatePost(PostFormDto postFormDto) throws Exception {
        // 1. item 엔티티 수정
        // ★update를 진행하기 전 반드시 select를 해온다. 영속성 컨텍스트에 post 엔티티가 없을수도 있으므로 그럴경우에는 DB에서 영속성컨텍스트로 가져옴.
        Post post = postRepository.findById(postFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        // update 실행.
        // ★ 한번 select를 진행하면 엔티티가 영속성 컨텍스트에 저장되고, 변경감지 기능으로 인해 트랜잭션 커밋 시점에 엔티티와 DB에 저장된 값이 다른 내용은 JPA에서 업뎃해준다.
        post.updatePost(postFormDto);

        // 2. itemImg 엔티티 수정
        //List<Long> itemImgIds = itemFormDto.getItemImgIds(); // 상품 이미지 아이디 리스트 조회. 아이템이미지 아이디들이 매개변수로 쓰이기떄문에 만들었어.

        // 5개의 이미지 파일을 업로드 했으므로 아래처럼 for문을 이용해 하나씩 이미지 업데이트를 진행.
//        for (int i = 0; i < itemImgFileList.size(); i++) {
//            //itemImgService.updateItemImg(매개변수:itemImg Id, 이미지파일);
//            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
       // }

        return post.getId(); // 변경한 item의 id리턴.
    }




    // 본인확인(현재 로그인한 사용자와 주문데이터를 생성한 사용자가 같은지 검사)
    @Transactional(readOnly = true) //find메소드밖에 사용안했기때문에 readonly
    public boolean validatePost(Long postId, String email) {
        //로그인한 사용자 찾기
        Member curMember = memberReposiotry.findByEmail(email);
        //주문내역
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        String savedMember = post.getCreatedBy(); // 주문한 사용자 찾기

        // 로그인한 사용자의 이메일과 주문한 사용자의 이메일이 같은지 비교.
        if(!StringUtils.equals(curMember.getEmail(), savedMember)) {
            return false;
        }
        return true;
    }


    // 주문 삭제
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        //CasCade 설정을 통해 order의 자식 엔티티에 해당하는 orderItem도 같이 삭제된다.
        postRepository.delete(post); // delete
    }
}
