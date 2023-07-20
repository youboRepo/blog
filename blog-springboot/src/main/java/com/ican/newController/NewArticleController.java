package com.ican.newController;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.ican.entity.custom.ArticleCustom;
import com.ican.entity.query.ArticleQuery;
import com.ican.excel.ArticleExcel;
import com.ican.excel.utils.ArticleExcelUtils;
import com.ican.model.vo.CategoryVO;
import com.ican.newService.NewArticleService;
import com.ican.newService.NewCategoryService;
import com.ican.utils.EasyExcelUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 文章控制层
 *
 * @author youbo
 * @date 2023/07/15
 */
@Api(tags = "新文章模块")
@RestController
@RequestMapping("new/articles")
public class NewArticleController {

    @Autowired
    private NewArticleService newArticleService;

    @Autowired
    private NewCategoryService newCategoryService;

    /**
     * 导出文章列表
     *
     * @param query
     */
    @PostMapping("export")
    public void exportArticleList(ArticleQuery query, HttpServletResponse response) {
        List<ArticleCustom> articles = null;

        switch (query.getExportType()) {
            case 1:
                articles = newArticleService.getArticleList(query);
                break;
            case 2:
                articles = newArticleService.getArticlePageList(query).getRecordList();
                break;
            case 3:
                articles = newArticleService.getArticleList(query.getIds());
                break;
            default:
                break;
        }

        Assert.notEmpty(articles, "文章列表为空");
        
        this.setCategoryName(articles);

        List<ArticleExcel> articleExcels = ArticleExcelUtils.toArticleExcelList(articles);

        EasyExcelUtils.exportExcel("文章列表", articleExcels, ArticleExcel.class, response);
    }

    private void setCategoryName(List<ArticleCustom> articles) {
        if (CollUtil.isEmpty(articles)) {
            return;
        }

        List<Integer> categoryIds = articles.stream().map(ArticleCustom::getCategoryId).distinct()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        Map<Integer, String> categoryMap = newCategoryService.getCategoryList(categoryIds).stream()
            .collect(Collectors.toMap(CategoryVO::getId, CategoryVO::getCategoryName));

        if (MapUtil.isEmpty(categoryMap)) {
            return;
        }

        articles.forEach(
            item -> Optional.ofNullable(item.getCategoryId()).map(categoryMap::get).ifPresent(item::setCategoryName));
    }
}
