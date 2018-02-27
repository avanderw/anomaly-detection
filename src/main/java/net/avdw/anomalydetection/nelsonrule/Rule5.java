package net.avdw.anomalydetection.nelsonrule;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Two (or three) out of three points in a row are more than 2 standard
 * deviations from the mean in the same direction. Problem indicated: There is a
 * medium tendency for samples to be mediumly out of control. The side of the
 * mean for the third point is unspecified.
 */
public class Rule5 implements NelsonRule.IRule
{

    Deque<Double> previous;

    public Rule5()
    {
        previous = new LinkedList();
    }

    @Override
    public Boolean compute(Double mean, Double stddev, Double observation)
    {
        if (previous.size() == 3)
        {
            previous.poll();
        }

        previous.offer(observation);

        Double twoStdDev = 2 * stddev;
        Integer countHigh = 0;
        Integer countLow = 0;
        for (Double curr : previous)
        {
            if (curr - mean > twoStdDev)
            {
                countHigh++;
            } else if (curr - mean < -twoStdDev)
            {
                countLow++;
            }
        }

        return countHigh >= 2 || countLow >= 2;
    }

    @Override
    public String description()
    {
        return "Two (or three) out of three points in a row are more than 2 standard deviations from the mean in the same direction. Problem indicated: There is a medium tendency for samples to be mediumly out of control.";
    }

}
