package com.TourGuide.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.Operateper;

@Repository
public class OperateperDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 *通过页数与页数容量来获取运营人员信息 
	 * time：2016-12-30 14:40:08
	 * */
	public List<Operateper> GetOperateUseInfoByPage(int currentPage,int rows)
	{
		int j=(currentPage-1)*rows;
		String sql="SELECT * FROM t_operateper LIMIT "+j+" ,"+rows+"";
		
		List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
		List<Operateper> listres=new ArrayList<>();
		for (int k = 0; k < list.size(); k++) {
			Operateper operateper=new Operateper();
			operateper.setOperateper_account((String) list.get(k).get("account"));
			operateper.setOperateper_name((String) list.get(k).get("name"));
			operateper.setOperateper_role((String) list.get(k).get("role"));
			operateper.setOperateper_phone((String) list.get(k).get("phone"));
			operateper.setOperateper_bool((int) list.get(k).get("bool"));
			listres.add(operateper);
		}
		
		return listres;
	}
	
	public  int  GetOperateCount() {
		String sql="SELECT * FROM t_operateper";
		return jdbcTemplate.queryForList(sql).size();
	}
	
	
	/*
	 * 通过sql查询语句进行查询运营人员的详细信息
	 * 参数：SQL语句
	 * 2016-12-31 14:49:28
	 * */
	public List<Operateper> SearchOperateInfoByAccount_Dao(String a) {
		final List<Operateper> list = new ArrayList<Operateper>();
		String sql=" select * from t_operateper where account = '" + a +"'";
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			

			@Override
			public void processRow(java.sql.ResultSet rSet) throws SQLException {
				// TODO Auto-generated method stub
				Operateper operateper = new Operateper();
				operateper.setOperateper_name(rSet.getString(1));
				operateper.setOperateper_account(rSet.getString(2));
				operateper.setOperateper_role(rSet.getString(3));
				operateper.setOperateper_phone(rSet.getString(4));
				operateper.setOperateper_bool(rSet.getInt(5));
				list.add(operateper);
			}
		});
		return list;
	}
	/*
	 * 增加运营人员
	 * 参数：运营人员信息类
	 * 2016-12-31 16:33:22
	 * */
	public boolean AddOperateperInfo_Dao(Operateper operateper) {
		String sql = " select count(*) from t_operateper where account = '"
					+operateper.getOperateper_account()+"'";
		 
		
		if (jdbcTemplate.queryForObject(sql, Integer.class) == 0) {
			sql =  " insert into t_operateper (name,account,role,phone) values (?,?,?,?) ";
			jdbcTemplate.update(sql, new Object[]{
				operateper.getOperateper_name(),
				operateper.getOperateper_account(),
				operateper.getOperateper_role(),
				operateper.getOperateper_phone()
			});
			return true;
		}
		return false;
	}
	/*
	 * 	删除运营人员
	 * 	参数：运营人员账号
	 * 2016-12-31 20:47:13
	 * */
	public boolean DeleteOperateperInfo(String s) {
		String sql = "delete from t_operateper where account='"+s+"'";
		if (jdbcTemplate.update(sql)>0) {
			return true;
		} else {
			return false;
		}
		
		
	}
	
	/*
	 * 更新运营人员信息
	 * 参数：运营人员信息类
	 * 2016-12-31 20:48:22
	 * */
	public boolean UpdateOperateperInfo(Operateper operateper) {
		String sql = "update   t_operateper set name=?,account=?,role=?,phone=?,bool=?   where account=?";
		int i=jdbcTemplate.update(sql, new Object[]{operateper.getOperateper_name()
				,operateper.getOperateper_account(),operateper.getOperateper_role(),
				operateper.getOperateper_phone(),operateper.getOperateper_bool()
				,operateper.getOperateper_account()});
		if (i>0) {
			return true;
		} else {
			return false;
		}
		
		
	}
}
