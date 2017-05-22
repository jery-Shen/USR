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
	<title>区域管理</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/admin.css" rel="stylesheet">
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/vue.min.js"></script>
	<script src="js/vue-resource.min.js"></script>
	
	<script src="js/admin.js?t=1"></script>
</head>
<body>

<script type="text/javascript">
$(function(){
	var app = new Vue({
	  created:function(){
	  	this.getData();
	  },
	  data: {
	  	areas:[],
        addAreaForm:{
            areaName:''
        },
        editAreaForm:{
        	iD:'',
        	areaName:''
        }
	  },
	  methods: {
        getData:function(){
            var that = this;
            that.$http.get('${pageContext.request.contextPath}/GetAreaList').then(function(res){
                if(res.body){
                	window.localStorage.setItem('areas',JSON.stringify(res.body.result));
                    this.areas = res.body.result;
                    
                }
            });
        },
        editArea:function(area){
            this.editAreaForm.iD=area.iD;
            this.editAreaForm.areaName=area.areaName;
            this.editAreaForm.chargeName=area.chargeName
            this.editAreaForm.chargePhone=area.chargePhone
            $('#editAreaModal').modal();

        },
	  	addAreaSubmit:function(){
            if(this.addAreaForm.areaName==''){
                alert('区域名不能为空');
                return;
            }
            var that = this;
            that.$http.post('${pageContext.request.contextPath}/AddArea',this.addAreaForm,{emulateJSON:true,headers:{'Content-Type':'application/x-www-form-urlencoded'}}).then(function(res){
                if(res.body.status==200){
                    this.addAreaForm = {
                    	areaName:'',
                    };
                    this.getData();
                    $('#addAreaModal').modal('hide');
                }else{
                    alert(res.body.error);
                }
            });
	  	},
        editAreaSubmit:function(){
            if(this.editAreaForm.areaName==''){
                alert('区域名不能为空');
                return;
            }
            var that = this;
            that.$http.post('${pageContext.request.contextPath}/UpdateArea',this.editAreaForm,{emulateJSON:true,headers:{'Content-Type':'application/x-www-form-urlencoded'}}).then(function(res){
                if(res.body.status==200){
                	this.getData();
                    $('#editAreaModal').modal('hide');
                }else{
                    alert(res.body.error);
                }
            });
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
                <li><a href="hostManage.jsp">主机映射</a></li>
                <li class="active"><a href="areaManage.jsp">区域管理</a></li>
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
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addAreaModal">创建区域</button>
        </div>
        <div class="clearfix"></div>
    </form>
    <div class="table-responsive">
        <table class="table table-bordered">
            <thead>
                <tr>
                	<th>区域号</th>
                    <th>区域名</th>
                    <th>负责人</th>
                    <th>联系电话</th>
                    <th>创建时间</th>
                    <th>操作</th>
                   
                </tr>
            </thead>
            <tbody>
                <tr v-for="area in areas">
                	<td>{{area.iD}}</td>
                    <td>{{area.areaName}}</td>
                    <td>{{area.chargeName}}</td>
                    <td>{{area.chargePhone}}</td>
                    <td>{{area.createTime}}</td>
                    <td><a href="javascript:;" @click="editArea(area)">编辑</a></td>
                </tr>
            </tbody>
        </table>
    </div>
    
    
    <div class="modal fade" id="addAreaModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">创建区域</h4>
                </div>
                <div class="modal-body">
                    <div class="form-horizontal" role="form">
                        <div class="form-group m-t">
                            <label for="firstname" class="col-sm-2 control-label">区域名 <span class="text-danger">*</span></label>
                            <div class="col-sm-8">
                                <input v-model="addAreaForm.areaName" type="text" class="form-control" placeholder="请输入区域名">
                            </div>
                        </div>
                        <div class="form-group m-t">
                            <label for="firstname" class="col-sm-2 control-label">负责人</label>
                            <div class="col-sm-8">
                                <input v-model="addAreaForm.chargeName" type="text" class="form-control" placeholder="请输入负责人">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">联系电话</label>
                            <div class="col-sm-8">
                                <input v-model="addAreaForm.chargePhone" type="text" class="form-control" placeholder="请输入设备号">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" @click="addAreaSubmit()" >添加</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <div class="modal fade" id="editAreaModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">编辑</h4>
                </div>
                <div class="modal-body">
                    <div class="form-horizontal" role="form">
                        <div class="form-group m-t">
                            <label for="firstname" class="col-sm-2 control-label">区域Id</label>
                            <div class="col-sm-8">
                                <input v-model="editAreaForm.iD" type="number" readonly="true" class="form-control">
                            </div>
                        </div>
                        <div class="form-group m-t">
                            <label for="firstname" class="col-sm-2 control-label">区域名 <span class="text-danger">*</span></label>
                            <div class="col-sm-8">
                                <input v-model="editAreaForm.areaName" type="text" class="form-control" placeholder="请输入区域名">
                            </div>
                        </div>
                        <div class="form-group m-t">
                            <label for="firstname" class="col-sm-2 control-label">负责人</label>
                            <div class="col-sm-8">
                                <input v-model="editAreaForm.chargeName" type="text" class="form-control" placeholder="请输入负责人">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">联系电话</label>
                            <div class="col-sm-8">
                                <input v-model="editAreaForm.chargePhone" type="text" class="form-control" placeholder="请输入设备号">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" @click="editAreaSubmit()" >保存</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    
</div>

</body>
</html>