package net.avdw.anomoly.detection;

import java.util.concurrent.BlockingQueue;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.pmw.tinylog.Logger;

public class DoubleStatisticFactory extends AFactory<Double, DescriptiveStatistics>
{

    DescriptiveStatistics statistics;

    public DoubleStatisticFactory(BlockingQueue<Double> input, BlockingQueue<DescriptiveStatistics> output)
    {
        super(input, output);
        statistics = new DescriptiveStatistics();
    }

    @Override
    void consume(Double item)
    {
        Logger.debug("");
        statistics.addValue(item);
    }

    @Override
    DescriptiveStatistics produce()
    {
        Logger.debug("");
        return statistics.copy();
    }

}
