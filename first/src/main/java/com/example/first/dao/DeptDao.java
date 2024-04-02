package com.example.first.dao;

import com.example.first.dto.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper //Mybatis mapper파일하고 연동하기 위해 붙여야 한다.
public interface DeptDao {
    
    // mapper파일에 작성한 쿼리문을 호출하기위해 사용 (mapper의 id와 동일하게 작성함)
    public List<Dept> selectList();
}
