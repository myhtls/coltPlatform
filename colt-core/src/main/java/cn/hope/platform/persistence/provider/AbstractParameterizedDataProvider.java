/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.provider;

import cn.hope.platform.persistence.DatasetEnvironment;
import cn.hope.platform.persistence.ParameterResolver;
import cn.hope.platform.persistence.params.Parameter;
import cn.hope.platform.persistence.params.ParameterParser;
import cn.hope.platform.persistence.params.RegexParameterParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author hutianlong
 */
public abstract class AbstractParameterizedDataProvider<T> extends AbstractDataProvider<T>
        implements ParameterizedDataProvider<T> {

    private ParameterParser parameterParser = new RegexParameterParser();
    private Map<String, Object> parameters = new HashMap<>();
    private List<ParameterResolver> parameterResolvers = new ArrayList<>();

    @Override
    public void addParameter(String name, Object value) {
        parameters.put(name, value);
    }

    @Override
    public void addParameterResolver(ParameterResolver parameterResolver) {
        parameterResolvers.add(parameterResolver);
    }

    @Override
    public Object resolveParameter(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        Parameter parameter = new Parameter(name);
        if (DatasetEnvironment.getInstance().resolveParameter(this, parameter)) {
            return parameter;
        }

        for (ParameterResolver resolver : parameterResolvers) {
            if (resolver.acceptParameter(name)) {
                if (resolver.resolveParameter(this, parameter)) {
                    return parameter.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public List<ParameterResolver> getParameterResolvers() {
        return parameterResolvers;
    }

    @Override
    public void setParameterResolvers(List<ParameterResolver> parameterResolvers) {
        this.parameterResolvers = parameterResolvers;
    }

    public ParameterParser getParameterParser() {
        return parameterParser;
    }

    public void setParameterParser(ParameterParser parameterParser) {
        this.parameterParser = parameterParser;
    }

}
