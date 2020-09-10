package com.evente.tree.model;

public class SLNode<T> implements Node<T> {

    private T element;
    private SLNode<T> next;

    public SLNode() {
        this(null, null);
    }

    public SLNode(T element, SLNode<T> next) {
        this.element = element;
        this.next = next;
    }


    public SLNode<T> getNext() {
        return next;
    }

    public void setNext(SLNode<T> next) {
        this.next = next;
    }

    @Override
    public T get() {
        return this.element;
    }

    @Override
    public void set(T s) {
        this.element = s;
    }
}
