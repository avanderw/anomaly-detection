package net.avdw.anomoly.detection;

import java.util.concurrent.BlockingQueue;
import org.pmw.tinylog.Logger;

class VoidConsumer extends AConsumer
{

    public VoidConsumer(BlockingQueue input)
    {
        super(input);
    }

    @Override
    void consume(Object item)
    {
        Logger.debug(String.format("voiding %s", item));
    }

}
