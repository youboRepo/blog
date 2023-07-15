package com.ican.common.jdbc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 我的基础映射器接口
 *
 * @author youbo
 * @date 2023/07/15
 */
public interface MyBaseMapper<E> extends BaseMapper<E> {

    /**
     * 插入忽略
     *
     * @param entity
     * @return
     */
    int ignore(E entity);

    /**
     * 插入替换
     *
     * @param entity
     * @return
     */
    int replace(E entity);

    /**
     * 插入更新
     *
     * @param entity
     * @return
     */
    int duplicate(E entity);
}