package com.company;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Main {

    public static void main(String[] args) {
        // write your code here
    }

    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadCpuTime() : 0L;
    }
}
