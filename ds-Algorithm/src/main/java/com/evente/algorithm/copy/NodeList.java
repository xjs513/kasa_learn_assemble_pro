package com.evente.algorithm.copy;

public class NodeList {
    private int element;
    private NodeList nextNode;

    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public NodeList getNextNode() {
        return nextNode;
    }

    public void setNextNode(NodeList nextNode) {
        this.nextNode = nextNode;
    }


    public static void main(String[] argv){
        //实例化链表
        NodeList list = new NodeList();
        //设值
        list.setElement(1);
        //实例化链表
        NodeList next = new NodeList();
        //设值
        next.setElement(2);
        //设值链表
        list.setNextNode(next);
        //空链表
        NodeList cur;
        //设值
        for (int i = 3; i <= 5; i++) {
            cur = new NodeList();
            cur.setElement(i);
            // 下一个链表
            next.setNextNode(cur);
            //获取下一个表
            next = next.getNextNode();
        }

        printNodeList(list);

        System.out.println();

        printNodeList(reverse(list));


    }


    public static NodeList reverse(NodeList nodeList){
        //当前链表或下一项为空时返回
        if(nodeList == null || nodeList.getNextNode() == null){
            return nodeList;
        }
        //获取下一项链表
        NodeList list = reverse(nodeList.getNextNode());
        //下一项链表的下一个指针位置指向当前链表
        nodeList.getNextNode().setNextNode(nodeList);
        //当前链表的下一项指针位置 设置为空 为终止条件返回当前链表
        nodeList.setNextNode(null);
        return list;
    }

    public static void printNodeList(NodeList nodeList){
        if(nodeList != null){
            System.out.print(nodeList.getElement()+",");
            printNodeList(nodeList.getNextNode());
        }
    }
}
