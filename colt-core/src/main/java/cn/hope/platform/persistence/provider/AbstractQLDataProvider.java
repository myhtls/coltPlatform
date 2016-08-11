/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.provider;

import cn.hope.platform.persistence.Paginator;
import cn.hope.platform.persistence.utils.DataQuery;
import java.util.List;

/**
 *
 * @author hutianlong
 */
public abstract class AbstractQLDataProvider<T> extends AbstractParameterizedDataProvider<T> {

    private String selectStatement;
    private String countStatement;
    private String suffix = null;
    private String prefix = null;
    private String groupBy = null;
    
    @Override
    protected Integer doFetchResultCount() {
        DataQuery query = buildDataQuery(getCountStatement(), false, null);
        return queryForCount(query);
    }

    @Override
    protected List<T> doFetchResults(Paginator paginator) {
        DataQuery query = buildDataQuery(getSelectStatement(), true, paginator);
        Integer count = paginator.includeAllResults() ? null : paginator.getMaxRows() + 1;
        List<T> temp = queryForResults(query, paginator.getFirstResult(), count);

        boolean nextAvailable = (!paginator.includeAllResults() && temp.size() > paginator.getMaxRows());
        paginator.setNextAvailable(nextAvailable);
        if (paginator.includeAllResults() || temp.size() < paginator.getMaxRows()) {
            return temp;
        }

        return temp.subList(0, paginator.getMaxRows());
    }

    protected abstract List<T> queryForResults(DataQuery query, Integer firstResult, Integer count);

    protected abstract Integer queryForCount(DataQuery query);

    protected abstract DataQuery buildDataQuery(String baseStatement, boolean includeOrdering, Paginator paginator);

    @Override
    public String getSelectStatement() {
        return selectStatement;
    }

    @Override
    public void setSelectStatement(String selectStatement) {
        this.selectStatement = selectStatement;
    }

    @Override
    public String getCountStatement() {
        return countStatement;
    }

    @Override
    public void setCountStatement(String countStatement) {
        this.countStatement = countStatement;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    
}
