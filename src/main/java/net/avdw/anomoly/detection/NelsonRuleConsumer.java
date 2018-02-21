package net.avdw.anomoly.detection;

import java.util.concurrent.BlockingQueue;

public class NelsonRuleConsumer extends AConsumer {

    public NelsonRuleConsumer(BlockingQueue input)
    {
        super(input);
    }

    @Override
    void consume(Object item)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
