package com.evente.algorithm.test;

import com.evente.tree.model.SLNode;

public class SLNodeTest {
    public static void main(String[] args) {
        SLNode<String> head = new SLNode<>("1", null);


        SLNode<String> node2 = new SLNode<>("2", null);

        SLNode<String> node3 = new SLNode<>("3", null);

        SLNode<String> node4 = new SLNode<>("4", null);

        SLNode<String> node5 = new SLNode<>("5", null);

        SLNode<String> node6 = new SLNode<>("6", null);

        SLNode<String> node7 = new SLNode<>("7", null);

        SLNode<String> node8 = new SLNode<>("8", null);

        SLNode<String> node9 = new SLNode<>("9", null);

        SLNode<String> node10 = new SLNode<>("10", null);

        head.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node5);
        node5.setNext(node6);
        node6.setNext(node7);
        node7.setNext(node8);
        node8.setNext(node9);

        // 便利链表
        SLNode p = head;
        while (p != null){
            System.out.println("p.get() = " + p.get());
            p = p.getNext();
        }

    }
}
