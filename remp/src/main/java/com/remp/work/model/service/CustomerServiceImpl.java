package com.remp.work.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remp.work.model.dao.CustomesrDao;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomesrDao customerDao;
	 
	@Override
	public Boolean setPassword(String id, String pw, String newPw) {
		return null;
		//return customerDao.updatePassword(id, pw, newPw);
	}
}
