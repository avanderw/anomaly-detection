package net.avdw.anomoly.detection.nelson;

public interface IRule
{
    Boolean compute(Double mean, Double stddev, Double observation);
}
