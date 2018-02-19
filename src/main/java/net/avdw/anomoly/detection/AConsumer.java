package net.avdw.anomoly.detection;

import java.util.concurrent.BlockingQueue;
import org.pmw.tinylog.Logger;

public abstract class AConsumer<T> implements Runnable {
    private volatile Thread thread;
    private final BlockingQueue<T> input;

    public AConsumer(BlockingQueue<T> input) {
        this.input = input;
    }

   @Override
    public void run() {
        Thread.currentThread().setName(this.getClass().getSimpleName());
        Logger.info(String.format("started"));

        while (thread == Thread.currentThread()) {
            try {
                 consume(input.take());
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

    abstract void consume(T item);
}
