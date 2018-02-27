package net.avdw.anomalydetection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import net.avdw.anomalydetection.api.AnomalyDetectionResource;
import net.avdw.economy.api.ASupplier;
import org.pmw.tinylog.Logger;

 class RandomSupplier extends ASupplier<AnomalyDetectionResource>
{

    public RandomSupplier(BlockingQueue output)
    {
        super(output);
    }

    @Override
    public AnomalyDetectionResource produce()
    {
        Logger.debug("");
        try
        {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException ex)
        {
            Logger.warn(ex);
        }

        AnomalyDetectionResource resource = new AnomalyDetectionResource();
        resource.value = Math.random();
        return resource;
    }

}
