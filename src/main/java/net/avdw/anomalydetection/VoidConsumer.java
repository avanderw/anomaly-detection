package net.avdw.anomalydetection;

import java.util.List;
import net.avdw.economy.api.AConsumer;
import org.pmw.tinylog.Logger;

 class VoidConsumer extends AConsumer
{

    public VoidConsumer(List inputs)
    {
        super(inputs);
    }

    @Override
    public void consume(Object good)
    {
        Logger.debug(String.format("voiding good: %s", good.getClass().getSimpleName()));
    }

}
