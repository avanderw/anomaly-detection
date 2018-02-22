package net.avdw.economy.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.pmw.tinylog.Logger;

public abstract class AFactory<I, O> extends AThread
{

    private final List<BlockingQueue<Container<I>>> inputs;
    private final List<BlockingQueue<Container<O>>> outputs;

    public AFactory(BlockingQueue<Container<I>> input, BlockingQueue<Container<O>>... outputs)
    {
        this.inputs = new ArrayList();
        this.inputs.add(input);
        this.outputs = Arrays.asList(outputs);
    }

    public AFactory(BlockingQueue<Container<I>> input, List<BlockingQueue<Container<O>>> outputs)
    {
        this.inputs = new ArrayList();
        this.inputs.add(input);
        this.outputs = outputs;
    }

    public AFactory(List<BlockingQueue<Container<I>>> inputs, BlockingQueue<Container<O>> output)
    {
        this.inputs = inputs;
        this.outputs = new ArrayList();
        this.outputs.add(output);
    }

    public AFactory(List<BlockingQueue<Container<I>>> inputs, List<BlockingQueue<Container<O>>> outputs)
    {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    @Override
    public void run()
    {
        Thread.currentThread().setName(this.getClass().getSimpleName());
        Logger.debug(String.format("started"));

        while (thread == Thread.currentThread())
        {
            inputs.forEach((input) ->
            {
                try
                {
                    Container<I> inputContainer = input.take();
                    inputContainer.stamp(this.getClass().getSimpleName());
                    consume(inputContainer.unpack());

                    O good = produce();

                    for (BlockingQueue<Container<O>> output : outputs)
                    {
                        Container<O> container = new Container(inputContainer);
                        container.pack(good);
                        container.stamp(this.getClass().getSimpleName());

                        output.put(container);
                    }
                } catch (InterruptedException ex)
                {
                    Logger.warn(ex);
                }
            });
        }

        Logger.info(String.format("stopped"));
    }

    public abstract void consume(I good);

    public abstract O produce();
}
