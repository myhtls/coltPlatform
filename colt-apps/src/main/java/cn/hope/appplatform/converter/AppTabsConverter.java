package cn.hope.appplatform.converter;



import cn.hope.appplatform.platform.service.AppTabService;
import cn.hope.platform.core.entity.platform.AppTabs;


import javax.enterprise.inject.spi.CDI;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**111
 * Created by myhtls on 16/6/15.
 */
@RequestScoped
@FacesConverter(value = "appTabsConverter")
public class AppTabsConverter implements Converter {

    private AppTabService appTabService;


    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().length() == 0) {
            return value;
        }

        long key = Long.valueOf(value);
        appTabService =  (AppTabService)CDI.current().select(AppTabService.class).get();
        return  appTabService.find(key);
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        AppTabs bc = null;
        String temp = "";
        if (value instanceof AppTabs) {
            bc = (AppTabs) value;
            temp = String.valueOf(bc.getId());
        }
        return temp;
    }
}