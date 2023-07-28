# SecurityFramework

###### 帮助快速启动并且想使用springSecurity安全框架的同学（参考博客https://blog.csdn.net/u012760435/article/details/126558412）

# 快速开始

###### 1、配置yml中的redis地址

###### 2、数据源自行修改，demo给出的是sqllite，无需安装,使用数据库管理工具连接并执行资源目录下SQL语句

##### 3、接口测试顺序

1. /login POST 

   ```JSON
   {
     "loginKey":"admin",
     "password":"111111"
   }
   ```

2. /normal

3. /perm

4. /perm2

##### 4、 登录添加角色、权限可在UserDetailServiceImpl进行修改

##### 5、 想增加多权限可以修改PermissionService
