package com.company;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        int n;
        int nStart = 512; //min 3
        int nSteps = 10;
        int nScale = 2;
        long startTime;
        int maxTrials = 10;
        int trialCount;
        long maxTime = 3_000_000;

        long[] bruteTimes = new long[nSteps];
        long[] fastTimes = new long[nSteps];
        long[] fastestTimes = new long[nSteps];

        int[] array;

        //brute force loop
        n = nStart;
        for (int i = 0; i < nSteps; i++) {
            System.out.println("Brute n=" + n + " step=" + i);
            bruteTimes[i] = 0;
            for (trialCount = 0; (trialCount < maxTrials) && (bruteTimes[i] < maxTime); trialCount++) {
                array = generateUniqueArray(n, -n * 2, n * 2);
                startTime = System.nanoTime();
                threeSumBrute(array);
                bruteTimes[i] += System.nanoTime() - startTime;
            }
            n *= nScale;
            bruteTimes[i] /= trialCount;
        }


        //fast loop
        n = nStart;
        for (int i = 0; i < nSteps; i++) {
            System.out.println("Fast n=" + n + " step=" + i);
            fastTimes[i] = 0;
            for (trialCount = 0; (trialCount < maxTrials) && (fastTimes[i] < maxTime); trialCount++) {
                array = generateUniqueArray(n, -n * 2, n * 2);
                startTime = System.nanoTime();
                threeSumFast(array);
                fastTimes[i] += System.nanoTime() - startTime;
            }
            n *= nScale;
            fastTimes[i] /= trialCount;
        }

        //fastest loop
        n = nStart;
        for (int i = 0; i < nSteps; i++) {
            System.out.println("Fastest n=" + n + " step=" + i);
            fastestTimes[i] = 0;
            for (trialCount = 0; (trialCount < maxTrials) && (fastestTimes[i] < maxTime); trialCount++) {
                array = generateUniqueArray(n, -n * 2, n * 2);
                startTime = System.nanoTime();
                threeSumFastest(array);
                fastestTimes[i] += System.nanoTime() - startTime;
            }
            n *= nScale;
            fastestTimes[i] /= trialCount;
        }

        String headerFormatString = "%6s|%13s%12s%15s|%13s%12s%15s|%13s%12s%15s|\n";
        String tableFormatString =  "%6d|%13d%12.3f%15.3f|%13d%12.3f%15.3f|%13d%12.3f%15.3f|\n";
        System.out.println("3sum results with nStart=" + nStart + ", nSteps=" + nSteps + ", maxTrials=" + maxTrials);
        System.out.format(headerFormatString, "", "Brute 3sum", "", "", "Fast 3sum", "", "", "Fastest 3sum", "", "");
        System.out.format(headerFormatString, "N",
                "Time", nScale + "x Ratio", "Ex. " + nScale+"x Ratio",
                "Time", nScale + "x Ratio", "Ex. " + nScale+"x Ratio",
                "Time", nScale + "x Ratio", "Ex. " + nScale+"x Ratio");
        n = nStart;
        System.out.format(tableFormatString, n, bruteTimes[0], -1.0, -1.0, fastTimes[0], -1.0, -1.0, fastestTimes[0], -1.0, -1.0);
        n *= nScale;
        for (int i = 1; i < nSteps; i++) {
            System.out.format(tableFormatString, n,
                    bruteTimes[i], (double) bruteTimes[i] / bruteTimes[i-1], Math.pow(n, 3) / Math.pow(n/nScale, 3),
                    fastTimes[i], (double) fastTimes[i] / fastTimes[i-1], (n*n*Math.log(n)) / (Math.pow(n/nScale, 2)*Math.log(n/nScale)),
                    fastestTimes[i], (double) fastestTimes[i] / fastestTimes[i-1], Math.pow(n, 2) / Math.pow(n/nScale, 2));
            n *= nScale;
        }
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

    private static int[] generateUniqueArray(int count, int min, int max) {
        Set<Integer> set = new HashSet<>();
        Random r = new Random();
        while (set.size() < count)
            set.add(r.nextInt(max - min + 1) + min);
        return set.stream().mapToInt(i -> i).toArray();
    }
}
