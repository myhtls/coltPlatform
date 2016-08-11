package cn.hope.viewplatform.primefaces;

import org.primefaces.context.RequestContext;

/**
 * Created by myhtls on 16/7/2.
 */
public class PrimeFacesUtils {

    /**
     * 执行js方法
     * @param function
     */
    public static void executeScript(String function){
        RequestContext.getCurrentInstance().execute(function);
    }

    /**
     * 回调参数
     * @param name
     * @param value
     */
    public static void addCallbackParam(String name,String value){
        RequestContext.getCurrentInstance().addCallbackParam(name,value);
    }

}
