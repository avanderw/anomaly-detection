package net.avdw.anomoly.detection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

/*
https://en.wikipedia.org/wiki/Association_rule_learning
http://odds.cs.stonybrook.edu/
https://anomaly.io/anomaly-detection-moving-median-decomposition/
https://en.wikipedia.org/wiki/Nelson_rules
*/
public class Detection {

    public static void main(String[] args) {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} [{thread}] {class}.{method}() {level}: {message}")
                .level(Level.TRACE)
                .activate();

        BlockingQueue<Double> queue = new LinkedBlockingQueue();
        RandomProducer randomProducer = new RandomProducer(queue);
        VoidConsumer voidConsumer = new VoidConsumer(queue);

        randomProducer.start();
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException ex) {
            Logger.warn(ex);
        }
        voidConsumer.start();
    }
}
