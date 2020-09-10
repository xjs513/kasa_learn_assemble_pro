package com.evente.algorithm.service;

public interface Strategy<E> {

    boolean equals(E e1, E e2);

    int compare(E e1, E e2);
}
