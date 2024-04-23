package com.shopmax.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log // 로그(기록)을 남길수있는 어노테이션.
public class FileService { // 파일 업로드하는 기능이 있는 클래스

    // 이미지 파일을 서버에 업로드.
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID(); // 중복되지 않은 파일의 이름을 만든다.

        // 이미지1.jpg -> 이미지 확장자 명을 구한다.
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 파일 이름 생성 -> EERERERT-ERERERER-CDFDFGD.jpg
        String savedFileName = uuid.toString() + extension;

        // C:/shop/item/EERERERT-ERERERER-CDFDFGD.jpg
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        // 파일 업로드
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData); // 이미지 파일 업로드.
        fos.close(); // stream 은 열고 항상 닫아줘야함

        return savedFileName;
    }

    // 이미지 파일을 서버에서 삭제.
    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);
        
        // 파일 삭제
        if(deleteFile.exists()) { // 해당파일이 존재하면
            deleteFile.delete(); // 파일 삭제.
            log.info("파일을 삭제하였습니다."); // 서버에 로그 기록을 저장한다.
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
