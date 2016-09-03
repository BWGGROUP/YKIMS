package cn.com.sims.model.code;

import java.sql.Timestamp;


public class CodeGenerator  {
    
	private int generatorId;
	private int generatorType;
	private long startNum;
	private long endNum;
	private int stepNum;
	private String generatorName;
	private String deleteflag;
	private String createid;
	private Timestamp createtime;
	private String updid;
	private Timestamp updtime;
	public int getGeneratorId() {
		return generatorId;
	}
	public void setGeneratorId(int generatorId) {
		this.generatorId = generatorId;
	}
	public int getGeneratorType() {
		return generatorType;
	}
	public void setGeneratorType(int generatorType) {
		this.generatorType = generatorType;
	}
	public long getStartNum() {
		return startNum;
	}
	public void setStartNum(long startNum) {
		this.startNum = startNum;
	}
	public long getEndNum() {
		return endNum;
	}
	public void setEndNum(long endNum) {
		this.endNum = endNum;
	}
	
	public int getStepNum() {
		return stepNum;
	}
	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}
	public String getGeneratorName() {
		return generatorName;
	}
	public void setGeneratorName(String generatorName) {
		this.generatorName = generatorName;
	}
	
	public String getDeleteflag() {
	    return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
	    this.deleteflag = deleteflag;
	}
	public String getCreateid() {
	    return createid;
	}
	public void setCreateid(String createid) {
	    this.createid = createid;
	}
	public Timestamp getCreatetime() {
	    return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
	    this.createtime = createtime;
	}
	public String getUpdid() {
	    return updid;
	}
	public void setUpdid(String updid) {
	    this.updid = updid;
	}
	public Timestamp getUpdtime() {
	    return updtime;
	}
	public void setUpdtime(Timestamp updtime) {
	    this.updtime = updtime;
	}
}
