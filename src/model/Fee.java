package model;

import java.io.Serializable;

public class Fee implements Serializable {

	private static final long serialVersionUID = 1L;
	private int fee_id;
	private String fee_crs_id;
	private int fee_amount;
	private String fee_type;
	private String fee_description;
	
	public Fee() {
		super();
	}
	
	
	public int getFeeId() {
		return this.fee_id;
	}
	
	public void setFeeId(int id) {
		this.fee_id = id;
	}
	
	public String getFeeCrs() {
		return this.fee_crs_id;
	}
	
	public void setFeeCrs(String crsId) {
		this.fee_crs_id = crsId;
	}
	
	public int getFeeAmt() {
		return this.fee_amount;
	}
	
	public void setFeeAmt(int feeAmt) {
		this.fee_amount = feeAmt;
	}
	
	public String getFeeType() {
		return this.fee_type;
	}
	
	public void setFeeType(String feeType) {
		this.fee_type = feeType;
	}
	
	public String getFeeDesc() {
		return this.fee_description;
	}
	
	public void setFeeDesc(String desc) {
		this.fee_description = desc;
	}
}
