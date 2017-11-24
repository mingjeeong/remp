package com.remp.work.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remp.work.model.dao.ItemsDao;
import com.remp.work.model.dto.Item;

@Service("assetService")
public class AssetServiceImpl implements AssetService {
	@Autowired
	private ItemsDao itemDao;

	public ArrayList<Item> getItemList(){
		return itemDao.getItemList();
	}
	
	public ArrayList<Item> getSearchList(String sb_search, String item){
		return itemDao.getSearchList(sb_search, item);
	}
	
	public Item getItem(int itemId) {
		return itemDao.getItem(itemId);
	}

}
