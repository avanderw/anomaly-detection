package net.avdw.anomolydetection.nelsonrule;

import net.avdw.anomalydetection.nelsonrule.Rule8;
import java.util.Arrays;
import net.avdw.anomalydetection.nelsonrule.NelsonRule;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;
import static org.junit.Assert.*;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

public class Rule8Test
{

    public Rule8Test()
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
        NelsonRule.IRule rule = new Rule8();
        for (Double value : Arrays.asList(13., 14., 13., 14., 13., 15., 12., 14., 13., 20., 19., 20., 19., 20., 19., 20., 19., 20.))
        {
            stats.addValue(value);
            rule.compute(stats.getMean(), stats.getStandardDeviation(), value);
        }

        assertTrue(rule.compute(stats.getMean(), 2., 20.));
        assertFalse(rule.compute(stats.getMean(), 2., 16.));
    }

}
