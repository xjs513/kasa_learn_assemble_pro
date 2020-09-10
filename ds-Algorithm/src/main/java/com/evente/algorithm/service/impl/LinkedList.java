package com.evente.algorithm.service.impl;

import com.evente.algorithm.copy.NodeList;
import com.evente.algorithm.service.List;
import com.evente.algorithm.service.Strategy;
import com.evente.tree.model.SLNode;


public class LinkedList<T> implements List<T> {

    private int size;
    private SLNode<T> head;
    private Strategy<T> strategy;

    public SLNode<T> getHead() {
        return head;
    }

    public LinkedList() {
        this.size = 0;
        head = new SLNode<>(null, null);
        this.strategy = new Strategy<T>() {
            @Override
            public boolean equals(T e1, T e2) {
                return e1.equals(e2);
            }

            @Override
            public int compare(T e1, T e2) {
                return 0;
            }
        };
    }


    @Override
    public void shift(T t){
        SLNode<T> newNode = new SLNode<>(t, null);
        newNode.setNext(head.getNext());
        head.setNext(newNode);
        this.size++;
    }

    @Override

    public void print(){
        printNodeList(head.getNext());
        System.out.println();
    }

    void printNodeList(SLNode<T> node){
        if(node != null){
            System.out.print(node.get()+",");
            printNodeList(node.getNext());
        }
    }

//    public void print() {
//        System.out.println("-----------------------------");
//        SLNode<T> p = head;
//        int index = 0;
//        if (isEmpty())
//            return;
//        do {
//            p = p.getNext();
//            System.out.println("{index: " +  (index++) + ", content: " + p.get() + "]");
//        } while (p.getNext() != null);
//    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(T s) {
        SLNode<T> p = head;
        do {
            p = p.getNext();
            if (null == p) return false;
            else if (strategy.equals(s, p.get())) return true;
        } while (p.getNext() != null);
        return false;
    }

    @Override
    public int indexOf(T s) {
        int index = -1;
        SLNode<T> p = head;
        do {
            p = p.getNext();
            index++;
            if (null == p) return -1;
            else if (strategy.equals(s, p.get())) return index;
        } while (p.getNext() != null);
        return -1;
    }

    @Override
    public void insert(int i, T s) {
        SLNode<T> newNode = new SLNode<>(s, null);
        if (i<0 || i>size)
            throw new ArrayIndexOutOfBoundsException(String.format("下标越界异常[%d,%d],i = %d", 0, size, i));
        else if (i == 0){// 头部插入
            shift(s);
        } else if (i == size){// 尾部插入
            SLNode<T> p = head;
            // 移动到尾部
            do {
                p = p.getNext();
            } while (p.getNext() != null);
            p.setNext(newNode);
        } else {
            int index = -1;
            SLNode<T> p = head;
            // 需要定位到i元素的上一个元素
            do {
                p = p.getNext();
                index++;
                if (index == (i-1)) break;
            } while ( p.getNext() != null);
            newNode.setNext(p.getNext());
            p.setNext(newNode);
        }
        this.size++;
    }

    @Override
    public boolean insertAfter(T ojb, T s) {
        int index = indexOf(ojb);
        if (index == -1)
            return false;
        else {
            insert(index+1>size?size:index+1, s);
            return true;
        }
    }

    @Override
    public boolean insertBefore(T ojb, T s) {
        int index = indexOf(ojb);
        if (index == -1)
            return false;
        else {
            insert(index, s);
            return true;
        }
    }

    @Override
    public T remove(int i) throws IndexOutOfBoundsException {
        if (i<0 || i>size-1)
            throw new ArrayIndexOutOfBoundsException(String.format("下标越界异常[%d,%d],i = %d", 0, size-1, i));
        else if (i == 0){// 移除头部元素
            SLNode<T> node = head.getNext();
            T result = node.get();
            head.setNext(node.getNext());
            this.size--;
            return result;
        } else if (i == size-1){// 移除尾部元素
            int index = -1;
            SLNode<T> p = head;
            // 要定位到尾部元素的上一个元素
            do {
                p = p.getNext();
                index++;
                if (index == (this.size-2)) break;
            } while ( p.getNext() != null);
            T result = p.getNext().get();
            p.setNext(null);
            return result;
        } else {
            int index = -1;
            SLNode<T> p = head;
            // 需要定位到i元素的上一个元素
            do {
                p = p.getNext();
                index++;
                if (index == (i-1)) break;
            } while ( p.getNext() != null);
            T result = p.getNext().get();
            p.setNext(p.getNext().getNext());
            this.size--;
            return result;
        }
    }

    @Override
    public boolean remove(T s) {
        int index = indexOf(s);
        try {
            remove(index);
            this.size--;
            return true;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public T replace(int i, T s) throws IndexOutOfBoundsException {
        if (i<0 || i>size-1)
            throw new ArrayIndexOutOfBoundsException(String.format("下标越界异常[%d,%d],i = %d", 0, size-1, i));
        else {
            int index = -1;
            SLNode<T> p = head;
            // 需要定位到i元素
            do {
                p = p.getNext();
                index++;
                if (index == i) break;
            } while ( p.getNext() != null);
            T result = p.get();
            p.set(s);
            return result;
        }
    }

    @Override
    public T get(int i) throws IndexOutOfBoundsException {
        if (i<0 || i>size-1)
            throw new ArrayIndexOutOfBoundsException(String.format("下标越界异常[%d,%d],i = %d", 0, size-1, i));
        else {
            int index = -1;
            SLNode<T> p = head;
            // 需要定位到i元素
            do {
                p = p.getNext();
                index++;
                if (index == i) break;
            } while ( p.getNext() != null);
            return p.get();
        }
    }

//    public void reverse(){
//        if (this.size<2)
//            return;
//        // 定位到第一个元素，也是临时链表的首结点
//        SLNode<T> newTempFirstNode = head.getNext();
//        // 定位到第二个元素
//        SLNode<T> tempFirstNode = head.getNext().getNext();
//        // 第一个元素脱离链表
//        newTempFirstNode.setNext(null);
//        // head 指向第二个元素
//        head.setNext(tempFirstNode);
//        do {
//            // 取出第一个节点
//            SLNode<T> next = head.getNext();
//            // 第一个节点脱离链表
//            head.setNext(next.getNext());
//            // 新脱离链表采用头插法插入到前面脱离链表元素组成的新链表中
//            next.setNext(newTempFirstNode);
//            // 调整临时头结点指向
//            newTempFirstNode = next;
//        } while (head.getNext() != null);
//        // 循环完毕，调整头节点指向
//        head.setNext(newTempFirstNode);
//    }


    public void reverse(){
        SLNode<T> node = reverse2(head.getNext());
        head.setNext(node);
    }

    // 递归法反转链表
    public SLNode<T>  reverse2(SLNode<T> nodeList){
        //当前链表或下一项为空时返回
        if(nodeList == null || nodeList.getNext() == null){
            return nodeList;
        }
        //获取下一项链表
        SLNode<T>  list = reverse2(nodeList.getNext());
        //下一项链表的下一个指针位置指向当前链表
        nodeList.getNext().setNext(nodeList);
        //当前链表的下一项指针位置 设置为空 为终止条件返回当前链表
        nodeList.setNext(null);
        return list;
    }

}
