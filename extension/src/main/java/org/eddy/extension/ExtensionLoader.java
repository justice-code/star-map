package org.eddy.extension;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ExtensionLoader implements ApplicationContextAware{

    private ApplicationContext applicationContext;

    @Autowired
    private ExtensionConfig extensionConfig;

    public <T> T loadExtension(Class<T> type) {
        check(type);
        String key = name(type);
        return applicationContext.getBean(key, type);
    }

    private String name(Class<?> type) {
        if (type.isAnnotationPresent(Activation.class)) {
            return type.getAnnotation(Activation.class).value();
        } else {
            return extensionConfig.name(type.getAnnotation(Extension.class).value());
        }
    }

    private void check(Class type) {
        if (! type.isInterface()) {
            throw new RuntimeException("must be interface");
        }
        if (! type.isAnnotationPresent(Extension.class)) {
            throw new RuntimeException("must has Extension Annotation");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
