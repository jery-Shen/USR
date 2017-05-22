<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
if(session.getAttribute("user") == null){
	response.sendRedirect(request.getContextPath() + "/login.jsp");
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>用户管理</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/admin.css" rel="stylesheet">
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/vue.min.js"></script>
	<script src="js/vue-resource.min.js"></script>
	<script src="js/admin.js?t=2"></script>
</head>
<body>

<script type="text/javascript">
$(function(){
	var app = new Vue({
	  created:function(){
	  	this.getData();
	  	this.areas = JSON.parse(window.localStorage.getItem('areas'));
	  },
	  data: {
	  	users:[],
	  	areas:[],
        addUserForm:{
            userName:'',
            userPwd:'',
            areaId:''
        },
        updateUserForm:{
            userName:''
        },
        resetPwdForm:{
            userName:'',
            userPwd:'',
            newPwd:''
        }
	  },
	  methods: {
        getData:function(){
            var that = this;
            that.$http.get('${pageContext.request.contextPath}/GetUserList').then(function(res){
                if(res.body){
                    var users = res.body.result;
                    that.users = [];
                    for(var i=0;i<users.length;i++){
                        if(users[i].privilege!=5){
                        	users[i].areaName = getAreaNameById(users[i].areaId);
                            that.users.push(users[i]);
                        }
                    }
                }
            });
        },
        resetPwd:function(userName,userPwd){
            this.resetPwdForm.userName=userName;
            this.resetPwdForm.userPwd=userPwd;
            this.resetPwdForm.newPwd=''
            $('#resetPwdModal').modal();

        },
        updateUser:function(user){
            this.updateUserForm = user;
            $('#updateUserModal').modal();

        },
	  	addUserSubmit:function(){
            if(this.addUserForm.userName==''){
                alert('用户名不能为空');
                return;
            }
            if(this.addUserForm.userPwd==''){
                alert('密码不能为空');
                return;
            }
            if(this.addUserForm.areaId==''){
                alert('控制区域不能为空');
                return;
            }
            if(this.addUserForm.userPwd.length<6){
                alert('密码长度必须大于6位');
                return;
            }
            if(this.addUserForm.phone&&this.addUserForm.phone!=''){
            	var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
            	if(!reg.test(this.addUserForm.phone)) 
            	{ 
            	    alert('请输入有效的手机号码！'); 
            	    return; 
            	} 
            }
            if(this.addUserForm.email&&this.addUserForm.email!=''){
            	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
            	if(!reg.test(this.addUserForm.email)) 
            	{ 
            	    alert('请输入有效的邮箱地址！'); 
            	    return; 
            	} 
            }
            var that = this;
            that.$http.post('${pageContext.request.contextPath}/AddUser',this.addUserForm,{emulateJSON:true,headers:{'Content-Type':'application/x-www-form-urlencoded'}}).then(function(res){
                if(res.body.status==200){
                    this.addUserForm = {
                        userName:'',
                        userPwd:'',
                        areaId:''
                    };
                    this.getData();
                    $('#addUserModal').modal('hide');
                }else{
                    alert(res.body.error);
                }
            });
	  	},
	  	updateUserSubmit:function(){
	  		if(this.updateUserForm.phone==undefined||this.updateUserForm.phone==''){
            	this.updateUserForm.phone='-'
            }
	  		
            if(this.updateUserForm.email==undefined||this.updateUserForm.email==''){
            	this.updateUserForm.email='-'
            }
            
            if(this.updateUserForm.areaId==''){
                alert('控制区域不能为空');
                return;
            }
            if(this.updateUserForm.phone&&this.updateUserForm.phone!='-'){
            	var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
            	if(!reg.test(this.updateUserForm.phone)) 
            	{ 
            	    alert('请输入有效的手机号码！'); 
            	    return; 
            	} 
            }
            if(this.updateUserForm.email&&this.updateUserForm.email!='-'){
            	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
            	if(!reg.test(this.updateUserForm.email)) 
            	{ 
            	    alert('请输入有效的邮箱地址！'); 
            	    return; 
            	} 
            }
            var that = this;
            that.$http.post('${pageContext.request.contextPath}/UpdateUser',this.updateUserForm,{emulateJSON:true,headers:{'Content-Type':'application/x-www-form-urlencoded'}}).then(function(res){
                if(res.body.status==200){
                    $('#updateUserModal').modal('hide');
                }else{
                    alert(res.body.error);
                }
            });
	  	},
        resetPwdSubmit:function(){
            if(this.resetPwdForm.newPwd.length<6){
                alert('密码长度必须大于6位');
                return;
            }
            var that = this;
            that.$http.post('${pageContext.request.contextPath}/UpdateUserPwd',this.resetPwdForm,{emulateJSON:true,headers:{'Content-Type':'application/x-www-form-urlencoded'}}).then(function(res){
                if(res.body.status==200){
                    alert('重置成功');
                    $('#resetPwdModal').modal('hide');
                }else{
                    alert(res.body.error);
                }
            });
        },
        deleteUser:function(userName){
            var that = this;
            that.$http.get('${pageContext.request.contextPath}/DeleteUser',{params:{userName:userName}}).then(function(res){
                if(res.body&&res.body.status==200){
                    this.getData();
                }
            });
        },

	  }
	}).$mount('#app');
});
</script>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid"> 
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#example-navbar-collapse">
			<span class="sr-only">切换导航</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="javascript:;">USR</a>
	</div>
	<div class="collapse navbar-collapse" id="example-navbar-collapse">
		<ul class="nav navbar-nav">
				<li><a href="deviceManage.jsp">设备管理</a></li>
                <li class="active"><a href="userManage.jsp">用户管理</a></li>
                <li><a href="hostManage.jsp">主机映射</a></li>
                <li><a href="areaManage.jsp">区域管理</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.userName }
                       <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a onclick="logout()" href="javascript:;">注销</a></li>
                    </ul>
                </li>
            </ul>
	</div>
	</div>
