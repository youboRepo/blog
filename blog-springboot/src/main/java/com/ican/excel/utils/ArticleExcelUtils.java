package com.ican.excel.utils;

import cn.hutool.core.util.BooleanUtil;
import com.github.houbb.heaven.util.lang.BoolUtil;
import com.ican.entity.custom.ArticleCustom;
import com.ican.enums.ArticleStatusEnum;
import com.ican.enums.ArticleTypeEnum;
import com.ican.excel.ArticleExcel;
import com.ican.utils.CommonUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArticleExcelUtils {

    /**
     * 转换表格列表
     * 
     * @param articles
     * @return
     */
    public static List<ArticleExcel> toArticleExcelList(List<ArticleCustom> articles) {
        return articles.stream().map(article -> {
            ArticleExcel articleExcel = new ArticleExcel();
            articleExcel.setArticleTitle(article.getArticleTitle());
            articleExcel.setCategory(article.getCategoryName());
            articleExcel.setArticleContent(article.getArticleContent());
            articleExcel.setIsTop(CommonUtils.getYesOrNo(article.getIsTop()));
            articleExcel.setIsDelete(CommonUtils.getYesOrNo(article.getIsDelete()));
            articleExcel.setIsRecommend(CommonUtils.getYesOrNo(article.getIsRecommend()));
            Optional.ofNullable(article.getArticleType()).map(String::valueOf).map(ArticleTypeEnum::build)
                .map(ArticleTypeEnum::display).ifPresent(articleExcel::setArticleType);
            Optional.ofNullable(article.getStatus()).map(ArticleStatusEnum::build)
                .map(ArticleStatusEnum::getDescription).ifPresent(articleExcel::setState);

            try {
                articleExcel.setArticleCover(new URL(article.getArticleCover()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return articleExcel;
        }).collect(Collectors.toList());
    }
}
