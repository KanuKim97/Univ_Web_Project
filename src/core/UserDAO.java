package core;

import java.sql.*;
import org.json.simple.JSONObject;
import util.*;

public class UserDAO {
	public String login(String uid, String upw) throws Exception{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = ConnectionPool.getInstance().getConn();
			
			String sql = "SELECT id, password FROM user WHERE id =?";
			st = conn.prepareStatement(sql);
			st.setString(1, uid);
						
			String code = "OK";
			rs = st.executeQuery();
			
			if(!rs.next()){
				code = "EX";//회원정보가 없음
			}
			else if(!rs.getString("password").equals(upw)){
				code = "PS"; //비밀번호가 일치 하지 않음
			}
			else{
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("id", rs.getString("id"));
				code = jsonobj.toJSONString();
			}
			return code;
		}
		finally{
			if(rs != null) rs.close();
			if(st != null) st.close();
			if(conn != null) conn.close();
		}
	}
	
	public String signup(String uid, String upw, String name) throws Exception{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		conn = ConnectionPool.getInstance().getConn();
		
		String sql = "SELECT id, password FROM user WHERE id = ?";
		
		st = conn.prepareStatement(sql);
		st.setString(1, uid);
		
		String code = "OK";
		rs = st.executeQuery();
		
		if(rs.next()){
			code = "EX"; 
		}
		else{
		  st.close();
		  
		  sql ="INSERT INTO user(id, password, name) VALUES(?, ?, ?)";
		  
		  st = conn.prepareStatement(sql);
		  st.setString(1,uid);
		  st.setString(2,upw);
		  st.setString(3,name);
		  
		  int cnt = st.executeUpdate();
			  if(cnt == 0){
				  code = "ER";
			  }
			  else{
				  code = "OK";
			  }
		}
		conn.close();
		st.close();
		conn.close();
		return code;
	}
	
	public String FindPW(String uid, String uname) throws Exception{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = ConnectionPool.getInstance().getConn();
			
			String sql = "SELECT id, password, name FROM user WHERE id = ? AND name = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, uid);
			st.setString(2, uname);
			
			
			String code;
			rs = st.executeQuery();
			
			if(!rs.next()){
				code = "귀하의 회원정보가 존재하지 않습니다";
			}
			else{
				code = "귀하의 비밀번호는" + rs.getString("password") + "입니다";
			}
			return code;
		}
		finally{
			if(rs != null) rs.close();
			if(st != null) st.close();
			if(conn != null) conn.close();
		}
	}
	
	public String ProFileFetch(String uid) throws Exception{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = ConnectionPool.getInstance().getConn();
			
			String sql = "SELECT name, university, grade FROM user WHERE id = ?";
			st = conn.prepareStatement(sql);
			st.setString(1, uid);

			String code = null;
			rs = st.executeQuery();
			
			if(!rs.next()){
				code = "귀하의 회원정보가 존재하지 않습니다";//회원정보가 없음
			}
			else{
				//OK++
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("name", rs.getString("name"));
				jsonobj.put("university", rs.getString("university"));
				jsonobj.put("grade", rs.getString("grade"));
				code = jsonobj.toJSONString();
			}
			return code;
		}
		finally{
			if(rs != null) rs.close();
			if(st != null) st.close();
			if(conn != null) conn.close();
		}
	}
	
	public String ProFileUpdate(String uid, String univ, String ugrade, String uname) throws Exception{
		Connection conn = null;
		PreparedStatement st = null;
		try{
			conn = ConnectionPool.getInstance().getConn();
			
			String sql = "UPDATE user set university=?, grade=?, name=? WHERE id = ?";
			st = conn.prepareStatement(sql);
			st.setString(1, univ);
			st.setString(2, ugrade);
			st.setString(3, uname);
			st.setString(4, uid);
			int cnt = st.executeUpdate();
			
			return (cnt == 0)? "ER":"OK";
			
		}
		finally{
			if(st != null) st.close();
			if(conn != null) conn.close();
		}
	}
	
	public String del_account(String uid) throws Exception{
		Connection conn = null;
		PreparedStatement st = null;
		try{
			conn = ConnectionPool.getInstance().getConn();
			
			String sql = "DELETE from user WHERE id = ?";
			st = conn.prepareStatement(sql);
			st.setString(1, uid);
			int cnt = st.executeUpdate();
			
			return (cnt == 0)? "ER":"OK";
			
		}
		finally{
			if(st != null) st.close();
			if(conn != null) conn.close();
		}
	}
}
