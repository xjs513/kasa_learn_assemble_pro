##  编译安装 OpenResty

见另一文档

##  Nginx 配置

###  主配置文件

```
user  mosh mosh;
worker_processes  2;

#error_log  logs/error.log;
#pid        var/nginx.pid;

worker_rlimit_nofile 65535;

events {
    use epoll;
    worker_connections  65535;
    multi_accept on;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request_method $request_uri $server_protocol" '
            '$status $body_bytes_sent "$http_referer" '
            '"$http_user_agent"';

    access_log  off;

    server_tokens off;
    server_names_hash_bucket_size 128;
    client_header_buffer_size 32k;
    large_client_header_buffers 4 32k;
    client_max_body_size 50m;

    sendfile        on;
    autoindex off;
    tcp_nopush     on;
    keepalive_timeout  65;
    tcp_nodelay on;

    fastcgi_connect_timeout 300;
    fastcgi_send_timeout 300;
    fastcgi_read_timeout 300;
    fastcgi_buffer_size 128k;
    fastcgi_buffers 8 256k;
    fastcgi_busy_buffers_size 256k;
    fastcgi_temp_file_write_size 256k;
    client_body_timeout    3m;

    proxy_http_version 1.1;

    gzip on;
    gzip_min_length  1k;
    gzip_buffers     4 16k;
    gzip_http_version 1.1;
    gzip_comp_level 6;
    gzip_types     text/plain application/javascript application/x-javascript text/javascript text/css application/xml application/xml+rss application/x-httpd-php;
    gzip_vary on;
    gzip_proxied   expired no-cache no-store private auth;
    gzip_disable   "MSIE [1-6]\.";
    
    server {
        listen       80;
        server_name  localhost;
   
        location / {
            root   html;
            index  index.html index.htm;
        }
   
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
    
    server {
         listen        8080;
         server_name   data-center.evente-in.cn;
         #server_name  localhost;
          location / {
              #root   html;
              #index  index.html index.htm;
              proxy_pass http://192.168.0.32:8761;
          }
      }

    include evente/*.conf;

}
```





###  包含配置文件 

```
/home/pubsrv/nginx/conf/evente/vhost.embed-tj.conf

server {
    listen 443;
    ssl on;
    ssl_certificate /home/pubsrv/nginx/cert/emplusys-cert/emplusys2020.pem;
    ssl_certificate_key /home/pubsrv/nginx/cert/emplusys-cert/emplusys2020.key;

    server_name tj.emplusys.com;

    # 默认统计
    location /__ea.gif {
        set $redis_ip "192.168.0.40";
        set $redis_port 6379;
        set $redis_password "Evente2016Db";
        set $log_root_dir "/data/resources/nginx/logs/statistics-v2";
        set $ignore_root_dir "/data/resources/nginx/logs/lua_ignore_logs";
        set $access_file_name "/data_embed_access.log";
        set $ignore_file_name "/data_embed_ignore.log";

        log_not_found off;
        access_log off;

        default_type text/html;
        content_by_lua_file /home/pubsrv/nginx/lua/main.lua;
    }

}
server {
    listen 80;
    server_name tj.emplusys.com;
    rewrite ^(.*)$  https://$host$1 permanent;
}
```





###  Lua 脚本

```
function get_redis_client(ip, port, password) 
    local redis = require "resty.redis"
    local redis_client = redis:new()
    redis_client:set_timeout(1000) 
    redis_client:set_keepalive(60000, 20)
    local ok, err = redis_client:connect(ip, port)
    if not ok then 
        ngx.say("connect to redis error : ", err) 
        return 
    end
    local count, err = redis_client:get_reused_times()
    if 0 == count then ---- reconnect nedd auth
        ok, err = redis_client:auth(password)
        if not ok then
            ngx.say("failed to auth: ", err)
            return
        end
    elseif err then  -- get connection from pool, do not need auth again
        ngx.say("failed to get reused times: ", err)
        return
    end
    return redis_client
end

local function isnil(value)
    if value == nil then
        value = "-"
    end
    return value
end

local log_root_dir = ngx.var.log_root_dir
local ignore_root_dir = ngx.var.ignore_root_dir
local access_file_name = ngx.var.access_file_name
local ignore_file_name = ngx.var.ignore_file_name

local function log4embed(project, root_dir, file_name)
    if not project then
        return
    end
    local remote_addr = isnil(ngx.var.remote_addr)
    local remote_user = isnil(ngx.var.remote_user)
    local time_local = isnil(ngx.var.time_local)
    local request_method = isnil(ngx.var.request_method)
    local request_uri = isnil(ngx.var.request_uri)
    local server_protocol = isnil(ngx.var.server_protocol)
    local status = isnil(ngx.var.status)
    local body_bytes_sent = isnil(ngx.var.body_bytes_sent)
    local http_referer = isnil(ngx.var.http_referer)
    local http_user_agent = isnil(ngx.var.http_user_agent)
    local separator = " "
    local logContent = ""
    logContent = remote_addr .. separator .. "-" .. separator ..
                 remote_user .. separator .. 
                 "[" .. time_local.. "]" .. separator .. 
                 "\"" .. request_method .. separator  .. 
                 request_uri .. separator  .. 
                 server_protocol .. "\"" .. separator  .. 
                 status .. separator  .. 
                 body_bytes_sent .. separator  .. 
                 "\"" .. http_referer .. "\"" .. separator  .. 
                 "\"" .. http_user_agent .. "\"";

    --ngx.say("<hr />");
    --ngx.say(logContent);
    --ngx.say("<hr />");
    local year = os.date("%Y",unixTime);
    local month = os.date("%m",unixTime);
    local day = os.date("%d",unixTime);
    local dir = root_dir .. "/" .. project .. "/" .. year .. "/" .. month .. "/" .. day;
    local file = io.open(dir .. file_name,"a");
    if file == nil then
        os.execute("mkdir -p " .. dir)
        file = io.open(dir .. file_name,"a")
    end
    local hc = "\n"
    file:write(logContent)
    file:write(hc)
    file:close()
    ngx.exit(0)
end

local function pass_check(redis_client, project, token)
    if not redis_client or not project or not token then
        return 0
    end
    local resp, err = redis_client:get('project:' .. project);
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
redis_client:select(8);
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
    ngx.say("</br>"); 
    log4embed(project, log_root_dir, access_file_name);
else
    ngx.say("project or token is invalidate, ignore log....");
    ngx.say("</br>"); 
    log4embed(project, ignore_root_dir, ignore_file_name);
end
```

