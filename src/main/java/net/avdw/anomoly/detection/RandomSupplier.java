package net.avdw.anomoly.detection;

import net.avdw.economy.api.ASupplier;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.pmw.tinylog.Logger;

public class RandomSupplier extends ASupplier<Double>
{

    public RandomSupplier(BlockingQueue output)
    {
        super(output);
    }

    @Override
    public Double produce()
    {
        Logger.debug("");
        try
        {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException ex)
        {
            Logger.warn(ex);
        }

        return Math.random();
    }

}
