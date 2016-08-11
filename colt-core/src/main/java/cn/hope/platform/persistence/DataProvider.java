
package cn.hope.platform.persistence;

import java.util.List; 

/**
 * 数据源接口
 *
 * @author hutianlong
 * @param <T>
 */
public interface DataProvider<T> {

    public List<T> fetchResults(Paginator paginator);

    public Integer fetchResultCount();
    
    
}
