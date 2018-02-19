package net.avdw.anomoly.detection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.pmw.tinylog.Logger;

public class RandomProducer extends AProducer {

    public RandomProducer(BlockingQueue output) {
        super(output);
    }

    @Override
    Double produce() {
        Logger.debug("");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            Logger.warn(ex);
        }
        return Math.random();
    }

}
