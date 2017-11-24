package com.remp.work.model.dto;

import java.io.Serializable;

public class Product implements Serializable{
	private String id;
	private String buyId;
	private String name;
	private String code;
	private String firstDay;
	private String inDay;
	private String manufacturer;
	private String outDay;
	private String outTarget;
	private String location;
	private String needs;
	private String state;
	private String contract;
	private int count;
	private String qr;
	
	public Product() {
	
	}

	public Product(String id, String buyId, String name, String code, String firstDay, String inDay,
			String manufacturer, String outDay, String outTarget, String location, String needs, String state,
			String contract, int count, String qr) {
		this.id = id;
		this.buyId = buyId;
		this.name = name;
		this.code = code;
		this.firstDay = firstDay;
		this.inDay = inDay;
		this.manufacturer = manufacturer;
		this.outDay = outDay;
		this.outTarget = outTarget;
		this.location = location;
		this.needs = needs;
		this.state = state;
		this.contract = contract;
		this.count = count;
		this.qr = qr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBuyId() {
		return buyId;
	}

	public void setBuyId(String buyId) {
		this.buyId = buyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(String firstDay) {
		this.firstDay = firstDay;
	}

	public String getInDay() {
		return inDay;
	}

	public void setInDay(String inDay) {
		this.inDay = inDay;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getOutDay() {
		return outDay;
	}

	public void setOutDay(String outDay) {
		this.outDay = outDay;
	}

	public String getOutTarget() {
		return outTarget;
	}

	public void setOutTarget(String outTarget) {
		this.outTarget = outTarget;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNeeds() {
		return needs;
	}

	public void setNeeds(String needs) {
		this.needs = needs;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getQr() {
		return qr;
	}

	public void setQr(String qr) {
		this.qr = qr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(id);
		builder.append(", ");
		builder.append(buyId);
		builder.append(", ");
		builder.append(name);
		builder.append(", ");
		builder.append(code);
		builder.append(", ");
		builder.append(firstDay);
		builder.append(", ");
		builder.append(inDay);
		builder.append(", ");
		builder.append(manufacturer);
		builder.append(", ");
		builder.append(outDay);
		builder.append(", ");
		builder.append(outTarget);
		builder.append(", ");
		builder.append(location);
		builder.append(", ");
		builder.append(needs);
		builder.append(", ");
		builder.append(state);
		builder.append(", ");
		builder.append(contract);
		builder.append(", ");
		builder.append(count);
		builder.append(", ");
		builder.append(qr);
		return builder.toString();
	}
}
