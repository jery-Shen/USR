<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
if(session.getAttribute("user") == null){
	//response.sendRedirect(request.getContextPath() + "/login.jsp");
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>IVC联网设备检查程序</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/admin.css?t=2" rel="stylesheet">
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/vue.min.js"></script>
	<script src="js/vue-resource.min.js"></script>
	<script src="js/admin.js?t=5"></script>
</head>
<body class="clearfix" style="background: #f8f8f8;padding: 20px;">

<script type="text/javascript">
$(function(){
	var app = new Vue({
	  created:function(){
	  	this.getData();
	  	this.edit = getQueryString('edit'); 
	  	this.edit = 1;
	  	this.areas = JSON.parse(window.localStorage.getItem('areas'));
	  },
	  data: {
	  	devices:[],
	  	areas:[],
        areaId:0,
        edit:0,
		detailDeviceForm:{ },
        updateDeviceForm:{ },
        updateDeviceCopy:{ },
        filterData:{areaId:0}
	  },
	  methods: {
        getData:function(){
            var that = this;
            that.$http.get('${pageContext.request.contextPath}/GetDeviceList',{params:{areaId:that.areaId}}).then(function(res){
                if(res.body){
                    var devices = res.body.result;
                    that.devices = [];
                    for(var i=0;i<devices.length;i++){
                        devices[i] = formatDevice(devices[i]);
                        devices[i].areaName = getAreaNameById(devices[i].areaId);
                        var alarms = JSON.parse(devices[i].alarmHistory);
                        if(alarms.length>0){
                        	alarms.reverse();
                        	devices[i].alarms = alarms;
                        }
                        if(devices[i].infoBar==0){
                        	devices[i].trClass='text-muted';
                        }else if(devices[i].infoBar==1){
                        	devices[i].trClass='';
                        }else{
                        	devices[i].trClass='text-danger';
                        }
                        if(devices[i].online==0){
                        	devices[i].trClass='text-muted';
                        }
                        that.devices.push(devices[i]);
                    }
                    
                }
            });
        },
        searchFilter:function(){
            this.areaId = this.filterData.areaId;
            this.getData();
        },
        clearFilter:function(){
            this.filterData.areaId = 0;
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
	            that.$http.post('${pageContext.request.contextPath}/UpdateDevice',param,{emulateJSON:true,headers:{'Content-Type':'application/x-www-form-urlencoded'}}).then(function(res){
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
<nav class="navbar navbar-primary navbar-fixed-top" role="navigation">
	<div class="container-fluid"> 
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#example-navbar-collapse">
				<span class="sr-only">切换导航</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="javascript:;">IVC联网设备检查程序</a>
		</div>
	</div>
</nav>

<div id="app" class="content">
    <div class="device-list">
        <div class="device-item">
            <div class="device-item-body">
                <div class="head">
                    <img src="${pageContext.request.contextPath}/img/logo_blue.png">
                    <span class="title">智控1</span>
                    <span class="status">工作正常，按关闭键停止</span>  
                </div>
                <div class="values">
                    <p>
                        <span class="v1">温度：10</span>
                        <span class="v2">换气次数：20</span>
                    </p>
                    <p>
                        <span class="v1">湿度：40</span>
                        <span class="v2">进风速度：20.00</span>
                    </p>
                    <p>
                        <span class="v1">压差：19</span>
                        <span class="v2">目标压差：20</span>
                    </p>
                </div>
            </div>
            <div class="device-item-bottom">
                <a  class="btn">编辑</a>
                <a  class="btn">详情</a>
            </div>
        </div>
        <div class="device-item">
            <div class="device-item-body">
                <div class="head">
                    <img src="${pageContext.request.contextPath}/img/logo_blue.png">
                    <span class="title">智控2</span>
                    <span class="status">工作正常，按关闭键停止</span>
                </div>
                <div class="values">
                    <p>
                        <span class="v1">温度：10</span>
                        <span class="v2">换气次数：20</span>
                    </p>
                    <p>
                        <span class="v1">湿度：40</span>
                        <span class="v2">进风速度：20.00</span>
                    </p>
                    <p>
                        <span class="v1">压差：19</span>
                        <span class="v2">目标压差：20</span>
                    </p>
                </div>
            </div>
            <div class="device-item-bottom">
                <a  class="btn">编辑</a>
                <a  class="btn">详情</a>
            </div>
        </div>
        
        <div class="device-item text-mute">
            <div class="device-item-body">
                <div class="head">
                    <img src="${pageContext.request.contextPath}/img/logo_mute.png">
                    <span class="title">智控3</span>
                    <span class="status">失去连接</span>
                </div>
                <div class="values">
                    <p>
                        <span class="v1">温度：11</span>
                        <span class="v2">换气次数：20</span>
                    </p>
                    <p>
                        <span class="v1">湿度：40</span>
                        <span class="v2">进风速度：20.00</span>
                    </p>
                    <p>
                        <span class="v1">压差：19</span>
                        <span class="v2">目标压差：20</span>
                    </p>
                </div>
            </div>
            <div class="device-item-bottom">
                <a  class="btn">编辑</a>
                <a  class="btn">详情</a>
            </div>
        </div>

        <div class="device-item" :class="device.trClass" v-for="device in devices">
            <div class="device-item-body">
                <div class="head">
                    <img src="${pageContext.request.contextPath}/img/logo_blue.png">
                    <span class="title">智控{{device.deviceId}}</span>
                    <span class="status">{{device.infoBarStr}}</span>
                </div>
                <div class="values">
                    <p>
                        <span class="v1">温度：{{device.temp}}</span>
                        <span class="v2">换气次数：20</span>
                    </p>
                    <p>
                        <span class="v1">湿度：{{device.hr}}</span>
                        <span class="v2">进风速度：20.00</span>
                    </p>
                    <p>
                        <span class="v1">压差：{{device.dp}}</span>
                        <span class="v2">目标压差：20</span>
                    </p>
                </div>
            </div>
            <div class="device-item-bottom">
                <a @click="updateDeivce(device)" class="btn">编辑</a>
                <a href="javascript:;" @click="detailDevice(device)" class="btn">详情</a>
            </div>
        </div>
        <div class="device-item text-danger">
            <div class="device-item-body">
                <div class="head">
                    <img src="${pageContext.request.contextPath}/img/logo_danger.png">
                    <span class="title">智控8</span>
                    <span class="status">温度超高</span>
                </div>
                <div class="values">
                    <p>
                        <span class="v1">温度：45</span>
                        <span class="v2">换气次数：20</span>
                    </p>
                    <p>
                        <span class="v1">湿度：40</span>
                        <span class="v2">进风速度：20.00</span>
                    </p>
                    <p>
                        <span class="v1">压差：19</span>
                        <span class="v2">目标压差：20</span>
                    </p>
                </div>
            </div>
            <div class="device-item-bottom">
                <a  class="btn">编辑</a>
                <a  class="btn">详情</a>
            </div>
        </div>

        <div class="device-item text-mute">
            <div class="device-item-body">
                <div class="head">
                    <img src="${pageContext.request.contextPath}/img/logo_mute.png">
                    <span class="title">智控9</span>
                    <span class="status">待机状态，按开启键启动</span>
                </div>
                <div class="values">
                    <p>
                        <span class="v1">温度：45</span>
                        <span class="v2">换气次数：20</span>
                    </p>
                    <p>
                        <span class="v1">湿度：40</span>
                        <span class="v2">进风速度：20.00</span>
                    </p>
                    <p>
                        <span class="v1">压差：19</span>
                        <span class="v2">目标压差：20</span>
                    </p>
                </div>
            </div>
            <div class="device-item-bottom">
                <a  class="btn">编辑</a>
                <a  class="btn">详情</a>
            </div>
        </div>
        <div class="device-item text-danger">
            <div class="device-item-body">
                <div class="head">
                    <img src="${pageContext.request.contextPath}/img/logo_danger.png">
                    <span class="title">智控25</span>
                    <span class="status">模拟量采集通讯故障</span>
                </div>
                <div class="values">
                    <p>
                        <span class="v1">温度：12</span>
                        <span class="v2">换气次数：20</span>
                    </p>
                    <p>
                        <span class="v1">湿度：40</span>
                        <span class="v2">进风速度：20.00</span>
                    </p>
                    <p>
                        <span class="v1">压差：19</span>
                        <span class="v2">目标压差：20</span>
                    </p>
                </div>
            </div>
            <div class="device-item-bottom">
                <a  class="btn">编辑</a>
                <a  class="btn">详情</a>
            </div>
        </div>
        <div class="device-item">
            <div class="device-item-body">
                <div class="head">
                    <img src="${pageContext.request.contextPath}/img/logo_blue.png">
                    <span class="title">智控26</span>
                    <span class="status">工作正常，按关闭键停止</span> 
                </div>
                <div class="values">
                    <p>
                        <span class="v1">温度：10</span>
                        <span class="v2">换气次数：20</span>
                    </p>
                    <p>
                        <span class="v1">湿度：40</span>
                        <span class="v2">进风速度：20.00</span>
                    </p>
                    <p>
                        <span class="v1">压差：19</span>
                        <span class="v2">目标压差：20</span>
                    </p>
                </div>
            </div>
            <div class="device-item-bottom">
                <a  class="btn">编辑</a>
                <a  class="btn">详情</a>
            </div>
        </div>
        <div class="device-item">
            <div class="device-item-body">
                <div class="head">
                    <img src="${pageContext.request.contextPath}/img/logo_blue.png">
                    <span class="title">智控29</span>
                    <span class="status">工作正常，按关闭键停止</span> 
                </div>
                <div class="values">
                    <p>
                        <span class="v1">温度：11</span>
                        <span class="v2">换气次数：20</span>
                    </p>
                    <p>
                        <span class="v1">湿度：40</span>
                        <span class="v2">进风速度：21.00</span>
                    </p>
                    <p>
                        <span class="v1">压差：19</span>
                        <span class="v2">目标压差：20</span>
                    </p>
                </div>
            </div>
            <div class="device-item-bottom">
                <a  class="btn">编辑</a>
                <a  class="btn">详情</a>
            </div>
        </div>
        <div class="device-item">
            <div class="device-item-body">
                <div class="head">
                    <img src="${pageContext.request.contextPath}/img/logo_blue.png">
                    <span class="title">智控30</span>
                    <span class="status">工作正常，按关闭键停止</span> 
                </div>
                <div class="values">
                    <p>
                        <span class="v1">温度：10</span>
                        <span class="v2">换气次数：20</span>
                    </p>
                    <p>
                        <span class="v1">湿度：40</span>
                        <span class="v2">进风速度：20.00</span>
                    </p>
                    <p>
                        <span class="v1">压差：19</span>
                        <span class="v2">目标压差：20</span>
                    </p>
                </div>
            </div>
            <div class="device-item-bottom">
                <a  class="btn">编辑</a>
                <a  class="btn">详情</a>
            </div>
        </div>
        <div class="device-item">
            <div class="device-item-body">
                <div class="head">
                    <img src="${pageContext.request.contextPath}/img/logo_blue.png">
                    <span class="title">智控31</span>
                    <span class="status">工作正常，按关闭键停止</span> 
                </div>
                <div class="values">
                    <p>
                        <span class="v1">温度：10</span>
                        <span class="v2">换气次数：20</span>
                    </p>
                    <p>
                        <span class="v1">湿度：40</span>
                        <span class="v2">进风速度：20.00</span>
                    </p>
                    <p>
                        <span class="v1">压差：19</span>
                        <span class="v2">目标压差：20</span>
                    </p>
                </div>
            </div>
            <div class="device-item-bottom">
                <a  class="btn">编辑</a>
                <a  class="btn">详情</a>
            </div>
        </div>
    </div>

    
    
    <div class="modal fade" id="detailDeviceModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-md">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">智控{{detailDeviceForm.deviceId}}</h4>
                </div>
                <div class="modal-body">
						<div class="row">
                            <div class="col-md-6">
                                <table class="table  table-bordered">
                                <caption>基本信息</caption>
                            <tbody>
                            	<tr><td>系统信息栏</td><td>{{detailDeviceForm.infoBarStr}}</td></tr>
                                <tr><td>当前温度</td><td>{{detailDeviceForm.temp}}</td></tr>
                                <tr><td>温度上限</td><td>{{detailDeviceForm.tempUpLimit}}</td></tr>
                                <tr><td>温度 下限</td><td>{{detailDeviceForm.tempDownLimit}}</td></tr>
                                <tr><td>当前湿度</td><td>{{detailDeviceForm.hr}}</td></tr>
                                <tr><td>湿度上限</td><td>{{detailDeviceForm.hrUpLimit}}</td></tr>
                                <tr><td>湿度下限</td><td>{{detailDeviceForm.hrDownLimit}}</td></tr>
                                <tr><td>当前压差</td><td>{{detailDeviceForm.dp}}</td></tr>
                                <tr><td>压差上限</td><td>{{detailDeviceForm.dpUpLimit}}</td></tr>
                                <tr><td>压差下限</td><td>{{detailDeviceForm.dpDownLimit}}</td></tr>
                                <tr><td>换气次数</td><td>{{detailDeviceForm.airCount}}</td></tr>
                                <tr><td>进风变频速度</td><td>{{detailDeviceForm.inWindSpeed}}</td></tr>
                                <tr><td>出风变频速度</td><td>{{detailDeviceForm.outWindSpeed}}</td></tr>
                            </tbody>
                        </table>
                            </div>
                                              
                            <div class="col-md-6">
                                <table class="table  table-bordered">
                                <caption>系统参数</caption>
                            <tbody>
                                <tr><td>刷新时间</td><td>{{detailDeviceForm.updateTime}}</td></tr>
                                <tr><td>累计工作时间</td><td>{{detailDeviceForm.workTime}}</td></tr>
                                <tr><td>压差目标值</td><td>{{detailDeviceForm.dpTarget}}</td></tr>
                                <tr><td>正负压模式</td><td>{{detailDeviceForm.akpModeStr}}</td></tr>
                                <tr><td>压差传感器型号选择</td><td>{{detailDeviceForm.converterModelStr}}</td></tr>
                                <tr><td>变频器连续最高</td><td>{{detailDeviceForm.converterMax}}</td></tr>
                                <tr><td>变频器连续最低</td><td>{{detailDeviceForm.converterMin}}</td></tr> 
                                <tr><td>延周期检错</td><td>{{detailDeviceForm.cycleError}}</td></tr>
                                <tr><td>温度报警</td><td>{{detailDeviceForm.tempAlarmCloseStr}}</td></tr>
                                <tr><td>湿度报警</td><td>{{detailDeviceForm.hrAlarmCloseStr}}</td></tr>
                                <tr><td>压差报警</td><td>{{detailDeviceForm.dpAlarmCloseStr}}</td></tr>
                                <tr><td>进风速度上限报警</td><td>{{detailDeviceForm.inWindAlarmCloseStr}}</td></tr>
                                
                                <!-- <tr><td>10次换气速度:{{detailDeviceForm.airSpeed10}}</td><td>12次换气速度:{{detailDeviceForm.airSpeed12}}</td></tr>
                                <tr><td>14次换气速度:{{detailDeviceForm.airSpeed14}}</td><td>16次换气速度:{{detailDeviceForm.airSpeed16}}</td></tr>
                                <tr><td>18次换气速度:{{detailDeviceForm.airSpeed18}}</td><td>20次换气速度:{{detailDeviceForm.airSpeed20}}</td></tr>
                                <tr><td>22次换气速度:{{detailDeviceForm.airSpeed22}}</td><td>24次换气速度:{{detailDeviceForm.airSpeed24}}</td></tr>
                                <tr><td>26次换气速度:{{detailDeviceForm.airSpeed26}}</td><td>28次换气速度:{{detailDeviceForm.airSpeed28}}</td></tr>
                                <tr><td>30次换气速度:{{detailDeviceForm.airSpeed30}}</td><td>35次换气速度:{{detailDeviceForm.airSpeed35}}</td></tr>
                                <tr><td>40次换气速度:{{detailDeviceForm.airSpeed40}}</td><td>45次换气速度:{{detailDeviceForm.airSpeed45}}</td></tr>
                                <tr><td>50次换气速度:{{detailDeviceForm.airSpeed50}}</td><td></td></tr> -->
                            </tbody>
                        </table>

                            </div>                  
                        </div>
                         <table v-if="detailDeviceForm.alarms" class="table table-bordered alarm-table">
                            <caption>历史报警</caption>
                            <tbody>
                                <tr v-for="alarm in detailDeviceForm.alarms"><td>{{alarm.time}} <span>{{alarm.msg}}</span></td></tr>
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