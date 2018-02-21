package net.avdw.anomoly.detection;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.pmw.tinylog.Logger;

public abstract class AFactory<I, O> extends AThread
{

    private final BlockingQueue<I> input;
    private final List<BlockingQueue<O>> outputs;

    public AFactory(BlockingQueue<I> input, BlockingQueue<O>... outputs)
    {
        this.input = input;
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
                consume(input.take());
                
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

    abstract void consume(I item);

    abstract O produce();
}
