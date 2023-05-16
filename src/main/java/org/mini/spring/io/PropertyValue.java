package org.mini.spring.io;

/**
 * <p>
 *     对象注入的属性名称和属性值
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public class PropertyValue {

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性值
     */
    private Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
