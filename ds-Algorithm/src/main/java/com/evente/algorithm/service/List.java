package com.evente.algorithm.service;

public interface List<E> {
    // 返回线性表的大小，即数据元素的个数
    int getSize();

    // 判断线性表是否为空
    boolean isEmpty();

    // 判断是否包含数据元素e
    boolean contains(E e);

    // 返回元素e在线性表中的序号, 只返回第一个
    int indexOf(E e);

    // 将元素e插入到线性表的i号位置
    void insert(int i, E e);

    // 将元素e插入到元素obj之前
    boolean insertBefore(E ojb, E e);

    // 将元素e插入到元素obj之后
    boolean insertAfter(E ojb, E e);

    // 删除线性表中序号为i的元素并返回该元素
    E remove(int i) throws IndexOutOfBoundsException;

    // 删除第一个元素e
    boolean remove(E e);

    // 替换i位置的元素并返回原数据元素
    E replace(int i, E e) throws IndexOutOfBoundsException;


    // 返回线性表中i处元素
    E get(int i) throws IndexOutOfBoundsException;


    // 打印线性表
    void  print();

    void shift(E e);

    void reverse();

}
