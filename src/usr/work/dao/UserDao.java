package usr.work.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import usr.work.bean.User;
import usr.work.utils.DBO;

public class UserDao {
	
	public User get(String userName){
		User user = null;
		String sql = "select * from u_user where u_user_name=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setID(rs.getInt("ID"));
				user.setAreaId(rs.getInt("u_area_id"));
				user.setPrivilege(rs.getInt("u_privilege"));
				user.setUserName(rs.getString("u_user_name"));
				user.setUserPwd(rs.getString("u_user_pwd"));
				user.setPhone(rs.getString("u_phone"));
				user.setEmail(rs.getString("u_email"));
				user.setAddress(rs.getString("u_address"));
				user.setName(rs.getString("u_name"));
				user.setDes(rs.getString("u_des"));
				user.setLoginFlag(rs.getInt("u_login_flag"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt, rs);
		}
		return user;
	}
	
	public User get(int ID){
		User user = null;
		String sql = "select * from u_user where ID=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setID(rs.getInt("ID"));
				user.setAreaId(rs.getInt("u_area_id"));
				user.setPrivilege(rs.getInt("u_privilege"));
				user.setUserName(rs.getString("u_user_name"));
				user.setUserPwd(rs.getString("u_user_pwd"));
				user.setPhone(rs.getString("u_phone"));
				user.setEmail(rs.getString("u_email"));
				user.setAddress(rs.getString("u_address"));
				user.setName(rs.getString("u_name"));
				user.setDes(rs.getString("u_des"));
				user.setLoginFlag(rs.getInt("u_login_flag"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt, rs);
		}
		return user;
	}
	
	
	
	public boolean add(User user){
		boolean flag = false;
		String sql = "insert into u_user(u_privilege,u_user_name,u_user_pwd,u_area_id,u_phone,u_email,u_address,u_name,u_des) values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,user.getPrivilege());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getUserPwd());
			pstmt.setInt(4, user.getAreaId());
			pstmt.setString(5, user.getPhone());
			pstmt.setString(6, user.getEmail());
			pstmt.setString(7, user.getAddress());
			pstmt.setString(8, user.getName());
			pstmt.setString(9, user.getDes());
			pstmt.executeUpdate();
			flag = true;
		} catch (Exception e) {
			
		} finally {
			DBO.close(conn, pstmt);
		}
		return flag;
	}
	
	public void delete(String userName){
		String sql = "delete from u_user where u_user_name=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,userName);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt);
		}
	}
	
	public boolean update(User user){
		boolean flag = false;
		String sql = "update u_user set u_area_id=?,u_phone=?,u_email=?,u_address=?,u_name=?,u_des=? where u_user_name=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user.getAreaId());
			pstmt.setString(2, user.getPhone());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getAddress());
			pstmt.setString(5, user.getName());
			pstmt.setString(6, user.getDes());
			pstmt.setString(7, user.getUserName());
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
	
	public boolean updatePwd(String userName, String userPwd,String newPwd){
		boolean flag = false;
		String sql = "update u_user set u_user_pwd=? where u_user_name=? and u_user_pwd=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPwd);
			pstmt.setString(2, userName);
			pstmt.setString(3, userPwd);
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
	
	public boolean updateFlag(String userName){
		boolean flag = false;
		String sql = "update u_user set u_login_flag=u_login_flag+1 where u_user_name=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
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
	
	public List<User> getList(){
		List<User> userList = new ArrayList<User>();
		String sql = "select * from u_user";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setID(rs.getInt("ID"));
				user.setAreaId(rs.getInt("u_area_id"));
				user.setPrivilege(rs.getInt("u_privilege"));
				user.setUserName(rs.getString("u_user_name"));
				user.setUserPwd(rs.getString("u_user_pwd"));
				user.setPhone(rs.getString("u_phone"));
				user.setEmail(rs.getString("u_email"));
				user.setAddress(rs.getString("u_address"));
				user.setName(rs.getString("u_name"));
				user.setDes(rs.getString("u_des"));
				userList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt, rs);
		}
		return userList;
	}

	public List<User> getList(int areaId) {
		List<User> userList = new ArrayList<User>();
		String sql = "select * from u_user where u_area_id=?";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBO.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setID(rs.getInt("ID"));
				user.setAreaId(rs.getInt("u_area_id"));
				user.setPrivilege(rs.getInt("u_privilege"));
				user.setUserName(rs.getString("u_user_name"));
				user.setUserPwd(rs.getString("u_user_pwd"));
				user.setPhone(rs.getString("u_phone"));
				user.setEmail(rs.getString("u_email"));
				user.setAddress(rs.getString("u_address"));
				user.setName(rs.getString("u_name"));
				user.setDes(rs.getString("u_des"));
				userList.add(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBO.close(conn, pstmt, rs);
		}
		return userList;
	}
}
