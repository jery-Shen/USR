package usr.work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import usr.work.bean.Region;
import usr.work.utils.DBO;

public class RegionDao {

	public boolean add(Region region){
		boolean flag = false;
		String sql = "insert into u_region(u_region_name,u_charge_name,u_charge_phone,u_create_time) values(?,?,?,now())";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,region.getRegionName() );
			pstmt.setString(2, region.getChargeName());
			pstmt.setString(3, region.getChargePhone());
			pstmt.executeUpdate();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt);
		}
		return flag;
	}
	
	public boolean update(Region region){
		boolean flag = false;
		String sql = "update u_region set u_region_name=?,u_charge_name=?,u_charge_phone=? where ID=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, region.getRegionName());
			pstmt.setString(2, region.getChargeName());
			pstmt.setString(3, region.getChargePhone());
			pstmt.setInt(4, region.getID());
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
	
	public List<Region> getList(){
		List<Region> regionList = new ArrayList<Region>();
		String sql = "select * from u_region";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Region region = new Region();
				region.setID(rs.getInt("ID"));
				region.setRegionName(rs.getString("u_region_name"));
				region.setChargeName(rs.getString("u_charge_name"));
				region.setChargePhone(rs.getString("u_charge_phone"));
				region.setCreateTime(rs.getString("u_create_time"));
				regionList.add(region);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt, rs);
		}
		return regionList;
	}
}
