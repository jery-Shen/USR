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
	<title>主机映射</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/admin.css?t=2" rel="stylesheet">
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
	  	hosts:[],
	  	areas:[],
	  	areaId:0,
	  	filterData:{areaId:0},
        addHostForm:{
            areaId:0,
            deviceId:'',
            mac:''
        },
        editHostForm:{
        	areaId:'',
        	areaName:'',
            deviceId:'',
            mac:''
        }
	  },
	  methods: {
        getData:function(){
            var that = this;
            that.$http.get('${pageContext.request.contextPath}/GetHostList',{params:{areaId:that.areaId}}).then(function(res){
                if(res.body){
                    var hosts = res.body.result;
                    that.hosts = [];
                    for(var i=0;i<hosts.length;i++){
                    	hosts[i].areaName = getAreaNameById(hosts[i].areaId);
                    	hosts[i].enableStr = hosts[i].enable ? '是' : '否';
                    	hosts[i].enableEditStr = hosts[i].enable ? '停用' : '启用';
                    	that.hosts.push(hosts[i]);
                    }
                }
            });
        },
        editHost:function(host){
        	this.editHostForm.areaId=host.areaId;
            this.editHostForm.areaName=host.areaName;
            this.editHostForm.deviceId=host.deviceId;
            this.editHostForm.mac=host.mac
            this.editHostForm.des=host.des
            $('#editHostModal').modal();

        },
	  	addHostSubmit:function(){
            if(this.addHostForm.areaId==''){
                alert('设备区域不能为空');
                return;
            }
            if(this.addHostForm.deviceId==''){
                alert('设备号不能为空');
                return;
            }
            if(this.addHostForm.mac==''){
                alert('mac地址不能为空');
                return;
            }
            this.addHostForm.mac = this.addHostForm.mac.replace(/\W/g,'');
            var that = this;
            that.$http.post('${pageContext.request.contextPath}/AddHost',this.addHostForm,{emulateJSON:true,headers:{'Content-Type':'application/x-www-form-urlencoded'}}).then(function(res){
                if(res.body.status==200){
                    this.addHostForm = {
                    	areaId:0,
                        deviceId:'',
                        mac:''
                    };
                    this.getData();
                    $('#addHostModal').modal('hide');
                }else{
                    alert(res.body.error);
                }
            });
	  	},
        editHostSubmit:function(){
            if(this.editHostForm.mac==''){
                alert('mac地址不能为空');
                return;
            }
            this.editHostForm.mac = this.editHostForm.mac.replace(/\W/g,'');
            var that = this;
            that.$http.post('${pageContext.request.contextPath}/UpdateHost',this.editHostForm,{emulateJSON:true,headers:{'Content-Type':'application/x-www-form-urlencoded'}}).then(function(res){
                if(res.body.status==200){
                	this.getData();
                    $('#editHostModal').modal('hide');
                }else{
                    alert(res.body.error);
                }
            });
        },
        enableHost:function(host){
        	var that = this;
            that.$http.get('${pageContext.request.contextPath}/UpdateHostEnable',{params:{areaId:host.areaId,deviceId:host.deviceId,enable:1-host.enable}}).then(function(res){
                if(res.body&&res.body.status==200){
                    this.getData();
                }
            });
        },
        deleteHost:function(host){
            if(confirm("确认删除")){
            	var that = this;
                that.$http.get('${pageContext.request.contextPath}/DeleteHost',{params:{areaId:host.areaId,deviceId:host.deviceId}}).then(function(res){
                    if(res.body&&res.body.status==200){
                        this.getData();
                    }
                });
            }
            
        },
        searchFilter:function(){
            this.areaId = this.filterData.areaId;
            this.getData();
        },
        clearFilter:function(){
            this.filterData.areaId = 0;
            this.areaId = 0;
            this.getData();
        }
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
                <li><a href="userManage.jsp">用户管理</a></li>
                <li class="active"><a href="hostManage.jsp">主机映射</a></li>
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
   		<div class="form-group">
            <label class="" for="name">区域:</label>
            <select v-model="filterData.areaId"  class="form-control select">
            	<option value="0">请选择</option>
            	<option v-for="area in areas" :value="area.iD" >{{area.areaName}}</option>
            </select>
        </div>
        <div class="form-group m-l">
            <button type="button" class="btn btn-primary" @click="searchFilter()">查询</button>
            <button type="button" class="btn btn-danger" @click="clearFilter()">清空</button>
        </div>
        <div class="form-group m-l pull-right">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addHostModal">新增主机映射</button>
        </div>
        <div class="clearfix"></div>
    </form>
    <div class="table-responsive">
        <table class="table table-bordered">
            <thead>
                <tr>
                	<th>区域号</th>
                	<th>设备区域</th>
                    <th>设备号</th>
                    <th>是否启用</th>
                    <th>mac地址</th>
                    <th>备注</th>
                    <th>操作</th>
                   
                </tr>
            </thead>
            <tbody>
                <tr v-for="host in hosts">
                	<td>{{host.areaId}}</td>
                	<td>{{host.areaName}}</td>
                    <td>{{host.deviceId}}</td>
                    <td>{{host.enableStr}}</td>
                    <td>{{host.mac}}</td>
                    <td>{{host.des}}</td>
                    <td><a href="javascript:;" @click="enableHost(host)">{{host.enableEditStr}}</a> <a href="javascript:;" @click="editHost(host)">编辑</a> <a href="javascript:;" @click="deleteHost(host)">删除</a></td>
                </tr>
            </tbody>
        </table>
    </div>
    
    <div class="modal fade" id="addHostModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">添加主机映射</h4>
                </div>
                <div class="modal-body">
                    <div class="form-horizontal" role="form">
                        <div class="form-group m-t">
                            <label for="firstname" class="col-sm-2 control-label">设备区域 <span class="text-danger">*</span></label>
                            <div class="col-sm-8">
                                <select v-model="addHostForm.areaId"  class="form-control" placeholder="请输入设备区域">
                                	<option value="0">请选择</option>
                                	<option v-for="area in areas" :value="area.iD" >{{area.areaName}}</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">设备号 <span class="text-danger">*</span></label>
                            <div class="col-sm-8">
                                <input v-model="addHostForm.deviceId" type="number" class="form-control" placeholder="请输入设备号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">mac地址 <span class="text-danger">*</span></label>
                            <div class="col-sm-8">
                                <input v-model="addHostForm.mac" type="text" class="form-control" placeholder="请输入mac地址">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">备注</label>
                            <div class="col-sm-8">
                                <input v-model="addHostForm.des" type="text" class="form-control" placeholder="请输入备注">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" @click="addHostSubmit()" >添加</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <div class="modal fade" id="editHostModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">编辑</h4>
                </div>
                <div class="modal-body">
                    <div class="form-horizontal" role="form">
                        <div class="form-group m-t">
                            <label for="firstname" class="col-sm-2 control-label">设备区域</label>
                            <div class="col-sm-8">
                                <input v-model="editHostForm.areaName" type="text" readonly="true" class="form-control">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">设备号</label>
                            <div class="col-sm-8">
                                <input v-model="editHostForm.deviceId" type="number" readonly="true" class="form-control">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">mac地址 <span class="text-danger">*</span></label>
                            <div class="col-sm-8">
                                <input v-model="editHostForm.mac" type="text" class="form-control" placeholder="请输入mac地址 ">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">备注</label>
                            <div class="col-sm-8">
                                <input v-model="editHostForm.des" type="text" class="form-control" placeholder="请输入备注">
                            </div>
                        </div>
                        
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" @click="editHostSubmit()" >保存</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    
</div>

</body>
</html>