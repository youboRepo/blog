package com.ican.excel.utils;

import com.ican.entity.Article;
import com.ican.excel.ArticleExcel;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleExcelUtils {

    /**
     * 转换表格列表
     * 
     * @param articles
     * @return
     */
    public static List<ArticleExcel> toArticleExcelList(List<Article> articles) {
        return articles.stream().map(article -> {
            ArticleExcel articleExcel = new ArticleExcel();
            articleExcel.setArticleTitle("111");
            articleExcel.setUser("222");
            articleExcel.setCategory("333");
            articleExcel.setArticleContent("444");
            articleExcel.setArticleType("555");
            articleExcel.setIsTop("666");
            articleExcel.setIsDelete("555");
            articleExcel.setIsRecommend("323");
            articleExcel.setState("232");
            return articleExcel;
        }).collect(Collectors.toList());
    }
}
