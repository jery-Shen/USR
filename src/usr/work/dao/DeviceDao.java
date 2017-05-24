package usr.work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import usr.work.bean.Device;
import usr.work.utils.DBO;

public class DeviceDao {
	
	public boolean saveOrUpdate(Device device) {
		String sql = "insert into u_device(u_online,u_area_id,u_device_id,u_device_ip,u_update_time,"
				+ "u_temp,u_temp_up_limit,u_temp_down_limit,u_temp_off,u_temp_really,"
				+ "u_work_mode,u_air_count,u_in_wind_speed,u_out_wind_speed,"
				+ "u_hr,u_hr_up_limit,u_hr_down_limit,u_hr_off,u_hr_really,"
				+ "u_communicate_false,u_communicate_true,u_info_bar,u_state_switch,"
				+ "u_dp,u_dp_up_limit,u_dp_down_limit,u_dp_off,u_dp_really,u_dp_target,u_akp_mode,"
				+ "u_work_hour,u_work_second,u_converter_max,u_converter_min,u_converter_model,u_cycle_error,"
				+ "u_alarm_cycle,u_temp_alarm_close,u_hr_alarm_close,u_dp_alarm_close,u_in_wind_alarm_cLose,"
				+ "u_air_speed10,u_air_speed12,u_air_speed14,u_air_speed16,u_air_speed18,"
				+ "u_air_speed20,u_air_speed22,u_air_speed24,u_air_speed26,u_air_speed28,"
				+ "u_air_speed30,u_air_speed35,u_air_speed40,u_air_speed45,u_air_speed50) "
				+ "values(1,?,?,?,now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
				+ ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update "
				+ "u_online=1,u_update_time=now(),u_temp=?,u_temp_up_limit=?,u_temp_down_limit=?,u_temp_off=?,u_temp_really=?,"
				+ "u_work_mode=?,u_air_count=?,u_in_wind_speed=?,u_out_wind_speed=?,"
				+ "u_hr=?,u_hr_up_limit=?,u_hr_down_limit=?,u_hr_off=?,u_hr_really=?,"
				+ "u_communicate_false=?,u_communicate_true=?,u_info_bar=?,u_state_switch=?,"
				+ "u_dp=?,u_dp_up_limit=?,u_dp_down_limit=?,u_dp_off=?,u_dp_really=?,u_dp_target=?,u_akp_mode=?,"
				+ "u_work_hour=?,u_work_second=?,u_converter_max=?,u_converter_min=?,u_converter_model=?,u_cycle_error=?,"
				+ "u_alarm_cycle=?,u_temp_alarm_close=?,u_hr_alarm_close=?,u_dp_alarm_close=?,u_in_wind_alarm_cLose=?,"
				+ "u_air_speed10=?,u_air_speed12=?,u_air_speed14=?,u_air_speed16=?,u_air_speed18=?,"
				+ "u_air_speed20=?,u_air_speed22=?,u_air_speed24=?,u_air_speed26=?,u_air_speed28=?,"
				+ "u_air_speed30=?,u_air_speed35=?,u_air_speed40=?,u_air_speed45=?,u_air_speed50=?";
		PreparedStatement pstmt = null;
		boolean flag = false;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, device.getAreaId());
			pstmt.setInt(2, device.getDeviceId());
			pstmt.setString(3, device.getDeviceIp());
			pstmt.setInt(4, device.getTemp());
			pstmt.setInt(5, device.getTempUpLimit());
			pstmt.setInt(6, device.getTempDownLimit());
			pstmt.setInt(7, device.getTempOff());
			pstmt.setInt(8, device.getTempReally());
			pstmt.setInt(9, device.getWorkMode());
			pstmt.setInt(10, device.getAirCount());
			pstmt.setInt(11, device.getInWindSpeed());
			pstmt.setInt(12, device.getOutWindSpeed());
			pstmt.setInt(13, device.getHr());
			pstmt.setInt(14, device.getHrUpLimit());
			pstmt.setInt(15, device.getHrDownLimit());
			pstmt.setInt(16, device.getHrOff());
			pstmt.setInt(17, device.getHrReally());
			pstmt.setInt(18, device.getCommunicateFalse());
			pstmt.setInt(19, device.getCommunicateTrue());
			pstmt.setInt(20, device.getInfoBar());
			pstmt.setInt(21, device.getStateSwitch());
			pstmt.setInt(22, device.getDp());
			pstmt.setInt(23, device.getDpUpLimit());
			pstmt.setInt(24, device.getDpDownLimit());
			pstmt.setInt(25, device.getDpOff());
			pstmt.setInt(26, device.getDpReally());
			pstmt.setInt(27, device.getDpTarget());
			pstmt.setInt(28, device.getAkpMode());
			pstmt.setInt(29, device.getWorkHour());
			pstmt.setInt(30, device.getWorkSecond());
			pstmt.setInt(31, device.getConverterMax());
			pstmt.setInt(32, device.getConverterMin());
			pstmt.setInt(33, device.getConverterModel());
			pstmt.setInt(34, device.getCycleError());
			pstmt.setInt(35, device.getAlarmCycle());
			pstmt.setInt(36, device.getTempAlarmClose());
			pstmt.setInt(37, device.getHrAlarmClose());
			pstmt.setInt(38, device.getDpAlarmClose());
			pstmt.setInt(39, device.getInWindAlarmClose());
			pstmt.setInt(40, device.getAirSpeed10());
			pstmt.setInt(41, device.getAirSpeed12());
			pstmt.setInt(42, device.getAirSpeed14());
			pstmt.setInt(43, device.getAirSpeed16());
			pstmt.setInt(44, device.getAirSpeed18());
			pstmt.setInt(45, device.getAirSpeed20());
			pstmt.setInt(46, device.getAirSpeed22());
			pstmt.setInt(47, device.getAirSpeed24());
			pstmt.setInt(48, device.getAirSpeed26());
			pstmt.setInt(49, device.getAirSpeed28());
			pstmt.setInt(50, device.getAirSpeed30());
			pstmt.setInt(51, device.getAirSpeed35());
			pstmt.setInt(52, device.getAirSpeed40());
			pstmt.setInt(53, device.getAirSpeed45());
			pstmt.setInt(54, device.getAirSpeed50());
			pstmt.setInt(55, device.getTemp());
			pstmt.setInt(56, device.getTempUpLimit());
			pstmt.setInt(57, device.getTempDownLimit());
			pstmt.setInt(58, device.getTempOff());
			pstmt.setInt(59, device.getTempReally());
			pstmt.setInt(60, device.getWorkMode());
			pstmt.setInt(61, device.getAirCount());
			pstmt.setInt(62, device.getInWindSpeed());
			pstmt.setInt(63, device.getOutWindSpeed());
			pstmt.setInt(64, device.getHr());
			pstmt.setInt(65, device.getHrUpLimit());
			pstmt.setInt(66, device.getHrDownLimit());
			pstmt.setInt(67, device.getHrOff());
			pstmt.setInt(68, device.getHrReally());
			pstmt.setInt(69, device.getCommunicateFalse());
			pstmt.setInt(70, device.getCommunicateTrue());
			pstmt.setInt(71, device.getInfoBar());
			pstmt.setInt(72, device.getStateSwitch());
			pstmt.setInt(73, device.getDp());
			pstmt.setInt(74, device.getDpUpLimit());
			pstmt.setInt(75, device.getDpDownLimit());
			pstmt.setInt(76, device.getDpOff());
			pstmt.setInt(77, device.getDpReally());
			pstmt.setInt(78, device.getDpTarget());
			pstmt.setInt(79, device.getAkpMode());
			pstmt.setInt(80, device.getWorkHour());
			pstmt.setInt(81, device.getWorkSecond());
			pstmt.setInt(82, device.getConverterMax());
			pstmt.setInt(83, device.getConverterMin());
			pstmt.setInt(84, device.getConverterModel());
			pstmt.setInt(85, device.getCycleError());
			pstmt.setInt(86, device.getAlarmCycle());
			pstmt.setInt(87, device.getTempAlarmClose());
			pstmt.setInt(88, device.getHrAlarmClose());
			pstmt.setInt(89, device.getDpAlarmClose());
			pstmt.setInt(90, device.getInWindAlarmClose());
			pstmt.setInt(91, device.getAirSpeed10());
			pstmt.setInt(92, device.getAirSpeed12());
			pstmt.setInt(93, device.getAirSpeed14());
			pstmt.setInt(94, device.getAirSpeed16());
			pstmt.setInt(95, device.getAirSpeed18());
			pstmt.setInt(96, device.getAirSpeed20());
			pstmt.setInt(97, device.getAirSpeed22());
			pstmt.setInt(98, device.getAirSpeed24());
			pstmt.setInt(99, device.getAirSpeed26());
			pstmt.setInt(100, device.getAirSpeed28());
			pstmt.setInt(101, device.getAirSpeed30());
			pstmt.setInt(102, device.getAirSpeed35());
			pstmt.setInt(103, device.getAirSpeed40());
			pstmt.setInt(104, device.getAirSpeed45());
			pstmt.setInt(105, device.getAirSpeed50());
			pstmt.executeUpdate();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt);
		}
		return flag;
	}
	
	public boolean add(Device device){
		boolean flag = false;
		String sql = "insert into u_device(u_area_id,u_device_id,u_mac,u_des,u_update_time) values(?,?,?,?,now())";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,device.getAreaId());
			pstmt.setInt(2, device.getDeviceId());
			pstmt.setString(3, device.getMac());
			pstmt.setString(4, device.getDes());
			pstmt.executeUpdate();
			flag = true;
		} catch (Exception e) {
			
		} finally {
			DBO.close(conn, pstmt);
		}
		return flag;
	}
	
	public boolean update(Device device){
		boolean flag = false;
		String sql ="update u_device set "
				+ "u_update_time=now(),u_online=?,u_enable=?,u_device_ip=?,u_temp=?,u_temp_up_limit=?,u_temp_down_limit=?,u_temp_off=?,u_temp_really=?,"
				+ "u_work_mode=?,u_air_count=?,u_in_wind_speed=?,u_out_wind_speed=?,"
				+ "u_hr=?,u_hr_up_limit=?,u_hr_down_limit=?,u_hr_off=?,u_hr_really=?,"
				+ "u_communicate_false=?,u_communicate_true=?,u_info_bar=?,u_state_switch=?,"
				+ "u_dp=?,u_dp_up_limit=?,u_dp_down_limit=?,u_dp_off=?,u_dp_really=?,u_dp_target=?,u_akp_mode=?,"
				+ "u_work_hour=?,u_work_second=?,u_converter_max=?,u_converter_min=?,u_converter_model=?,u_cycle_error=?,"
				+ "u_alarm_cycle=?,u_temp_alarm_close=?,u_hr_alarm_close=?,u_dp_alarm_close=?,u_in_wind_alarm_cLose=?,u_alarm_history=? "
				+ "where u_area_id=? and u_device_id=?";
		PreparedStatement pstmt =null;
		Connection conn=null;
		try {
			conn=DBO.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, device.getOnline());
			pstmt.setInt(2, device.getEnable());
			pstmt.setString(3, device.getDeviceIp());
			pstmt.setInt(4, device.getTemp());
			pstmt.setInt(5, device.getTempUpLimit());
			pstmt.setInt(6, device.getTempDownLimit());
			pstmt.setInt(7, device.getTempOff());
			pstmt.setInt(8, device.getTempReally());
			pstmt.setInt(9, device.getWorkMode());
			pstmt.setInt(10, device.getAirCount());
			pstmt.setInt(11, device.getInWindSpeed());
			pstmt.setInt(12, device.getOutWindSpeed());
			pstmt.setInt(13, device.getHr());
			pstmt.setInt(14, device.getHrUpLimit());
			pstmt.setInt(15, device.getHrDownLimit());
			pstmt.setInt(16, device.getHrOff());
			pstmt.setInt(17, device.getHrReally());
			pstmt.setInt(18, device.getCommunicateFalse());
			pstmt.setInt(19, device.getCommunicateTrue());
			pstmt.setInt(20, device.getInfoBar());
			pstmt.setInt(21, device.getStateSwitch());
			pstmt.setInt(22, device.getDp());
			pstmt.setInt(23, device.getDpUpLimit());
			pstmt.setInt(24, device.getDpDownLimit());
			pstmt.setInt(25, device.getDpOff());
			pstmt.setInt(26, device.getDpReally());
			pstmt.setInt(27, device.getDpTarget());
			pstmt.setInt(28, device.getAkpMode());
			pstmt.setInt(29, device.getWorkHour());
			pstmt.setInt(30, device.getWorkSecond());
			pstmt.setInt(31, device.getConverterMax());
			pstmt.setInt(32, device.getConverterMin());
			pstmt.setInt(33, device.getConverterModel());
			pstmt.setInt(34, device.getCycleError());
			pstmt.setInt(35, device.getAlarmCycle());
			pstmt.setInt(36, device.getTempAlarmClose());
			pstmt.setInt(37, device.getHrAlarmClose());
			pstmt.setInt(38, device.getDpAlarmClose());
			pstmt.setInt(39, device.getInWindAlarmClose());
			pstmt.setString(40, device.getAlarmHistory());
			pstmt.setInt(41, device.getAreaId());
			pstmt.setInt(42, device.getDeviceId());
			if(pstmt.executeUpdate()>0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBO.close(conn,pstmt);
		}
		return flag;
	}

	public Device get(int areaId,int deviceId) {
		Device device = null;
		String sql = "select * from u_device where u_area_id=? and u_device_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaId);
			pstmt.setInt(2, deviceId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				device = new Device();
				device.setOnline(rs.getInt("u_online"));
				device.setEnable(rs.getInt("u_enable"));
				device.setMac(rs.getString("u_mac"));
				device.setDes(rs.getString("u_res"));
				device.setAreaId(rs.getInt("u_area_id"));
				device.setDeviceId(rs.getInt("u_device_id"));
				device.setDeviceIp(rs.getString("u_device_ip"));
				device.setUpdateTime(rs.getString("u_update_time"));
				device.setTemp(rs.getInt("u_temp"));
				device.setTempUpLimit(rs.getInt("u_temp_up_limit"));
				device.setTempDownLimit(rs.getInt("u_temp_down_limit"));
				device.setTempOff(rs.getInt("u_temp_off"));
				device.setTempReally(rs.getInt("u_temp_really"));
				device.setWorkMode(rs.getInt("u_work_mode"));
				device.setAirCount(rs.getInt("u_air_count"));
				device.setInWindSpeed(rs.getInt("u_in_wind_speed"));
				device.setOutWindSpeed(rs.getInt("u_out_wind_speed"));
				device.setHr(rs.getInt("u_hr"));
				device.setHrUpLimit(rs.getInt("u_hr_up_limit"));
				device.setHrDownLimit(rs.getInt("u_hr_down_limit"));
				device.setHrOff(rs.getInt("u_hr_off"));
				device.setHrReally(rs.getInt("u_hr_really"));
				device.setCommunicateFalse(rs.getInt("u_communicate_false"));
				device.setCommunicateTrue(rs.getInt("u_communicate_true"));
				device.setInfoBar(rs.getInt("u_info_bar"));
				device.setStateSwitch(rs.getInt("u_state_switch"));
				device.setDp(rs.getInt("u_dp"));
				device.setDpUpLimit(rs.getInt("u_dp_up_limit"));
				device.setDpDownLimit(rs.getInt("u_dp_down_limit"));
				device.setDpOff(rs.getInt("u_dp_off"));
				device.setDpReally(rs.getInt("u_dp_really"));
				device.setDpTarget(rs.getInt("u_dp_target"));
				device.setAkpMode(rs.getInt("u_akp_mode"));
				device.setWorkHour(rs.getInt("u_work_hour"));
				device.setWorkSecond(rs.getInt("u_work_second"));
				device.setConverterMax(rs.getInt("u_converter_max"));
				device.setConverterMin(rs.getInt("u_converter_min"));
				device.setConverterModel(rs.getInt("u_converter_model"));
				device.setCycleError(rs.getInt("u_cycle_error"));
				device.setTempAlarmClose(rs.getInt("u_temp_alarm_close"));
				device.setHrAlarmClose(rs.getInt("u_hr_alarm_close"));
				device.setDpAlarmClose(rs.getInt("u_dp_alarm_close"));
				device.setInWindAlarmClose(rs.getInt("u_in_wind_alarm_cLose"));
				device.setAirSpeed10(rs.getInt("u_air_speed10"));
				device.setAirSpeed12(rs.getInt("u_air_speed12"));
				device.setAirSpeed14(rs.getInt("u_air_speed14"));
				device.setAirSpeed16(rs.getInt("u_air_speed16"));
				device.setAirSpeed18(rs.getInt("u_air_speed18"));
				device.setAirSpeed20(rs.getInt("u_air_speed20"));
				device.setAirSpeed22(rs.getInt("u_air_speed22"));
				device.setAirSpeed24(rs.getInt("u_air_speed24"));
				device.setAirSpeed26(rs.getInt("u_air_speed26"));
				device.setAirSpeed28(rs.getInt("u_air_speed28"));
				device.setAirSpeed30(rs.getInt("u_air_speed30"));
				device.setAirSpeed35(rs.getInt("u_air_speed35"));
				device.setAirSpeed40(rs.getInt("u_air_speed40"));
				device.setAirSpeed45(rs.getInt("u_air_speed45"));
				device.setAirSpeed50(rs.getInt("u_air_speed50"));
				device.setAlarmHistory(rs.getString("u_alarm_history"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt, rs);
		}
		return device;
	}		

	public List<Device> getList(int areaId) {
		
		List<Device> deviceList = new ArrayList<>();
		
		String sql = "select * from u_device where u_area_id=? order by u_device_id asc";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Device device = new Device();
				device.setOnline(rs.getInt("u_online"));
				device.setEnable(rs.getInt("u_enable"));
				device.setMac(rs.getString("u_mac"));
				device.setDes(rs.getString("u_res"));
				device.setAreaId(rs.getInt("u_area_id"));
				device.setDeviceId(rs.getInt("u_device_id"));
				device.setDeviceIp(rs.getString("u_device_ip"));
				device.setUpdateTime(rs.getString("u_update_time"));
				device.setTemp(rs.getInt("u_temp"));
				device.setTempUpLimit(rs.getInt("u_temp_up_limit"));
				device.setTempDownLimit(rs.getInt("u_temp_down_limit"));
				device.setTempOff(rs.getInt("u_temp_off"));
				device.setTempReally(rs.getInt("u_temp_really"));
				device.setWorkMode(rs.getInt("u_work_mode"));
				device.setAirCount(rs.getInt("u_air_count"));
				device.setInWindSpeed(rs.getInt("u_in_wind_speed"));
				device.setOutWindSpeed(rs.getInt("u_out_wind_speed"));
				device.setHr(rs.getInt("u_hr"));
				device.setHrUpLimit(rs.getInt("u_hr_up_limit"));
				device.setHrDownLimit(rs.getInt("u_hr_down_limit"));
				device.setHrOff(rs.getInt("u_hr_off"));
				device.setHrReally(rs.getInt("u_hr_really"));
				device.setCommunicateFalse(rs.getInt("u_communicate_false"));
				device.setCommunicateTrue(rs.getInt("u_communicate_true"));
				device.setInfoBar(rs.getInt("u_info_bar"));
				device.setStateSwitch(rs.getInt("u_state_switch"));
				device.setDp(rs.getInt("u_dp"));
				device.setDpUpLimit(rs.getInt("u_dp_up_limit"));
				device.setDpDownLimit(rs.getInt("u_dp_down_limit"));
				device.setDpOff(rs.getInt("u_dp_off"));
				device.setDpReally(rs.getInt("u_dp_really"));
				device.setDpTarget(rs.getInt("u_dp_target"));
				device.setAkpMode(rs.getInt("u_akp_mode"));
				device.setWorkHour(rs.getInt("u_work_hour"));
				device.setWorkSecond(rs.getInt("u_work_second"));
				device.setConverterMax(rs.getInt("u_converter_max"));
				device.setConverterMin(rs.getInt("u_converter_min"));
				device.setConverterModel(rs.getInt("u_converter_model"));
				device.setCycleError(rs.getInt("u_cycle_error"));
				device.setTempAlarmClose(rs.getInt("u_temp_alarm_close"));
				device.setHrAlarmClose(rs.getInt("u_hr_alarm_close"));
				device.setDpAlarmClose(rs.getInt("u_dp_alarm_close"));
				device.setInWindAlarmClose(rs.getInt("u_in_wind_alarm_cLose"));
				device.setAirSpeed10(rs.getInt("u_air_speed10"));
				device.setAirSpeed12(rs.getInt("u_air_speed12"));
				device.setAirSpeed14(rs.getInt("u_air_speed14"));
				device.setAirSpeed16(rs.getInt("u_air_speed16"));
				device.setAirSpeed18(rs.getInt("u_air_speed18"));
				device.setAirSpeed20(rs.getInt("u_air_speed20"));
				device.setAirSpeed22(rs.getInt("u_air_speed22"));
				device.setAirSpeed24(rs.getInt("u_air_speed24"));
				device.setAirSpeed26(rs.getInt("u_air_speed26"));
				device.setAirSpeed28(rs.getInt("u_air_speed28"));
				device.setAirSpeed30(rs.getInt("u_air_speed30"));
				device.setAirSpeed35(rs.getInt("u_air_speed35"));
				device.setAirSpeed40(rs.getInt("u_air_speed40"));
				device.setAirSpeed45(rs.getInt("u_air_speed45"));
				device.setAirSpeed50(rs.getInt("u_air_speed50"));
				device.setAlarmHistory(rs.getString("u_alarm_history"));
				deviceList.add(device);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt, rs);
		}
		return deviceList;
	}
	
	public List<Device> getList() {
		
		List<Device> deviceList = new ArrayList<>();
		String sql = "select * from u_device order by u_area_id asc,u_device_id asc";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Device device = new Device();
				device.setOnline(rs.getInt("u_online"));
				device.setEnable(rs.getInt("u_enable"));
				device.setMac(rs.getString("u_mac"));
				device.setDes(rs.getString("u_res"));
				device.setAreaId(rs.getInt("u_area_id"));
				device.setDeviceId(rs.getInt("u_device_id"));
				device.setDeviceIp(rs.getString("u_device_ip"));
				device.setUpdateTime(rs.getString("u_update_time"));
				device.setTemp(rs.getInt("u_temp"));
				device.setTempUpLimit(rs.getInt("u_temp_up_limit"));
				device.setTempDownLimit(rs.getInt("u_temp_down_limit"));
				device.setTempOff(rs.getInt("u_temp_off"));
				device.setTempReally(rs.getInt("u_temp_really"));
				device.setWorkMode(rs.getInt("u_work_mode"));
				device.setAirCount(rs.getInt("u_air_count"));
				device.setInWindSpeed(rs.getInt("u_in_wind_speed"));
				device.setOutWindSpeed(rs.getInt("u_out_wind_speed"));
				device.setHr(rs.getInt("u_hr"));
				device.setHrUpLimit(rs.getInt("u_hr_up_limit"));
				device.setHrDownLimit(rs.getInt("u_hr_down_limit"));
				device.setHrOff(rs.getInt("u_hr_off"));
				device.setHrReally(rs.getInt("u_hr_really"));
				device.setCommunicateFalse(rs.getInt("u_communicate_false"));
				device.setCommunicateTrue(rs.getInt("u_communicate_true"));
				device.setInfoBar(rs.getInt("u_info_bar"));
				device.setStateSwitch(rs.getInt("u_state_switch"));
				device.setDp(rs.getInt("u_dp"));
				device.setDpUpLimit(rs.getInt("u_dp_up_limit"));
				device.setDpDownLimit(rs.getInt("u_dp_down_limit"));
				device.setDpOff(rs.getInt("u_dp_off"));
				device.setDpReally(rs.getInt("u_dp_really"));
				device.setDpTarget(rs.getInt("u_dp_target"));
				device.setAkpMode(rs.getInt("u_akp_mode"));
				device.setWorkHour(rs.getInt("u_work_hour"));
				device.setWorkSecond(rs.getInt("u_work_second"));
				device.setConverterMax(rs.getInt("u_converter_max"));
				device.setConverterMin(rs.getInt("u_converter_min"));
				device.setConverterModel(rs.getInt("u_converter_model"));
				device.setCycleError(rs.getInt("u_cycle_error"));
				device.setTempAlarmClose(rs.getInt("u_temp_alarm_close"));
				device.setHrAlarmClose(rs.getInt("u_hr_alarm_close"));
				device.setDpAlarmClose(rs.getInt("u_dp_alarm_close"));
				device.setInWindAlarmClose(rs.getInt("u_in_wind_alarm_cLose"));
				device.setAirSpeed10(rs.getInt("u_air_speed10"));
				device.setAirSpeed12(rs.getInt("u_air_speed12"));
				device.setAirSpeed14(rs.getInt("u_air_speed14"));
				device.setAirSpeed16(rs.getInt("u_air_speed16"));
				device.setAirSpeed18(rs.getInt("u_air_speed18"));
				device.setAirSpeed20(rs.getInt("u_air_speed20"));
				device.setAirSpeed22(rs.getInt("u_air_speed22"));
				device.setAirSpeed24(rs.getInt("u_air_speed24"));
				device.setAirSpeed26(rs.getInt("u_air_speed26"));
				device.setAirSpeed28(rs.getInt("u_air_speed28"));
				device.setAirSpeed30(rs.getInt("u_air_speed30"));
				device.setAirSpeed35(rs.getInt("u_air_speed35"));
				device.setAirSpeed40(rs.getInt("u_air_speed40"));
				device.setAirSpeed45(rs.getInt("u_air_speed45"));
				device.setAirSpeed50(rs.getInt("u_air_speed50"));
				device.setAlarmHistory(rs.getString("u_alarm_history"));
				deviceList.add(device);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt, rs);
		}
		return deviceList;
	}
	
	public void delete(int areaId,int deviceId){
		String sql = "delete from u_device where u_area_id=? and u_device_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaId);
			pstmt.setInt(2, deviceId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt);
		}
	}
	
	public void deviceClose(int areaId,int deviceId){
		String sql ="update u_device set u_online=0 where u_area_id=? and u_device_id=?";
		PreparedStatement pstmt =null;
		Connection conn=null;
		try {
			conn=DBO.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,areaId);
			pstmt.setInt(2,deviceId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBO.close(conn,pstmt);
		}
	}
	
	public void deviceClose(){
		String sql ="update u_device set u_online=0";
		PreparedStatement pstmt =null;
		Connection conn=null;
		try {
			conn=DBO.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBO.close(conn,pstmt);
		}
	}

	public void updateAlarm(int areaId, int deviceId,String alarmHistory) {
		String sql ="update u_device set u_alarm_history=? where u_area_id=? and u_device_id=?";
		PreparedStatement pstmt =null;
		Connection conn=null;
		try {
			conn=DBO.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,alarmHistory);
			pstmt.setInt(2,areaId);
			pstmt.setInt(3,deviceId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBO.close(conn,pstmt);
		}
	}
	
//	public String formatDate(Date date) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
//		return sdf.format(date);
//	}
//	
//	private void recordAlarm(int areaId,int deviceId,String alarmMsg) {
//		DeviceDao deviceDao = new DeviceDao();
//		Device device = deviceDao.get(areaId, deviceId);
//		String alarmHistory = device.getAlarmHistory();
//		JSONArray alarmJsonArray = JSON.parseArray(alarmHistory);
//		if(alarmJsonArray.size()>=8){
//			alarmJsonArray.remove(0);
//		}
//		JSONObject alarmJson =  new JSONObject();
//		alarmJson.put("time", formatDate(new Date()));
//		alarmJson.put("msg", alarmMsg);
//		alarmJsonArray.add(alarmJson);
//		deviceDao.updateAlarm(areaId,deviceId,alarmJsonArray.toJSONString());
//	}
//	
//	public static void main(String[] args) {
//		new DeviceDao().recordAlarm(1, 1, "温度超高,当前28大于上限25");
//	}
	
	

}
