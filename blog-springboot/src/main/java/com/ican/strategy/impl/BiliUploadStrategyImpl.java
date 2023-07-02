package com.ican.strategy.impl;

import com.ican.service.impl.BiliServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class BiliUploadStrategyImpl extends AbstractUploadStrategyImpl {

    @Value("${bili.sess}")
    private String sess;

    @Value("${bili.csrf}")
    private String csrf;
    
    @Autowired
    private BiliServiceImpl biliService;

    @Override
    public Boolean exists(String filePath) {
        return false;
    }

    @Override
    public String upload(String path, String fileName, InputStream inputStream) throws IOException {
        
        // 上传图片到bilibili
        return biliService.uploadBiliPicture(inputStream, fileName, csrf, sess);
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return "";
    }
}
