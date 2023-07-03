package com.ican.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ican.service.BiliService;
import com.ican.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * B站图片接口实现类
 *
 * @author ican
 * @date 2023/06/09 17:47
 **/
@Service
public class BiliServiceImpl implements BiliService {

    @Value("${bili.url}")
    private String url;

    @Override
    public String uploadBiliPicture(MultipartFile file, String csrf, String sess) {
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Cookie", "SESSDATA=" + sess);
        // 上传图片
        String result = HttpClientUtils.uploadFileByHttpClient(url, csrf, headers, file);
        // 解析结果
        String imageUrl = Optional.ofNullable(result).filter(StrUtil::isNotBlank).map(JSONUtil::parseObj)
                .map(item -> item.getJSONObject("data")).map(item -> item.getStr("image_url")).orElse("");

        return StrUtil.replaceFirst(imageUrl, "http", "https");
    }

    @Override
    public String uploadBiliPicture(InputStream inputStream, String fileName, String csrf, String sess) {
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Cookie", "SESSDATA=" + sess);
        // 上传图片
        String result = HttpClientUtils.uploadFileByHttpClient(url, csrf, headers, inputStream, fileName);

        // 解析结果
        String imageUrl = Optional.ofNullable(result).filter(StrUtil::isNotBlank).map(JSONUtil::parseObj)
                .map(item -> item.getJSONObject("data")).map(item -> item.getStr("image_url")).orElse("");

        return StrUtil.replaceFirst(imageUrl, "http", "https");
    }
}