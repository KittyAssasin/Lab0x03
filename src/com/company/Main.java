package com.company;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int nStart = 4;
        int nSteps = 10;
        int nScale = 2;
        long startTime;

        long[] bruteTimes = new long[nSteps];
        long[] fastTimes = new long[nSteps];
        long[] fastestTimes = new long[nSteps];

        int[] testArrayFalse = {-19, -14, -7, -2, 4, 7, 8, 23, 45};
        int[] testArrayTrue = {-19, -14, -7, -2, 4, 6, 7, 8, 23, 45};

        System.out.println("3sum brute test (true): " + threeSumBrute(testArrayTrue));
        System.out.println("3sum brute test (false): " + threeSumBrute(testArrayFalse));
        System.out.println("3sum fast test (true): " + threeSumFast(testArrayTrue));
        System.out.println("3sum fast test (false): " + threeSumFast(testArrayFalse));
        System.out.println("3sum fastest test (true): " + threeSumFastest(testArrayTrue));
        System.out.println("3sum fastest test (false): " + threeSumFastest(testArrayFalse));
    }

    //3sum problem: are there three numbers in this list that sum to 0?
    //3sum brute force (N^3)
    private static boolean threeSumBrute(int[] array) {
        int len = array.length;

        for (int i = 0; i < len-2; i++)
            for (int k = i+1; k < len-1; k++)
                for (int m = k+1; m < len; m++)
                    if ((array[i] + array[k] + array[m]) == 0)
                        return true;
        return false;
    }

    //3sum faster (N^2 * log(N))
    private static boolean threeSumFast(int[] array) {
        int len = array.length;
        Arrays.sort(array);
        for (int i = 0; i < len-2; i++)
            for (int k = i+1; k < len-1; k++) {
                int result = Arrays.binarySearch(Arrays.copyOfRange(array, k+1, len), -array[i] - array[k]);
                if (result >= 0) //check if key found, result is key index in sub-array (add k + 1)
                    return true;
            }
        return false;
    }

    //3sum fastest (N^2)
    private static boolean threeSumFastest(int[] array) {
        int len = array.length;
        Arrays.sort(array);
        for (int i = 0; i < len - 2; i++) {
            int leftIndex = i + 1;
            int rightIndex = len - 1;
            while (leftIndex < rightIndex) {
                int sum = array[i] + array[leftIndex] + array[rightIndex];
                if (sum == 0)
                    return true;
                else if (sum > 0) //if the sum is > 0, reduce the size of the largest element
                    rightIndex--;
                else             //otherwise increase the size of the smallest element
                    leftIndex++;
            }
        }
        return false;
    }

    //private static int[] generateUniqueArray(int count, int min, int max) {}

    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadCpuTime() : 0L;
    }
}
