package com.remp.work.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("eisDao")
public class EisDaoImpl implements EisDao {
	private static EisDaoImpl instance;
	private  EisDaoImpl() {
		
	}
	public static EisDaoImpl getInstance() {
		if(instance == null) {
			instance = new EisDaoImpl();
		}
		return instance;
	}
	
	private FactoryDao factory;
	@Autowired
	public void setFactoryDao(FactoryDao factory) {
		this.factory = factory;
	}
}
