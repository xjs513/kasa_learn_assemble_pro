package com.evente.algorithm.test;

import com.evente.algorithm.service.impl.BinSearchTree;
import com.evente.algorithm.service.impl.LinkedList;
import com.evente.tree.model.BinTreeNode;

public class BinTreeDemo {

    public static void main(String[] args) {
        BinSearchTree bTree = new BinSearchTree(10);
        bTree.add(6);
        bTree.add(90);
        bTree.add(16);
        bTree.add(3);

        LinkedList<Integer> list = bTree.preOrder();
        list.reverse();
        list.print();

    }

}
