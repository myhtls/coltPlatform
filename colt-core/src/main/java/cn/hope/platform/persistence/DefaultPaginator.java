/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;

/**
 * 默认实现分页
 * @author hutianlong
 */
public class DefaultPaginator implements Paginator,Serializable{
    
    private int firstResult = 0;
    private Integer maxRows;
    private boolean orderAscending = true;
    private String orderKey;
    private boolean nextAvailable;

    public DefaultPaginator() {
       
    }

    public DefaultPaginator(Integer maxRows) {
        this.maxRows = maxRows;
    }
    
    

    @Override
    public int getFirstResult() {
        return firstResult;
    }

    @Override
    public void setFirstResult(int firstResult) {
       this.firstResult = firstResult;
    }

    @Override
    public Integer getMaxRows() {
        return this.maxRows;
    }

    @Override
    public void setMaxRows(Integer maxRows) {
        this.maxRows = maxRows;
    }

    @Override
    public String getOrderKey() {
        return this.orderKey;
    }

    @Override
    public void setOrderKey(String orderkey) {
        this.orderKey = orderkey;
    }

    @Override
    public boolean isOrderAscending() {
        return this.orderAscending;
    }

    @Override
    public void setOrderAscending(boolean orderAscending) {
        this.orderAscending =orderAscending;
    }

    @Override
    public void changeOrderKey(String orderKey) {
        /**
         * 当前排定如果为空时，将当前排序字段赋于this.orderKey
         */
       if(StringUtils.isEmpty(this.orderKey)){
           this.orderKey = orderKey;
           return;
       }
       
       /**
        * 当排序一致时,orderAscending设为false
        */
       if(this.orderKey.equals(orderKey)){
           this.orderAscending = !this.orderAscending;
           return;
       }
       this.orderKey = orderKey;
       this.orderAscending = true;
    }

    /**
     * 插入全部结果
     * @return 
     */
    @Override
    public boolean includeAllResults() {
        return getMaxRows() == null;
    }

    /**
     * 获得下一个可用的数据
     * @return 
     */
    @Override
    public boolean isNextAvailable() {
        return nextAvailable;
    }

    @Override
    public boolean isPreviousAvailable() {
        return firstResult > 0;
    }

    @Override
    public void setNextAvailable(boolean nextAvailable) {
       this.nextAvailable = nextAvailable;
    }

    @Override
    public void next() {
        if(isNextAvailable()){
           firstResult = firstResult + getMaxRows(); 
        }
    }

    @Override
    public void previous() {
        if(this.isPreviousAvailable()){
            firstResult -=getMaxRows();
            if(firstResult < 0){
                firstResult =0;
            }
        }
    }
    
    
    
}
