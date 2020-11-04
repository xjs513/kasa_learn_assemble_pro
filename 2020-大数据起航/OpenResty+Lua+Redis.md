##  1. OpenResty 安装	

教程:  https://blog.csdn.net/qq_30336433/article/details/88733988

下载:  https://openresty.org/download/openresty-1.17.8.2.tar.gz

编译安装：

```shell
tar zxf openresty-1.17.8.2.tar.gz
cd openresty-1.17.8.2
./configure --prefix=/opt/openresty

./configure --prefix=/home/pubsrv/openresty

gmake && gmake install
```

```shell
cp conf/uwsgi_params \
                '/opt/openresty/nginx/conf/uwsgi_params.default'
test -f '/opt/openresty/nginx/conf/scgi_params' \
                || cp conf/scgi_params '/opt/openresty/nginx/conf'
cp conf/scgi_params \
                '/opt/openresty/nginx/conf/scgi_params.default'
test -f '/opt/openresty/nginx/conf/nginx.conf' \
                || cp conf/nginx.conf '/opt/openresty/nginx/conf/nginx.conf'
cp conf/nginx.conf '/opt/openresty/nginx/conf/nginx.conf.default'
test -d '/opt/openresty/nginx/logs' \
                || mkdir -p '/opt/openresty/nginx/logs'
test -d '/opt/openresty/nginx/logs' \
                || mkdir -p '/opt/openresty/nginx/logs'
test -d '/opt/openresty/nginx/html' \
                || cp -R docs/html '/opt/openresty/nginx'
test -d '/opt/openresty/nginx/logs' \
                || mkdir -p '/opt/openresty/nginx/logs'
gmake[2]: Leaving directory `/opt/openresty-1.17.8.2/build/nginx-1.17.8'
gmake[1]: Leaving directory `/opt/openresty-1.17.8.2/build/nginx-1.17.8'
mkdir -p /opt/openresty/site/lualib /opt/openresty/site/pod /opt/openresty/site/manifest
ln -sf /opt/openresty/nginx/sbin/nginx /opt/openresty/bin/openresty
```



curl mosh-data-front:20080/test



 curl mosh-data-front:20080/test?project=abc&token=909090&data=yyf



curl http://mywebsite.com/index.php?a=1\&b=2\&c=3



nginx 配置

监听端口修改为：20080 避免和之前的冲突。

