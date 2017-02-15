package com.TourGuide.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.Menu;


@Repository
public class MenuDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 通过账号得到角色
	 * @param account
	 * @return
	 * 2017-2-14 07:50:58
	 */
	public Map<String , Object> getMenuByrole(String account)
	{
		Map<String,Object > map=new HashMap<String, Object>();
		String sql="SELECT t_authority.role,t_authority.authvalue from "
				+ "t_authority,t_admin "
				+ "WHERE t_authority.role=t_admin.role "
				+ "AND t_admin.username='"+account+"'";
		String value=(String) jdbcTemplate.queryForMap(sql).get("authvalue");
		String role=(String) jdbcTemplate.queryForMap(sql).get("role");
		String sql1="SELECT * FROM t_menu GROUP BY MenuOrder ASC";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql1);
		char[] s1=value.toCharArray();
		String string="";
		
		for (int i = 0; i < s1.length; i++) {
			if (s1[i]=='1') {
				string=string+(String) list.get(i).get("MenuHtml");
			}
		}
		map.put("string", string);
		map.put("role",role);
		return map;
	}
	
	
	/**
	 * 得到后台运营人员权限
	 * @return
	 * 2017-2-14 07:51:29
	 */
	public List<Map<String, Object>> getAuthority()
	{
		List<Map<String, Object>> map=new ArrayList<>(); 
		String sql="SELECT t_authority.role,t_authority.authvalue from "
				+ "t_authority ";
		map=jdbcTemplate.queryForList(sql);
		return map;
	}
}
