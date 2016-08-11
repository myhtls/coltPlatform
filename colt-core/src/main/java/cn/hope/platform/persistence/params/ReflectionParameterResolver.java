/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.params;

import cn.hope.platform.persistence.ParameterResolver;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.PropertyUtils;
import cn.hope.platform.persistence.provider.ParameterizedDataProvider;

/**
 *
 * @author hutianlong
 */
public class ReflectionParameterResolver implements ParameterResolver, java.io.Serializable {

    private Object object;

    public ReflectionParameterResolver(Object object) {
        this.object = (object == null ? this : object);
    }

    public ReflectionParameterResolver() {
        this(null);
    }

    public Object getObject() {
        return object;
    }

    @Override
    public boolean resolveParameter(ParameterizedDataProvider<? extends Object> dataset, Parameter parameter) {
        String strippedName = parameter.getName().substring(1);
        String[] properties = strippedName.split("\\.");
        int index = 0;
        Object base = object;
        while (index < properties.length && base != null) {
            base = resolveProperty(base, properties[index]);
            index++;

        }

        if (base != null) {
            parameter.setValue(base);
            return true;
        }

        return false;

    }
    
    public Object resolveProperty(Object base,String property){
        try{
            return PropertyUtils.getProperty(base, property);
        }catch(IllegalAccessException|InvocationTargetException|NoSuchMethodException e){
            
        }
        return null;
    }
    
    @Override
    public boolean acceptParameter(String parameter){
        return parameter.startsWith(":");
    }

}
