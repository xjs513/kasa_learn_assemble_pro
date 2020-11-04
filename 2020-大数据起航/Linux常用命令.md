##  1. 查看CentOS版本信息

```cat /etc/issue   ```

```cat /etc/redhat-release```

```   cat /proc/version```

```uname -a  uname -r```

查看系统是32位或者64位的方法

1. getconf LONG_BIT or getconf WORD_BIT
    输入：getconf LONG_BIT
    返回结果：64
    输入：getconf WORD_BIT
    返回结果：32 
    分析：32位的系统中int类型和long类型一般都是4字节，64位的系统中int类型还是4字节的，但是long已变成了8字节inux系统中可用”getconf WORD_BIT”和”getconf     LONG_BIT”获得word和long的位数。64位系统中应该分别得到32和64。
    所以该系统为64为Linux系统。

2. file /bin/ls
    /bin/ls: ELF 64-bit LSB executable, x86-64, version 1 (SYSV), dynamically linked (uses shared libs), for GNU/Linux 2.6.18, stripped

   可以看到 ELF 64-bit LSB 所以该系统为64位