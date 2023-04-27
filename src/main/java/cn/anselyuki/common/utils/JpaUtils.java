package cn.anselyuki.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author AnselYuki
 */
@SuppressWarnings("unused")
public class JpaUtils {
    /**
     * 所有为空值的属性都不copy
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyNotNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullField(source));
    }

    /**
     * 获取属性中为空的字段
     *
     * @param target 目标对象
     * @return String[]
     */
    private static String[] getNullField(Object target) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(target);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> notNullFieldSet = new HashSet<>();
        for (PropertyDescriptor p : propertyDescriptors) {
            String name = p.getName();
            Object value = beanWrapper.getPropertyValue(name);
            if (Objects.isNull(value)) {
                notNullFieldSet.add(name);
            }
        }
        String[] notNullField = new String[notNullFieldSet.size()];
        return notNullFieldSet.toArray(notNullField);
    }
}
