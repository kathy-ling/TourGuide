package com.TourGuide.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.VisitorInfo;

@Repository
public class VisitorDao {

		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		private final int disable = 0;  //用户是否被禁止登陆，0-否，1-是
		
		/**
		 * 用户注册
		 * @param nickName 用户昵称
		 * @param sex  性别
		 * @param name  用户姓名
		 * @param phone  手机号
		 * @param passwd  用户密码
		 * @param image   用户头像
		 * @return
		 */
		public boolean visitorRegister(String nickName, String sex,
				String name, String phone, String passwd, String image){
			
			boolean bool = false;
			String sqlRegister = "insert into t_visitor (nickName,sex,name,phone,image) "
					+ "values (?,?,?,?,?)";
			int i = jdbcTemplate.update(sqlRegister, new Object[]{nickName, sex, name, phone, image});
			
			String sqlSetPass = "insert into t_visitorlogin (phone,password,disable) "
					+ "values (?,?,?)";
			int j = jdbcTemplate.update(sqlSetPass, new Object[]{phone, passwd, disable});
			
			if(i!=0 && j!=0){
				bool = true;
			}
			return bool;
		}
		
		/*
		 *通过页数与页数容量来获取游客信息 
		 * time：2017-1-2 17:22:30
		 * */
		public List<VisitorInfo> GetVisitorInfoByPage(int currentPage,int rows)
		{
			int j=(currentPage-1)*rows;
			String sql="SELECT * FROM t_visitor LIMIT "+j+" ,"+rows+"";
			
			List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
			List<VisitorInfo> listres=new ArrayList<>();
			for (int k = 0; k < list.size(); k++) {
				VisitorInfo visitorInfo=new VisitorInfo();
				visitorInfo.setPhone((String) list.get(k).get("phone"));
				visitorInfo.setName((String) list.get(k).get("name"));
				visitorInfo.setNickName((String) list.get(k).get("nickName"));
				visitorInfo.setImage((String) list.get(k).get("image"));
				visitorInfo.setSex((String) list.get(k).get("sex"));
				visitorInfo.setCity((String) list.get(k).get("city"));
				listres.add(visitorInfo);
			}
			
			return listres;
		}
		
		public  int  GetVisitorCount() {
			String sql="SELECT * FROM t_visitor";
			return jdbcTemplate.queryForList(sql).size();
		}
		
		
		/*
		 * 通过sql查询语句进行查询游客的详细信息
		 * 参数：SQL语句
		 * 2017-1-2 17:22:30
		 * */
		public List<VisitorInfo> SearchVisitorInfoByPhone_Dao(String a) {
			final List<VisitorInfo> list = new ArrayList<VisitorInfo>();
			String sql=" select * from t_visitor where phone = '" + a +"'";
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				
				@Override
				public void processRow(java.sql.ResultSet rSet) throws SQLException {

					VisitorInfo visitorInfo = new VisitorInfo();
					visitorInfo.setPhone(rSet.getString(1));
					visitorInfo.setName(rSet.getString(2));
					visitorInfo.setNickName(rSet.getString(3));
					visitorInfo.setImage(rSet.getString(4));
					visitorInfo.setSex(rSet.getString(5));
					visitorInfo.setCity(rSet.getString(6));
					list.add(visitorInfo);
				}
			});
			return list;
		}
		/*
		 * 增加游客
		 * 参数：游客信息类
		 * 2017-1-2 17:23:30
		 * */
		public boolean AddVisitorInfo_Dao(VisitorInfo visitorInfo) {
			String sql = " select count(*) from t_visitor where phone = '"
						+visitorInfo.getPhone()+"'";
			 
			
			if (jdbcTemplate.queryForObject(sql, Integer.class) == 0) {
				sql =  " insert into t_visitor values (?,?,?,?,?,?) ";
				jdbcTemplate.update(sql, new Object[]{
					visitorInfo.getPhone(),
					visitorInfo.getName(),
					visitorInfo.getNickName(),
					"0",
					visitorInfo.getSex(),
					visitorInfo.getCity() });
				return true;
			}
			return false;
		}
		/*
		 * 	删除游客
		 * 	参数：游客账号
		 * 2017-1-2 17:23:30
		 * */
		public boolean DeleteVisitorInfo(String s) {
			String sql = "delete from t_visitor where phone='"+s+"'";
			if (jdbcTemplate.update(sql)>0) {
				return true;
			} else {
				return false;
			}
			
			
		}
		
		/*
		 * 更新游客信息
		 * 参数：游客信息类
		 * 2017-1-2 17:24:30 
		 * */
		public boolean UpdateVisitorInfo(VisitorInfo visitorInfo) {
			String sql = "update   t_visitor set phone=?,name=?,nickName=?, "+
						" sex=?,city=? where phone=?";
			int i=jdbcTemplate.update(sql, new Object[]{
					visitorInfo.getPhone(),
					visitorInfo.getName(),
					visitorInfo.getNickName(),
					visitorInfo.getSex(),
					visitorInfo.getCity(),
					visitorInfo.getPhone()});
			if (i>0) {
				return true;
			} else {
				return false;
			}
			
		}
}
