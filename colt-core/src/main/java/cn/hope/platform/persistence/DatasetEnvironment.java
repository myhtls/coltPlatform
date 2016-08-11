/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence;

import cn.hope.platform.persistence.params.AbstractParameterResolver;
import cn.hope.platform.persistence.params.Parameter;
import java.util.ArrayList;
import java.util.List;
import cn.hope.platform.persistence.provider.ParameterizedDataProvider;

/**
 *
 * @author hutianlong
 */
public class DatasetEnvironment implements ParameterResolver {

    private static DatasetEnvironment instance = new DatasetEnvironment();

    private DatasetEnvironment() {
        addDefaultParameterResolver();
    }

    public static DatasetEnvironment getInstance() {
        return instance;
    }

    private List<ParameterResolver> parameterResolvers = new ArrayList<>();

    private void addDefaultParameterResolver() {
        addParameterResolver(new AbstractParameterResolver() {
            @Override
            public boolean resolveParameter(ParameterizedDataProvider<? extends Object> dataset, Parameter parameter) {
                if (dataset == null) {
                    throw new IllegalArgumentException("Null dataset in provider parameter resolution,you need to write the param resolver which accepts a data provider as a parameter.");
                }
                String strippedName = parameter.getName().substring(1);
                if (dataset.getParameters().containsKey(strippedName)) {
                    parameter.setValue(dataset.getParameters().get(strippedName));
                    return true;
                }
                return false;
            }

            @Override
            public boolean acceptParameter(String name) {
                return name.startsWith(":");
            }
        });
    }

    public void addParameterResolver(ParameterResolver parameterResolver) {
        parameterResolvers.add(parameterResolver);
    }

    @Override
    public boolean resolveParameter(ParameterizedDataProvider<? extends Object> dataset, Parameter parameter) {
        return parameterResolvers.stream().filter((resolver) 
                -> (resolver.acceptParameter(parameter.getName()))).anyMatch((resolver)
                        -> (resolver.resolveParameter(dataset, parameter)));
    }

    @Override
    public boolean acceptParameter(String name) {
        return true;
    }

}
