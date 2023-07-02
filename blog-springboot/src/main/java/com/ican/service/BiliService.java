package com.ican.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * B站服务接口
 *
 * @author ican
 * @date 2023/06/09 17:46
 **/
public interface BiliService {

    /**
     * B站图片上传
     *
     * @param file 图片
     * @param csrf csrf
     * @param data data
     * @return 图片链接
     */
    String uploadBiliPicture(MultipartFile file, String csrf, String data);

    /**
     * B站图片上传
     *
     * @param inputStream 文件输入流
     * @param fileName 文件名字
     * @param csrf csrf
     * @param data data
     * @return 图片链接
     */
    String uploadBiliPicture(InputStream inputStream, String fileName, String csrf, String data);
}