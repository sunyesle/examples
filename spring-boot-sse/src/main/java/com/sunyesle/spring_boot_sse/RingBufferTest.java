package com.sunyesle.spring_boot_sse;

public class RingBufferTest {
    public static void main(String[] args) {
        RingBuffer buffer = new RingBuffer(5);
        for (int i = 0; i < 5; i++) {
            buffer.offer(i);
        }
        buffer.print();

        for (int i = 0; i < 3; i++) {
            buffer.poll();
        }
        buffer.print();

        for (int i = 0; i < 2; i++) {
            buffer.offer(5 + i);
        }
        buffer.print();
    }
}
