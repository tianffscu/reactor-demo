package com.tianff.reactordemo.android.simulation.main.impl;

import com.tianff.reactordemo.android.simulation.main.Activity;
import com.tianff.reactordemo.android.simulation.main.Framework;
import com.tianff.reactordemo.android.simulation.main.OnClickListener;
import com.tianff.reactordemo.android.simulation.main.RuntimeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

import static com.tianff.reactordemo.android.simulation.main.impl.SystemHealthChecker.startSystemHealthCheck;

@Component
public class AndroidFramework implements Framework {
    private static final Logger LOGGER = LoggerFactory.getLogger(AndroidFramework.class);

    private long systemStartTimeMills;
    private boolean onclick;
    private List<Activity> activities;
    private List<RuntimeEvent> runtimeEvents;

    private long lastRefreshTimeMills = 0L;

    private boolean running;

    @Autowired
    public AndroidFramework(List<Activity> activities, List<RuntimeEvent> runtimeEvents) {
        this.systemStartTimeMills = System.currentTimeMillis();
        this.onclick = true;
        this.activities = activities;
        this.runtimeEvents = runtimeEvents;
    }

    @Override
    public void run() {
        refreshUI();
        startSystemHealthCheck(this);
        running = true;

        activities.forEach(Activity::onStart);
        while (running) {
            processRuntimeEvent();

            refreshUI();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                LOGGER.error("System Error: " + e.getMessage(), e);
            }
        }
    }

    private void processRuntimeEvent() {
        //simulate Random click!
        if (onclick &&
                System.currentTimeMillis() - this.systemStartTimeMills
                        > Duration.ofSeconds(5).toMillis()) {
            runtimeEvents.forEach(this::dispatch);
            this.onclick = false;
        }
    }

    private void refreshUI() {
        this.lastRefreshTimeMills = System.currentTimeMillis();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Refresh UI on " + lastRefreshTimeMills);
        }
    }

    @Override
    public long getLastRefreshTimeMills() {
        return this.lastRefreshTimeMills;
    }

    private void dispatch(RuntimeEvent runtimeEvent) {
        // TODO: 2018/9/11 Onclick only dispatched when click event happens
        if (runtimeEvent instanceof OnClickListener) {
            ((OnClickListener) runtimeEvent).onClick();
        }
        // TODO: 2018/9/11  
    }

    @Override
    public void stop() {
        running = false;
        activities.forEach(Activity::onDestroy);
        LOGGER.error("Do NOT make any long time operations in Main Thread!");
    }
}
