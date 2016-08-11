package cn.hope.platform.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by myhtls on 16/6/12.
 */
public class ChineseInfoUtils {

    /**
     * 判断是否是中文
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str){
        Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m=p.matcher(str);
        return m.find();
    }
}
