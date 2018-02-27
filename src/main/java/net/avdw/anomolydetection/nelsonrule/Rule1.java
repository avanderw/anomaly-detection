package net.avdw.anomolydetection.nelsonrule;

/**
 * One point is more than 3 standard deviations from the mean. Problem
 * indicated: One sample is grossly out of control.
 */
public class Rule1 implements NelsonRule.IRule
{

    @Override
    public Boolean compute(Double mean, Double stddev, Double observation)
    {
        Double threeStdDev = 3 * stddev;
        Double upperBound = mean + threeStdDev;
        Double lowerBound = mean - threeStdDev;

        return observation > upperBound || observation < lowerBound;
    }

    @Override
    public String description()
    {
        return "One point is more than 3 standard deviations from the mean. Problem indicated: One sample is grossly out of control.";
    }
}
