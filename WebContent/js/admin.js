function formatDevice(device){
   
    switch(device.infoBar){
        case 0:
            device.infoBar='待机状态，按开启键启动';
            break;
        case 1:
            device.infoBar='工作正常，按关闭键停止';
            break;
        case 2:
            device.infoBar='温度过低';
            break;
        case 3:
            device.infoBar='断电报警';
            break;
        case 4:
            device.infoBar='温度超高';
            break;
        case 5:
            device.infoBar='温度过低';
            break;
        case 6:
            device.infoBar='湿度超高';
            break;
        case 7:
            device.infoBar='湿度过低';
            break;
        case 8:
            device.infoBar='压差过高';
            break;
        case 9:
            device.infoBar='压差过低';
            break;
        case 10:
            device.infoBar='模拟量采集通讯故障';
            break;
        case 11:
            device.infoBar='进风自动调节上限';
            break;
        case 12:
            device.infoBar='进风自动调节下限';
            break;
        case 13:
            device.infoBar='模拟量采集通讯故障';
            break;

    }
    
    device.online=device.online?'是':'否';
    
    device.communicateFalse=device.communicateFalse?'是':'否';
    device.communicateTrue=device.communicateTrue?'是':'否';
    device.tempAlarmClose=device.tempAlarmClose?'关':'开';
    device.hrAlarmClose=device.hrAlarmClose?'关':'开';
    device.dpAlarmClose=device.dpAlarmClose?'关':'开';
    device.inWindAlarmClose=device.inWindAlarmClose?'关':'开';
   
    device.inWindSpeed = (device.inWindSpeed/100).toFixed(2);
    device.outWindSpeed = (device.outWindSpeed/100).toFixed(2);
    device.airSpeed10 = (device.airSpeed10/100).toFixed(2);
    device.airSpeed12 = (device.airSpeed12/100).toFixed(2);
    device.airSpeed14 = (device.airSpeed14/100).toFixed(2);
    device.airSpeed16 = (device.airSpeed16/100).toFixed(2);
    device.airSpeed18 = (device.airSpeed18/100).toFixed(2);
    device.airSpeed20 = (device.airSpeed20/100).toFixed(2);
    device.airSpeed22 = (device.airSpeed22/100).toFixed(2);
    device.airSpeed24 = (device.airSpeed24/100).toFixed(2);
    device.airSpeed26 = (device.airSpeed26/100).toFixed(2);
    device.airSpeed28 = (device.airSpeed28/100).toFixed(2);
    device.airSpeed30 = (device.airSpeed30/100).toFixed(2);
    device.airSpeed35 = (device.airSpeed35/100).toFixed(2);
    device.airSpeed40 = (device.airSpeed40/100).toFixed(2);
    device.airSpeed45 = (device.airSpeed45/100).toFixed(2);
    device.airSpeed50 = (device.airSpeed50/100).toFixed(2);
    return device;
}



function logout(){
	console.info(11);
    $.ajax({
    	method:'get',
        url:'/USR/AdminLogout',
        dataType:'json',
        data:{},
        success:function(res){
        	if(res.status==200){
        		location.href="login.jsp";
        	}
        }
    })
}