package com.remp.work.model.service;

import java.util.ArrayList;

import com.remp.work.model.dto.Item;

public interface AssetService {
	public ArrayList<Item> getItemList();
	public ArrayList<Item> getSearchList(String sb_search, String item);
	public Item getItem(int itemId);

}
