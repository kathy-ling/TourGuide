package com.TourGuide.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.ScenicTeam;

@Repository
public class ScenicTeamDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	/**
	 * 分页获取景区拼团信息
	 * @param i
	 * @param j
	 * @return
	 * 2017-3-1 11:22:40
	 */
	public List<ScenicTeam> getScenicTeam(int i, int j) {

		int k = (i - 1) * j;
		int l = 0;
		ScenicTeam scenicTeam= new ScenicTeam();
		List<ScenicTeam> list=new ArrayList<>();
		DataSource dataSource = jdbcTemplate.getDataSource();
		try {
			Connection conn = dataSource.getConnection();
			CallableStatement cst = conn.prepareCall("call p_getScenicTeam(?,?)");
			cst.setInt(1, k);
			cst.setInt(2, j);
			ResultSet rst = cst.executeQuery();
			while (rst.next()) {
				
				if (l%3==0) {
					scenicTeam=new ScenicTeam();
					scenicTeam.setScenicID(rst.getString(1));
					scenicTeam.setScenicName(rst.getString(2));
					scenicTeam.setDay1_fee(rst.getInt(4));
					scenicTeam.setDay1_maxNum(rst.getInt(5));
				}else if (l%3==1) {
					scenicTeam.setDay2_fee(rst.getInt(4));
					scenicTeam.setDay2_maxNum(rst.getInt(5));
				}else if(l%3==2) {
					scenicTeam.setDay3_fee(rst.getInt(4));
					scenicTeam.setDay3_maxNum(rst.getInt(5));
					list.add(scenicTeam);				}
				l++;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 通过景区编号得到景区拼团信息
	 * @param scenicNo
	 * @return
	 */
	public List<ScenicTeam> getScenicTeamByscenicNo(String scenicNo) {
		int l = 0;
		ScenicTeam scenicTeam= new ScenicTeam();
		List<ScenicTeam> list=new ArrayList<>();
		DataSource dataSource = jdbcTemplate.getDataSource();
		try {
			Connection conn = dataSource.getConnection();
			CallableStatement cst = conn.prepareCall("call P_getScenicTeamByscenicNo(?)");
			cst.setString(1, scenicNo);
			ResultSet rst = cst.executeQuery();
			while (rst.next()) {
				
				if (l%3==0) {
					scenicTeam=new ScenicTeam();
					scenicTeam.setScenicID(rst.getString(1));
					scenicTeam.setScenicName(rst.getString(2));
					scenicTeam.setDay1_fee(rst.getInt(4));
					scenicTeam.setDay1_maxNum(rst.getInt(5));
				}else if (l%3==1) {
					scenicTeam.setDay2_fee(rst.getInt(4));
					scenicTeam.setDay2_maxNum(rst.getInt(5));
				}else if(l%3==2) {
					scenicTeam.setDay3_fee(rst.getInt(4));
					scenicTeam.setDay3_maxNum(rst.getInt(5));
					list.add(scenicTeam);				}
				l++;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public int UpdateScenicTeam(String scenicNo,int fee,int maxNum,String date) {
		
		String sql="update  t_introfeeandmaxnum set fee=?,maxNum=?  "
				+ "where scenicNo=? and date=?";
		int i=jdbcTemplate.update(sql, new Object[]{fee,maxNum,scenicNo,date});
		
		return i;
	}
}
