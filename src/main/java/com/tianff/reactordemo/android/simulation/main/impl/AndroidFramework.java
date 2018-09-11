package com.tianff.reactordemo.android.simulation.main.impl;

import com.tianff.reactordemo.android.simulation.main.Activity;
import com.tianff.reactordemo.android.simulation.main.Framework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AndroidFramework implements Framework {
    private static final Logger LOGGER = LoggerFactory.getLogger(AndroidFramework.class);

    private List<Activity> activities;

    @Override
    public void run() {
        while (true) {



            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                LOGGER.error("System Error: " + e.getMessage(), e);
            }
        }
    }
}
