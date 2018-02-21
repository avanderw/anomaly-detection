package net.avdw.anomoly.detection;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.pmw.tinylog.Logger;

public abstract class ASupplier<O> extends AThread
{

    private final List<BlockingQueue<O>> outputs;

    public ASupplier(BlockingQueue<O>... outputs)
    {
        this.outputs = Arrays.asList(outputs);
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
                O item = produce();
                for (BlockingQueue<O> output : outputs)
                {
                    output.put(item);
                }
            } catch (InterruptedException ex)
            {
                Logger.warn(ex);
            }
        }
        Logger.info(String.format("stopped"));
    }

    abstract O produce();
}
