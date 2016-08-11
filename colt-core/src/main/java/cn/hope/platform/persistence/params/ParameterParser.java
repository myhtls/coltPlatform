/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.params;

/**
 * 参数解析器
 * @author hutianlong
 */
public interface ParameterParser {
    
    String[] extractParameters(String line);
    
}
