package net.avdw.anomalydetection.nelsonrule;

import java.util.ArrayList;
import java.util.List;

/**
 * Nelson rules are a method in process control of determining if some measured
 * variable is out of control (unpredictable versus consistent). The rules are
 * applied to a control chart on which the magnitude of some variable is plotted
 * against time. The rules are based around the mean value and the standard
 * deviation of the samples.
 *
 * @author Andrew van der Westhuizen
 */
public class NelsonRule
{
    public IRule rule1;
    public IRule rule2;
    public IRule rule3;
    public IRule rule4;
    public IRule rule5;
    public IRule rule6;
    public IRule rule7;
    public IRule rule8;
    public List<IRule> rules;

    public NelsonRule() {
        rules = new ArrayList();
        rules.add(rule1 = new Rule1());
        rules.add(rule2 = new Rule2());
        rules.add(rule3 = new Rule3());
        rules.add(rule4 = new Rule4());
        rules.add(rule5 = new Rule5());
        rules.add(rule6 = new Rule6());
        rules.add(rule7 = new Rule7());
        rules.add(rule8 = new Rule8());
    }

    public interface IRule
    {
        Boolean compute(Double mean, Double stddev, Double observation);
        String description();
    }
}
