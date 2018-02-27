package net.avdw.anomalydetection;

import net.avdw.economy.api.AFactory;
import java.util.concurrent.BlockingQueue;
import net.avdw.anomalydetection.nelsonrule.NelsonRule;
import net.avdw.anomalydetection.nelsonrule.NelsonRule.IRule;
import net.avdw.economy.api.Container;
import org.pmw.tinylog.Logger;

class NelsonRuleFactory extends AFactory<StatisticGood, NelsonRuleGood>
{
    StatisticGood statisticGood;
    IRule rule;

    NelsonRuleFactory(NelsonRule.IRule rule, BlockingQueue<Container<StatisticGood>> input, BlockingQueue<Container<NelsonRuleGood>>... outputs)
    {
        super(input, outputs);
        this.rule = rule;
    }

    @Override
    public void consume(StatisticGood statisticGood)
    {
        Logger.debug("");
        this.statisticGood = statisticGood;
    }

    @Override
    public NelsonRuleGood produce()
    {
        Logger.debug("");
        NelsonRuleGood nelsonRuleGood = new NelsonRuleGood();
        nelsonRuleGood.rule = rule.getClass().getSimpleName();
        nelsonRuleGood.description = rule.description();
        nelsonRuleGood.failed = rule.compute(statisticGood.mean, statisticGood.stddev, statisticGood.value);
        return nelsonRuleGood;
    }

}
