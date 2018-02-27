package net.avdw.anomoly.detection.nelsonrule;

import net.avdw.anomalydetection.nelsonrule.Rule2;
import java.util.Arrays;
import net.avdw.anomalydetection.nelsonrule.NelsonRule;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;
import static org.junit.Assert.*;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

public class Rule2Test
{

    public Rule2Test()
    {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} {method}(): {message}")
                .level(Level.TRACE)
                .activate();
    }

    @Test
    public void testCompute()
    {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        NelsonRule.IRule rule = new Rule2();
        for (Double value : Arrays.asList(13., 14., 15., 16., 17., 18., 19., 20., 21., 22., 23., 24., 25., 26., 27., 28., 29., 30.))
        {
            stats.addValue(value);
            rule.compute(stats.getMean(), stats.getStandardDeviation(), value);
        }

        assertTrue(rule.compute(stats.getMean(), stats.getStandardDeviation(), 30.));
        assertFalse(rule.compute(stats.getMean(), stats.getStandardDeviation(), 13.));
    }
}