```shell

#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {

    # must set within http location
    lua_package_path "/usr/local/openresty/lualib/resty/?.lua;;";

    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            root   html;
            index  index.html index.htm;
        }
	
	location /hello {
            default_type text/html;
	    content_by_lua_block {
		ngx.say("Hello, World.")
	    }
	}

	location /test {
	    log_not_found off;
	    access_log off;
            default_type text/html;
	    content_by_lua_block {
		clientIP = ngx.req.get_headers()['x_forwarded_for']
		ngx.say("Forwarded_IP:", clientIP, "<br />")
		if clientIP == nli then
		    clientIP = ngx.var.remote_addr
		    ngx.say("Remote_IP:", clientIP)
		end
		ngx.say("<br />")
		token = ngx.req.get_uri_args()['token']
		project = ngx.req.get_uri_args()['project']
		if token ==  nli or project == nli then
		    ngx.say("token or project is empty, invalidate request..!!")
		else
		    ngx.say("token is:", token, ", and project is:", project)
		    ngx.say("<br />")
		    ngx.say("we get them both, then check them.")
		    ngx.say("<br />")
		end

		local function close_redis(red)  
		    if not red then  
		        return  
		    end  
    		    --释放连接(连接池实现)  
    		    local pool_max_idle_time = 10000 --毫秒  
    		    local pool_size = 100 --连接池大小  
		    local ok, err = red:set_keepalive(pool_max_idle_time, pool_size)  
		    if not ok then  
        		        ngx.say("set keepalive error : ", err)  
    		    end  
		end 


		local function isnil(value)
		    if value == nil then
		        value = "-"
		    end
		    return value
		end


		local function log()
		    local args = {}
		    args = ngx.req.get_uri_args()
		    local v_prev = ""
		    local sp = "\""
		    for key,val in pairs(args) do
		        if key == nil or val == nil then
		        else
		            v_prev = v_prev .. sp ..  key .. sp .. ":" .. sp .. val .. sp .. ","
		        end
		    end

		    local logContent = ""
		    if v_prev ~= nil then
		    	-- ngx.status = 200
		        local http_cdn_src_ip = isnil(ngx.var.http_cdn_src_ip)
		        local time_local = isnil(ngx.var.time_local)
		        local status = isnil(ngx.var.status)
		        local body_bytes_sent = isnil(ngx.var.body_bytes_sent)
		        local request_body = isnil(ngx.var.request_body)
		        local content_length = isnil(ngx.var.content_length)
		        local http_referer = isnil(ngx.var.http_referer)
		        local http_user_agent = isnil(ngx.var.http_user_agent)
		        local http_x_forwarded_for = isnil(ngx.var.http_x_forwarded_for)
		        local remote_addr = isnil(ngx.var.remote_addr)
		        local upstream_response_time = isnil(ngx.var.upstream_response_time)
		        local request_time = isnil(ngx.var.request_time)
		        local http_x_trace_code = isnil(ngx.var.http_x_trace_code)
		        logContent = "{\"http_cdn_src_ip\":" .. sp .. http_cdn_src_ip .. sp .. ",\"time_local\":" .. sp .. time_local .. sp  .. ",\"request\":" ..  "{" .. string.sub(v_prev,1,#v_prev-1) ..  "}" .. ",\"status\":" .. sp .. status .. sp .. ",\"body_bytes_sent\":" .. sp .. body_bytes_sent .. sp .. ",\"request_body\":" .. sp .. request_body .. sp .. ",\"content_length\":" .. sp .. content_length .. sp .. ",\"http_referer\":" .. sp .. http_referer .. sp .. ",\"http_user_agent\":" .. sp .. http_user_agent .. sp .. ",\"http_x_forwarded_for\":" .. sp .. http_x_forwarded_for .. sp .. ",\"remote_addr\":" .. sp .. remote_addr .. sp .. ",\"upstream_response_time\":" .. sp .. upstream_response_time .. sp .. ",\"request_time\":" .. sp .. request_time .. sp .. ",\"http_x_trace_code\":" .. sp .. http_x_trace_code .. sp .. "}"
		        local dir = "/usr/local/openresty/nginx/logs/" .. project
		        local file_name = "/ckl_access.log"
		        local file = io.open(dir .. file_name,"a")
		        if file == nil then
		            os.execute("mkdir -p " .. dir)
		            file = io.open(dir .. file_name,"a")
		        end
		        local hc = "\n"
		        file:write(logContent)
		        file:write(hc)
		        file:close()
		    end
		    ngx.exit(0)
		end

		local redis = require "resty.redis"
		local red = redis:new()
		red:set_timeout(1000) 
		local ip = "127.0.0.1" 
		local port = 6379
		local ok, err = red:connect(ip, port)
		if not ok then 
		    ngx.say("connect to redis error : ", err) 
		    return close_redis(red)
		end
		

		local count, err = red:get_reused_times()
		if 0 == count then ----新建连接，需要认证密码
    		    ok, err = red:auth("redis123")
    		    if not ok then
        		ngx.say("failed to auth: ", err)
        		return
    		    end
		elseif err then  ----从连接池中获取连接，无需再次认证密码
    		    ngx.say("failed to get reused times: ", err)
    		    return
		end

		local resp, err = red:get('project:' .. project)  

		if 'nil' == err then  
		    ngx.say("get project:"  .. project ..  " token error : ", err)  
		    return close_redis(red)  
		end 

		ngx.say("project:" .. project .. " token : ", resp) 
		close_redis(red) 

		ngx.say("<hr />")
		for i, v in pairs(ngx.req.get_uri_args()) do
    		    ngx.say(i .. "==>" .. v)
		    ngx.say("<br />")
		end 

		ngx.say("<hr />")
		for i, v in pairs(ngx.req.get_headers()) do
    		    ngx.say(i .. "==>" .. v)
		    ngx.say("<br />")
		end 
		
		if resp == token then
		    ngx.say("token is validate, record log....")
		    log()
		else
		    ngx.say("token is invalidate, ignore log....")
		end


	    }
	}


        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}

```

