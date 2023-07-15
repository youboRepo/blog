package com.ican.enums;

import com.ican.common.model.CodeEnum;

import java.util.Arrays;

/**
 * 文章类型枚举类
 * 
 * @author youbo
 * @date 2023/7/15/20:36
 */
public enum ArticleTypeEnum implements CodeEnum {
    ORIGINAL("1", "原创"),

    REPRODUCED("2", "转载"),

    TRANSLATE("3", "翻译");
    

    private String code;

    private String display;

    ArticleTypeEnum(String code, String display) {
        this.code = code;
        this.display = display;
    }

    public static ArticleTypeEnum build(String code) {
        return Arrays.stream(values()).filter(type -> type.code.equalsIgnoreCase(code)).findAny().orElse(null);
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String display() {
        return this.display;
    }
}
