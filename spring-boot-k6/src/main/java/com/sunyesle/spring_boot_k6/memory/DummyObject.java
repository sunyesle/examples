package com.sunyesle.spring_boot_k6.memory;

public class DummyObject {
    private byte[] heavyData = new byte[1024 * 1024]; // 1MB
    private long timestamp = System.currentTimeMillis();
}
