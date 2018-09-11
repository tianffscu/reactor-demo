package com.tianff.reactordemo.android.simulation.main.impl;

import com.tianff.reactordemo.android.simulation.main.Framework;

public class SystemHealthChecker {

    private static final long MAX_TIME_INTERVAL_REFRESH_UI = 100;
    private static Framework framework;

    private SystemHealthChecker() {
    }

    public static void startSystemHealthCheck(Framework framework) {
        SystemHealthChecker.framework = framework;

        Thread t = new Thread(new SystemHealthChecker.Task(),
                "Background-Health-Checker");
        t.setDaemon(true);
        t.start();
    }

    private static class Task implements Runnable {
        @Override
        public void run() {
            while (true) {
                long lastRefresh = framework.getLastRefreshTimeMills();
                long current = System.currentTimeMillis();
                if (current - lastRefresh > MAX_TIME_INTERVAL_REFRESH_UI) {
                    try {
                        framework.stop();
                    } catch (Exception e) {
                    }
                    throw new IllegalStateException("Do NOT make any long time operations in Main Thread!");
                }
            }
        }
    }
}
