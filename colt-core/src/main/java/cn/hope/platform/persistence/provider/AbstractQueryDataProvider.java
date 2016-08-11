/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.provider;

import cn.hope.platform.persistence.Paginator;
import cn.hope.platform.persistence.utils.DataQuery;
import cn.hope.platform.persistence.utils.DataQueryBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hutianlong
 * @param <T>
 */
public abstract class AbstractQueryDataProvider<T>
        extends AbstractQLDataProvider<T> implements QueryDataProvider<T>, java.io.Serializable {

    private int paramId;
    private Map<String, String> orderKeyMap = new HashMap<>();
    private List<String> restrictions = new ArrayList<>();
    private boolean orderedParams = false;

    @Override
    public void init(Class<? extends Object> clazz, String prefix) {
        setCountStatement(String.
                format("select count(%s) from %s %s", prefix, clazz.getSimpleName(), prefix));

        setSelectStatement(String.format("select %s from %s %s", prefix, clazz.getSimpleName(), prefix));
    }

    protected String translateOrderKey(String ordeyKeyValue) {
        return getOrderKeyMap().get(ordeyKeyValue);
    }

    protected String getNextParamName() {
        return "_param_" + String.valueOf(this.paramId++);
    }

    @Override
    public void addRestriction(String restriction) {
        getRestrictions().add(restriction);
    }

    @Override
    public boolean addRestriction(String syntax, Object value) {
        return addRestriction(syntax, value, value);
    }

    @Override
    public boolean addRestrictionStr(String syntax, String value) {
        return addRestrictionStr(syntax, value, value);
    }

    @Override
    public boolean addRestrictionStr(String syntax, String paramValue, String isNullValue) {
        if (isValidTestValue(isNullValue, true)) {
            return addRestriction(syntax, paramValue, isNullValue);
        }
        return false;
    }

    @Override
    public boolean addRestriction(String syntax, Object paramValue, Object testValue) {
        if (isValidTestValue(testValue, false)) {
            if (paramValue instanceof Collection<?>) {
                addCollectionRestriction(syntax, (Collection<?>) paramValue);
            } else {
                addRestrictionForDefault(syntax, paramValue);
            }
            return true;
        }
        return false;
    }

    private void addCollectionRestriction(String syntax, Collection<?> paramValues) {
        StringBuilder paramList = new StringBuilder();
        paramValues.stream().filter((o) -> (isValidTestValue(o, false))).forEach((o) -> {
            // prefix with comma if its not the first
            if (paramList.length() != 0) {
                paramList.append(",");
            }
            String name = getNextParamName();
            paramList.append(":").append(name);
            getParameters().put(name, o);
        });
        syntax = syntax.replace(":param", paramList.toString());
        addRestriction(syntax);
    }

    protected boolean isValidTestValue(Object obj, boolean testStrings) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String && testStrings) {
            String s = (String) obj;
            return s.length() != 0;
        }
        if (obj instanceof Collection<?>) {
            if (((Collection<?>) obj).isEmpty()) {
                return false;
            }
            // check it contains a valid test value
            for (Object o : (Collection<?>) obj) {
                // avoid recursion by checking for self
                if (o != obj) {
                    if (isValidTestValue(o, testStrings)) {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    private void addRestrictionForDefault(String syntax, Object paramValue) {
        String name = getNextParamName();
        syntax = syntax.replace(":param", ":" + name);
        addRestriction(syntax);
        getParameters().put(name, paramValue);
    }
    
    protected DataQueryBuilder createDataQueryBuilder() {
        return new DataQueryBuilder();
    }

    @Override
    protected DataQuery buildDataQuery(String baseStatement, boolean includeOrdering, Paginator paginator) {
        DataQueryBuilder builder = new DataQueryBuilder();
        builder.setProvider(this);
        builder.setBaseStatement(baseStatement);
        builder.setGroupBy(getGroupBy());
        builder.setPrefix(getPrefix());
        builder.setOrderedParams(orderedParams);
        if (includeOrdering) {
            builder.setOrderBy(translateOrderKey(paginator.getOrderKey()));
            builder.setOrderAscending(paginator.isOrderAscending());
        }
        builder.setSuffix(getSuffix());
        return builder.build();
    }

    @Override
    public Map<String, String> getOrderKeyMap() {
        return orderKeyMap;
    }

    @Override
    public void setOrderKeyMap(Map<String, String> orderKeyMap) {
        this.orderKeyMap = orderKeyMap;
    }

    @Override
    public List<String> getRestrictions() {
        return restrictions;
    }

    @Override
    public void setRestrictions(List<String> restrictions) {
        this.restrictions = restrictions;
    }

    public boolean isOrderedParams() {
        return orderedParams;
    }

    public void setOrderedParams(boolean orderedParams) {
        this.orderedParams = orderedParams;
    }

}
