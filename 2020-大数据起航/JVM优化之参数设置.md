## 常用参数

Xmn、Xms、Xmx、Xss都是JVM对内存的配置参数，我们可以根据不同需要区修改这些参数，以达到运行程序的最好效果。

**-Xms** 堆内存的最小大小，默认为物理内存的1/64

**-Xmx** 堆内存的最大大小，默认为物理内存的1/4

**-Xmn** 堆内新生代的大小。通过这个值也可以得到老生代的大小：-Xmx减去-Xmn

**-Xss**   设置每个线程可使用的内存大小，即栈的大小。

在相同物理内存下，减小这个值能生成更多的线程，当然操作系统对一个进程内的线程数还是有限制的，不能无限生成。线程栈的大小是个双刃剑，如果设置过小，可能会出现**栈溢出**，特别是在该线程内有递归、大的循环时出现溢出的可能性更大，如果该值设置过大，就有影响到创建栈的数量，如果是多线程的应用，就会出现内存溢出的错误。

------

除了这些配置，JVM还有非常多的配置，常用的如下：

1. 堆设置

   - **-Xms**: 初始堆大小，也就是最小值

   - **-Xmx**: 最大堆大小

   - **-Xmn**: 新生代大小

   - **-XX:NewRatio**: 

     设置新生代和老年代的比值。如：默认为2，表示年轻代与老年代比值为1：2

   - **-XX:SurvivorRatio**: 

     新生代中Eden区与两个Survivor区的比值。

     注意Survivor区有两个。

     如：默认为8，表示Eden：Survivor=8：2，一个Survivor区占整个新生代的1/10

   - **-XX:MaxTenuringThreshold**:

     设置转入老年代的存活次数。如果是0，则直接跳过新生代进入老年代

   - **-XX:PermSize**、**-XX:MaxPermSize**:

     分别设置永久代最小大小与最大大小（Java8以前）

   - **-XX:MetaspaceSize**、**-XX:MaxMetaspaceSize**:

     分别设置元空间最小大小与最大大小（Java8以后）

   - **-XX:Newsize** : 设置Yong Generation的初始值大小

   - **-XX:Maxnewsize**：设置Yong Generation的最大值大小

   - 

     

2. 收集器设置

   - **-XX:+UseSerialGC**:设置串行收集器
   - **-XX:+UseParallelGC**:设置并行收集器
   - **-XX:+UseParalledlOldGC**:设置并行老年代收集器
   - **-XX:+UseConcMarkSweepGC**:设置并发收集器

3. 垃圾回收统计信息

   - **-XX:+PrintGC**
   - **-XX:+PrintGCDetails**
   - **-XX:+PrintGCTimeStamps**
   - **-Xloggc:filename**

4. 并行收集器设置

   - **-XX:ParallelGCThreads=n**:设置并行收集器收集时使用的CPU数。并行收集线程数。
   - **-XX:MaxGCPauseMillis=n**:设置并行收集最大暂停时间
   - **-XX:GCTimeRatio=n**:设置垃圾回收时间占程序运行时间的百分比。公式为1/(1+n)

5. 并发收集器设置

   - **-XX:+CMSIncrementalMode**:

     设置为增量模式。适用于单CPU情况。

   - **-XX:ParallelGCThreads=n**:

     设置并发收集器新生代收集方式为并行收集时，使用的CPU数。并行收集线程数。

##  调优实例

###  新生代 Survivor 空间不足

**新生代调优规律**

增大新生代空间。 Minor GC 频率降低， Minor GC 时间上升。 降低新生代空间， Minor GC 频率上升， Minor GC 时间下降

**新生代典型问题**

先看一段 GC 日志：新生代使用 ParNew。 老年代使用 CMS 

