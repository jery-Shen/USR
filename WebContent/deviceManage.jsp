<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>设备管理</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/admin.css" rel="stylesheet">
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/vue.min.js"></script>
	<script src="js/vue-resource.min.js"></script>
	<script src="js/admin.js"></script>
</head>
<body>

<script type="text/javascript">
$(function(){
	var app = new Vue({
	  created:function(){
	  	this.getData();
	  },
	  data: {
	  	devices:[],
        areaId:0,
        filterData:{areaId:''}
	  },
	  methods: {
        getData:function(){
            var that = this;
            that.$http.get('/USR/GetDeviceList',{params:{areaId:that.areaId}}).then(function(res){
                if(res.body){
                    console.info(res.body.result);
                    that.devices = res.body.result;
                    for(var i=0;i<that.devices.length;i++){
                        that.devices[i] = formatDevice(that.devices[i]);
                    }
                }
            });
        },
        searchFilter:function(){
            this.areaId = this.filterData.areaId;
            this.getData();
        },
        clearFilter:function(){
            this.filterData.areaId = '';
            this.areaId = 0;
            this.getData();
        },
	  	detail:function(deviceId,areaId){
	  		//location.href="deviceDetail.html?deviceId="+deviceId+"&areaId="+areaId;
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
                <li><a href="userManage.jsp">用户管理</a></li>
                <li class="active"><a href="deviceManage.jsp">设备管理</a></li>
                <li><a href="hostManage.jsp">主机映射</a></li>
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
            <label class="" for="name">区域Id:</label>
            <input v-model="filterData.areaId" type="text" class="form-control" id="name" placeholder="输入区域Id">
        </div>
        <div class="form-group m-l">
            <button type="button" class="btn btn-primary" @click="searchFilter()">查询</button>
            <button type="button" class="btn btn-danger" @click="clearFilter()">清空</button>
        </div>
        
        <div class="clearfix"></div>
    </form>
    <div class="table-responsive">
        <table class="table table-bordered">
            <thead>
                <tr>
                	<th>设备区域</th>
                    <th>设备号</th>
                    <th>是否在线</th>
                    <th>系统状态</th>
                    <th>温度</th>
                    <th>湿度</th>
                    <th>压差</th>
                    <th>进风变频速度</th>
                    <th>出风变频速度</th>
                    <th>压差目标值</th>
                    <th>刷新时间</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="device in devices">
                	<td>{{device.areaId}}</td>
                    <td>{{device.deviceId}}</td>
                    <td>{{device.online}}</td>
                    <td>{{device.infoBar}}</td>
                    <td>{{device.temp}}</td>
                    <td>{{device.hr}}</td>
                    <td>{{device.dp}}</td>
                    <td>{{device.inWindSpeed}}</td>
                    <td>{{device.outWindSpeed}}</td>
                    <td>{{device.dpTarget}}</td>
                    <td>{{device.updateTime}}</td>
                </tr>
                
            </tbody>
        </table>
    </div>
    <div class="page" style="display: none;">
        <ul class="pagination">
            <li><a href="#">&laquo;</a></li>
            <li class="active"><a href="#">1</a></li>
            <li class="disabled"><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#">&raquo;</a></li>
        </ul>
        <div class="page-info">搜到5000条记录，当前1/500页</div>
    </div>
</div>

</body>
</html>