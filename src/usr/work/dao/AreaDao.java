package usr.work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import usr.work.bean.Area;
import usr.work.utils.DBO;

public class AreaDao {

	public boolean add(Area area){
		boolean flag = false;
		String sql = "insert into u_area(u_area_name,u_charge_name,u_charge_phone,u_create_time) values(?,?,?,now())";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,area.getAreaName() );
			pstmt.setString(2, area.getChargeName());
			pstmt.setString(3, area.getChargePhone());
			pstmt.executeUpdate();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt);
		}
		return flag;
	}
	
	public boolean update(Area area){
		boolean flag = false;
		String sql = "update u_area set u_area_name=?,u_charge_name=?,u_charge_phone=? where ID=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, area.getAreaName());
			pstmt.setString(2, area.getChargeName());
			pstmt.setString(3, area.getChargePhone());
			pstmt.setInt(4, area.getID());
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
	
	public List<Area> getList(){
		List<Area> areaList = new ArrayList<Area>();
		String sql = "select * from u_area";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Area area = new Area();
				area.setID(rs.getInt("ID"));
				area.setAreaName(rs.getString("u_area_name"));
				area.setChargeName(rs.getString("u_charge_name"));
				area.setChargePhone(rs.getString("u_charge_phone"));
				area.setCreateTime(rs.getString("u_create_time"));
				areaList.add(area);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt, rs);
		}
		return areaList;
	}
}
