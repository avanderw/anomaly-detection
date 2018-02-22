package net.avdw.economy.api;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.pmw.tinylog.Logger;

public abstract class ASupplier<T> extends AThread
{

    private final List<BlockingQueue<Container>> outputs;

    public ASupplier(BlockingQueue<Container>... outputs)
    {
        this.outputs = Arrays.asList(outputs);
    }

    @Override
    public void run()
    {
        Thread.currentThread().setName(this.getClass().getSimpleName());
        Logger.debug(String.format("started"));

        while (thread == Thread.currentThread())
        {
            try
            {
                Container container = new Container();
                container.pack(produce());
                container.stamp(this.getClass().getSimpleName());

                for (BlockingQueue<Container> output : outputs)
                {
                    output.put(container);
                }
            } catch (InterruptedException ex)
            {
                Logger.warn(ex);
            }
        }
        Logger.info(String.format("stopped"));
    }

    public abstract T produce();
}
