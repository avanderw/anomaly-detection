package net.avdw.anomoly.detection.nelsonrule;

import net.avdw.anomolydetection.nelsonrule.Rule3;
import java.util.Arrays;
import net.avdw.anomolydetection.nelsonrule.NelsonRule;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;
import static org.junit.Assert.*;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

public class Rule3Test
{

    public Rule3Test()
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
        NelsonRule.IRule rule = new Rule3();
        for (Double value : Arrays.asList(13., 14., 15., 16., 17., 18., 19., 20., 21., 22., 23., 24., 25., 26., 27., 28., 29., 30.))
        {
            stats.addValue(value);
            rule.compute(stats.getMean(), stats.getStandardDeviation(), value);
        }

        assertTrue(rule.compute(stats.getMean(), stats.getStandardDeviation(), 31.));
        assertFalse(rule.compute(stats.getMean(), stats.getStandardDeviation(), 2.));
    }

}
