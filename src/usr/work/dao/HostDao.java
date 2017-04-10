package usr.work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import usr.work.bean.Host;
import usr.work.utils.DBO;

public class HostDao {
	
	public List<Host> getList(){
		List<Host> hostList = new ArrayList<Host>();
		String sql = "select * from u_host";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Host host = new Host();
				host.setAreaId(rs.getInt("u_area_id"));
				host.setDeviceId(rs.getInt("u_device_id"));
				host.setMac(rs.getString("u_mac"));
				host.setDes(rs.getString("u_des"));
				hostList.add(host);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt, rs);
		}
		return hostList;
	}
	
	public List<Host> getList(int areaId){
		List<Host> hostList = new ArrayList<Host>();
		String sql = "select * from u_host where u_area_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Host host = new Host();
				host.setAreaId(rs.getInt("u_area_id"));
				host.setDeviceId(rs.getInt("u_device_id"));
				host.setMac(rs.getString("u_mac"));
				host.setDes(rs.getString("u_des"));
				hostList.add(host);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt, rs);
		}
		return hostList;
	}
	
	public boolean add(Host host){
		boolean flag = false;
		String sql = "insert into u_host(u_area_id,u_device_id,u_mac,u_des) values(?,?,?,?)";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,host.getAreaId());
			pstmt.setInt(2, host.getDeviceId());
			pstmt.setString(3, host.getMac());
			pstmt.setString(4, host.getDes());
			pstmt.executeUpdate();
			flag = true;
		} catch (Exception e) {
			
		} finally {
			DBO.close(conn, pstmt);
		}
		return flag;
	}
	
	public boolean update(Host host){
		boolean flag = false;
		String sql = "update u_host set u_mac=?,u_des=? where u_area_id=? and u_device_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, host.getMac());
			pstmt.setString(2, host.getDes());
			pstmt.setInt(3, host.getAreaId());
			pstmt.setInt(4, host.getDeviceId());
			int rs = pstmt.executeUpdate();
			if(rs>0){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt);
		}
		return flag;
	}
	
	public void delete(int areaId,int deviceId){
		String sql = "delete from u_host where u_area_id=? and u_device_id=?";
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
