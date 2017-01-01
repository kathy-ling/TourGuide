package com.TourGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.ScenicTicketsDao;
import com.TourGuide.model.ScenicTickets;

@Service
public class ScenicTicketService {

	@Autowired
	ScenicTicketsDao scenicTicketsDao;
	
	/**
	 * 根据景区编号，查询该景区的门票信息
	 * @param scenicNo  景区编号
	 * @return 景区的门票信息
	 */
	public ScenicTickets geTicketsByScenicNo(String scenicNo){
		return scenicTicketsDao.geTicketsByScenicNo(scenicNo);
	}
}
