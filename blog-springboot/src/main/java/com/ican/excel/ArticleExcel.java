package com.ican.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ColumnWidth(20)
@ContentRowHeight(40)
public class ArticleExcel {
    /**
     * 文章标题
     */
    @ExcelProperty("文章标题")
    private String articleTitle;

    /**
     * 文章作者
     */
    @ExcelProperty("文章作者")
    private String user;

    /**
     * 文章分类
     */
    @ExcelProperty("文章分类")
    private String category;

    /**
     * 文章内容
     */
    @ExcelProperty("文章内容")
    private String articleContent;

    /**
     * 文章类型
     */
    @ExcelProperty("文章类型")
    private String articleType;

    /**
     * 是否置顶
     */
    @ExcelProperty("是否置顶")
    private String isTop;

    /**
     * 是否删除
     */
    @ExcelProperty("是否删除")
    private String isDelete;

    /**
     * 是否推荐
     */
    @ExcelProperty("是否推荐")
    private String isRecommend;

    /**
     * 状态
     */
    @ExcelProperty("状态")
    private String state;
}
