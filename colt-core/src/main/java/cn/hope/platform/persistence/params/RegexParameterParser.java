/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.params;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * @author hutianlong
 */
public class RegexParameterParser implements ParameterParser,java.io.Serializable{
    
    private Pattern pattern;
    
    protected Pattern getPattern(){
        if(pattern == null){
            pattern = Pattern.compile(getRegEx());
        }
        return pattern;
    }
    
    private String getRegEx(){
        return ":[A-Za-z0-9\\_]+#\\{.+}";
    }
    
    @Override
    public String[] extractParameters(String line){
        List<String> parameters  = new ArrayList<>();
        Matcher matcher = getPattern().matcher(line);
        while(matcher.find()){
            parameters.add(matcher.group());
        }
        return parameters.toArray(new String[parameters.size()]);
    }
    
}
