package com.tianff.reactordemo.android.simulation.main;

public interface Framework {

    void run();

    void stop();

    long getLastRefreshTimeMills();
}
