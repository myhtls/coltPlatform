/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.provider;

import cn.hope.platform.persistence.DataProvider;
import cn.hope.platform.persistence.Paginator;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 
 * @author hutianlong
 */
public abstract class AbstractDataProvider<T> implements DataProvider<T>,java.io.Serializable{
    
    private Class<?> entityClass;
    
    public Class<?> getEntityClass(){
        if(entityClass == null){
            ParameterizedType type = (ParameterizedType)entityClass.getGenericSuperclass();
            entityClass = (Class<?>)type.getActualTypeArguments()[0];
        }
        return entityClass;
    }

    @Override
    public List<T> fetchResults(Paginator paginator) {
       doPreFetch();
       return doPostFetchResults(doFetchResults(paginator), paginator);
    }

    @Override
    public Integer fetchResultCount() {
       doPreFetch(); 
       return doPostFetchResultCount(doFetchResultCount());
    }

    
    protected List<T> doPostFetchResults(List<T> results,Paginator paginator){
        return results;
    }
    
    protected Integer doPostFetchResultCount(Integer resultCount){
        return resultCount;
    }
    
    
    
    
    protected void doPreFetch(){}
    
    
    protected abstract List<T> doFetchResults(Paginator paginator);
    
    protected abstract Integer doFetchResultCount();
    
    
    
    
    
}
