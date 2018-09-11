package com.tianff.reactordemo.android.simulation;

import com.tianff.reactordemo.android.simulation.main.Framework;
import com.tianff.reactordemo.android.simulation.main.impl.AndroidFramework;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Boot {

    public static void main(String[] args) {
        ApplicationContext context
                = new AnnotationConfigApplicationContext("com.tianff.reactordemo");

        Framework framework = context.getBean("androidFramework", AndroidFramework.class);
        framework.run();
    }
}
