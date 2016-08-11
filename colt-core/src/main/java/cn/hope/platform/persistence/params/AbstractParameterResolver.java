/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.params;

import cn.hope.platform.persistence.ParameterResolver;

/**
 *
 * @author hutianlong
 */
public abstract class AbstractParameterResolver implements ParameterResolver, java.io.Serializable {

    public final boolean isElExpression(String name) {
        if (name != null) {
            return name.startsWith("#{") && name.endsWith("}");
        }
        return false;
    }

    public final boolean isCommaExpression(String name) {
        if (name != null) {
            return name.startsWith(":");
        }
        return false;
    }
}