</nav>

<div id="app" class="content well">
    <form class="form-inline" role="form">
        <div class="form-group m-l pull-right">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addUserModal">新增用户</button>
        </div>
        <div class="clearfix"></div>
    </form>
    <div class="table-responsive">
        <table class="table table-bordered">
            <thead>
                <tr>
                	<th>用户名</th>
                    <th>控制区域</th>
                    <th>姓名</th>
                    <th>电话</th>
                    <th>邮箱</th>
                    <th>地址</th>
                    <th>描述</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="user in users">
                	<td>{{user.userName}}</td>
                    <td>{{user.areaName}}</td>
                    <td>{{user.name}}</td>
                    <td>{{user.phone}}</td>
                    <td>{{user.email}}</td>
                    <td>{{user.address}}</td>
                    <td>{{user.des}}</td>
                    <td><!-- <a href="javascript:;" @click="updateUser(user)">编辑</a> --> <a href="javascript:;" @click="resetPwd(user.userName,user.userPwd)">重置密码</a> <a href="javascript:;" @click="deleteUser(user.userName)">删除</a></td>
                </tr>
            </tbody>
        </table>
    </div>
    
    <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">添加用户</h4>
                </div>
                <div class="modal-body">
                    <div class="form-horizontal" role="form">
                        <div class="form-group m-t">
                            <label for="firstname" class="col-sm-2 control-label">用户名 <span class="text-danger">*</span></label>
                            <div class="col-sm-8">
                                <input v-model="addUserForm.userName" type="text" class="form-control" placeholder="请输入用户名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">密码 <span class="text-danger">*</span></label>
                            <div class="col-sm-8">
                                <input v-model="addUserForm.userPwd" type="password" class="form-control" placeholder="请输入密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">控制区域 <span class="text-danger">*</span></label>
                            <div class="col-sm-8">
                                <input v-model="addUserForm.areaId" type="number" class="form-control" placeholder="请输入控制区域">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-8">
                                <input v-model="addUserForm.name" type="text" class="form-control" placeholder="请输入姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">电话</label>
                            <div class="col-sm-8">
                                <input v-model="addUserForm.phone" type="text" class="form-control" placeholder="请输入电话">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-8">
                                <input v-model="addUserForm.email" type="email" class="form-control" placeholder="请输入邮箱">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">地址</label>
                            <div class="col-sm-8">
                                <input v-model="addUserForm.address" type="text" class="form-control" placeholder="请输入地址">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-8">
                                <input v-model="addUserForm.des" type="text" class="form-control" placeholder="请输入描述">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" @click="addUserSubmit()" >添加</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    
    <div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">编辑</h4>
                </div>
                <div class="modal-body">
                    <div class="form-horizontal" role="form">
                        <div class="form-group m-t">
                            <label for="firstname" class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-8">
                                <input v-model="updateUserForm.userName" type="text" readonly="true" class="form-control" placeholder="请输入用户名">
                            </div>
                        </div>
                         <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">控制区域 <span class="text-danger">*</span></label>
                            <div class="col-sm-8">
                                <input v-model="updateUserForm.areaId" type="number" class="form-control" placeholder="请输入控制区域">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-8">
                                <input v-model="updateUserForm.name" type="text" class="form-control" placeholder="请输入姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">电话</label>
                            <div class="col-sm-8">
                                <input v-model="updateUserForm.phone" type="text" class="form-control" placeholder="请输入电话">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-8">
                                <input v-model="updateUserForm.email" type="email" class="form-control" placeholder="请输入邮箱">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">地址</label>
                            <div class="col-sm-8">
                                <input v-model="updateUserForm.address" type="text" class="form-control" placeholder="请输入地址">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-8">
                                <input v-model="updateUserForm.des" type="text" class="form-control" placeholder="请输入描述">
                            </div>
                        </div>
                        
                        
                        
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" @click="updateUserSubmit()" >保存</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <div class="modal fade" id="resetPwdModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">重置密码</h4>
                </div>
                <div class="modal-body">
                    <div class="form-horizontal" role="form">
                        <div class="form-group m-t">
                            <label for="firstname" class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-8">
                                <input v-model="resetPwdForm.userName" type="text" readonly="true" class="form-control" placeholder="请输入用户名">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">新密码 <span class="text-danger">*</span></label>
                            <div class="col-sm-8">
                                <input v-model="resetPwdForm.newPwd" type="password" class="form-control" placeholder="请输入新密码">
                            </div>
                        </div>
                        
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" @click="resetPwdSubmit()" >重置</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    
</div>

</body>
</html>