##  2.  Lua 脚本模块化管理

content_by_lua 和 content_by_lua_file

content_by_lua 一般在很简单的lua脚本时使用：

```
location /lua {
	set $test "hello, world.";
	content_by_lua '
		ngx.header.content_type = "text/plain";
		ngx.say(ngx.var.test);
	';
}
```

cotent_by_lua_file 适应于复杂的 lua 脚本，专门放入一个文件中：

```
location /lua2 {
	#lua_code_cache off;
	content_by_lua_file lua/hello.lua;
}
```

路径相对于 /opt/openresty/nginx

```
[root@localhost lua]# pwd
/opt/openresty/nginx/lua
[root@localhost lua]# cat hello.lua
ngx.say('hello ngx_lua!!!!');
```

本例子中 hello.lua 只包含一句： ngx.say(‘hello ngx_lua!!!’);

访问 /lua2 :

```
[root@localhost lua]# curl localhost/lua
hello ngx_lua!!!!
```

==可以看到访问成功。==



main.lua脚本内容如下：

```
function close_redis_client(redis_client)  
    if not redis_client then  
	return  
    end  
    --释放连接(连接池实现)  
    local pool_max_idle_time = 10000 --毫秒  
    local pool_size = 100 --连接池大小  
    local ok, err = redis_client:set_keepalive(pool_max_idle_time, pool_size)  
    if not ok then  
	ngx.say("redis set keepalive error : ", err)  
    end  
end 

function get_redis_client(ip, port, password) 
    local redis = require "resty.redis"
    local redis_client = redis:new()
    redis_client:set_timeout(1000) 
    local ok, err = redis_client:connect(ip, port)
    if not ok then 
        ngx.say("connect to redis error : ", err) 
        return close_redis_client(redis_client)
    else
        return redis_client
    end
end

local function isnil(value)
    if value == nil then
	value = "-"
    end
    return value
end

local log_root_dir = ngx.var.log_root_dir

local function log(project)
    if not project then
        return
    end
    local args = {}
    args = ngx.req.get_uri_args()
    local v_prev = ""
    local sp = "\""
    for key,val in pairs(args) do
	if key == nil or val == nil then
	else
	    v_prev = v_prev .. sp ..  key .. sp .. ":" .. sp .. val .. sp .. ","
	end
    end

    local logContent = ""
    if v_prev ~= nil then
	-- ngx.status = 200
	local http_cdn_src_ip = isnil(ngx.var.http_cdn_src_ip)
	local time_local = isnil(ngx.var.time_local)
	local status = isnil(ngx.var.status)
	local body_bytes_sent = isnil(ngx.var.body_bytes_sent)
	local request_body = isnil(ngx.var.request_body)
	local content_length = isnil(ngx.var.content_length)
	local http_referer = isnil(ngx.var.http_referer)
	local http_user_agent = isnil(ngx.var.http_user_agent)
	local http_x_forwarded_for = isnil(ngx.var.http_x_forwarded_for)
	local remote_addr = isnil(ngx.var.remote_addr)
	local upstream_response_time = isnil(ngx.var.upstream_response_time)
	local request_time = isnil(ngx.var.request_time)
	local http_x_trace_code = isnil(ngx.var.http_x_trace_code)
	logContent = "{\"http_cdn_src_ip\":" .. sp .. http_cdn_src_ip .. sp .. ",\"time_local\":" .. sp .. time_local .. sp  .. ",\"request\":" ..  "{" .. string.sub(v_prev,1,#v_prev-1) ..  "}" .. ",\"status\":" .. sp .. status .. sp .. ",\"body_bytes_sent\":" .. sp .. body_bytes_sent .. sp .. ",\"request_body\":" .. sp .. request_body .. sp .. ",\"content_length\":" .. sp .. content_length .. sp .. ",\"http_referer\":" .. sp .. http_referer .. sp .. ",\"http_user_agent\":" .. sp .. http_user_agent .. sp .. ",\"http_x_forwarded_for\":" .. sp .. http_x_forwarded_for .. sp .. ",\"remote_addr\":" .. sp .. remote_addr .. sp .. ",\"upstream_response_time\":" .. sp .. upstream_response_time .. sp .. ",\"request_time\":" .. sp .. request_time .. sp .. ",\"http_x_trace_code\":" .. sp .. http_x_trace_code .. sp .. "}"
	
	local year = os.date("%Y",unixTime)
	local month = os.date("%m",unixTime)
	local day = os.date("%d",unixTime)


	local dir = log_root_dir .. "/" .. project .. "/" .. year .. "/" .. month .. "/" .. day
	local file_name = "/ckl_access.log"
	local file = io.open(dir .. file_name,"a")
	if file == nil then
	    os.execute("mkdir -p " .. dir)
	    file = io.open(dir .. file_name,"a")
	end
	local hc = "\n"
	file:write(logContent)
	file:write(hc)
	file:close()
    end
    ngx.exit(0)
end

local function pass_check(redis_client, project, token)
    if not redis_client or not project or not token then
        return 0
    end
    local resp, err = redis_client:get('project:' .. project);
    close_redis_client(redis_client)
    if 'nil' == err then  
        ngx.say("get project:"  .. project ..  " token error : ", err)   
	return 0;
    elseif resp == token then
	ngx.say("</br>");
	return 1;
    else
	return 0;
    end
end


local function get_project_req()
    return ngx.req.get_uri_args()['project']
end

local function get_token_req()
    return ngx.req.get_uri_args()['token']
end

local redis_client =  get_redis_client(ngx.var.redis_ip, ngx.var.redis_port, ngx.var.redis_password)

-- ngx.say("redis_client:" .. redis_client);
redis_client:select(7);
-- ngx.say("</br>");

local project = get_project_req()
local token = get_token_req()

-- ngx.say(project);
-- ngx.say(token);
-- ngx.say("</br>");
local pass_check_result = pass_check(redis_client, project, token);
ngx.say(pass_check_result);
ngx.say("</br>");

if 1 == pass_check_result then
    ngx.say("project & token is validate, record log....")
    log(project);
else
    ngx.say("project or token is invalidate, ignore log....");
    ngx.say("</br>");    
end
```

