package com.remp.work.model.dto;

import java.io.Serializable;

public class Deprive implements Serializable {
	private String id;
	private String viId;
	private String day;
	private String cuId;
	private String cuName;
	private String prId;
	private String state;
	private String engineerId;
	private String engineerName;
	private String content;
	
	/**
	 * 
	 */
	public Deprive() {
		super();
	}

	/**
	 * @param id
	 * @param viId
	 * @param day
	 * @param cuId
	 * @param cuName
	 * @param prId
	 * @param state
	 * @param engineerId
	 * @param engineerName
	 * @param content
	 */
	public Deprive(String id, String viId, String day, String cuId, String cuName, String prId, String state,
			String engineerId, String engineerName, String content) {
		super();
		this.id = id;
		this.viId = viId;
		this.day = day;
		this.cuId = cuId;
		this.cuName = cuName;
		this.prId = prId;
		this.state = state;
		this.engineerId = engineerId;
		this.engineerName = engineerName;
		this.content = content;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the viId
	 */
	public String getViId() {
		return viId;
	}

	/**
	 * @param viId the viId to set
	 */
	public void setViId(String viId) {
		this.viId = viId;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return the cuId
	 */
	public String getCuId() {
		return cuId;
	}

	/**
	 * @param cuId the cuId to set
	 */
	public void setCuId(String cuId) {
		this.cuId = cuId;
	}

	/**
	 * @return the cuName
	 */
	public String getCuName() {
		return cuName;
	}

	/**
	 * @param cuName the cuName to set
	 */
	public void setCuName(String cuName) {
		this.cuName = cuName;
	}

	/**
	 * @return the prId
	 */
	public String getPrId() {
		return prId;
	}

	/**
	 * @param prId the prId to set
	 */
	public void setPrId(String prId) {
		this.prId = prId;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the engineerId
	 */
	public String getEngineerId() {
		return engineerId;
	}

	/**
	 * @param engineerId the engineerId to set
	 */
	public void setEngineerId(String engineerId) {
		this.engineerId = engineerId;
	}

	/**
	 * @return the engineerName
	 */
	public String getEngineerName() {
		return engineerName;
	}

	/**
	 * @param engineerName the engineerName to set
	 */
	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}