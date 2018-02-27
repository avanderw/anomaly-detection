package net.avdw.anomolydetection.nelsonrule;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Nine (or more) points in a row are on the same side of the mean. Problem
 * indicated: Some prolonged bias exists.
 */
public class Rule2 implements NelsonRule.IRule
{

    Deque<Double> previous;

    public Rule2()
    {
        previous = new LinkedList();
    }

    @Override
    public Boolean compute(Double mean, Double stddev, Double observation)
    {
        if (previous.size() == 9)
        {
            previous.poll();
        }

        previous.offer(observation);

        Long count = previous.stream().filter((item) -> item > mean).count();
        return (count == 0 && previous.size() == 9) || count == 9;
    }

    @Override
    public String description()
    {
        return "Nine (or more) points in a row are on the same side of the mean. Problem indicated: Some prolonged bias exists.";
    }

}
