package com.TourGuide.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.SNSUserInfo;
import com.TourGuide.model.VisitorInfo;
import com.TourGuide.model.VisitorLoginInfo;

@Repository
public class VisitorDao {

		@Autowired
		private JdbcTemplate jdbcTemplate;
		private final int disable = 0;  //用户是否被禁止登陆，0-否，1-是
		
		
		
		/**
		 * 将微信端获取到的用户信息，存入数据库
		 * @param snsUserInfo
		 * @return
		 */
		public boolean recordWeixinInfo(SNSUserInfo snsUserInfo){
			
			boolean bool = false;
			String TEST = "test";
			
			String select = "select * from t_visitor where openID='"+snsUserInfo.getOpenId()+"'";
			List<Map<String , Object>> list = jdbcTemplate.queryForList(select);
			
			if(list.size() == 0){
				String insert = "insert into t_visitor (nickName,sex,name,phone,image,openID) "
						+ "values ('"+snsUserInfo.getNickname()+"','"+snsUserInfo.getSex()+"',"
						+ "'"+TEST+"','"+snsUserInfo.getOpenId()+"','"+snsUserInfo.getHeadImgUrl()+"',"
						+ "'"+snsUserInfo.getOpenId()+"')";
				int i = jdbcTemplate.update(insert);
				
				if(i != 0){
					bool = true;
				}
			}
						
			return bool;
		}
		
		
		/**
		 * 根据openID，查看用户的信息
		 * @param openID
		 * @return
		 */
		public VisitorInfo getInfobyOpenID(String openID){
					
			final VisitorInfo visitorInfo = new VisitorInfo();
			String sql = "select * from t_visitor where openID='"+openID+"'";
			
			jdbcTemplate.query(sql,  new RowCallbackHandler() {
				
				@Override
				public void processRow(ResultSet res) throws SQLException {
					visitorInfo.setPhone(res.getString(1));
					visitorInfo.setName(res.getString(2));
					visitorInfo.setNickName(res.getString(3));
					visitorInfo.setImage(res.getString(4));
					visitorInfo.setSex(res.getString(5));
					visitorInfo.setOpenID(res.getString(6));
				}
			});
			
			return visitorInfo;
		}
		
		
		/**
		 * 用户注册
		 * @param nickName 用户昵称
		 * @param sex  性别
		 * @param name  用户姓名
		 * @param phone  手机号
		 * @param passwd  用户密码
		 * @param image   用户头像
		 * 
		 * @return
		 */
		public boolean visitorRegister(String nickName, String sex,
				String name, String phone, String passwd, String image,String openID){
			
			boolean bool = false;
			String sqlRegister = "insert into t_visitor (nickName,sex,name,phone,image,openID) "
					+ "values (?,?,?,?,?,?)";
			int i = jdbcTemplate.update(sqlRegister, new Object[]{nickName, sex, name, phone, image, openID});
			
			String sqlSetPass = "insert into t_visitorlogin (phone,password,disable) "
					+ "values (?,?,?)";
			int j = jdbcTemplate.update(sqlSetPass, new Object[]{phone, passwd, disable});
			
			if(i!=0 && j!=0){
				bool = true;
			}
			return bool;
		}
		
		
		/**
		 * 根据游客的手机号，查询个人详细信息
		 * @param phone  手机号
		 * @return 手机号、姓名、性别、昵称、头像
		 */
		public VisitorInfo getVisitorInfoWithPhone(String phone){
			
			final VisitorInfo visitorInfo = new VisitorInfo();
			
			String sqlSearch = "select * from t_visitor where phone='"+phone+"'";
			
			jdbcTemplate.query(sqlSearch, new RowCallbackHandler() {
				
				@Override
				public void processRow(java.sql.ResultSet rSet) throws SQLException {

					visitorInfo.setPhone(rSet.getString(1));
					visitorInfo.setName(rSet.getString(2));
					visitorInfo.setNickName(rSet.getString(3));
					visitorInfo.setImage(rSet.getString(4));
					visitorInfo.setSex(rSet.getString(5));
				}
			});
			
			return visitorInfo;
		}  
		
