package com.sunyesle.spring_boot_sse;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

public class ByteBufferTest {

    @Test
    void create(){/*
        ByteBuffer buffer = ByteBuffer.allocate(10);
        ByteBuffer buffer = ByteBuffer.allocateDirect(10);

        byte[] bytes = new byte[10];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);


        ByteBuffer buffer = ByteBuffer.wrap(bytes, 0, bytes.length);
        */

        for (int i = 0; i < 10; i++) {
            allocate();
        }

        for (int i = 0; i < 10; i++) {
            allocateDirect();
        }
    }

    @Test
    void allocate() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000000; i++) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
        }

        long end = System.currentTimeMillis();
        System.out.println("실행 시간(ms) : " + (end - start)); // 실행 시간(ms) : 42
    }

    @Test
    void allocateDirect() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000000; i++) {
            ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        }

        long end = System.currentTimeMillis();
        System.out.println("실행 시간(ms) : " + (end - start)); // 실행 시간(ms) : 782
    }
}
