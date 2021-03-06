package com.bili.diushoujuaner.utils.entity.po;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "PARTY".
 */
public class Party {

    private Long id;
    private long partyNo;
    /** Not-null value. */
    private String partyName;
    private long ownerNo;
    private String information;
    /** Not-null value. */
    private String registerTime;
    /** Not-null value. */
    private String picPath;

    public Party() {
    }

    public Party(Long id) {
        this.id = id;
    }

    public Party(Long id, long partyNo, String partyName, long ownerNo, String information, String registerTime, String picPath) {
        this.id = id;
        this.partyNo = partyNo;
        this.partyName = partyName;
        this.ownerNo = ownerNo;
        this.information = information;
        this.registerTime = registerTime;
        this.picPath = picPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPartyNo() {
        return partyNo;
    }

    public void setPartyNo(long partyNo) {
        this.partyNo = partyNo;
    }

    /** Not-null value. */
    public String getPartyName() {
        return partyName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public long getOwnerNo() {
        return ownerNo;
    }

    public void setOwnerNo(long ownerNo) {
        this.ownerNo = ownerNo;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    /** Not-null value. */
    public String getRegisterTime() {
        return registerTime;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    /** Not-null value. */
    public String getPicPath() {
        return picPath;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

}