		/*
		 *通过页数与页数容量来获取未禁用游客信息 
		 * time：2017-1-2 17:22:30
		 * */
		public List<VisitorInfo> GetVisitorInfoByPage(int currentPage,int rows)
		{
			int i=(currentPage-1)*rows;
			List<VisitorInfo> listres=new ArrayList<>();
			DataSource dataSource=jdbcTemplate.getDataSource();
			try {
				Connection conn=dataSource.getConnection();
				CallableStatement cst=conn.prepareCall("call getvisitorNo(?,?)");
				cst.setInt(1, i);
				cst.setInt(2, rows);
				ResultSet rst=cst.executeQuery();
				while (rst.next()) {
					VisitorInfo visitorInfo=new VisitorInfo();
					visitorInfo.setPhone( rst.getString(1));
					visitorInfo.setName(rst.getString(2));
					visitorInfo.setNickName(rst.getString(3));
					visitorInfo.setImage(rst.getString(4));
					visitorInfo.setSex(rst.getString(5));
					listres.add(visitorInfo);
				}
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			
			return listres;
		}
		
		/**
		 * 分页得到禁用的游客基本信息
		 * @param currentPage
		 * @param rows
		 * @return
		 * 2017-2-9 16:03:40
		 */
		public List<VisitorInfo> GetVisitorInfoDisabled(int currentPage,int rows)
		{
			int i=(currentPage-1)*rows;
			List<VisitorInfo> listres=new ArrayList<>();
			DataSource dataSource=jdbcTemplate.getDataSource();
			try {
				Connection conn=dataSource.getConnection();
				CallableStatement cst=conn.prepareCall("call getvisitorDisabled(?,?)");
				cst.setInt(1, i);
				cst.setInt(2, rows);
				ResultSet rst=cst.executeQuery();
				while (rst.next()) {
					VisitorInfo visitorInfo=new VisitorInfo();
					visitorInfo.setPhone( rst.getString(1));
					visitorInfo.setName(rst.getString(2));
					visitorInfo.setNickName(rst.getString(3));
					visitorInfo.setImage(rst.getString(4));
					visitorInfo.setSex(rst.getString(5));
					listres.add(visitorInfo);
				}
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			
			return listres;
		}
		
		
		/**
		 * 获取未禁用游客的数量
		 * @return
		 * 2017-2-8 19:45:59
		 */
		public  int  GetVisitorCount() {
			DataSource dataSource=jdbcTemplate.getDataSource();
			int i=0;
			try {
				Connection connection=dataSource.getConnection();
				CallableStatement cst=connection.prepareCall("call getVisitorCount(?)");
				cst.registerOutParameter(1, java.sql.Types.BIGINT);
				cst.execute();
				i=cst.getInt(1);
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			return i;
		}
		
		/**
		 * 获取黑名单游客的数量
		 * @return
		 * 2017-2-9 16:15:30
		 */
		public  int  GetVisitorDisabledCount() {
			DataSource dataSource=jdbcTemplate.getDataSource();
			int i=0;
			try {
				Connection connection=dataSource.getConnection();
				CallableStatement cst=connection.prepareCall("call getVisitorDisabledCount(?)");
				cst.registerOutParameter(1, java.sql.Types.BIGINT);
				cst.execute();
				i=cst.getInt(1);
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			return i;
		}
		
		public List<VisitorInfo> SearchVisitorDisByPhone(String phone) {
			List<VisitorInfo> list = new ArrayList<VisitorInfo>();
			DataSource dataSource=jdbcTemplate.getDataSource();
			try {
				Connection connection=dataSource.getConnection();
				CallableStatement cst=connection.prepareCall("call getVisiDisableByPhone(?)") ;
				cst.setString(1, phone);
				ResultSet rSet=cst.executeQuery();
				while (rSet.next()) {
					VisitorInfo visitorInfo = new VisitorInfo();
					visitorInfo.setPhone(rSet.getString(1));
					visitorInfo.setName(rSet.getString(2));
					visitorInfo.setNickName(rSet.getString(3));
					visitorInfo.setImage(rSet.getString(4));
					visitorInfo.setSex(rSet.getString(5));
					list.add(visitorInfo);
				}
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
				
			return list;
		}
		
		/*
		 * 通过游客手机号进行查询未禁用游客的详细信息
		 * 参数：SQL语句
		 * 2017-1-2 17:22:30
		 * */
		public List<VisitorInfo> SearchVisitorInfoByPhone(String phone) {
			List<VisitorInfo> list = new ArrayList<VisitorInfo>();
			DataSource dataSource=jdbcTemplate.getDataSource();
			try {
				Connection connection=dataSource.getConnection();
				CallableStatement cst=connection.prepareCall("call getVisitorByphone(?)") ;
				cst.setString(1, phone);
				ResultSet rSet=cst.executeQuery();
				while (rSet.next()) {
					VisitorInfo visitorInfo = new VisitorInfo();
					visitorInfo.setPhone(rSet.getString(1));
					visitorInfo.setName(rSet.getString(2));
					visitorInfo.setNickName(rSet.getString(3));
					visitorInfo.setImage(rSet.getString(4));
					visitorInfo.setSex(rSet.getString(5));
					list.add(visitorInfo);
				}
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
				
			return list;
		}
		/*
		 * 更新游客信息
		 * 参数：游客信息类
		 * 2017-1-2 17:24:30 
		 * */
		public boolean UpdateVisitorInfo(VisitorInfo visitorInfo) {
			String sql = "update   t_visitor set phone=?,name=?,nickName=?, "+
						" sex=? where phone=?";
			int i=jdbcTemplate.update(sql, new Object[]{
					visitorInfo.getPhone(),
					visitorInfo.getName(),
					visitorInfo.getNickName(),
					visitorInfo.getSex(),
					visitorInfo.getPhone()});
			if (i>0) {
				return true;
			} else {
				return false;
			}			
		}
		
		/*
		 * 禁用游客
		 * */
		public boolean ForbidVisitorInfo_Dao(String phone) {
			String sql = " update t_visitorlogin set disable=1 where phone='"+phone+"'";
			int i = jdbcTemplate.update(sql);
			
			if (i > 0) return true;
			return false;
		}
		/*
		 * 解禁游客
		 * */
		public boolean RelieveVisitorInfo_Dao(String phone) {
			String sql = " update t_visitorlogin set disable=0 where phone='"+phone+"'";
			int i = jdbcTemplate.update(sql);
			
			if (i > 0) return true;
			return false;
		}
		
}
