package com.datao.bigidea.entity;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 281080545417377L;

    private Integer id;

    private String loginName;

    private String name;

    private String ticket;

    private String phoneNum;

    private String email;

    private String status;

    private String role;

    private Integer vipTime;

    private String companyName;

    private String companyPosition;

    private String vipMsg;

    private String emailStatus;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPosition() {
        return companyPosition;
    }

    public void setCompanyPosition(String companyPosition) {
        this.companyPosition = companyPosition;
    }

    public Integer getVipTime() {
        return vipTime;
    }

    public void setVipTime(Integer vipTime) {
        this.vipTime = vipTime;
    }

    public String getVipMsg() {
        return vipMsg;
    }

    public void setVipMsg(String vipMsg) {
        this.vipMsg = vipMsg;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }
}