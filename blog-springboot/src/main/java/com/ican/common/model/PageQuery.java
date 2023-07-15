package com.ican.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询对象
 *
 * @author youbo
 * @date 2023/07/15
 */
@Data
public class PageQuery implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private long current = 1L;

    /**
     * 每页数量
     */
    private long size = 10L;
}