package com.TourGuide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.OperateperDao;
import com.TourGuide.model.Operateper;

@Service
public class OperateperService {
	
	@Autowired
	private OperateperDao operateperDao;
	
	public List<Operateper> getOperatepersByPage(int i,int j) {
		return operateperDao.GetOperateUseInfoByPage(i, j);
	}
	
	public  int  GetOperateCount()
	{
		return operateperDao.GetOperateCount();
	}
}
