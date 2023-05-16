package org.mini.spring.io;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 属性集合
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    /**
     * 新增属性集合
     * @param propertyValue
     */
    public PropertyValues addPropertyValue(PropertyValue propertyValue) {
        propertyValueList.add(propertyValue);
        return this;
    }

    /**
     * 获取全量的属性，返回数组类型
     * @return
     */
    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    /**
     * 根据属性名称获取属性
     * @param propertyName
     * @return
     */
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyName.equals(propertyValue.getName())) {
                return propertyValue;
            }
        }
        return null;
    }

}
