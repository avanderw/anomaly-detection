package net.avdw.anomoly.detection;

import java.util.concurrent.BlockingQueue;
import org.pmw.tinylog.Logger;

public abstract class AProducer<T> implements Runnable
{
    private volatile Thread thread;
    private final BlockingQueue<T> output;

    public AProducer(BlockingQueue<T> output) {
        this.output = output;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(this.getClass().getSimpleName());
        Logger.info(String.format("started"));

        while (thread == Thread.currentThread()) {
            try {
                output.put(produce());
            } catch (InterruptedException ex) {
                Logger.warn(ex);
            }
        }
        Logger.info(String.format("stopped"));
    }

    void start() {
        Logger.info(String.format("starting thread"));
        thread = new Thread(this);
        thread.start();
    }

    void stop() {
        Logger.info(String.format("stopping thread"));
        thread = null;
    }

    abstract T produce();
}
