package usr.work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import usr.work.bean.Device;
import usr.work.utils.DBO;

public class DeviceDao {
	
	
	public boolean add(Device device){
		boolean flag = false;
		String sql = "insert into u_device(u_area_id,u_device_id,u_mac,u_desc,u_des,u_update_time) values(?,?,?,?,?,now())";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,device.getAreaId());
			pstmt.setInt(2, device.getDeviceId());
			pstmt.setString(3, device.getMac());
			pstmt.setString(4, device.getDesc());
			pstmt.setString(5, device.getDes());
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
				+ "u_update_time=now(),u_online=?,u_enable=?,u_device_ip=?,u_mac=?,u_des=?,u_temp=?,u_temp_up_limit=?,u_temp_down_limit=?,u_temp_off=?,u_temp_really=?,"
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
			pstmt.setString(4, device.getMac());
			pstmt.setString(5, device.getDes());
			
			pstmt.setFloat(6, device.getTemp());
			pstmt.setFloat(7, device.getTempUpLimit());
			pstmt.setFloat(8, device.getTempDownLimit());
			pstmt.setFloat(9, device.getTempOff());
			pstmt.setFloat(10, device.getTempReally());
			pstmt.setInt(11, device.getWorkMode());
			pstmt.setInt(12, device.getAirCount());
			pstmt.setInt(13, device.getInWindSpeed());
			pstmt.setInt(14, device.getOutWindSpeed());
			pstmt.setFloat(15, device.getHr());
			pstmt.setFloat(16, device.getHrUpLimit());
			pstmt.setFloat(17, device.getHrDownLimit());
			pstmt.setFloat(18, device.getHrOff());
			pstmt.setFloat(19, device.getHrReally());
			pstmt.setInt(20, device.getCommunicateFalse());
			pstmt.setInt(21, device.getCommunicateTrue());
			pstmt.setInt(22, device.getInfoBar());
			pstmt.setInt(23, device.getStateSwitch());
			pstmt.setFloat(24, device.getDp());
			pstmt.setFloat(25, device.getDpUpLimit());
			pstmt.setFloat(26, device.getDpDownLimit());
			pstmt.setFloat(27, device.getDpOff());
			pstmt.setFloat(28, device.getDpReally());
			pstmt.setInt(29, device.getDpTarget());
			pstmt.setInt(30, device.getAkpMode());
			pstmt.setInt(31, device.getWorkHour());
			pstmt.setInt(32, device.getWorkSecond());
			pstmt.setInt(33, device.getConverterMax());
			pstmt.setInt(34, device.getConverterMin());
			pstmt.setInt(35, device.getConverterModel());
			pstmt.setInt(36, device.getCycleError());
			pstmt.setInt(37, device.getAlarmCycle());
			pstmt.setInt(38, device.getTempAlarmClose());
			pstmt.setInt(39, device.getHrAlarmClose());
			pstmt.setInt(40, device.getDpAlarmClose());
			pstmt.setInt(41, device.getInWindAlarmClose());
			pstmt.setString(42, device.getAlarmHistory());
			pstmt.setInt(43, device.getAreaId());
			pstmt.setInt(44, device.getDeviceId());
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
	
