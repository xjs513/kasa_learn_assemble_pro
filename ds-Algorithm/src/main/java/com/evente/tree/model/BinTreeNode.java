package com.evente.tree.model;

public class BinTreeNode<T> implements Node<T> {

    private T data; // 数据域
    private BinTreeNode<T> parent; // 父节点
    private BinTreeNode<T> lChild; // 左孩子
    private BinTreeNode<T> rChild; // 右孩子
    private int height;            // 以该节点为跟的树高
    private int size;              // 该节点子孙数，包括自己

    public BinTreeNode(T data){
        this.data = data;
        height = 0;
        size = 1;
        parent = lChild = rChild = null;
    }

    /** 接口方法 **/
    @Override
    public T get() {
        return data;
    }

    @Override
    public void set(T t) {
        this.data = t;
    }

    /** 辅助方法，判断当前节点位置情况 **/

    // 判断是否有双亲节点
    public boolean hasParent(){
        return parent != null;
    }

    // 判断是否有左孩子
    public boolean hasLChild(){
        return lChild != null;
    }

    // 判断是否有右孩子
    public boolean hasRChild(){
        return rChild != null;
    }

    // 判断是否为叶子节点
    public boolean isLeaf(){
        return !hasLChild() && !hasRChild();
    }

    // 判断是否为某节点的左孩子
    public boolean isLChild(){
        return hasParent() && this == parent.lChild;
    }

    // 判断是否为某节点的右孩子
    public boolean isRChild(){
        return hasParent() && this == parent.rChild;
    }

    /** 与 height 相关的方法 **/

    // 取节点的高度，即以该节点为跟的树高
    public int getHeight(){
        return this.height;
    }

    // 更新当前节点及其祖先的高度
    public void updateHeight(){
        int newH = 0; // 新高度初始化为0， 高度等于左右子树高度加1中的大着  <=  >=
        if (hasLChild())
            newH = Math.max(newH, this.lChild.getHeight() + 1);
        if (hasRChild())
            newH = Math.max(newH, this.rChild.getHeight() + 1);
        if (newH == this.height) // 如果高度没有变化，则直接返回
            return;
        this.height = newH;
        if (hasParent())
            this.parent.updateHeight();
    }


    /**  与 size 相关的方法  **/

    // 取以该节点为根的树的节点数
    public int getSize(){
        return this.size;
    }

    // 更新当前节点及其祖先的子孙数
    public void updateSize(){
        size = 1; // 初始化为1， 节点本身
        if (hasLChild())
            size += this.lChild.getSize();
        if (hasRChild())
            size += this.rChild.getSize();
        if (hasParent())
            this.parent.updateHeight();
    }


    /**  与 parent 相关的方法  **/

    // 取双亲节点
    public BinTreeNode<T> getParent(){
        return this.parent;
    }

    // 断开与双亲节点的关系
    public void  server(){
        if (!hasParent())
            return;
        if (isLChild())
            parent.lChild = null;
        else
            parent.rChild = null;
        parent.updateHeight();
        parent.updateSize();
        parent = null;
    }

    /**  与 lChild 相关方法  **/

    // 取左孩子
    public BinTreeNode<T> getLChild(){
        return this.lChild;
    }

    // 设置当前节点的左孩子，返回原左孩子
    public BinTreeNode<T> setLChild(BinTreeNode<T> lc){
        BinTreeNode<T> oldLC = this.getLChild();
        if (hasLChild())
            lChild.server(); // 断开当前左孩子与节点的关系
        if (lc != null){
            lc.server();
            this.lChild = lc;
            lc.parent = this;
            this.updateHeight();
            this.updateSize();
        }
        return oldLC;
    }

    /**  与 rChild 相关方法  **/

    // 取右孩子
    public BinTreeNode<T> getRChild(){
        return this.rChild;
    }

    // 设置当前节点的右孩子，返回原右孩子
    public BinTreeNode<T> setRChild(BinTreeNode<T> rc){
        BinTreeNode<T> oldRC = this.getRChild();
        if (hasRChild())
            rChild.server(); // 断开当前右孩子与节点的关系
        if (rc != null){
            rc.server();
            this.rChild = rc;
            rc.parent = this;
            this.updateHeight();
            this.updateSize();
        }
        return oldRC;
    }

}
