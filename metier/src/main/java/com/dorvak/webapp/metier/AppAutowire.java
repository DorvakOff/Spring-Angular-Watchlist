package com.dorvak.webapp.metier;

import com.dorvak.webapp.moteur.MoteurWebApplication;
import org.springframework.stereotype.Component;

@Component
public class AppAutowire {

    private static AppAutowire instance;

    private AppAutowire() {
        instance = this;
    }

    public static AppAutowire getInstance() {
        return instance == null ? new AppAutowire() : instance;
    }

    public <R> R getRepository(Class<R> clazz) {
        return getBean(clazz);
    }

    public <S> S getService(Class<S> clazz) {
        return getBean(clazz);
    }

    public <C> C getComponent(Class<C> clazz) {
        return getBean(clazz);
    }

    public <B> B getBean(Class<B> clazz) {
        return MoteurWebApplication.getInstance().getApplicationContext().getBean(clazz);
    }
}
