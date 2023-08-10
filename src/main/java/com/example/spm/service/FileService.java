package com.example.spm.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.invoke.StringConcatException;
import java.util.UUID;

@Service
@Log
public class FileService {

    // UUID 이용해 파일 생성
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileDate) throws Exception{
        UUID uuid =UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid. toString() + extension;

        // 경로 + 파일명
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        //F1leoutoutStream객체를이용하여경로지정후파일저장
        FileOutputStream fos = new FileOutputStream ((fileUploadFullUrl));
        fos.write(fileDate);
        fos.close ();

        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{

        File deleteFile = new File(filePath);

        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일을 삭제했습니다");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
