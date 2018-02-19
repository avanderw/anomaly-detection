//package net.avdw.anomoly.detection;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Nelson rules are a method in process control of determining if some measured variable is out of control (unpredictable versus consistent).
// * The rules are applied to a control chart on which the magnitude of some variable is plotted against time.
// * The rules are based around the mean value and the standard deviation of the samples.
// *
// * @author Andrew van der Westhuizen
// */
//public class NelsonRules {
//
//    /**
//     * One point is more than 3 standard deviations from the mean.
//     * Problem indicated: One sample is grossly out of control.
//     *
//     * @param sample
//     * @return
//     */
//    public static Boolean passRule1(List<Double> sample) {
//        Boolean isPass = Boolean.TRUE;
//        Double mean = DescriptiveStatistics.calcMean(sample);
//        Double threeStdDev = 3 * DescriptiveStatistics.calcStdDev(sample);
//        Double upperBound = mean + threeStdDev;
//        Double lowerBound = mean - threeStdDev;
//
//        for (Double s : sample) {
//            if (s > upperBound || s < lowerBound) {
//                isPass = Boolean.FALSE;
//                break;
//            }
//        }
//
//        return isPass;
//    }
//
//    /**
//     * Nine (or more) points in a row are on the same side of the mean.
//     * Problem indicated: Some prolonged bias exists.
//     *
//     * @param sample
//     * @return
//     */
//    public static Boolean passRule2(List<Double> sample) {
//        Boolean isPass = Boolean.TRUE;
//        Double mean = DescriptiveStatistics.calcMean(sample);
//
//        Integer count = 0;
//        Integer side = 1;
//        for (Double s : sample) {
//            if (side > 0) {
//                if (mean - s > 0) {
//                    count++;
//                } else {
//                    side = -1;
//                    count = 1;
//                }
//            } else {
//                if (mean - s < 0) {
//                    count++;
//                } else {
//                    side = 1;
//                    count = 1;
//                }
//            }
//            if (count == 9) {
//                isPass = Boolean.FALSE;
//                break;
//            }
//        }
//        return isPass;
//    }
//
//    /**
//     * Six (or more) points in a row are continually increasing (or decreasing).
//     * Problem indicated: A trend exists.
//     *
//     * @param sample
//     * @return
//     */
//    public static Boolean passRule3(List<Double> sample) {
//        Boolean isPass = Boolean.TRUE;
//        Double lastPoint = Double.NaN;
//        Boolean increasing = Boolean.FALSE;
//        Integer count = 1;
//
//        for (Double s : sample) {
//            if (lastPoint != Double.NaN) {
//                if (increasing) {
//                    if (s > lastPoint) {
//                        count++;
//                    } else {
//                        increasing = Boolean.FALSE;
//                        count = 1;
//                    }
//                } else {
//                    if (s < lastPoint) {
//                        count++;
//                    } else {
//                        increasing = Boolean.TRUE;
//                        count = 1;
//                    }
//                }
//            }
//
//            if (count == 6) {
//                isPass = Boolean.FALSE;
//                break;
//            }
//            lastPoint = s;
//        }
//        return isPass;
//    }
//
//    /**
//     * Fourteen (or more) points in a row alternate in direction, increasing then decreasing.
//     * Problem indicated: This much oscillation is beyond noise.
//     * Note that the rule is concerned with directionality only. The position of the mean and the size of the standard deviation have no bearing.
//     *
//     * @param sample
//     * @return
//     */
//    public static Boolean passRule4(List<Double> sample) {
//        Boolean isPass = Boolean.TRUE;
//        Double lastPoint = Double.NaN;
//        Boolean increasing = Boolean.TRUE;
//        Integer count = 0;
//
//        for (Double s : sample) {
//            if (lastPoint != Double.NaN) {
//                if (increasing) {
//                    if (s < lastPoint) {
//                        count++;
//                        increasing = Boolean.FALSE;
//                    } else {
//                        count = 1;
//                    }
//                } else {
//                    if (s > lastPoint) {
//                        increasing = Boolean.TRUE;
//                        count++;
//                    } else {
//                        count = 1;
//                    }
//                }
//                if (count == 14) {
//                    isPass = Boolean.FALSE;
//                }
//            }
//
//            lastPoint = s;
//        }
//
//        return isPass;
//    }
//
//    /**
//     * Two (or three) out of three points in a row are more than 2 standard deviations from the mean in the same direction.
//     * Problem indicated: There is a medium tendency for samples to be mediumly out of control.
//     * The side of the mean for the third point is unspecified.
//     *
//     * @param sample
//     * @return
//     */
//    public static Boolean passRule5(List<Double> sample) {
//        Boolean isPass = Boolean.TRUE;
//        Double mean = DescriptiveStatistics.calcMean(sample);
//        Double twoStdDev = 2 * DescriptiveStatistics.calcStdDev(sample);
//        Double upperBound = mean + twoStdDev;
//        Double lowerBound = mean - twoStdDev;
//        Integer count = 0;
//        Integer sameSideCount = 0;
//        Boolean positive = Boolean.TRUE;
//
//        for (Double s : sample) {
//            if (s > upperBound || s < lowerBound) {
//                count++;
//            } else {
//                count = 0;
//            }
//
//            if (s > upperBound) {
//                if (positive || sameSideCount == 2) {
//                    sameSideCount++;
//                } else {
//                    sameSideCount = 1;
//                }
//                positive = Boolean.TRUE;
//            }
//
//            if (s < lowerBound) {
//                if (!positive || sameSideCount == 2) {
//                    sameSideCount++;
//                } else {
//                    sameSideCount = 1;
//                }
//                positive = Boolean.FALSE;
//            }
//
//            if (count == 3 && sameSideCount >= 2) {
//                isPass = Boolean.FALSE;
//                break;
//            }
//        }
//
//        return isPass;
//    }
//
//    /**
//     * Four (or five) out of five points in a row are more than 1 standard deviation from the mean in the same direction.
//     * Problem indicated: There is a strong tendency for samples to be slightly out of control.
//     * The side of the mean for the fifth point is unspecified.
//     *
//     * @param sample
//     * @return
//     */
//    public static Boolean passRule6(List<Double> sample) {
//        Boolean isPass = Boolean.TRUE;
//        Double mean = DescriptiveStatistics.calcMean(sample);
//        Double stdDev = DescriptiveStatistics.calcStdDev(sample);
//        Double upperBound = mean + stdDev;
//        Double lowerBound = mean - stdDev;
//        Integer count = 0;
//        Integer sameSideCount = 0;
//        Boolean positive = Boolean.TRUE;
//
//        for (Double s : sample) {
//            if (s > upperBound || s < lowerBound) {
//                count++;
//            } else {
//                count = 0;
//            }
//
//             if (s > upperBound) {
//                if (positive || sameSideCount == 4) {
//                    sameSideCount++;
//                } else {
//                    sameSideCount = 1;
//                }
//                positive = Boolean.TRUE;
//            }
//
//            if (s < lowerBound) {
//                if (!positive || sameSideCount == 4) {
//                    sameSideCount++;
//                } else {
//                    sameSideCount = 1;
//                }
//                positive = Boolean.FALSE;
//            }
//
//            if (count == 5 && sameSideCount >= 4) {
//                isPass = Boolean.FALSE;
//                break;
//            }
//        }
//
//        return isPass;
//    }
//
//    /**
//     * Fifteen points in a row are all within 1 standard deviation of the mean on either side of the mean.
//     * Problem indicated: With 1 standard deviation, greater variation would be expected.
//     *
//     * @param sample
//     * @return
//     */
//    public static Boolean passRule7(List<Double> sample) {
//        Boolean isPass = Boolean.TRUE;
//        Double mean = DescriptiveStatistics.calcMean(sample);
//        Double stdDev = DescriptiveStatistics.calcStdDev(sample);
//        Double upperBound = mean + stdDev;
//        Double lowerBound = mean - stdDev;
//        Integer count = 0;
//
//        for (Double s : sample) {
//            if (s < upperBound && s > lowerBound) {
//                count++;
//            } else {
//                count = 0;
//            }
//
//            if (count == 15) {
//                isPass = Boolean.FALSE;
//                break;
//            }
//        }
//
//        return isPass;
//    }
//
//    /**
//     * Eight points in a row exist with none within 1 standard deviation of the mean and the points are in both directions from the mean.
//     * Problem indicated: Jumping from above to below whilst missing the first standard deviation band is rarely random.
//     *
//     * @param sample
//     * @return
//     */
//    public static Boolean passRule8(List<Double> sample) {
//        Boolean isPass = Boolean.TRUE;
//        Double mean = DescriptiveStatistics.calcMean(sample);
//        Double stdDev = DescriptiveStatistics.calcStdDev(sample);
//        Double upperBound = mean + stdDev;
//        Double lowerBound = mean - stdDev;
//        Integer count = 0;
//
//        for (Double s : sample) {
//            if (s < upperBound && s > lowerBound) {
//                count = 0;
//            } else {
//                count++;
//            }
//
//            if (count == 8) {
//                isPass = Boolean.FALSE;
//                break;
//            }
//        }
//
//        return isPass;
//    }
//
//    public static void main(String[] args) {
//        List<Double> sample = new ArrayList<>();
//        Integer sampleSize = 20;
//
//        for (int i = 0; i < sampleSize; i++) {
//            sample.add(Math.random()*10);
//        }
//
//        System.out.println(sample);
//        System.out.println("Random 1: " + passRule1(sample));
//        System.out.println("Random 2: " + passRule2(sample));
//        System.out.println("Random 3: " + passRule3(sample));
//        System.out.println("Random 4: " + passRule4(sample));
//        System.out.println("Random 5: " + passRule5(sample));
//        System.out.println("Random 6: " + passRule6(sample));
//        System.out.println("Random 7: " + passRule7(sample));
//        System.out.println("Random 8: " + passRule8(sample));
//
//        System.out.println("\nPositive\nRule 1: " + passRule1(Arrays.asList(13., 18., 13., 14., 13., 16., 14., 21., 16., 13., 18., 13., 14., 13., 16., 14., 21., 13.)));
//        System.out.println("Rule 2: " + passRule2(Arrays.asList(13., 18., 16., 16., 16., 16., 16., 21., 16., 13., 13., 13., 14., 13., 16., 14., 21., 13.)));
//        System.out.println("Rule 3: " + passRule3(Arrays.asList(1., 2., 3., 4., 5., 3., 4., 4., 8., 6., 5., 4., 9., 8., 7., 5., 6., 1.)));
//        System.out.println("Rule 4: " + passRule4(Arrays.asList(3., 9., 3., 9., 3., 9., 3., 9., 3., 9., 3., 9., 3., 2., 3., 9., 3., 9.)));
//        System.out.println("Rule 5: " + passRule5(Arrays.asList(27., 28., 15., 14., 13., 16., 14., 21., 16., 13., 18., 13., 14., 13., 16., 14., 21., 13.)));
//        System.out.println("Rule 5: " + passRule5(Arrays.asList(3., 27., 3., 14., 13., 16., 14., 21., 16., 13., 18., 13., 14., 13., 16., 14., 21., 13.)));
//        System.out.println("Rule 6: " + passRule6(Arrays.asList(21., 21., 21., 21., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20.)));
//        System.out.println("Rule 7: " + passRule7(Arrays.asList(21., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 21., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20.)));
//        System.out.println("Rule 8: " + passRule8(Arrays.asList(20., 19., 21., 19., 19., 21., 19., 21., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20.)));
//
//        System.out.println("\nNegative\nRule 1: " + !passRule1(Arrays.asList(29., 18., 13., 14., 13., 16., 14., 21., 16., 13., 18., 13., 14., 13., 16., 14., 21., 13.)));
//        System.out.println("Rule 1: " + !passRule1(Arrays.asList(-29., 18., 13., 14., 13., 16., 14., 21., 16., 13., 18., 13., 14., 13., 16., 14., 21., 13.)));
//        System.out.println("Rule 2: " + !passRule2(Arrays.asList(13., 18., 16., 16., 16., 16., 16., 21., 16., 16., 18., 13., 14., 13., 16., 14., 21., 13.)));
//        System.out.println("Rule 2: " + !passRule2(Arrays.asList(13., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 13., 14., 13., 16., 14., 21., 13.)));
//        System.out.println("Rule 3: " + !passRule3(Arrays.asList(1., 2., 3., 4., 5., 6., 7., 4., 8., 6., 5., 4., 9., 8., 7., 5., 6., 1.)));
//        System.out.println("Rule 3: " + !passRule3(Arrays.asList(7., 6., 5., 4., 3., 2., 1., 1., 8., 6., 5., 4., 9., 8., 7., 5., 6., 1.)));
//        System.out.println("Rule 4: " + !passRule4(Arrays.asList(3., 9., 3., 9., 3., 9., 3., 9., 3., 9., 3., 9., 3., 9., 3., 9., 3., 9.)));
//        System.out.println("Rule 5: " + !passRule5(Arrays.asList(27., 28., 5., 14., 13., 16., 14., 21., 16., 13., 18., 13., 14., 13., 16., 14., 21., 13.)));
//        System.out.println("Rule 5: " + !passRule5(Arrays.asList(3., 3., 27., 14., 13., 16., 14., 21., 16., 13., 18., 13., 14., 13., 16., 14., 21., 13.)));
//        System.out.println("Rule 5: " + !passRule5(Arrays.asList(27., 3., 3., 14., 13., 16., 14., 21., 16., 13., 18., 13., 14., 13., 16., 14., 21., 13.)));
//        System.out.println("Rule 6: " + !passRule6(Arrays.asList(21., 21., 21., 21., 21., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20.)));
//        System.out.println("Rule 6: " + !passRule6(Arrays.asList(21., 21., 21., 21., 19., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20.)));
//        System.out.println("Rule 6: " + !passRule6(Arrays.asList(19., 21., 21., 21., 21., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20.)));
//        System.out.println("Rule 7: " + !passRule7(Arrays.asList(21., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20.)));
//        System.out.println("Rule 8: " + !passRule8(Arrays.asList(21., 19., 21., 19., 19., 21., 19., 21., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20.)));
//    }
//}