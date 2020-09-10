package com.evente.algorithm.service.impl;

import com.evente.tree.model.BinTreeNode;

public class BinSearchTree {
    private BinTreeNode<Integer> root; // 二叉树根节点引用
    private int size;

    public BinSearchTree(BinTreeNode<Integer> root) {
        this.root = root;
        this.size = 1;
    }

    public BinSearchTree(Integer root) {
        BinTreeNode<Integer> ele = new BinTreeNode<>(root);
        this.root = ele;
        this.size = 1;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public void add(BinTreeNode<Integer> node, Integer t){
        if (t < this.root.get()){
            // 插入到左子树中
            if (node.hasLChild()){
                add(node.getLChild(), t);
            } else {
                node.setLChild(new BinTreeNode<>(t));
            }
        } else {
            // 插入到右子树中
            if (node.hasRChild()){
                add(node.getRChild(), t);
            } else {
                node.setRChild(new BinTreeNode<>(t));
            }
        }
        this.size++;
    }

    public void add(Integer t){
        add(this.root, t);
    }


    // 先序遍历二叉搜索树
    public LinkedList<Integer> preOrder(){
        LinkedList<Integer> result = new LinkedList<>();
        preOrderRecursion(root, result);
        return result;
    }

    private void preOrderRecursion(BinTreeNode<Integer> node, LinkedList<Integer> list){
        if (node == null || node.getSize() ==0)
            return; // 递归基础，空树直接返回
        list.shift(node.get()); // 访问根节点
        preOrderRecursion(node.getLChild(), list); // 遍历左子树
        preOrderRecursion(node.getRChild(), list); // 遍历右子树
    }

}
