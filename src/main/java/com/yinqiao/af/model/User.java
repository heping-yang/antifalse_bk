package com.yinqiao.af.model;

import java.util.Date;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AF_USER.TELNUM
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    private String telnum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AF_USER.OPENID
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    private String openid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AF_USER.PASSWORD
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AF_USER.USERNAME
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AF_USER.IDCARD
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    private String idcard;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AF_USER.EFFSTART
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    private Date effstart;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AF_USER.EFFEND
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    private Date effend;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AF_USER.USERSTATUS
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    private String userstatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column AF_USER.CREATETIME
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    private Date createtime;
    
    private String usertype;
    private String examType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AF_USER.TELNUM
     *
     * @return the value of AF_USER.TELNUM
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public String getTelnum() {
        return telnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AF_USER.TELNUM
     *
     * @param telnum the value for AF_USER.TELNUM
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AF_USER.OPENID
     *
     * @return the value of AF_USER.OPENID
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AF_USER.OPENID
     *
     * @param openid the value for AF_USER.OPENID
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AF_USER.PASSWORD
     *
     * @return the value of AF_USER.PASSWORD
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AF_USER.PASSWORD
     *
     * @param password the value for AF_USER.PASSWORD
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AF_USER.USERNAME
     *
     * @return the value of AF_USER.USERNAME
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AF_USER.USERNAME
     *
     * @param username the value for AF_USER.USERNAME
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AF_USER.IDCARD
     *
     * @return the value of AF_USER.IDCARD
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AF_USER.IDCARD
     *
     * @param idcard the value for AF_USER.IDCARD
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AF_USER.EFFSTART
     *
     * @return the value of AF_USER.EFFSTART
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public Date getEffstart() {
        return effstart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AF_USER.EFFSTART
     *
     * @param effstart the value for AF_USER.EFFSTART
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public void setEffstart(Date effstart) {
        this.effstart = effstart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AF_USER.EFFEND
     *
     * @return the value of AF_USER.EFFEND
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public Date getEffend() {
        return effend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AF_USER.EFFEND
     *
     * @param effend the value for AF_USER.EFFEND
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public void setEffend(Date effend) {
        this.effend = effend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AF_USER.USERSTATUS
     *
     * @return the value of AF_USER.USERSTATUS
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public String getUserstatus() {
        return userstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AF_USER.USERSTATUS
     *
     * @param userstatus the value for AF_USER.USERSTATUS
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column AF_USER.CREATETIME
     *
     * @return the value of AF_USER.CREATETIME
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column AF_USER.CREATETIME
     *
     * @param createtime the value for AF_USER.CREATETIME
     *
     * @mbg.generated Wed Jan 24 15:20:54 CST 2018
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}
}