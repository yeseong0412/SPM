package com.example.spm.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
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
        //File out put Stream 객체를 이용하여 경로 지정 후 파일 저장
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
