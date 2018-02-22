package net.avdw.economy.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.pmw.tinylog.Logger;

public abstract class AConsumer<I> extends AThread
    {
        private final List<BlockingQueue<Container<I>>> inputs;

        public AConsumer(BlockingQueue<Container<I>> input)
        {
            this.inputs = new ArrayList();
            this.inputs.add(input);
        }

        public AConsumer(List<BlockingQueue<Container<I>>> inputs)
        {
            this.inputs = inputs;
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
                        Container<I> container = input.take();
                        container.stamp(this.getClass().getSimpleName());
                        consume(container.unpack());
                        Logger.trace(container);
                    } catch (InterruptedException ex)
                    {
                        Logger.warn(ex);
                    }
                });
            }
            Logger.info(String.format("stopped"));
        }

        public abstract void consume(I good);
    }
