package net.avdw.anomoly.detection.nelsonrule;

import java.util.Deque;
import java.util.LinkedList;

    /**
     * Fifteen points in a row are all within 1 standard deviation of the mean on either side of the mean.
     * Problem indicated: With 1 standard deviation, greater variation would be expected.
     */
public class Rule7 implements NelsonRule.IRule
{

    Deque<Double> previous;

    public Rule7()
    {
        previous = new LinkedList();
    }

    @Override
    public Boolean compute(Double mean, Double stddev, Double observation)
    {
        if (previous.size() == 15)
        {
            previous.poll();
        }

        previous.offer(observation);

        Long count = previous.stream().filter((item) -> Math.abs(item - mean) < stddev).count();
        return count == 15;
    }

    @Override
    public String description()
    {
        return "Fifteen points in a row are all within 1 standard deviation of the mean on either side of the mean. Problem indicated: With 1 standard deviation, greater variation would be expected.";
    }

}
