package com.organization.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrganizationVO implements Serializable {
    private Integer organizationId;
    private String name;            
    private String type;            
    private Timestamp createdTime;  
    private Integer status;         

    
    public OrganizationVO() {}

   
    public OrganizationVO(Integer organizationId, String name, String type, Timestamp createdTime, Integer status) {
        this.organizationId = organizationId;
        this.name = name;
        this.type = type;
        this.createdTime = createdTime;
        this.status = status;
    }

   
    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrganizationVO{" +
                "organizationId=" + organizationId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", createdTime=" + createdTime +
                ", status=" + status +
                '}';
    }
}
