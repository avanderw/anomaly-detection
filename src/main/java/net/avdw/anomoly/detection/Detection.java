package net.avdw.anomoly.detection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

/*
https://en.wikipedia.org/wiki/Association_rule_learning
http://odds.cs.stonybrook.edu/
https://anomaly.io/anomaly-detection-moving-median-decomposition/
https://en.wikipedia.org/wiki/Nelson_rules
 */
public class Detection
{

    public static void main(String[] args)
    {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.TRACE)
                .activate();

        BlockingQueue<Double> randomQueue = new LinkedBlockingQueue();
        BlockingQueue<DescriptiveStatistics> statisticQueue = new LinkedBlockingQueue();

        ASupplier producer = new RandomSupplier(randomQueue);
        AFactory factory = new DoubleStatisticFactory(randomQueue, statisticQueue);
        AConsumer voidConsumer = new VoidConsumer(statisticQueue);

        producer.start();
        factory.start();
        voidConsumer.start();
    }
}
