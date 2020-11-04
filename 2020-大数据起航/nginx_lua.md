##  Nginx [OpenResty]和 Lua 的安装

https://www.bilibili.com/video/BV1Sb41147hP?p=4

操作系统：CentOS 7.5  ```abc```

默认 Nginx 不支持 Lua 模块，需要安装 LuaJIT 解释器，重新编译 Nginx。

~~可选用 ==OpenResty== 替代，已经集成好所需模块。~~

>  LuaJIT
>
> Ngx_devel_kit 和 lua-nginx-module

1. 环境准备：

```shell
yum -y install  gcc gcc-c++ make pcre-devel zlib-devel openssl-devel
```

	2.  下载所需的工具包：`luajit`、`ngx_devel_kit` 和 `lua-nginx-module`

```shell
mkdir -p /kasa_install/src & cd /kasa_install/src
wget http://luajit.org/download/LuaJIT-2.0.4.tar.gz
wget https://github.com/simpl/ngx_devel_kit/archive/v0.2.19.tar.gz
wget https://github.com/openresty/lua-nginx-module/archive/v0.10.13.tar.gz
```

3.  解压 `ngx_devel_kit` 和 `lua-nginx-module`

```
tar xf v0.2.19.tar.gz  解压后为：ngx_devel_kit-0.2.19
tar xf v0.10.13.tar.gz  解压后为：lua-nginx-module-0.10.13
```

4.  安装 LuaJIT 这是 Lua 的即时编译器

```
tar zxvf LuaJIT-2.0.4.tar.gz
cd LuaJIT-2.0.4
make && make install
最后一步需要 root 运行
ln -sf luajit-2.0.4 /usr/local/bin/luajit
==== Successfully installed LuaJIT 2.0.4 to /usr/local ====
```

5.  安装 Nginx 并加载模块

```
cd /kasa_install/src
wget http://nginx.org/download/nginx-1.12.2.tar.gz
tar xf nginx-1.12.2.tar.gz
cd nginx-1.12.2
./configure --prefix=/kasa_install/nginx --with-http_ssl_module \
--with-http_stub_status_module --with-file-aio --with-http_dav_module \
--add-module=../ngx_devel_kit-0.2.19/ --add-module=../lua-nginx-module-0.10.13/

make && make install

echo $?  ===  0
```

// 建立软链接，否则会出现 share object 的错误

```
sudo ln -s /usr/local/lib/libluajit-5.1.so.2 /lib64/libluajit-5.1.so.2
```

**   至此，Nginx 与 Lua 配置结束！！

验证：

```
cd /kasa_install/nginx/conf
vi nginx.conf
在 server 里面添加如下内容：
# added by kasa
location /test {
	default_type text/html;
	content_by_lua_block {
		ngx.say("hello world.")
	}
}

location /test {
	default_type text/html;
	content_by_lua_block {
		clientIP = ngx.req.get_headers()["x_forwarded_for"]
		ngx.say("Forwarded_IP:", clientIP)
		if clientIP == nil then
			clientIP = ngx.var.remote_addr
			ngx.say("Remote_IP:", clientIP)
		end
	}
}

验证配置是否正确：
/kasa_install/nginx/sbin/nginx -t
启动 nginx：
/kasa_install/nginx/sbin/nginx
```

##  Nginx 调用 Lua 指令

`Nginx` 调用 `Lua` 模块指令，Nginx 的可插拔模块加载执行，共 11 个处理阶段。

| 语法                                    | 说明                                    |
| --------------------------------------- | --------------------------------------- |
| set_by_lua<br />set_by_lua_file         | 设置 Nginx 变量，可以实现负载的赋值逻辑 |
| access_by_lua<br />access_by_lua_file   | 请求访问阶段处理，用于访问控制          |
| content_by_lua<br />content_by_lua_file | 内容处理器 ，接受请求处理并 输出响应    |

`Nginx` 调用 `Lua API`

| 变量                 | 说明                               |
| -------------------- | ---------------------------------- |
| ngx.var              | nginx  变量                        |
| ngx.req.get_headers  | 获取请求头                         |
| ngx.req.get_uri_args | 获取 url 请求参数                  |
| ngx.redirect         | 重定向                             |
| ngx.print            | 输出响应内容体                     |
| ngx.say              | 输出响应内容体，最后输出一个换行符 |
| ngx.header           | 输出 响应头                        |

##  Nginx + Lua  实现代码 灰度发布

灰度发布是指在黑与白之间，能够平滑过渡的一种发布方式。

AB test 就是一种灰度 发布方式：

让一部分 用户 继续用 A,一部分用户开始用  B,如果用户对 B 没反对意见，

则 逐步扩大范围，最终把所有用户都迁移到 B 上来。

灰度 发布可以保证系统整体的稳定，在初始灰度的时候可以发现、调整问题，以保证其影响度。

>  按照一定的关系区别，分不同的代码进行上线，使代码的发布能平滑过渡上线。

1. 用户的信息 cookie 等信息区别
2. 根据用户的 IP 地址，颗粒度更广

这里的用于 WEB 系统新代码的测试发布，让一部分用户访问新版本，其余用户不变，原理如下：

![image-20200908151404192](C:\Users\hds\AppData\Roaming\Typora\typora-user-images\image-20200908151404192.png)

执行过程：

1. 用户请求到达前端代理 Nginx，内嵌的 lua 模块会解析 Nginx 配置文件中 Lua 脚本。
2. Lua 脚本会获取客户端 IP 地址，查看是否在缓存中。
3. 如果存在则执行 @java_test，否则执行 @java_prod。
4. 如果是@java_test，那么location会将请求转发到新版本代码集群。
5. 如果是@java_prod，那么location会将请求转发到旧版本代码集群。
6. 最后整个过程执行后结束。





lua.h

luaconf.h

lauxlib.h