package net.avdw.anomalydetection;

import java.util.concurrent.BlockingQueue;
import net.avdw.anomalydetection.api.AnomalyDetectionGood;
import net.avdw.anomalydetection.api.AnomalyDetectionResource;
import net.avdw.economy.api.AFactory;
import net.avdw.economy.api.Container;

class AnomalyDetectionFactory extends AFactory<AnomalyDetectionResource, AnomalyDetectionGood>
{

    public AnomalyDetectionFactory(BlockingQueue<Container<AnomalyDetectionResource>> input, BlockingQueue<Container<AnomalyDetectionGood>>... outputs)
    {
        super(input, outputs);
    }

    @Override
    public void consume(AnomalyDetectionResource good)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnomalyDetectionGood produce()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
