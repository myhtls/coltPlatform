/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence;

/**
 *
 * @author hutianlong
 */
public interface Paginator {
    
    public int getFirstResult();
    
    public void setFirstResult(int firstResult);
    
    public Integer getMaxRows();
    
    public void setMaxRows(Integer maxRows);
    
    public String getOrderKey();
    
    public void setOrderKey(String orderkey);
    
    public boolean isOrderAscending();
    
    public void setOrderAscending(boolean orderAscending);
    
    public void changeOrderKey(String orderKey);
    
    public boolean includeAllResults();
    
    public boolean isNextAvailable();
    
    public boolean isPreviousAvailable();
    
    public void setNextAvailable(boolean nextAvailable);
    
    public void next();
    
    public void previous();
    
    
}
