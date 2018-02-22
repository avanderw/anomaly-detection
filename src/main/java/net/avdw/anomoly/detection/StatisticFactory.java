package net.avdw.anomoly.detection;

import net.avdw.economy.api.AFactory;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import net.avdw.economy.api.Container;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.pmw.tinylog.Logger;

class StatisticFactory extends AFactory<Double, StatisticGood>
{

    private final DescriptiveStatistics statistics;
    private Double inputGood;

    StatisticFactory(BlockingQueue<Container<Double>> input, List<BlockingQueue<Container<StatisticGood>>> outputs)
    {
        super(input, outputs);
        statistics = new DescriptiveStatistics();
    }

    @Override
    public void consume(Double inputGood)
    {
        Logger.debug("");
        this.inputGood = inputGood;
        statistics.addValue(inputGood);
    }

    @Override
    public StatisticGood produce()
    {
        Logger.debug("");
        StatisticGood statisticGood = new StatisticGood();
        statisticGood.mean = statistics.getMean();
        statisticGood.stddev = statistics.getStandardDeviation();
        statisticGood.value = this.inputGood;
        return statisticGood;
    }

}
