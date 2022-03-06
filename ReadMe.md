## COLLECT后端项目部署

1. `ps -aux | grep COLLECT-版本号-SNAPSHOT.jar`
    例如：

    ![image-20220305170920614](https://gitee.com/xdefiner/image-host/raw/master/Picgo/image-20220305170920614.png)
    其中第一行的**4052342**就是PID
    使用`kill -9 PID`就可以杀死该进程（-9表示强制停止)

    > HUP  1  终端断线
    >
    > INT   2  中断（同 Ctrl + C）
    >
    > QUIT  3  退出（同 Ctrl + \）
    >
    > TERM  15  终止
    >
    > KILL  9  强制终止
    >
    > CONT  18  继续（与STOP相反， fg/bg命令）
    >
    > STOP  19  暂停（同 Ctrl + Z）
    >
    > 更多：[Ubuntu的kill命令](https://blog.csdn.net/qq_26591517/article/details/82469803)

2. `netstat -ap | grep 9999`

    查看被占用的端口号（这里抓取了9999）
    ![image-20220305171100513](https://gitee.com/xdefiner/image-host/raw/master/Picgo/image-20220305171100513.png)

    然后可以选择杀死该进程

3. `nohup java -jar COLLECT-5.0-SNAPSHOT.jar >templog.txt &`

    >  `&`表示后台运行

4. nginx配置

    ```nginx
    http {
    	# 一堆不重要的
    
    	server {
    		listen 9999 ;
            server_name localhost; #需要修改为自己的域名
            root  /home/xwk/COLLECT/;    #需要修改为/home/hexoBlog;
        
        	# Load configuration files for the default server block.
    
    		include /etc/nginx/default.d/*.conf;
            location / {
            root html;
            index index.html index.htm index.php;
            }
    
    		# location / {
            # 	proxy_set_header Host $host;
            # 	proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            # 	proxy_pass http://127.0.0.1:9999;
            # }
            error_page 404 /404.html;
            
    		location = /40x.html {
            }
    	}
    }
    ```

    
