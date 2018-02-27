package net.avdw.anomalydetection;

import net.avdw.anomalydetection.nelsonrule.NelsonRule;
import net.avdw.economy.api.AConsumer;
import net.avdw.economy.api.AFactory;
import net.avdw.economy.api.ASupplier;
import net.avdw.economy.api.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import net.avdw.anomalydetection.api.AnomalyDetectionGood;
import net.avdw.anomalydetection.api.AnomalyDetectionResource;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

/*
https://en.wikipedia.org/wiki/Association_rule_learning
http://odds.cs.stonybrook.edu/
https://anomaly.io/anomaly-detection-moving-median-decomposition/
https://en.wikipedia.org/wiki/Nelson_rules
 */
public class AnomalyDetection
{

    public AnomalyDetection(BlockingQueue<Container<AnomalyDetectionResource>> inQ, BlockingQueue<Container<AnomalyDetectionGood>> outQ)
    {
        AnomalyDetectionFactory anomalyDetectionFactory = new AnomalyDetectionFactory(inQ, outQ);
    }

    public static void main(String[] args)
    {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.INFO)
                .activate();

        BlockingQueue<Container<Double>> producerQ = new LinkedBlockingQueue();
        ASupplier producer = new RandomSupplier(producerQ);

        List<BlockingQueue<Container<StatisticGood>>> statisticQs = new ArrayList();
        AFactory statisticsFactory = new StatisticFactory(producerQ, statisticQs);

        NelsonRule nelsonRules = new NelsonRule();
        List<BlockingQueue<Container<NelsonRuleGood>>> nelsonRuleQs = new ArrayList();
        List<NelsonRuleFactory> nelsonRuleFactories = new ArrayList();
        nelsonRules.rules.forEach((rule) ->
        {
            BlockingQueue<Container<StatisticGood>> statisticQ = new LinkedBlockingQueue();
            statisticQs.add(statisticQ);

            BlockingQueue<Container<NelsonRuleGood>> nelsonRuleQ = new LinkedBlockingQueue();
            nelsonRuleQs.add(nelsonRuleQ);

            nelsonRuleFactories.add(new NelsonRuleFactory(rule, statisticQ, nelsonRuleQ));
        });

        AConsumer consumer = new NelsonRuleConsumer(nelsonRuleQs);

        producer.start();
        statisticsFactory.start();
        nelsonRuleFactories.forEach((factory) ->
        {
            factory.start();
        });
        consumer.start();
    }
}
