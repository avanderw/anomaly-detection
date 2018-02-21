package net.avdw.anomoly.detection;

import org.pmw.tinylog.Logger;

public abstract class AThread implements Runnable
{

    protected volatile Thread thread;

    void start()
    {
        Logger.info(String.format("starting thread"));
        thread = new Thread(this);
        thread.start();
    }

    void stop()
    {
        Logger.info(String.format("stopping thread"));
        thread = null;
    }

    abstract public void run();
}