nginx.conf 核心配置如下：

```
location /song {
    log_not_found off;# 关闭日志记录
    access_log off;   # 关闭日志记录
    set $redis_ip "192.168.1.201";
	set $redis_port "6379";
	set $redis_password "redis_password";
	set $log_root_dir "/opt/openresty/nginx/logs";
    #lua_code_cache off;
	default_type text/html;
    content_by_lua_file lua/main.lua;
}
```





 	1. Redis 连接
 	2. 日志记录
 	3. 权限验证

##  3.  Redis 设置密码影响范围

```
project:aaa-->token123
原来的埋点
```



##  4.  日志目录的权限设置和日志组织格式调整

先设置一个总目录, 比如：/opt/openresty/nginx/logs

然后根据项目/年/月/日分层组织：

比如：

```shell
/opt/openresty/nginx/logs/evente/2020/10/9/ckl_access.log
```





## **启动、停止nginx**

**cd /usr/local/nginx/sbin/**

**./nginx** 

**./nginx -s stop**

**./nginx -s quit**

**./nginx -s reload**

**./nginx -s quit****:此方式停止步骤是待nginx进程处理任务完毕进行停止。**

**./nginx -s stop****:此方式相当于先查出nginx进程id再使用kill命令强制杀掉进程。**

**查询nginx进程：**

**ps aux|grep nginx**

## **重启 nginx**

**1.先停止再启动（推荐）：**

**对 nginx 进行重启相当于先停止再启动，即先执行停止命令再执行启动命令。如下：**

**./nginx -s quit**

**./nginx**

**2.重新加载配置文件：**

**当 ngin x的配置文件 nginx.conf 修改后，要想让配置生效需要重启 nginx，使用****-s reload****不用先停止 ngin x再启动 nginx 即可将配置信息在 nginx 中生效，如下：**

**./nginx -s reload**