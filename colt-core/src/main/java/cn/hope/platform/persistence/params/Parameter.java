/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.params;

import java.util.Collection;

/**
 * 代表参数
 * @author hutianlong
 */
public class Parameter {
    
    private String name;
    
    private Object value;

    public Parameter() {
    }

    public Parameter(String name) {
        this.name = name;
    }

    public Parameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }
    
    public boolean isNull(){
        if(value == null){
            return true;
        }else if(value instanceof String && ((String)value).length() == 0){
            return true;
        }else if(value instanceof Collection && ((Collection)value).isEmpty()){
            return true;
        }else if(value instanceof Object[] && ((Object[])value).length == 0){
            return true;
        }
        
        return false;
    }
    
    public boolean isNotNull(){
        return !isNull();
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Parameter{" + "name=" + name + ", value=" + value + '}';
    }
    
    
    
}
