package com.sunyesle.spring_boot_sse;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class RingBuffer {

    private Object[] elements = null;

    private int capacity = 0;
    private int writePos = 0;
    private int readPos = 0;
    private boolean flipped = false;

    RingBuffer(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[this.capacity];
    }

    public boolean offer(Object data) {
        if (isFull()) {
            return false; // Buffer is full
        }

        elements[writePos] = data;

        writePos++;
        if (writePos >= capacity) {
            writePos = 0;
            flipped = true;
        }

        return true;
    }

    public Object poll() {
        if (isEmpty()) {
            return null; // Buffer is empty
        }

        Object data = elements[readPos];

        readPos++;
        if (readPos >= capacity) {
            readPos = 0;
            flipped = false;
        }

        return data;
    }

    public boolean isEmpty() {
        return !flipped && writePos == readPos;
    }

    public boolean isFull() {
        return flipped && writePos == readPos;
    }

    public int size() {
        if (!flipped) {
            return writePos - readPos;
        } else {
            return capacity - (readPos - writePos);
        }
    }

    public void print() {
        for (Object i : elements) System.out.print(i + " ");
        System.out.println();
    }
}
