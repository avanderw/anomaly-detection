package net.avdw.anomoly.detection;

import java.util.concurrent.BlockingQueue;
import org.pmw.tinylog.Logger;

public abstract class AConsumer<T> extends AThread
{

    private final BlockingQueue<T> input;

    public AConsumer(BlockingQueue<T> input)
    {
        this.input = input;
    }

    @Override
    public void run()
    {
        Thread.currentThread().setName(this.getClass().getSimpleName());
        Logger.info(String.format("started"));

        while (thread == Thread.currentThread())
        {
            try
            {
                consume(input.take());
            } catch (InterruptedException ex)
            {
                Logger.warn(ex);
            }
        }
        Logger.info(String.format("stopped"));
    }

    abstract void consume(T item);
}
