<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
if(session.getAttribute("user") == null){
	response.sendRedirect("/USR/login.jsp");
}
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
	<script src="js/admin.js?t=5"></script>
</head>
<body>

<script type="text/javascript">
$(function(){
	var app = new Vue({
	  created:function(){
	  	this.getData();
	  	this.edit = getQueryString('edit'); 
	  },
	  data: {
	  	devices:[],
        areaId:0,
        edit:0,
		detailDeviceForm:{ },
        updateDeviceForm:{ },
        updateDeviceCopy:{ },
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
        detailDevice:function(device){
	  		this.detailDeviceForm =  device;
	  		$('#detailDeviceModal').modal();
	  	},
	  	updateDeivce:function(device){
	  		this.updateDeviceForm =  device;
	  		this.updateDeviceCopy = extendCopy(device);
	  		$('#updateDeviceModal').modal();
	  	},
	  	updateDeivceSubmit:function(){
	  		var param = {};
			if(this.updateDeviceForm.tempUpLimit!=this.updateDeviceCopy.tempUpLimit){
				param.tempUpLimit = this.updateDeviceForm.tempUpLimit;
			}
			if(this.updateDeviceForm.tempDownLimit!=this.updateDeviceCopy.tempDownLimit){
				param.tempDownLimit = this.updateDeviceForm.tempDownLimit;
			}
			if(this.updateDeviceForm.hrUpLimit!=this.updateDeviceCopy.hrUpLimit){
				param.hrUpLimit = this.updateDeviceForm.hrUpLimit;
			}
			if(this.updateDeviceForm.hrDownLimit!=this.updateDeviceCopy.hrDownLimit){
				param.hrDownLimit = this.updateDeviceForm.hrDownLimit;
			}
			if(this.updateDeviceForm.dpUpLimit!=this.updateDeviceCopy.dpUpLimit){
				param.dpUpLimit = this.updateDeviceForm.dpUpLimit;
			}
			if(this.updateDeviceForm.dpDownLimit!=this.updateDeviceCopy.dpDownLimit){
				param.dpDownLimit = this.updateDeviceForm.dpDownLimit;
			}
			if(this.updateDeviceForm.tempAlarm!=this.updateDeviceCopy.tempAlarm){
				param.tempAlarmClose = Number(!this.updateDeviceForm.tempAlarm);
			}
			if(this.updateDeviceForm.hrAlarm!=this.updateDeviceCopy.hrAlarm){
				param.hrAlarmClose = Number(!this.updateDeviceForm.hrAlarm);
			}
			if(this.updateDeviceForm.dpAlarm!=this.updateDeviceCopy.dpAlarm){
				param.dpAlarmClose = Number(!this.updateDeviceForm.dpAlarm);
			}
			if(this.updateDeviceForm.inWindAlarm!=this.updateDeviceCopy.inWindAlarm){
				param.inWindAlarmClose = Number(!this.updateDeviceForm.inWindAlarm);
			}
			if(!isEmptyObject(param)){
				param.areaId = this.updateDeviceForm.areaId;
				param.deviceId = this.updateDeviceForm.deviceId;
				console.info(param);
				var that = this;
	            that.$http.post('/USR/UpdateDevice',param,{emulateJSON:true,headers:{'Content-Type':'application/x-www-form-urlencoded'}}).then(function(res){
	                if(res.body.status==200){
	                	alert('修改成功');
	                	$('#updateUserModal').modal('hide');
	                }else{
	                    alert(res.body.error);
	                }
	            });
			}
			
	  		
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
                    <th>刷新时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="device in devices">
                	<td>{{device.areaId}}</td>
                    <td>{{device.deviceId}}</td>
                    <td>{{device.onlineStr}}</td>
                    <td>{{device.infoBarStr}}</td>
                    <td>{{device.temp}}</td>
                    <td>{{device.hr}}</td>
                    <td>{{device.dp}}</td>
                    <td>{{device.updateTime}}</td>
                    <td><a href="javascript:;" @click="detailDevice(device)">详情</a> <a v-if="edit" href="javascript:;" @click="updateDeivce(device)">修改</a></td>
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
    
    
    <div class="modal fade" id="detailDeviceModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">设备{{detailDeviceForm.deviceId}}</h4>
                </div>
                <div class="modal-body">
                	
						<table class="table  table-bordered">
							<tbody>
								<tr><td>系统信息栏</td><td>{{detailDeviceForm.infoBarStr}}</td></tr>
								<tr><td>系统开关状态</td><td>{{detailDeviceForm.stateSwitchStr}}</td></tr>
								<tr><td>连续通讯错误</td><td>{{detailDeviceForm.communicateFalseStr}}</td></tr>
								<tr><td>刷新时间</td><td>{{detailDeviceForm.updateTime}}</td></tr>
								<tr><td>温度</td><td>当前:{{detailDeviceForm.temp}}， 上限:{{detailDeviceForm.tempUpLimit}}， 下限:{{detailDeviceForm.tempDownLimit}}</td></tr>
								<tr><td>湿度</td><td>当前:{{detailDeviceForm.hr}}， 上限:{{detailDeviceForm.hrUpLimit}}， 下限:{{detailDeviceForm.hrDownLimit}}</td></tr>
								<tr><td>压差</td><td>当前:{{detailDeviceForm.dp}}， 上限:{{detailDeviceForm.dpUpLimit}}， 下限:{{detailDeviceForm.dpDownLimit}}</td></tr>
								<tr><td>换气次数</td><td>{{detailDeviceForm.airCount}}</td></tr>
								<tr><td>进风变频速度</td><td>{{detailDeviceForm.inWindSpeed}}</td></tr>
								<tr><td>出风变频速度</td><td>{{detailDeviceForm.outWindSpeed}}</td></tr>
								
								<tr><td>压差目标值</td><td>{{detailDeviceForm.dpTarget}}</td></tr>
								<tr><td>正负压模式</td><td>{{detailDeviceForm.akpModeStr}}</td></tr>
								<tr><td>压差传感器型号选择</td><td>{{detailDeviceForm.converterModelStr}}</td></tr>
								<tr><td>变频器连续最高</td><td>{{detailDeviceForm.converterMax}}</td></tr>
								<tr><td>变频器连续最低</td><td>{{detailDeviceForm.converterMin}}</td></tr>
								
								<tr><td>延周期检错</td><td>{{detailDeviceForm.cycleError}}</td></tr>
								<tr><td>连续报警周期数</td><td>{{detailDeviceForm.alarmCycle}}</td></tr>
								
								<tr><td>温度报警</td><td>{{detailDeviceForm.tempAlarmCloseStr}}</td></tr>
								<tr><td>湿度报警</td><td>{{detailDeviceForm.hrAlarmCloseStr}}</td></tr>
								<tr><td>压差报警</td><td>{{detailDeviceForm.dpAlarmCloseStr}}</td></tr>
								<tr><td>进风速度上限报警</td><td>{{detailDeviceForm.inWindAlarmCloseStr}}</td></tr>
								<tr><td>累计工作时间</td><td>{{detailDeviceForm.workTime}}</td></tr>
								
								<tr><td>10次换气速度:{{detailDeviceForm.airSpeed10}}</td><td>12次换气速度:{{detailDeviceForm.airSpeed12}}</td></tr>
								<tr><td>14次换气速度:{{detailDeviceForm.airSpeed14}}</td><td>16次换气速度:{{detailDeviceForm.airSpeed16}}</td></tr>
								<tr><td>18次换气速度:{{detailDeviceForm.airSpeed18}}</td><td>20次换气速度:{{detailDeviceForm.airSpeed20}}</td></tr>
								<tr><td>22次换气速度:{{detailDeviceForm.airSpeed22}}</td><td>24次换气速度:{{detailDeviceForm.airSpeed24}}</td></tr>
								<tr><td>26次换气速度:{{detailDeviceForm.airSpeed26}}</td><td>28次换气速度:{{detailDeviceForm.airSpeed28}}</td></tr>
								<tr><td>30次换气速度:{{detailDeviceForm.airSpeed30}}</td><td>35次换气速度:{{detailDeviceForm.airSpeed35}}</td></tr>
								<tr><td>40次换气速度:{{detailDeviceForm.airSpeed40}}</td><td>45次换气速度:{{detailDeviceForm.airSpeed45}}</td></tr>
								<tr><td>50次换气速度:{{detailDeviceForm.airSpeed50}}</td><td></td></tr>
							</tbody>
						</table>
					</div>
                
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    
    <div class="modal fade" id="updateDeviceModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">修改参数</h4>
                </div>
                <div class="modal-body">
                    <div class="form-horizontal" role="form">
                        
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">当前温度</label>
                            <div class="col-sm-8">
                                <input v-model="updateDeviceForm.temp" readonly="true" type="number" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">温度上限</label>
                            <div class="col-sm-8">
                                <input v-model="updateDeviceForm.tempUpLimit" type="number" class="form-control" placeholder="请输入温度上限">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">温度下限 </label>
                            <div class="col-sm-8">
                                <input v-model="updateDeviceForm.tempDownLimit" type="number" class="form-control" placeholder="请输入温度下限">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">当前湿度 </label>
                            <div class="col-sm-8">
                                <input v-model="updateDeviceForm.hr" type="number" readonly="true" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">湿度上限 </label>
                            <div class="col-sm-8">
                                <input v-model="updateDeviceForm.hrUpLimit" type="number" class="form-control" placeholder="请输入湿度上限">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">湿度下限 </label>
                            <div class="col-sm-8">
                                <input v-model="updateDeviceForm.hrDownLimit" type="number" class="form-control" placeholder="请输入湿度下限">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">当前压差 </label>
                            <div class="col-sm-8">
                                <input v-model="updateDeviceForm.dp" readonly="true" type="number" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">压差上限</label>
                            <div class="col-sm-8">
                                <input v-model="updateDeviceForm.dpUpLimit" type="number" class="form-control" placeholder="请输入压差上限">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">压差下限</label>
                            <div class="col-sm-8">
                                <input v-model="updateDeviceForm.dpDownLimit" type="number" class="form-control" placeholder="请输入压差下限">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">温度报警</label>
                            <div class="col-sm-8">
                               <div class="checkbox">
							    <label>
							      <input v-model="updateDeviceForm.tempAlarm" type="checkbox">
							    </label>
							  </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">湿度报警</label>
                            <div class="col-sm-8">
                                <div class="checkbox">
								    <label>
								      <input v-model="updateDeviceForm.hrAlarm" type="checkbox">
								    </label>
								  </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">压差报警</label>
                            <div class="col-sm-8">
                                <div class="checkbox">
							    <label>
							      <input v-model="updateDeviceForm.dpAlarm" type="checkbox">
							    </label>
							  </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">进风报警</label>
                            <div class="col-sm-8">
                                <div class="checkbox">
							    <label>
							      <input v-model="updateDeviceForm.inWindAlarm" type="checkbox">
							    </label>
							  </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" @click="updateDeivceSubmit()" >保存</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    
</div>

</body>
</html>