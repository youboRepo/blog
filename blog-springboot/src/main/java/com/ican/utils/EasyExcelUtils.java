package com.ican.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.ican.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 电子表格导入导出工具类
 *
 * @author youbo
 * @date 2020/07/09
 */
@Slf4j
public class EasyExcelUtils {

    /**
     * 导入表格
     *
     * @param file
     * @param clazz
     * @param sheetNo
     * @param consumer
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz, Integer sheetNo,
        Consumer<ExcelReaderBuilder> consumer) {
        try (InputStream input = file.getInputStream()) {
            ExcelReaderBuilder builder = EasyExcel.read(input).head(clazz);
            if (consumer != null) {
                consumer.accept(builder);
            }
            List<T> excelModels = builder.sheet(sheetNo).doReadSync();
            Assert.notEmpty(excelModels, "导入电子表格为空");
            return excelModels;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("导入电子表格错误");
        }
    }

    /**
     * 导入表格
     *
     * @param file
     * @param clazz
     * @param consumer
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz, Consumer<ExcelReaderBuilder> consumer) {
        return importExcel(file, clazz, null, consumer);
    }

    /**
     * 导入表格
     *
     * @param file
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz) {
        return importExcel(file, clazz, null);
    }

    /**
     * 导入表格
     *
     * @param file
     * @param sheetNo
     * @return
     */
    public static List<List<String>> importExcel(MultipartFile file, Integer sheetNo) {
        List<Map<Integer, String>> data = importExcel(file, null, sheetNo, null);
        return data.stream().map(row -> new ArrayList<>(row.values())).collect(Collectors.toList());
    }

    /**
     * 导入表格
     *
     * @param file
     * @return
     */
    public static List<List<String>> importExcel(MultipartFile file) {
        List<Map<Integer, String>> data = importExcel(file, null, null);
        return data.stream().map(row -> new ArrayList<>(row.values())).collect(Collectors.toList());
    }

    /**
     * 导出表格
     *
     * @param fileName
     * @param data
     * @param clazz
     * @param response
     * @param consumer
     */
    public static void exportExcel(String fileName, List<?> data, Class<?> clazz, HttpServletResponse response,
        Consumer<ExcelWriterBuilder> consumer) {
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder
                .encode(fileName, "UTF-8") + System.currentTimeMillis() + ".xlsx");
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            ExcelWriterBuilder builder = EasyExcel.write(response.getOutputStream(), clazz);
            if (consumer != null) {
                consumer.accept(builder);
            }
            builder.sheet("Sheet1").doWrite(data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("导出电子表格错误");
        }
    }

    /**
     * 导出表格
     *
     * @param fileName
     * @param data
     * @param clazz
     * @param response
     */
    public static void exportExcel(String fileName, List<?> data, Class<?> clazz, HttpServletResponse response) {
        exportExcel(fileName, data, clazz, response, null);
    }

    /**
     * 插入图片
     *
     * <p></p>
     *
     * @param workbook
     * @param patriarch 画笔
     * @param url       图片链接
     * @param dx1       起始单元格中的x,y坐标.
     * @param dy1       起始单元格中的x,y坐标.
     * @param dx2       结束单元格中的x,y坐标
     * @param dy2       结束单元格中的x,y坐标
     * @param col1      指定起始的单元格，下标从0开始
     * @param row1      指定起始的单元格，下标从0开始
     * @param col2      指定起始的单元格，下标从0开始
     * @param row2      指定起始的单元格，下标从0开始
     */
    public static void insertImage(HSSFWorkbook workbook, HSSFPatriarch patriarch, URL url, int dx1, int dy1, int dx2,
        int dy2, short col1, int row1, short col2, int row2) {
        BufferedImage bufferImage = null;
        ByteArrayOutputStream byteArrayOut = null;

        try {
            bufferImage = ImageIO.read(url);

            // 字节输出流，用来写二进制文件
            byteArrayOut = new ByteArrayOutputStream();

            ImageIO.write(bufferImage, "jpg", byteArrayOut);

            // 设置为形状
            HSSFClientAnchor anchor = new HSSFClientAnchor(dx1, dy1, dx2, dy2, col1, row1, col2, row2);

            // 将图片添加到Excel文件中
            patriarch.createPicture(anchor, workbook
                .addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        } finally {
            IOUtils.closeQuietly(byteArrayOut);
        }
    }

    /**
     * 根据时间在文件名后面添加时间命名的文件名
     *
     * <p>e.q. order.xls 变成 order.2016080808121212.xls
     *
     * @return String
     */
    public static String newFileNameByTime(String filename) {
        // 扩展名
        int lastDocIndex = filename.lastIndexOf(".");
        String extensionName = filename.substring(lastDocIndex + 1);

        String prefixName = filename.substring(0, lastDocIndex);

        Date currTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String timeStr = sdf.format(currTime);

        return prefixName + "." + timeStr + "." + extensionName;
    }
}