function formatDevice(device) {
	switch (device.infoBar) {
	case 0:
		device.infoBarStr = '待机状态，按开启键启动';
		break;
	case 1:
		device.infoBarStr = '工作正常，按关闭键停止';
		break;
	case 2:
		device.infoBarStr = '温度过低';
		break;
	case 3:
		device.infoBarStr = '断电报警';
		break;
	case 4:
		device.infoBarStr = '温度超高';
		break;
	case 5:
		device.infoBarStr = '温度过低';
		break;
	case 6:
		device.infoBarStr = '湿度超高';
		break;
	case 7:
		device.infoBarStr = '湿度过低';
		break;
	case 8:
		device.infoBarStr = '压差过高';
		break;
	case 9:
		device.infoBarStr = '压差过低';
		break;
	case 10:
		device.infoBarStr = '模拟量采集通讯故障';
		break;
	case 11:
		device.infoBarStr = '进风自动调节上限';
		break;
	case 12:
		device.infoBarStr = '进风自动调节下限';
		break;
	case 13:
		device.infoBarStr = '模拟量采集通讯故障';
		break;

	}
	
	device.workModeStr = device.workMode ? '自动' : '手动';
	device.onlineStr = device.online ? '是' : '否';
	device.communicateFalseStr = (device.communicateFalse == 0) ? '-'
			: device.communicateFalse + '次';
	device.communicateTrueStr = (device.communicateTrue == 0) ? '-'
			: device.communicateTrue + '次';
	device.akpModeStr = device.akpMode ? '正压' : '负压';
	device.converterModelStr = device.converterModel ? '西特263' : '上海森创';

	device.stateSwitchStr = device.stateSwitch ? '关' : '开';
	device.tempAlarmCloseStr = device.tempAlarmClose ? '关' : '开';
	device.hrAlarmCloseStr = device.hrAlarmClose ? '关' : '开';
	device.dpAlarmCloseStr = device.dpAlarmClose ? '关' : '开';
	device.inWindAlarmCloseStr = device.inWindAlarmClose ? '关' : '开';
	device.workTime = device.workHour + '小时'
			+ (device.workSecond / 60).toFixed(0) + '分钟'
			+ (device.workSecond % 60) + "秒";

	device.tempAlarm = !device.tempAlarmClose;
	device.hrAlarm = !device.hrAlarmClose;
	device.dpAlarm = !device.dpAlarmClose;
	device.inWindAlarm = !device.inWindAlarmClose;

	device.inWindSpeed = (device.inWindSpeed / 100).toFixed(2);
	device.outWindSpeed = (device.outWindSpeed / 100).toFixed(2);
	device.airSpeed10 = (device.airSpeed10 / 100).toFixed(2);
	device.airSpeed12 = (device.airSpeed12 / 100).toFixed(2);
	device.airSpeed14 = (device.airSpeed14 / 100).toFixed(2);
	device.airSpeed16 = (device.airSpeed16 / 100).toFixed(2);
	device.airSpeed18 = (device.airSpeed18 / 100).toFixed(2);
	device.airSpeed20 = (device.airSpeed20 / 100).toFixed(2);
	device.airSpeed22 = (device.airSpeed22 / 100).toFixed(2);
	device.airSpeed24 = (device.airSpeed24 / 100).toFixed(2);
	device.airSpeed26 = (device.airSpeed26 / 100).toFixed(2);
	device.airSpeed28 = (device.airSpeed28 / 100).toFixed(2);
	device.airSpeed30 = (device.airSpeed30 / 100).toFixed(2);
	device.airSpeed35 = (device.airSpeed35 / 100).toFixed(2);
	device.airSpeed40 = (device.airSpeed40 / 100).toFixed(2);
	device.airSpeed45 = (device.airSpeed45 / 100).toFixed(2);
	device.airSpeed50 = (device.airSpeed50 / 100).toFixed(2);
	return device;
}

function logout() {
	$.ajax({
		method : 'get',
		url : 'AdminLogout',
		dataType : 'json',
		data : {},
		success : function(res) {
			if (res.status == 200) {
				location.href = "login.jsp";
			}
		}
	})
}


function getAreaNameById(areaId){
	var areas = JSON.parse(window.localStorage.getItem('areas'));
	for(var i=0;i<areas.length;i++){
		if(areas[i].iD == areaId){
			return areas[i].areaName;
		}
	}
	return '';
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return decodeURIComponent(r[2]);
	return 0;
}

function extendCopy(p) {
	var c = {};
	for ( var i in p) {
		c[i] = p[i];
	}
	c.uber = p;
	return c;
}

function isEmptyObject(e) {  
    var t;  
    for (t in e)  
        return !1;  
    return !0  
} 