	public boolean updateDesc(Device device){
		boolean flag = false;
		String sql ="update u_device set "
				+ "u_desc=? "
				+ "where u_area_id=? and u_device_id=?";
		PreparedStatement pstmt =null;
		Connection conn=null;
		try {
			conn=DBO.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, device.getDesc());
			pstmt.setInt(2, device.getAreaId());
			pstmt.setInt(3, device.getDeviceId());
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
				device.setDes(rs.getString("u_des"));
				device.setDesc(rs.getString("u_desc"));
				device.setAreaId(rs.getInt("u_area_id"));
				device.setDeviceId(rs.getInt("u_device_id"));
				device.setDeviceIp(rs.getString("u_device_ip"));
				device.setUpdateTime(rs.getString("u_update_time"));
				device.setTemp(rs.getFloat("u_temp"));
				device.setTempUpLimit(rs.getFloat("u_temp_up_limit"));
				device.setTempDownLimit(rs.getFloat("u_temp_down_limit"));
				device.setTempOff(rs.getFloat("u_temp_off"));
				device.setTempReally(rs.getFloat("u_temp_really"));
				device.setWorkMode(rs.getInt("u_work_mode"));
				device.setAirCount(rs.getInt("u_air_count"));
				device.setInWindSpeed(rs.getInt("u_in_wind_speed"));
				device.setOutWindSpeed(rs.getInt("u_out_wind_speed"));
				device.setHr(rs.getFloat("u_hr"));
				device.setHrUpLimit(rs.getFloat("u_hr_up_limit"));
				device.setHrDownLimit(rs.getFloat("u_hr_down_limit"));
				device.setHrOff(rs.getFloat("u_hr_off"));
				device.setHrReally(rs.getFloat("u_hr_really"));
				device.setCommunicateFalse(rs.getInt("u_communicate_false"));
				device.setCommunicateTrue(rs.getInt("u_communicate_true"));
				device.setInfoBar(rs.getInt("u_info_bar"));
				device.setStateSwitch(rs.getInt("u_state_switch"));
				device.setDp(rs.getFloat("u_dp"));
				device.setDpUpLimit(rs.getFloat("u_dp_up_limit"));
				device.setDpDownLimit(rs.getFloat("u_dp_down_limit"));
				device.setDpOff(rs.getFloat("u_dp_off"));
				device.setDpReally(rs.getFloat("u_dp_really"));
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
				device.setDes(rs.getString("u_des"));
				device.setDesc(rs.getString("u_desc"));
				device.setAreaId(rs.getInt("u_area_id"));
				device.setDeviceId(rs.getInt("u_device_id"));
				device.setDeviceIp(rs.getString("u_device_ip"));
				device.setUpdateTime(rs.getString("u_update_time"));
				device.setTemp(rs.getFloat("u_temp"));
				device.setTempUpLimit(rs.getFloat("u_temp_up_limit"));
				device.setTempDownLimit(rs.getFloat("u_temp_down_limit"));
				device.setTempOff(rs.getFloat("u_temp_off"));
				device.setTempReally(rs.getFloat("u_temp_really"));
				device.setWorkMode(rs.getInt("u_work_mode"));
				device.setAirCount(rs.getInt("u_air_count"));
				device.setInWindSpeed(rs.getInt("u_in_wind_speed"));
				device.setOutWindSpeed(rs.getInt("u_out_wind_speed"));
				device.setHr(rs.getFloat("u_hr"));
				device.setHrUpLimit(rs.getFloat("u_hr_up_limit"));
				device.setHrDownLimit(rs.getFloat("u_hr_down_limit"));
				device.setHrOff(rs.getFloat("u_hr_off"));
				device.setHrReally(rs.getFloat("u_hr_really"));
				device.setCommunicateFalse(rs.getInt("u_communicate_false"));
				device.setCommunicateTrue(rs.getInt("u_communicate_true"));
				device.setInfoBar(rs.getInt("u_info_bar"));
				device.setStateSwitch(rs.getInt("u_state_switch"));
				device.setDp(rs.getFloat("u_dp"));
				device.setDpUpLimit(rs.getFloat("u_dp_up_limit"));
				device.setDpDownLimit(rs.getFloat("u_dp_down_limit"));
				device.setDpOff(rs.getFloat("u_dp_off"));
				device.setDpReally(rs.getFloat("u_dp_really"));
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
				device.setDes(rs.getString("u_des"));
				device.setDesc(rs.getString("u_desc"));
				device.setAreaId(rs.getInt("u_area_id"));
				device.setDeviceId(rs.getInt("u_device_id"));
				device.setDeviceIp(rs.getString("u_device_ip"));
				device.setUpdateTime(rs.getString("u_update_time"));
				device.setTemp(rs.getFloat("u_temp"));
				device.setTempUpLimit(rs.getFloat("u_temp_up_limit"));
				device.setTempDownLimit(rs.getFloat("u_temp_down_limit"));
				device.setTempOff(rs.getFloat("u_temp_off"));
				device.setTempReally(rs.getFloat("u_temp_really"));
				device.setWorkMode(rs.getInt("u_work_mode"));
				device.setAirCount(rs.getInt("u_air_count"));
				device.setInWindSpeed(rs.getInt("u_in_wind_speed"));
				device.setOutWindSpeed(rs.getInt("u_out_wind_speed"));
				device.setHr(rs.getFloat("u_hr"));
				device.setHrUpLimit(rs.getFloat("u_hr_up_limit"));
				device.setHrDownLimit(rs.getFloat("u_hr_down_limit"));
				device.setHrOff(rs.getFloat("u_hr_off"));
				device.setHrReally(rs.getFloat("u_hr_really"));
				device.setCommunicateFalse(rs.getInt("u_communicate_false"));
				device.setCommunicateTrue(rs.getInt("u_communicate_true"));
				device.setInfoBar(rs.getInt("u_info_bar"));
				device.setStateSwitch(rs.getInt("u_state_switch"));
				device.setDp(rs.getFloat("u_dp"));
				device.setDpUpLimit(rs.getFloat("u_dp_up_limit"));
				device.setDpDownLimit(rs.getFloat("u_dp_down_limit"));
				device.setDpOff(rs.getFloat("u_dp_off"));
				device.setDpReally(rs.getFloat("u_dp_really"));
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

}
