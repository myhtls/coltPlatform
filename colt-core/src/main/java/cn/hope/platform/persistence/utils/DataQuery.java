/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.utils;

import cn.hope.platform.persistence.params.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * 组装查询条件
 * @author hutianlong
 */
public class DataQuery {
    private String statement;
    private final List<Parameter> parameters = new ArrayList<>();

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

   
    
    
}
