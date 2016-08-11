/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.params;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * 参数的可遍例列表
 * @author hutianlong
 */
public class ParameterValues implements Iterable<Parameter>, Serializable {

    private List<Parameter> values = new ArrayList<>();

    public void add(Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("不能传递一个空参数到参数列表");
        }
        values.add(parameter);
    }

    public Parameter add(String name, Object value) {
        Parameter parameter = new Parameter(name, value);
        add(parameter);
        return parameter;
    }

    public void clear() {
        values.clear();
    }

    public int size() {
        return values.size();
    }

    @Override
    public Iterator<Parameter> iterator() {
        return values.iterator();
    }

    public boolean hasNullParameters() {
        return values.stream().anyMatch((parameter) -> (parameter.isNull()));
    }

    public Parameter getParameter(int index) {
        return values.get(index);
    }

    public Parameter getParameter(String name) {
        if (name != null) {
            for (Parameter parameter : values) {
                if (name.equals(parameter.getName())) {
                    return parameter;
                }
            }
        }

        return null;
    }

}