```
{Heap before GC invocations=0 (full 0):
 par new generation   total 943744K, used 838912K [0x0000000757000000, 0x0000000797000000, 0x0000000797000000)
  eden space 838912K, 100% used [0x0000000757000000, 0x000000078a340000, 0x000000078a340000)
  from space 104832K,   0% used [0x000000078a340000, 0x000000078a340000, 0x00000007909a0000)
  to   space 104832K,   0% used [0x00000007909a0000, 0x00000007909a0000, 0x0000000797000000)
 concurrent mark-sweep generation total 1560576K, used 0K [0x0000000797000000, 0x00000007f6400000, 0x00000007f6400000)
 concurrent-mark-sweep perm gen total 159744K, used 38069K [0x00000007f6400000, 0x0000000800000000, 0x0000000800000000)
2016-01-19T14:15:34.532+0800: 13.812: [GC2016-02-19T14:15:34.532+0800: 13.812: [ParNew
Desired survivor size 53673984 bytes, new threshold 1 (max 6)
- age   1:   55521392 bytes,   55521392 total
: 838912K->54474K(943744K), 0.0914620 secs] 838912K->54474K(2504320K), 0.0916240 secs] [Times: user=0.67 sys=0.06, real=0.09 secs]
Heap after GC invocations=1 (full 0):
 par new generation   total 943744K, used 54474K [0x0000000757000000, 0x0000000797000000, 0x0000000797000000)
  eden space 838912K,   0% used [0x0000000757000000, 0x0000000757000000, 0x000000078a340000)
  from space 104832K,  51% used [0x00000007909a0000, 0x0000000793ed2ae0, 0x0000000797000000)
  to   space 104832K,   0% used [0x000000078a340000, 0x000000078a340000, 0x00000007909a0000)
 concurrent mark-sweep generation total 1560576K, used 0K [0x0000000797000000, 0x00000007f6400000, 0x00000007f6400000)
 concurrent-mark-sweep perm gen total 159744K, used 38069K [0x00000007f6400000, 0x0000000800000000, 0x0000000800000000)
}
{Heap before GC invocations=1 (full 0):
 par new generation   total 943744K, used 893386K [0x0000000757000000, 0x0000000797000000, 0x0000000797000000)
  eden space 838912K, 100% used [0x0000000757000000, 0x000000078a340000, 0x000000078a340000)
  from space 104832K,  51% used [0x00000007909a0000, 0x0000000793ed2ae0, 0x0000000797000000)
  to   space 104832K,   0% used [0x000000078a340000, 0x000000078a340000, 0x00000007909a0000)
 concurrent mark-sweep generation total 1560576K, used 0K [0x0000000797000000, 0x00000007f6400000, 0x00000007f6400000)
 concurrent-mark-sweep perm gen total 159744K, used 53249K [0x00000007f6400000, 0x0000000800000000, 0x0000000800000000)
2016-01-19T14:15:41.943+0800: 21.222: [GC2016-02-19T14:15:41.943+0800: 21.223: [ParNew
Desired survivor size 53673984 bytes, new threshold 1 (max 6)
- age   1:  107256200 bytes,  107256200 total
: 893386K->104832K(943744K), 1.2389070 secs] 893386K->210614K(2504320K), 1.2391870 secs] [Times: user=2.89 sys=0.35, real=1.24 secs]
Heap after GC invocations=2 (full 0):
 par new generation   total 943744K, used 104832K [0x0000000757000000, 0x0000000797000000, 0x0000000797000000)
  eden space 838912K,   0% used [0x0000000757000000, 0x0000000757000000, 0x000000078a340000)
  from space 104832K, 100% used [0x000000078a340000, 0x00000007909a0000, 0x00000007909a0000)
  to   space 104832K,   0% used [0x00000007909a0000, 0x00000007909a0000, 0x0000000797000000)
  concurrent mark-sweep generation total 1560576K, used 105782K [0x0000000797000000, 0x00000007f6400000, 0x00000007f6400000)
 concurrent-mark-sweep perm gen total 159744K, used 53249K [0x00000007f6400000, 0x0000000800000000, 0x0000000800000000)
}
```

能够明显看出上述 GC 日志包括两次 Minor GC。 

注意到第二次 Minor GC 的情况， 日志打出 "Desired survivor size 53673984 bytes"。 可是却存活了 "- age  1: 107256200 bytes, 107256200 total" 这么多。

能够看出明显的新生代的 Survivor 空间不足。正由于 Survivor 空间不足， 

那么从 Eden 存活下来的和原来在 Survivor 空间中不够老的对象占满 Survivor 后， 就会提升到老年代， 

能够看到这一轮 Minor GC 后老年代由原来的 0K 占用变成了 105782K 占用， 

这属于一个典型的 JVM 内存问题。 称为 "premature promotion" (过早提升)。

"premature promotion” 在短期看来不会有问题， 可是常常性的 "premature promotion”， 最总会导致大量短期对象被提升到老年代， 

终于导致老年代空间不足， 引发还有一个 JVM 内存问题 “promotion failure”（提升失败： 即老年代空间不足以容乃 Minor GC 中提升上来的对象）。 “promotion failure” 发生就会让 JVM 进行一次 CMS 垃圾收集进而腾出空间接受新生代提升上来的对象。

 CMS 垃圾收集时间比 Minor GC 长， 导致吞吐量下降、 时延上升， 将对用户体验造成影响。

**二. 新生代调优建议**

对于上述的新生代问题， 假设server内存足够用， 建议是直接增大新生代空间(如 -Xmn)。

假设内存不够用。 则添加 Survivor 空间， 降低 Eden 空间， 

可是注意降低 Eden 空间会添加 Minor GC 频率， 要考虑到应用对延迟和吞吐量的指标终于是否符合。

要增大多少 Survivor 空间？ 须要观察多次 Minor GC 过程。 看 Minor GC 后存活下来的对象大小。 

终于确定 Survivor 的合适大小。 整个调优过程可能须要几次调整。 找到比較合适的值。

调整几次后， 假设内存还是不够用， 就要须要考虑增大server内存， 或者把负载分担到很多其它的 JVM 实例上。

Survivor 空间计算公式： survivor 空间大小 = -Xmn[value] / (-XX:SurvivorRatio=<ratio> + 2)  