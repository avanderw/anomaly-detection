package net.avdw.anomoly.detection.nelson;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Four (or five) out of five points in a row are more than 1 standard deviation
 * from the mean in the same direction. Problem indicated: There is a strong
 * tendency for samples to be slightly out of control. The side of the mean for
 * the fifth point is unspecified.
 */
public class Rule6 implements IRule
{

    Deque<Double> previous;

    public Rule6()
    {
        previous = new LinkedList();
    }

    @Override
    public Boolean compute(Double mean, Double stddev, Double observation)
    {
        if (previous.size() == 5)
        {
            previous.poll();
        }

        previous.offer(observation);

        Integer countHigh = 0;
        Integer countLow = 0;
        for (Double curr : previous)
        {
            if (curr - mean > stddev)
            {
                countHigh++;
            } else if (curr - mean < -stddev)
            {
                countLow++;
            }
        }

        return countHigh >= 4 || countLow >= 4;
    }

}
