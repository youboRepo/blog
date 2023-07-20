package com.ican.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 分页返回类
 *
 * @author ican
 * @date 2022/12/03 21:44
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分页返回类")
public class PageResult<T> {

    /**
     * 分页结果
     */
    @ApiModelProperty(value = "分页结果")
    private List<T> recordList;

    /**
     * 总数
     */
    @ApiModelProperty(value = "总数", dataType = "long")
    private Long count;

    /**
     * 克隆方法
     *
     * @param action
     * @param clazz
     * @param <R>
     * @return
     */
    public <R> PageResult<R> clone(BiFunction<List<T>, Class<R>, List<R>> action, Class<R> clazz) {
        return new PageResult<>(action.apply(this.recordList, clazz), this.count);
    }

    /**
     * 克隆方法
     *
     * @param mapper
     * @param <R>
     * @return
     */
    public <R> PageResult<R> clone(Function<Collection<T>, List<R>> mapper) {
        return new PageResult<>(mapper.apply(this.recordList), this.count);
    }

    /**
     * 循环方法
     *
     * @param action
     */
    public void forEach(Consumer<? super T> action) {
        Optional.ofNullable(this.recordList).orElse(Collections.emptyList()).forEach(action);
    }
}