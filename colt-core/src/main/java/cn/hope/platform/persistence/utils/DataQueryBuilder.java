/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.utils;

import cn.hope.platform.persistence.params.Parameter;
import cn.hope.platform.persistence.params.ParameterParser;
import cn.hope.platform.persistence.params.ParameterValues;
import cn.hope.platform.persistence.params.RegexParameterParser;
import cn.hope.platform.persistence.provider.ParameterizedDataProvider;
import cn.hope.platform.persistence.provider.QueryDataProvider;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hutianlong
 */
public class DataQueryBuilder {
     private int parameterId = 0;
    // private ParameterizedDataProvider<? extends Object> provider;
    private static Pattern commaSplitter = Pattern.compile(",");

    private StringBuilder statement = new StringBuilder();
    private StringBuilder restrictions = new StringBuilder();
    private Pattern logicalOpPattern = Pattern.compile("\\A[ (]*\\b(and|or)\\b[ (]*.*", Pattern.CASE_INSENSITIVE);
    private ParameterParser parameterParser = new RegexParameterParser();

    private boolean orderedParams = false;
    private boolean allowNullParameters = false;
    private ParameterizedDataProvider<? extends Object> provider;
    private String orderBy;
    private boolean orderAscending = true;
    private String baseStatement;
    private String suffix;
    private String prefix = null;
    private String groupBy;
    
     private static Logger log = LoggerFactory.getLogger(DataQueryBuilder.class);
    
    private void clear() {
        statement = new StringBuilder();
        restrictions = new StringBuilder();
    }

    public DataQuery build() {
        if (provider == null) {
            throw new NullPointerException("Cannot build Data Query without setting the provider");
        }

        if (baseStatement == null || baseStatement.length() == 0) {
            throw new IllegalArgumentException("Cannot build Data Query without a base statement");
        }

        clear();
        DataQuery query = new DataQuery();

        // 添加基础语句时，可以有空参数
        addLineToQuery(parameterizeLine(baseStatement, query, true));

        // is this a query? If so, lets add the restrictions for this query
        if (provider instanceof QueryDataProvider<?>) {
            QueryDataProvider<? extends Object> queryProvider = (QueryDataProvider<? extends Object>) provider;
            for (String restriction : queryProvider.getRestrictions()) {
                // 添加约束时，不得有空参数；如果此约束含空参数，则将此约束废弃
                addRestrictionToQuery(parameterizeLine(restriction, query, allowNullParameters));
            }
        }

        query.setStatement(buildFinalStatement());
        log.debug("Debug: DataQueryBuilder.build(), query.getStatement() = {}", query.getStatement());
        log.debug("Debug: DataQueryBuilder.build(), query.getParameters().size() = {}", query.getParameters().size());

        return query;
    }
    
    protected final String parameterizeLine(String line, DataQuery query, boolean includeNullParameters) {
        log.debug("processing restriction '{}'", line);
        // if this is a null or empty string, just return
        if (line == null || line.length() == 0) {
            return null;
        }
        // get the parameter expressions in this line
        String[] expressions = extractExpressions(line);

        // if there are no expressions, just add the restriction and leave
        if (expressions.length == 0) {
            log.debug("Found no restrictions, exiting");
            return line;
        }

        // build the parameters for this restriction line
        ParameterValues params = buildParameterList(expressions/*, query*/);

        // are we missing some parameter values? If so we are done if we are not
        // including missing parameters
        if (params.hasNullParameters() && !includeNullParameters) {
            log.debug("Cannot resolve parameters, abandon the restriction");
            return null;
        }

        // Process each parameter
        for (Parameter param : params) {
            int paramNum = 1;
            int paramType = 0; // 原始类型
            if (param.getValue() instanceof Collection) {
                paramType = 1; // 集合类型
                paramNum = ((Collection) param.getValue()).size();
            } else if (param.getValue() instanceof Object[]) {
                paramType = 2; // 数组类型
                paramNum = ((Object[]) param.getValue()).length;
            }
            // calculate new name for parameter (param_(int) or '?')
            String newName = getNewParameterName(paramNum);

            // replace the param in the restriction with the new name with the
            // necessary prefix ":" or blank if we are using ordered parameters
            String prefixedNewName = getNewParameterNamePrefix() + newName;
            // 把param.name转换为正则表达式oldValue
            String oldValue = param.getName();
            oldValue = oldValue.replace("{", "\\{");
            oldValue = oldValue.replace("}", "\\}");

            log.debug("Replacing {} with {} in expression", oldValue, prefixedNewName);
            line = line.replaceFirst(oldValue, prefixedNewName);

            if (!orderedParams || (orderedParams && paramType == 0)) {
                // set the new name of the parameter in the parameter info
                param.setName(newName);
                log.debug("Add the parameter: {}", param);
                // add the parameter to the final list
                query.getParameters().add(param);
            } else {
                // 集合类型
                if (paramType == 1) {
                    for (Object obj : (Collection) param.getValue()) {
                        // set the new name and new value of the parameter in the parameter info
                        Parameter newParm = new Parameter("?", obj);
                        log.debug("Add the parameter: {}", newParm);
                        // add the parameter to the final list
                        query.getParameters().add(newParm);
                    }
                }
                // 数组类型
                if (paramType == 2) {
                    for (Object obj : (Object[]) param.getValue()) {
                        // set the new name and new value of the parameter in the parameter info
                        Parameter newParm = new Parameter("?", obj);
                        log.debug("Add the parameter: {}", newParm);
                        // add the parameter to the final list
                        query.getParameters().add(newParm);
                    }
                }
            }
        }
        // finally, add the restriction to the list
        log.debug("Adding restriction {}", line);
        return line;
    }
    
    
    protected void addLineToQuery(String line) {
        if (line != null) {
            statement.append(line);
        }
    }

