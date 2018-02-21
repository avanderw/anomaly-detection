package net.avdw.anomoly.detection.nelson;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Six (or more) points in a row are continually increasing (or decreasing).
 * Problem indicated: A trend exists.
 */
public class Rule3 implements IRule
{

    Deque<Double> previous;

    public Rule3()
    {
        previous = new LinkedList();
    }

    @Override
    public Boolean compute(Double mean, Double stddev, Double observation)
    {
        if (previous.size() == 7)
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
                if (curr - prev <= 0)
                {
                    return Boolean.FALSE;
                }
            } else
            {
                if (curr - prev > 0)
                {
                    return Boolean.FALSE;
                }
            }
        }

        return Boolean.TRUE;
    }
}
