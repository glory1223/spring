package com.board.dto;

import com.board.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class PostFormDto {
    private Long Id;

    @NotBlank(message= "제목은 필수 입력입니다.")
    private String title;

    @NotNull(message= "내용은 필수 입력입니다.")
    private String content;


    //modelMapper 를 사용해서 dto <-> entity 편하게 바꿔요.
    private static ModelMapper modelMapper = new ModelMapper();

    //dto -> entity
    public Post createPost() {
        return modelMapper.map(this, Post.class); //(this(PostFormDto)를 Post클래스로 바꾸겠다.)
        // Post Entity객체를 리턴.
    }

    //entity -> dto
    public static PostFormDto of(Post post) { // static으로 인해 객체생성없이 쓸수있음?
        return modelMapper.map(post, PostFormDto.class);
        // ItemFormDto 객체를 리턴.
    }

}
