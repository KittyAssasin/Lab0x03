package com.company;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.MalformedParametersException;

public class Main {

    public static void main(String[] args) {
        int nStart = 4;
        int nSteps = 10;
        int nScale = 2;
        long startTime;
        long finalTime;

        long[] bruteTimes = new long[nSteps];
        long[] fastTimes = new long[nSteps];
        long[] fastestTimes = new long[nSteps];

        int[] testArray = {-19, -14, -7, -2, 4, 6, 7, 8, 23, 45};

        System.out.println("3sum brute test: " + threeSumBrute(testArray));
        // write your code here
    }

    //3sum problem: are there three numbers in this list that sum to 0?
    //3sum brute force (N^3)
    private static boolean threeSumBrute(int[] array) {
        int len = array.length;

        for (int i = 0; i < len; i++) {
            for (int k = i+1; k < len; k++) {
                for (int m = k+1; m < len; m++) {
                    if ((array[i] + array[k] + array[m]) == 0)
                        return true;
                }
            }
        }
        return false;
    }

    //3sum faster (N^2 * log(N))
    //private static boolean threeSumFast(int[] array) {}

    //3sum fastest (N^2)
    //private static boolean threeSumFastest(int[] array) {}

    //private static int[] generateArray(int count, int min, int max) {}

    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadCpuTime() : 0L;
    }
}
