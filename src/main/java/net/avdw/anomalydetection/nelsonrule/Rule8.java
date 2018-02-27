package net.avdw.anomalydetection.nelsonrule;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Eight points in a row exist with none within 1 standard deviation of the mean
 * and the points are in both directions from the mean. Problem indicated:
 * Jumping from above to below whilst missing the first standard deviation band
 * is rarely random.
 */
public class Rule8 implements NelsonRule.IRule
{
    Deque<Double> previous;

    public Rule8()
    {
        previous = new LinkedList();
    }

    @Override
    public Boolean compute(Double mean, Double stddev, Double observation)
    {
        if (previous.size() == 8)
        {
            previous.poll();
        }

        previous.offer(observation);

        Long count = previous.stream().filter((item) -> Math.abs(item - mean) > stddev).count();
        return count == 8;
    }

    @Override
    public String description()
    {
        return "Eight points in a row exist with none within 1 standard deviation of the mean and the points are in both directions from the mean. Problem indicated: Jumping from above to below whilst missing the first standard deviation band is rarely random.";
    }

}
