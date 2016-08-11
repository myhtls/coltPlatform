package cn.hope.platform.utils;

/**
 * Created by root on 16-7-4.
 */
public class StringTool {

    public static String getOnlyIntegerNumbers(String string)
    {
        return string.replaceAll("([^0-9])","");
    }


    public static String getLowerFirstLetter(String string) {
        if (string.length() == 1) {
            return string.toLowerCase();
        }
        if (string.length() > 1) {
            return string.substring(0, 1).toLowerCase() + "" + string.substring(1, string.length());
        }
        return "";
    }
}
