package cn.hope.viewplatform.primefaces;

import cn.hope.platform.persistence.dao.BaseDaoImpl;
import cn.hope.platform.persistence.utils.EntityUtils;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * Created by myhtls on 16/7/2.
 */
public class LazyDataModelImpl<E> extends LazyDataModel {

    private Object objectCriteria;//查询条件类
    private BaseDaoImpl baseDaoImpl;
    private String orderBy;

    public LazyDataModelImpl() {
    }

    public LazyDataModelImpl(BaseDaoImpl baseDaoImpl) {
        this.baseDaoImpl = baseDaoImpl;
    }

    /**
     * 返回当前选择行主键
     *
     * @param object
     * @return
     */
    @Override
    public Object getRowKey(Object object) {
        if (object != null) {
            return EntityUtils.getId(object);
        }
        return null;
    }

    /**
     * 通过主键获得唯一对象
     *
     * @param rowKey
     * @return
     */
    @Override
    public Object getRowData(String rowKey) {
        return super.getRowData(rowKey); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 懒加载
     *
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @param filters
     * @return
     */
    @Override
    public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
        return super.load(first, pageSize, sortField, sortOrder, filters); //To change body of generated methods, choose Tools | Templates.
    }

}
