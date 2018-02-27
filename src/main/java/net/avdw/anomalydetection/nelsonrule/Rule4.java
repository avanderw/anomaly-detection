package net.avdw.anomalydetection.nelsonrule;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Fourteen (or more) points in a row alternate in direction, increasing then
 * decreasing. Problem indicated: This much oscillation is beyond noise. Note
 * that the rule is concerned with directionality only. The position of the mean
 * and the size of the standard deviation have no bearing.
 */
public class Rule4 implements NelsonRule.IRule
{

    Deque<Double> previous;

    public Rule4()
    {
        previous = new LinkedList();
    }

    @Override
    public Boolean compute(Double mean, Double stddev, Double observation)
    {
        if (previous.size() == 14)
        {
            previous.poll();
        }

        previous.offer(observation);

        Double prev = null;
        Boolean increasing = null;
        for (Double curr : previous)
        {
            if (prev == null)
            {
                prev = curr;
                continue;
            }

            if (increasing == null)
            {
                increasing = curr - prev > 0;
                prev = curr;
                continue;
            }

            if (increasing)
            {
                if (curr - prev > 0)
                {
                    return Boolean.FALSE;
                }
            } else
            {
                if (curr - prev <= 0)
                {
                    return Boolean.FALSE;
                }
            }

            increasing = curr - prev > 0;
            prev = curr;
        }
        return Boolean.TRUE;
    }

    @Override
    public String description()
    {
        return "Fourteen (or more) points in a row alternate in direction, increasing then decreasing. Problem indicated: This much oscillation is beyond noise.";
    }

}
