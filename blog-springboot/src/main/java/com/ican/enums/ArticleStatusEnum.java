package com.ican.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 文章状态枚举
 *
 * @author ican
 */
@Getter
@AllArgsConstructor
public enum ArticleStatusEnum {

    /**
     * 公开
     */
    PUBLIC(1, "公开"),

    /**
     * 私密
     */
    SECRET(2, "私密"),

    /**
     * 草稿
     */
    DRAFT(3, "草稿");

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String description;

    public static ArticleStatusEnum build(Integer status) {
        return Arrays.stream(values()).filter(type -> type.status.equals(status)).findAny().orElse(null);
    }
}
