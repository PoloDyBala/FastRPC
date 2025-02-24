package com.yuan.Client.circuitBreaker;

import java.util.concurrent.atomic.AtomicInteger;

public class CircuitBreaker {
    private CircuitBreakerState state = CircuitBreakerState.CLOSED;
    private AtomicInteger failureCount = new AtomicInteger(0);
    private AtomicInteger successCount = new AtomicInteger(0);
    private AtomicInteger requestCount = new AtomicInteger(0);
    //失败次数阈值
    private final int failureThreshold;
    //半开启-》关闭状态的成功次数比例
    private final double halfOpenSuccessRate;
    //恢复时间
    private final long retryTimePeriod;
    //上一次失败时间
    private long lastFailureTime = 0;

    public CircuitBreaker(int failureThreshold, double halfOpenSuccessRate,long retryTimePeriod) {
        this.failureThreshold = failureThreshold;
        this.halfOpenSuccessRate = halfOpenSuccessRate;
        this.retryTimePeriod = retryTimePeriod;
    }

    public synchronized boolean allowRequest() {
        long currentTime = System.currentTimeMillis();
        System.out.println("熔断swtich之前!!!!!!!+failureNum=="+failureCount);
        switch (state) {
            case OPEN:
                if (currentTime - lastFailureTime > retryTimePeriod) {
                    state = CircuitBreakerState.HALF_OPEN;
                    resetCounts();
                    return true;
                }
                System.out.println("熔断生效!!!!!!!");
                return false;
            case HALF_OPEN:
                requestCount.incrementAndGet();
                return true;
            case CLOSED:
            default:
                return true;
        }
    }

    public synchronized void recordSuccess() {
        if(state == CircuitBreakerState.HALF_OPEN) {
            successCount.incrementAndGet();
            if(successCount.get() >= halfOpenSuccessRate * requestCount.get()){
                state = CircuitBreakerState.CLOSED;
                resetCounts();
            } else {
                resetCounts();
            }
        }


    }


    public synchronized void recordFailure() {
        failureCount.incrementAndGet();  // 增加失败计数
        System.out.println("记录失败!!!!!!!失败次数"+failureCount);  // 打印失败次数
        lastFailureTime = System.currentTimeMillis();  // 更新最后失败时间
        if (state == CircuitBreakerState.HALF_OPEN) {  // 如果是半开启状态
            state = CircuitBreakerState.OPEN;  // 转为开启状态
            lastFailureTime = System.currentTimeMillis();  // 更新最后失败时间
        } else if (failureCount.get() >= failureThreshold) {  // 如果失败次数超过阈值
            state = CircuitBreakerState.OPEN;  // 转为开启状态
        }
    }


    private void resetCounts() {
        failureCount.set(0);
        successCount.set(0);
        requestCount.set(0);
    }

    public CircuitBreakerState getState() {
        return state;  // 返回当前状态
    }



}

enum CircuitBreakerState {
    //关闭，开启，半开启
    CLOSED, OPEN, HALF_OPEN
}
