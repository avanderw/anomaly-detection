package net.avdw.anomalydetection;

import net.avdw.economy.api.AFactory;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import net.avdw.anomalydetection.api.AnomalyDetectionResource;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.pmw.tinylog.Logger;

class StatisticFactory extends AFactory<AnomalyDetectionResource, StatisticGood>
{

    private final DescriptiveStatistics statistics;
    private AnomalyDetectionResource resource;

    StatisticFactory(BlockingQueue<AnomalyDetectionResource> input, List<BlockingQueue<StatisticGood>> outputs)
    {
        super(input, outputs);
        statistics = new DescriptiveStatistics();
    }

    @Override
    public void consume(AnomalyDetectionResource resource)
    {
        Logger.debug("");
        this.resource = resource;
        statistics.addValue(resource.value);
    }

    @Override
    public StatisticGood produce()
    {
        Logger.debug("");
        StatisticGood statisticGood = new StatisticGood();
        statisticGood.mean = statistics.getMean();
        statisticGood.stddev = statistics.getStandardDeviation();
        statisticGood.value = this.resource.value;
        return statisticGood;
    }

}
