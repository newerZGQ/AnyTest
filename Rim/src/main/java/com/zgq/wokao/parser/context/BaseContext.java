package com.zgq.wokao.parser.context;

import com.zgq.wokao.parser.context.item.ParserItem;

import java.util.LinkedList;
import java.util.Queue;

public abstract class BaseContext implements Context {
    private Queue<ParserItem> itemQueue;
    private int queueCapacity = 5;

    public BaseContext() {
    }

    public BaseContext(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    @Override
    public void init(int length) {
        this.queueCapacity = length;
        itemQueue = new LinkedList<>();
    }

    @Override
    public void inContext(ParserItem item) {
        adjustQueue();
        itemQueue.offer(item);
    }

    @Override
    public void deContext() {
        itemQueue.poll();
    }

    @Override
    public ParserItem tail(int distance) {
        return (ParserItem) ((LinkedList) itemQueue).get(itemQueue.size() - 1 - distance);
    }

    private void adjustQueue() {
        if (itemQueue.size() <= queueCapacity - 1) {
            return;
        }
        while (itemQueue.size() > queueCapacity - 1) {
            itemQueue.poll();
        }
    }
}
