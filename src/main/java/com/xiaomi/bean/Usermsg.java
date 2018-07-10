package com.xiaomi.bean;

import java.io.Serializable;
import java.util.Date;

public class Usermsg extends BaseEntity{
    private static final long serialVersionUID = 1L;

	private Integer id;

    private String reason;

    private Integer companytype;

    private Integer actiontype;

    private Date actiontime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id == null ? null : id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getCompanytype() {
        return companytype;
    }

    public void setCompanytype(Integer companytype) {
        this.companytype = companytype;
    }

    public Integer getActiontype() {
        return actiontype;
    }

    public void setActiontype(Integer actiontype) {
        this.actiontype = actiontype;
    }

    public Date getActiontime() {
        return actiontime;
    }

    public void setActiontime(Date actiontime) {
        this.actiontime = actiontime;
    }

	@Override
	public String toString() {
		return "Usermsg [id=" + id + ", reason=" + reason + ", companytype=" + companytype + ", actiontype="
				+ actiontype + ", actiontime=" + actiontime + "]";
	}
    
    
}