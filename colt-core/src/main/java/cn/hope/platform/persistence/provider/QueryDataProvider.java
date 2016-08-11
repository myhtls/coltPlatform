/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.provider;

import java.util.List;
import java.util.Map;

/**
 * 添加查询条件接口
 * @author hutianlong
 * @param <T>
 */
public interface QueryDataProvider<T> extends ParameterizedDataProvider<T> {
    
    public Map<String,String> getOrderKeyMap();
    
    public void setOrderKeyMap(Map<String,String> orderKeyMap);
    
    public List<String> getRestrictions();
    
    public void setRestrictions(List<String> restrictions);
    
    public void addRestriction(String restriction);
    
    public boolean addRestriction(String restriction,Object value);
    
   
    
    public boolean addRestrictionStr(String restriction,String paramValue);
    
    public boolean addRestrictionStr(String restriction,String paramValue,String isNullValue);
    
     public boolean addRestriction(String restriction,Object paramValue,Object isNullValue);
    
}
