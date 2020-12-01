##  六 、bash 的初始化

```
https://www.bilibili.com/video/BV1Ur4y1F7SN?p=5
```

###  1、bash 环境变量文件

####  1.1 /etc/profile

*  全局配置，无论哪个用户，登录时都会加载该文件。

####  1. 2 /etc/bashrc

* Ubuntu 没有此文件，与之对应的是 /etc/bash.bashrc
* 它也是全局共有的
* bash 执行时，无论何种方式，都会加载改文件。

####  1.3 ~/.profile  可能没有

* 若bash以login 方式执行时，读取~/.bash_profile，若不存在，则

  读取~/.bash_login，若还是不存在，则读取 ~/.profile

* 图形模式登录时，读取此文件，即使存在~/.bash_profile和 ~/.bash_login

####  1.4 ~/.bash_login  可能没有  了解

* 若bash以login 方式执行时，读取~/.bash_profile，若不存在，则

  读取~/.bash_login，若还是不存在，则读取 ~/.profile

#### 1.5 ~/.bash_profile  了解

* Ubuntu 默认没有此文件，可新建
* 只有bash是以login形式执行时，才读取此文件。
* 通常该配置文件还会配置成去读取~/.bashrc

####  1.6 ~/.bashrc

* 当bash以no-login形式执行时，才会 读取此文件。
* 若是以login形式执行，则不会读取此文件。

#### 1.7 ~/.bash_logout  了解

* 注销时，且是login形式，才会读取此文件。
* 在文本模式注销时，读取此文件；图形模式注销时，不会读取。

###  2、bash 环境变量文件加载顺序

* 图形模式登陆时，顺序读取：/etc/profile 和 ~/.profile
* 图形模式登陆后，打开终端时，顺序读取：/etc/bashrc 和 ~/.bashrc
* 文本模式登陆时，顺序读取：/etc/bashrc; /etc/profile; ~/.bash_profile
* 从其他用户su到该用户，分两种情况：
  * 如果带 -l 参数，/etc/bashrc; /etc/profile; ~/.bash_profile
  * 不带参数，/etc/bashrc 和 ~/.bashrc  ==注意这时不读取 /etc/profile==

##  七 、bash特性

###  1、命令和文件自动补齐

```shell
yum -y install bash-completion
```

* 注意：重启后可使用tab补齐
* 默认情况下，Bash为Linux用户提供了下列标准补全功能：
  * 变量补全
  * 用户名补全
  * 主机名补全
  * 路径补全
  * 文件名补全

###  2、 历史命令记忆功能

* bash的历史记录保存在 .bash_history文件中。
* centos可以通过在/etc/profile中的文件来定义一些参数。
* 使用history命令来查看和操作之前的命令。
* history是bash内部命令，可以help history 看帮助文档。
* 调用命令的方法：

```shell
# 查看之前所有命令：history
# 显示最近 N个命令：history n
# 删除第N个命令：history -d n
# 执行历史记录中的第N条命令: !n
# 执行历史记录中的倒数第N条命令: !-n
# 执行最近的一条命令: !!
# 执行最近的一条以[String]开头的命令: ![String]

# 引用上一个命令中的最后一个参数： !$
# COMMAND + esc + . 输入命令后按下esc，松开后再按.键，可以自动输入最近一条语句使用的参数
# COMMAND + alt + . 输入命令后同时按下alt和.键，也可以自动输入最近一条语句使用的参数

# 将命令历史写入命令历史的文件中：history -w
# 回显 echo 之后的语句，用echo $FILENAME查看该file所在的路径：echo $HISTFILE

# 查看命令历史内容：cat .bash_history
# 删除所有命令历史记录：history -c
```

###  3、 别名功能

alias 命令创建别名，可以把长指令简化缩写，提高效率。

```shell
alias              # 查看系统当前所有别名
alias h5='head -5' # 定义别名，此后输入 h5 就相当于输入 ‘head -5’
unalias h5		   # 取消已定义别名
```

想别名永久生效，需要把别名定义到配置文件中：/etc/bashrc 或者 ~/.bashrc

如果想用真实命令可以在命令前加反斜杠(\\)，使别名失效

```shell
\cp -rf /etc/hosts
```

###  4、 快捷键

| 快捷键   | 作用说明                                             |
| -------- | ---------------------------------------------------- |
| ctrl + A | 移动光标到行首。                                     |
| ctrl + E | 移动光标到行尾。                                     |
| ctrl + C | 强制终止当前命令。                                   |
| ctrl + L | 清屏，相当于 clear 命令。                            |
| ctrl + U | 剪切光标之前的内容，不包括光标。                     |
| ctrl + K | 剪切光标之后的内容，包括光标。                       |
| ctrl + Y | 粘贴上面两个命令剪切的内容。                         |
| ctrl + R | 在命令历史中搜索。按下之后会出现搜索界面。           |
| ctrl + D | 退出当前终端。                                       |
| ctrl + Z | 暂停，并放入后台。这个牵扯到工作管理内容，后续详解。 |
| ctrl + S | 暂停屏幕输出。                                       |
| ctrl + Q | 恢复屏幕输出。                                       |

###  5、 前后台作业控制

Linux bashshell 单一终端界面下，经常需要管理或同时完成多个作业，如一边执行编译，

一边实现数据备份，以及执行SQL查询等其他任务。

所有上述工作可以在一个bash内实现，在同一个终端窗口完成。

####  1、前后台作业的定义

* 也就是前后台进程，有对应的pid，这里统称作业。

* 无论前后台作业，都来自当前的shell，是当前shell的子程序。

* 前台作业：可以由用户参与及控制的作业。

* 后台作业 ：自运行在内存的作业，用户无法参与交互或使用ctrl+c来终止，

  只能通过bg或者fg来调用该作业。

```shell
yum -y install sl
```

#### 2、几个常用的作业命令

* command & 直接让作业进入后台运行
* ctrl + Z 将当前作业切换到后台，并暂停运行
* jobs 查看后台作业状态
* fg %n 让后台运行的作业 n切换到前台来 
* bg %n 让指定的作业在后台运行 
* kill %n 移除指定的作业n
  * n 为jobs命令查看到的job编号，不是进程id
  * 每个job都有对应的job编号，编号在当前的终端从1开始分配
  * job编号的样式为[n]，后面可能有 “+” 或 “-”。
  * “+” 表示最近的一个job
  * “-” 表示倒数第二个被执行的job
  * 注意 “+” 和 “-” 会随着作业的执行或添加动态变化

* 通过jobs方式管理作业，当前终端的作业在其他终端不可见，但是PID可见。

####  3、演示后台作业命令



###  6、清除dead会话

###  6、输入输出重定向

###  8、命令排序

###  9、通配符

* 通配符（元字符）表示的不是本意

##  八、shell 脚本规范

1. 风格规范
2. 注释
3. 参数要规范
4. 

