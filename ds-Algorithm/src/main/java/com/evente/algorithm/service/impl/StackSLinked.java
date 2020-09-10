package com.evente.algorithm.service.impl;

import com.evente.algorithm.exception.StackEmptyException;
import com.evente.algorithm.service.Stack;
import com.evente.tree.model.SLNode;

public class StackSLinked<T> implements Stack<T> {

    private SLNode<T> top;  // 链表首节点引用 同时标志栈顶
    private int size;       // 栈的大小

    public StackSLinked() {
        top = null;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void push(T t) {
        SLNode<T> q = new SLNode<T>(t, top);
        top = q;
        size++;
    }

    @Override
    public T pop() throws StackEmptyException {
        if (isEmpty())
            throw new StackEmptyException("error: empty stack!!");
        T t = top.get();
        top = top.getNext();
        size--;
        return t;
    }

    @Override
    public T peek() throws StackEmptyException {
        if (isEmpty())
            throw new StackEmptyException("error: empty stack!!");
        return top.get();
    }
}
