/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.provider;

import cn.hope.platform.persistence.DataProvider;
import cn.hope.platform.persistence.ParameterResolver;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hutianlong
 */
public interface ParameterizedDataProvider<T> extends DataProvider<T> {
    
    public abstract Map<String,Object> getParameters();
    
    public abstract void setParameters(Map<String,Object> parameters);
    
    public abstract void addParameter(String name,Object value);
    
    public void addParameterResolver(ParameterResolver resoler);
    
    public Object resolveParameter(String name);
    
    public List<ParameterResolver> getParameterResolvers();
    
    public void  setParameterResolvers(List<ParameterResolver> parameterResolvers);
    
    public String getCountStatement();
    
    public void setCountStatement(String countStatement);
    
    public String getSelectStatement();
    
    public void setSelectStatement(String selectStatement);
    
    public void init(Class<? extends Object> clazz,String prefix);
    
}
