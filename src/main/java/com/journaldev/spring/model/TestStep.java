package com.journaldev.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TESTSTEP")
@NamedQuery(name = "TestStep.findById", query = "SELECT t FROM TestStep t WHERE t.id = :id")
public class TestStep implements java.io.Serializable{

	@Id
	@Column(name = "step_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	private String action;

	private String fieldId;

	private String fieldType;

	private String fieldValue;

	private String Result;
	
	@Override
	public String toString() {
		return "id, action, fieldId, fieldType, fieldValue, Result";
	}

	public String getAction() {
		return action;
	}
	
	public String getFieldId() {
		return fieldId;
	}

	public String getFieldType() {
		return fieldType;
	}

	public String getFieldValue() {
		return fieldValue;
	}


	public int getId() {
		return id;
	}


	public String getResult() {
		return Result;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setResult(String result) {
		Result = result;
	}

	public String getStepDetail() {
		return id+","+action+","+fieldId+","+fieldType+","+fieldValue+","+Result;
	}
	
}
