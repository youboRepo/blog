package com.ican.utils;

import cn.hutool.core.util.ObjUtil;
import com.ican.constant.CommonConstant;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 公共工具类
 *
 * @author ican
 */
public class CommonUtils {

    /**
     * 默认分隔正则
     */
    private static final String DEFAULT_SPLIT_REGEX = ",， \t\n\r";

    /**
     * 检测邮箱是否合法
     *
     * @param username 用户名
     * @return 合法状态
     */
    public static boolean checkEmail(String username) {
        String rule = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher m = p.matcher(username);
        //进行正则匹配
        return m.matches();
    }

    /**
     * 获取括号内容
     *
     * @param str str
     * @return {@link String} 括号内容
     */
    public static String getBracketsContent(String str) {
        return str.substring(str.indexOf("(") + 1, str.indexOf(")"));
    }

    /**
     * 转换List
     *
     * @param obj   obj
     * @param clazz clazz
     * @return {@link List<T>}
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return result;
    }
    
    public static String getYesOrNo(Integer bool) {
        if (bool == null) {
            return "否";
        }
        
        return bool.equals(CommonConstant.TRUE) ? CommonConstant.YES : CommonConstant.NO;
    }

    /**
     * 分隔字符列表
     *
     * @param text
     * @param regex
     * @return
     */
    public static List<String> splitStringList(String text, String regex) {
        String[] split = StringUtils.split(text, regex);
        if (split == null) {
            return Lists.newArrayList();
        }
        return Arrays.asList(split);
    }

    /**
     * 分隔字符列表
     *
     * @param text
     * @return
     */
    public static List<String> splitStringList(String text) {
        return splitStringList(text, DEFAULT_SPLIT_REGEX);
    }

    /**
     * 分隔整数列表
     *
     * @param text
     * @param regex
     * @return
     */
    public static List<Integer> splitIntegerList(String text, String regex) {
        String[] split = StringUtils.split(text, regex);
        if (split == null) {
            return Lists.newArrayList();
        }
        return Arrays.stream(split).filter(StringUtils::isNumeric).map(Integer::valueOf).collect(Collectors.toList());
    }

    /**
     * 分隔整数列表
     *
     * @param text
     * @return
     */
    public static List<Integer> splitIntegerList(String text) {
        return splitIntegerList(text, DEFAULT_SPLIT_REGEX);
    }

    /**
     * 分隔长整列表
     *
     * @param text
     * @param regex
     * @return
     */
    public static List<Long> splitLongList(String text, String regex) {
        String[] split = StringUtils.split(text, regex);
        if (split == null) {
            return Lists.newArrayList();
        }
        return Arrays.stream(split).filter(StringUtils::isNumeric).map(Long::valueOf).collect(Collectors.toList());
    }

    /**
     * 分隔长整列表
     *
     * @param text
     * @return
     */
    public static List<Long> splitLongList(String text) {
        return splitLongList(text, DEFAULT_SPLIT_REGEX);
    }
}