package net.avdw.anomalydetection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import net.avdw.anomalydetection.api.AnomalyDetectionGood;
import net.avdw.anomalydetection.api.AnomalyDetectionResource;
import net.avdw.anomalydetection.nelsonrule.NelsonRule;

/*
https://en.wikipedia.org/wiki/Association_rule_learning
http://odds.cs.stonybrook.edu/
https://anomaly.io/anomaly-detection-moving-median-decomposition/
https://en.wikipedia.org/wiki/Nelson_rules
 */
public class AnomalyDetection
{

    StatisticFactory statisticFactory;
    List<NelsonRuleFactory> nelsonRuleFactories;

    public AnomalyDetection(BlockingQueue<AnomalyDetectionResource> inQ, BlockingQueue<AnomalyDetectionGood> outQ)
    {
        List<BlockingQueue<StatisticGood>> statisticGoodQs = new ArrayList();

        statisticFactory = new StatisticFactory(inQ, statisticGoodQs);
        nelsonRuleFactories = new ArrayList();

        List<BlockingQueue<NelsonRuleGood>> nelsonRuleGoodQs = new ArrayList();
        NelsonRule nelsonRules = new NelsonRule();
        nelsonRules.rules.forEach((rule) ->
        {
            BlockingQueue statisticGoodQ = new LinkedBlockingQueue();
            statisticGoodQs.add(statisticGoodQ);

            BlockingQueue<NelsonRuleGood> nelsonRuleGoodQ = new LinkedBlockingQueue();
            nelsonRuleGoodQs.add(nelsonRuleGoodQ);

            nelsonRuleFactories.add(new NelsonRuleFactory(rule, statisticGoodQ, nelsonRuleGoodQ));
        });

    }

    public void start()
    {
        statisticFactory.start();
        nelsonRuleFactories.forEach((nelsonRuleFactory) ->
        {
            nelsonRuleFactory.start();
        });
    }

}
