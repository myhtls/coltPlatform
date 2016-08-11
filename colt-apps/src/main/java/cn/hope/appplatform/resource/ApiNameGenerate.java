package cn.hope.appplatform.resource;


import cn.hope.platform.utils.ChineseInfoUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created by myhtls on 16/6/12.
 */

public class ApiNameGenerate {

    public static String generateApiName(String labelName) {
        StringBuilder sb = convertForStringbuilder(labelName.toCharArray());
        String str = processingFirstCharacter(sb.charAt(0), sb,'_').toString();
        return StringUtils.isEmpty(str)?"自定义应用程序名称":str;
      }

    /**
     * 处理第一个字符串字符串
     *
     * @return
     */
    private static StringBuilder processingFirstCharacter(char first, StringBuilder sb,char underline) {
        /**
         * 如果开头是数字时
         */
        if (Character.isDigit(first)) {
            sb.insert(0, 'x');
        } else if (first == underline) {//如果开头是下划线时删除
            sb.deleteCharAt(0);
            if (Character.isDigit(sb.charAt(0))) {
                sb.insert(0, 'x');
            }
        }
        return sb;

    }


    /**
     * 将字节数组转换StringBuilder
     *
     * @param strArrays
     * @return
     */
    private static StringBuilder convertForStringbuilder(char[] strArrays) {
        StringBuilder sb = new StringBuilder();
        for (char c : strArrays) {
            if (isLetterOrChineseOrDigitOrUnderline(c)) {
                sb.append(c);
            }

        }
        return sb;
    }


    /**
     * 当是字母并且非中文或数字或下划线返回true
     *
     * @param c 参数
     * @return true or false
     */
    private static boolean isLetterOrChineseOrDigitOrUnderline(char c) {
        return Character.isLetter(c) && !ChineseInfoUtils.isChineseChar(String.valueOf(c)) || Character.isDigit(c) || c == '_';
    }

}
