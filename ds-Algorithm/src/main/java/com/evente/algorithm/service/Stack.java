package com.evente.algorithm.service;

import com.evente.algorithm.exception.StackEmptyException;

public interface Stack<T> {
    // 返回堆栈的大小
    int getSize();
    // 判断堆栈是否为空
    boolean isEmpty();
    // 数据元素 e 入栈
    void push(T t);
    // 栈顶元素出栈
    T pop() throws StackEmptyException;
    // 取栈顶元素但不出栈
    T peek() throws StackEmptyException;
}
