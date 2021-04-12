package thinking.in.spring.bean.definition;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import thinking.in.spring.ioc.overview.dependency.domain.User;

/**
 * BeanDefinition创建
 *
 * @author liqiang
 * @date 2021-04-12 下午1:02
 **/
public class BeanDefinitionCreateDemo {
    public static void main(String[] args) {
        // 1. 通过BeanDefinitionBuilder创建
        final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        builder.addPropertyValue("id", 1234);
        builder.addPropertyValue("name", "Java填坑笔记");
        final BeanDefinition beanDefinition = builder.getBeanDefinition();

        // 2. 通过AbstractBeanDefinition及其派生类创建
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(User.class);
        final MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("id", 456);
        propertyValues.add("name", "CRUDER");
        genericBeanDefinition.setPropertyValues(propertyValues);

    }
}
