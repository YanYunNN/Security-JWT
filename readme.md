# 心理学院会议室预约系统 API 接口文档


## 一、接口统一规约
在前后端分离的程序架构下，前后端交互一般是由前端通过`HTTP协议`访问后端提供的`Restful`风格的`API`进行的。在这种场景下，常用的`HTTP方法`主要包括：`GET`、`PUT`、`POST`和`DELETE`。
### 1. GET 
`GET`方法用于获取资源详情或者列表，不对资源做变更。其一般使用场景如下：
1. 获取某个资源的详情：`/resourceName/{id}`，`Path Variable`中的`id`即为要获取的那个资源的唯一`id`
   
    特别的，当要获取当前登录用户的详情时，由于后端的`Security Context`中知道当前用户是谁，所以一般直接使用：`/user` 即可。

2. 分页获取某种资源列表：`/resourceName/list?page=xxx&size=xxx`

    `page`指当前的页码，`size`指每一页的资源数量，同样的，可以增加其他`Query String`参数以实现例如下拉筛选和模糊查找等功能。

### 2. PUT
`PUT`方法用于新增（添加）某个资源。新增的资源数据一般放在`RequestBody`中提交给后端。

### 3. POST
`POST`方法用于对某个资源进行修改（更新）。需要更新的数据一般放在`RequestBody`中提交给后端。

### 4. DELETE
`DELETE`方法用于删除某个资源。一般用法是：`/resourceName/{id}`，同样的，`Path Variable`中的`id`即为要删除的那个资源的唯一`id`。


## 二、接口定义

### 1. 登陆接口
    接口地址：`/user/login`

    方法：`POST`

    参数说明：password经过md5后上传

`request-body`
```json
{
	"username":"admin", 
	"password":"e10adc3949ba59abbe56e057f20f883e"
}
```
`response-body`
```json
{
    "code": 0,
    "message": "OK",
    "result": {
        "intentProvince": null,
        "roles": [
            "ROLE_ADMINISTRATOR"
        ],
        "mobile": "110",
        "type": [
            "system"
        ],
        "title": null,
        "nativeProvince": null,
        "name": "超级管理员",
        "id": 17,
        "department": null,
        "job": null,
        "nativeCity": null,
        "email": null,
        "username": "admin"
    }
}
```

### 2. 获取当前登录用户的详细信息
    接口地址：`/user`

    方法：`GET`

`response-body`
```json
{
    "code": 0,
    "message": "OK",
    "result": {
        "intentProvince": null,
        "roles": [
            "ROLE_ADMINISTRATOR"
        ],
        "mobile": "110",
        "type": [
            "system"
        ],
        "title": null,
        "nativeProvince": null,
        "name": "超级管理员",
        "id": 17,
        "department": null,
        "job": null,
        "nativeCity": null,
        "email": null,
        "username": "admin"
    }
}
```

### 3. 创建用户
    接口地址：`/user`

    方法：`PUT`

`request-body`
```json
{
	"username":"51174500103", 
	"name":"周润发", 
	"password":"123", 
	"type":["XXType"], 
	"roles":[],
	"mobile":"18555180999", 
	"email":"xyxu3@cloume.com", 
	"department":"XX部门", 
	"title":"助理工程师", 
	"job":"XX职务", 
	"nativeProvince":"浙江省", 
	"nativeCity":"杭州市", 
	"intentProvince":"上海市"
}
```
`response-body`
```json
{
    "code": 0,
    "message": "OK",
    "result": {
        "intentProvince": "上海市",
        "roles": [
            ""
        ],
        "mobile": "18555180999",
        "type": [
            "XXType"
        ],
        "title": "助理工程师",
        "nativeProvince": "浙江省",
        "permissions": [],
        "name": "周润发",
        "id": 40,
        "department": "XX部门",
        "job": "XX职务",
        "nativeCity": "杭州市",
        "email": "xyxu3@cloume.com",
        "username": "51174500103"
    }
}
```

### 4. 根据id删除用户
    接口地址：`/user/{id}`

    方法：`DELETE`

`response-body`
```json
{
    "code": 0,
    "message": "OK",
    "result": "success"
}
```

### 8. 创建招生组接口
    接口地址：`/group`

    方法：`PUT`

`request-body`
```json
{
	"name":"甘肃招生组", 
	"province":"甘肃省", 
	"department":"XX部门", 
	"leader":"51174500138", 
	"manager":"51174500140"
}
```
`response-body`
```json
{
    "code": 0,
    "message": "OK",
    "result": {
        "id": 52,
        "createdTime": 1555155733046,
        "isRemoved": false,
        "creator": null,
        "name": "甘肃招生组",
        "province": "甘肃省",
        "department": "XX部门",
        "leader": "51174500138",
        "manager": "51174500140",
        "userList": [
            {
                "id": 3,
                "createdTime": 1554776471929,
                "isRemoved": false,
                "creator": null,
                "username": "51174500138",
                "name": "徐向阳",
                "roles": null,
                "type": "招生组组员",
                "mobile": "18555180995",
                "email": "xyxu3@cloume.com",
                "department": "XX部门",
                "title": "助理工程师",
                "job": "XX职务",
                "nativeProvince": "安徽省",
                "nativeCity": "亳州市",
                "intentProvince": "上海市",
                "enabled": true,
                "accountNonLocked": true,
                "authorities": null,
                "accountNonExpired": true,
                "credentialsNonExpired": true
            },
            {
                "id": 1,
                "createdTime": 1554776426543,
                "isRemoved": false,
                "creator": null,
                "username": "51174500140",
                "name": "徐向阳3",
                "roles": null,
                "type": "招生组组员",
                "mobile": "18555180995",
                "email": "xyxu3@cloume.com",
                "department": "XX部门",
                "title": "助理工程师",
                "job": "XX职务",
                "nativeProvince": "安徽省",
                "nativeCity": "亳州市",
                "intentProvince": "上海市",
                "enabled": true,
                "accountNonLocked": true,
                "authorities": null,
                "accountNonExpired": true,
                "credentialsNonExpired": true
            }
        ]
    }
}
```