    /**
     * Adds a restriction to the final query text. Checks to see if the line
     * starts with a logical operator (and/or) and if not, prefixes with ' AND
     * '.
     *
     * @param line Line to append to the query.
     */
    protected void addRestrictionToQuery(String line) {
        if (line != null) {
            // only worry about logical operators if its not empty.
            if (restrictions.length() != 0) {
                if (!startsWithLogicalOperator(line)) {
                    restrictions.append(" AND");
                }
                // add a space to separate this next statement
                restrictions.append(" ");
            }
            restrictions.append(line);
        }
    }

    /**
     * Returns a new parameter name based on whether this query is using ordered
     * parameters or named parameters. For ordered parameters, we always just
     * return a "?" but with named parameters, we return a new unique name.
     *
     * @param paramNum
     * @return A new parameter name
     */
    protected String getNewParameterName(int paramNum) {
        // return ? for jdbc type parameters
        if (orderedParams) {
            // return "?";
            // 返回paramNum个问号
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paramNum; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append("?");
            }
            return sb.toString();
        } else {
            return "param_" + parameterId++;
        }
    }
    
     protected String getNewParameterNamePrefix() {
        return orderedParams ? "" : ":";
    }

    /**
     * Takes a list of string parameter expressions and resolves them, storing
     * the results in the {@link ParameterValues} list.
     *
     * @param expressions List of expressions to resolve
     * @return List of resolved parameter values.
     */
    private ParameterValues buildParameterList(String[] expressions/*, DataQuery query*/) {
        ParameterValues results = new ParameterValues();
        if (provider != null) {
            for (String expression : expressions) {
                Object value = provider.resolveParameter(expression);
                results.add(expression, value);
            }
        }
        return results;
    }

    /**
     * Extracts the parameter expressions contained in a query restriction into
     * a string array. The parameters are extracted using a parameterParser
     * which can be changed at run time.
     *
     * @param restriction the query restriction we want to extract expressions
     * from
     * @return a string array holding the expressions.
     */
    protected String[] extractExpressions(String restriction) {
        if (parameterParser == null) {
            throw new IllegalStateException("Parameter parser is null in query builder");
        }
        return parameterParser.extractParameters(restriction);
    }

    public ParameterParser getParameterParser() {
        return parameterParser;
    }

    public void setParameterParser(ParameterParser parameterParser) {
        this.parameterParser = parameterParser;
    }

    /**
     * @param s Restriction line to check
     * @return True if the line starts with AND or OR
     */
    public boolean startsWithLogicalOperator(String s) {
        Matcher m = logicalOpPattern.matcher(s);
        return m.matches();
    }

    /**
     * Takes the statement and restrictions and builds a final statement which
     * is returned to the caller.
     *
     * @return The complete Query statement from the statement and restrictions.
     */
    protected String buildFinalStatement() {
        if (restrictions.length() > 0) {
            statement.append(" WHERE ");
            statement.append(restrictions);
        }

        statement.append(buildGroupBy());
        statement.append(buildPrefix());
        statement.append(buildOrderBy());
        statement.append(buildSuffix());
        return statement.toString();
    }

    private String buildOrderBy() {
        // parse out fields and add order
        if (orderBy == null || orderBy.length() == 0) {
            return "";
        }
        String[] fields = commaSplitter.split(orderBy);
        String order = "";
        // concatenate the fields with the direction, put commas in between
        for (String field : fields) {
            if (order.length() != 0) {
                order = order + ", ";
            }
            order = order + field + (isOrderAscending() ? " ASC" : " DESC");
        }
        return " ORDER BY " + order;
    }

    private String buildGroupBy() {
        if (groupBy == null) {
            return "";
        }
        return " " + groupBy;
    }

    private String buildPrefix() {
        if (prefix == null) {
            return "";
        }
        return " " + prefix;
    }

    private String buildSuffix() {
        // parse out fields and add order
        if (suffix == null) {
            return "";
        }
        return " " + suffix;
    }
    
     protected String buildFinalStatement(String orderBy) {
        if (orderBy != null && orderBy.length() != 0) {
            return buildFinalStatement() + " ORDER BY " + orderBy;
        }
        return buildFinalStatement();
    }

    public boolean getAllowNullParameters() {
        return allowNullParameters;
    }

    public void setAllowNullParameters(boolean allowNullParameters) {
        this.allowNullParameters = allowNullParameters;
    }

    public boolean isOrderAscending() {
        return orderAscending;
    }

    public boolean isOrderedParams() {
        return orderedParams;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setBaseStatement(String baseStatement) {
        this.baseStatement = baseStatement;
    }

    public ParameterizedDataProvider<? extends Object> getProvider() {
        return provider;
    }

    public void setProvider(ParameterizedDataProvider<? extends Object> provider) {
        this.provider = provider;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getBaseStatement() {
        return baseStatement;
    }

    public void setOrderedParams(boolean orderedParams) {
        this.orderedParams = orderedParams;
    }

    public void setOrderAscending(boolean orderAscending) {
        this.orderAscending = orderAscending;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
