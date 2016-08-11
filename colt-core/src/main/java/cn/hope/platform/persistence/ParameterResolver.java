/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence;

import cn.hope.platform.persistence.params.Parameter;
import cn.hope.platform.persistence.provider.ParameterizedDataProvider;

/**
 * 参数解析器
 * @author hutianlong
 */
public interface ParameterResolver {
    
    public boolean resolveParameter(ParameterizedDataProvider<? extends Object> dataset,Parameter parameter);
    public boolean acceptParameter(String name);
    
    
}
