package core;

import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import util.ConnectionPool;

public class FeedDAO {
		public String insert(String uid, String feed, JSONArray images) throws Exception{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = ConnectionPool.getInstance().getConn();
			synchronized(this){
				String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES ";
				sql += "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'feed'";
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				if(!rs.next()) return "ER";
				
				int feedNo = rs.getInt(1);
				
				JSONObject jsonobj = (JSONObject)(new JSONParser()).parse(feed);
				jsonobj.put("no", feedNo);
				jsonobj.put("images", images);	
				
				sql = "INSERT INTO feed(id, content) VALUES(?, ?)";
				st = conn.prepareStatement(sql);
				st.setString(1, uid);
				st.setString(2, jsonobj.toJSONString());
				
				int cnt = st.executeUpdate();;
				return (cnt == 0 )? "ER" : "OK";
			} 
		} finally{
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(conn!=null) conn.close();
		}
	}
		
	public String fetch(String uid) throws Exception{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = ConnectionPool.getInstance().getConn();
			
			String sql = "SELECT content FROM feed WHERE id = ? ORDER BY ts DESC LIMIT 20";
			st = conn.prepareStatement(sql);
			st.setString(1, uid);
			
			String res= "[";
			int cnt = 0;
			rs = st.executeQuery();
			while(rs.next()){
				if(cnt++ > 0) res += ",";
				res += rs.getString("content");
			}
			res+="]";
			return res;
		}
		finally{
			if(rs != null)
				rs.close();
			if(st != null)
				st.close();
			if(conn != null)
				conn.close();		
			}
	}
	
	public String delete(String no) throws Exception{
		Connection conn = null;
		PreparedStatement st = null;
		try{
			conn = ConnectionPool.getInstance().getConn();	
			String sql = "DELETE FROM feed WHERE no = ?";
			st = conn.prepareStatement(sql);
			st.setString(1, no);
			
			int cnt = st.executeUpdate();;
			return (cnt == 0 )? "ER" : "OK"; 
			} 
		finally{
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
			}
		}
	
	public String del_feed_all(String uid) throws Exception{
		Connection conn = null;
		PreparedStatement st = null;
		try{
			conn = ConnectionPool.getInstance().getConn();	
			String sql = "DELETE FROM feed WHERE id = ?";
			st = conn.prepareStatement(sql);
			st.setString(1, uid);
			
			int cnt = st.executeUpdate();;
			return (cnt == 0 )? "ER" : "OK"; 
			} 
		finally{
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
			}
		}
	
	public String Update(String uno, String feed) throws Exception{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = ConnectionPool.getInstance().getConn();
			synchronized(this){	
			JSONObject jsonobj = (JSONObject) new JSONParser().parse(feed);
					
			String sql = "Update feed set content = ? where no = ?";
			jsonobj.put("no", uno);
					
			st = conn.prepareStatement(sql);
			st.setString(1, jsonobj.toString());
			st.setString(2, uno);

			System.out.print(jsonobj.toString());
			int cnt = st.executeUpdate();;
			return (cnt == 0 )? "ER" : "OK";
			} 
		} 
		finally{
			if(rs!=null) 
				rs.close();
			if(st!=null) 
				st.close();
			if(conn!=null) 
				conn.close();
			}
		}

}