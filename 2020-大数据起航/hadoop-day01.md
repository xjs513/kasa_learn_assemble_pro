# 开课

## 1.  什么是大数据？

大数据就是一门针对海量 数据处理的编程技术。

https://www.bilibili.com/video/BV1gt411q7qw  多易教育 涛哥

核心技术

- 分布式存储
- 分布式计算

## 2. 大数据用来干嘛？

比如电商：分析用户浏览、购物等行为、分析规律、挖掘价值

比如金融：征信分析、运营分析

比如头条：个性化内容推送

只要有数据分析需求的场景，就可以用大数据技术来解决！

## 3. 核心技术组件

Hadoop  技术生态

Spark      技术生态    现在用的最多的

~~Storm~~     现在很少

Flink        技术生态    目前是最火爆的

## 4. 和学习其他学科的区别

AI 和 ML 不是编程技术，而是一种上层应用

Python 机器学习  大数据   千万不要随便搞机器学习！！

## 5.未来方向

大数据开发岗位方向

- 平台架构师/业务架构师
- 补数学-》数据挖掘算法-》机器学习算法-》深度学习-》AI

# 1. Hadoop 生态简介

Hadoop 是一套大数据技术组件，包含以下三个组件：

1. HDFS   				分布式文件管理系统
2. MapReduce		分布式计算
3. Yarn                     资源的调度

hadoop 也有大量外围组件，解决不同的问题：

1. Hive : 是一个基于 HDFS和MR的SQL工具
2. Hbase:是一个基于HDFS的分布式nosql数据库
3. Flume: 分布式日志采集系统
4. Sqoop: 是一个数据迁移工具

## 1.1 HDFS

### 1.1.1. 概念

分布式文件管理系统

- 文件读取服务
- 文件写入服务
- 查看目录信息服务

1. 核心工作机制

   服务角色：

   - NN namenode  

     1. 元数据管理：

     - HDFS系统的目录树

     - 文件的具体路径 
     - 块信息
     - 数据块的具体存储位置

     2. 为客户端提供查询服务

   - DN datanode

     1. 负责管理本地节点文件

     2. 帮客户端读取文件的数据块

   - SNN secondary namenode

      FSimage Editlog 两个概念

      SNN 的作用和工作原理

   - shell 客户端

   文件在 HDFS 中分块存储 

   每个数据块有N个副本

   每个块都有一个唯一的块ID
### 1.1.2. 读取流程 见笔记

block packet trunk

### 1.1.3. 集群安装  见笔记

### 1.1.4. 相关命令

```java
hadoop-daemon.sh start namenode
jps
ps -ef | grep java
netstat -nltp
```

### 1.1.5. HDFS 文件分块

```
[root@dev201 subdir237]# pwd
/home/dfs/dn/current/BP-867597199-192.168.1.201-1554283648258/current/finalized/subdir11/subdir237
[root@dev201 subdir237]# ll -lh
总用量 3.4G
-rw-r--r-- 1 hdfs hdfs 128M 6月  22 17:19 blk_1074523392
-rw-r--r-- 1 hdfs hdfs 1.1M 6月  22 17:19 blk_1074523392_782583.meta
-rw-r--r-- 1 hdfs hdfs 128M 6月  22 17:19 blk_1074523393
-rw-r--r-- 1 hdfs hdfs 1.1M 6月  22 17:19 blk_1074523393_782584.meta
```

- .meta文件是描述数据块的元数据 其中含校验和

### 1.1.6. HDFS 命令行操作  

>    &nbsp;&nbsp;修改 core-site.xml 中的设置只对客户端起作用，不用重启集群
>
>    &nbsp;&nbsp;客户端参数用来指定需要访问的目标文件系统
>
>    &nbsp;&nbsp;默认是：file:/// 表示本地文件系统   ==P9==

```sql
<property>
	<name>fs.defaultFS</name>
	<value>hdfs://dev201:8128</value>
</property>
```

#### 1.1.6.1 查看目录：

```java
hdfs dfs -ls /
hdfs dfs -ls -R /apps  # 递归显示
或者用：    
hadoop fs -ls -R /dim
查看文件大小：
hdfs dfs -du -h /
hdfs dfs -df -h / 
设置副本数：
hdfs fs -setrep 4 /zzz
```

#### 1.1.6.2 创建/删除目录：

```
hdfs dfs -mkdir /aaa
hdfs dfs -mkdir -p /ddd/cc
hdfs dfs -rm -r /xx
```

#### 1.1.6.3 上传/下载文件

```
hdfs dfs -put /local/path/to/file /ddd/
hdfs dfs -get /abc/test.txt
```

#### 1.1.6.4 移动/复制文件

```
hdfs dfs -mv /source/a.txt /dist/
hdfs dfs -cp /a.txt /dist/b.txt
```

#### 1.1.6.5 读取文件内容

```
hdfs dfs -cat /a.txt
hdfs dfs -text /a.txt
```

#### 1.1.6.6 追加内容到文件

```
hdfs dfs -appendToFile a.txt /xxx/zzz.txt
[root@dev201 ~]# hdfs dfs
```

### 1.1.7. HDFS 客户端API



##  MapReduce

![image-20200910102450697](C:\Users\hds\AppData\Roaming\Typora\typora-user-images\image-20200910102450697.png)