package net.avdw.anomalydetection;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import net.avdw.anomalydetection.NelsonRuleGood;
import net.avdw.economy.api.AConsumer;
import org.pmw.tinylog.Logger;

 class NelsonRuleConsumer extends AConsumer<NelsonRuleGood> {

    public NelsonRuleConsumer(List<BlockingQueue<NelsonRuleGood>> inputs)
    {
        super(inputs);
    }

    @Override
    public void consume(NelsonRuleGood good)
    {
        if (good.failed) {
            Logger.warn(String.format("failed Nelson %s: %s", new Object[]{good.rule, good.description}));
        }
    }

}
