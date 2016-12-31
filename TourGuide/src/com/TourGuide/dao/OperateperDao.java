package com.TourGuide.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
		int i=(currentPage-1)*rows;
		int j=currentPage*rows;
		String sql="SELECT * FROM t_operateper LIMIT "+i+" ,"+j+"";
		
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
}
