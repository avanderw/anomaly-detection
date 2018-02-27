/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.avdw.anomalydetection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import net.avdw.anomalydetection.api.AnomalyDetectionResource;
import net.avdw.anomalydetection.nelsonrule.NelsonRule;
import net.avdw.economy.api.AConsumer;
import net.avdw.economy.api.AFactory;
import net.avdw.economy.api.ASupplier;
import org.junit.Test;
import static org.junit.Assert.*;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

/**
 *
 * @author CP318674
 */
public class AnomalyDetectionTest
{

    public AnomalyDetectionTest()
    {
    }

    @Test
    public void testBasic() throws InterruptedException
    {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.INFO)
                .activate();

        BlockingQueue<AnomalyDetectionResource> producerQ = new LinkedBlockingQueue();
        ASupplier producer = new RandomSupplier(producerQ);

        List<BlockingQueue<StatisticGood>> statisticQs = new ArrayList();
        AFactory statisticFactory = new StatisticFactory(producerQ, statisticQs);

        NelsonRule nelsonRules = new NelsonRule();
        List<BlockingQueue<NelsonRuleGood>> nelsonRuleQs = new ArrayList();
        List<NelsonRuleFactory> nelsonRuleFactories = new ArrayList();
        nelsonRules.rules.forEach((rule) ->
        {
            BlockingQueue<StatisticGood> statisticQ = new LinkedBlockingQueue();
            statisticQs.add(statisticQ);

            BlockingQueue<NelsonRuleGood> nelsonRuleQ = new LinkedBlockingQueue();
            nelsonRuleQs.add(nelsonRuleQ);

            nelsonRuleFactories.add(new NelsonRuleFactory(rule, statisticQ, nelsonRuleQ));
        });

        AConsumer consumer = new NelsonRuleConsumer(nelsonRuleQs);

        producer.start();
        statisticFactory.start();
        nelsonRuleFactories.forEach((factory) ->
        {
            factory.start();
        });
        consumer.start();

        TimeUnit.SECONDS.sleep(3);

        producer.stop();
        statisticFactory.stop();
        nelsonRuleFactories.forEach((factory) ->
        {
            factory.stop();
        });
        consumer.stop();
    }

}
