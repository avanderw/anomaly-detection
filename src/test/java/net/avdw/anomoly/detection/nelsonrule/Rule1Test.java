package net.avdw.anomoly.detection.nelsonrule;

import net.avdw.anomoly.detection.nelsonrule.Rule1;
import java.util.Arrays;
import net.avdw.anomoly.detection.nelsonrule.NelsonRule;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;
import static org.junit.Assert.*;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;

public class Rule1Test
{

    public Rule1Test()
    {
        Configurator.currentConfig()
                .formatPattern("{date:yyyy-MM-dd HH:mm:ss} {method}(): {message}")
                .level(Level.TRACE)
                .activate();
    }

    @Test
    public void testLowerBound()
    {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        NelsonRule.IRule rule = new Rule1();
        for (Double value : Arrays.asList(13., 18., 13., 14., 13., 16., 14., 21., 16., 13., 18., 13., 14., 13., 16., 14., 21., 13.))
        {
            stats.addValue(value);
            rule.compute(stats.getMean(), stats.getStandardDeviation(), value);
        }

        assertTrue(rule.compute(stats.getMean(), stats.getStandardDeviation(), 0.));
    }

    @Test
    public void testUpperBound()
    {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        NelsonRule.IRule rule = new Rule1();
        for (Double value : Arrays.asList(13., 18., 13., 14., 13., 16., 14., 21., 16., 13., 18., 13., 14., 13., 16., 14., 21., 13.))
        {
            stats.addValue(value);
            rule.compute(stats.getMean(), stats.getStandardDeviation(), value);
        }

        assertTrue(rule.compute(stats.getMean(), stats.getStandardDeviation(), 26.));
    }

    @Test
    public void testInRange()
    {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        NelsonRule.IRule rule = new Rule1();
        for (Double value : Arrays.asList(29., 18., 13., 14., 13., 16., 14., 21., 16., 13., 18., 13., 14., 13., 16., 14., 21., 13.))
        {
            stats.addValue(value);
            rule.compute(stats.getMean(), stats.getStandardDeviation(), value);
        }

        assertFalse(rule.compute(stats.getMean(), stats.getStandardDeviation(), 13.));
    }
}
