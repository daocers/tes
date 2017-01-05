package co.bugu.tes.domain;

import java.util.Date;

public class Profile {
    private Integer id;

    private String examStatus;

    private Date examStatusUpdate;

    private String idNo;

    private String name;

    private Integer userId;

    private Date registTime;

    private Integer type;

    private Integer level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }

    public Date getExamStatusUpdate() {
        return examStatusUpdate;
    }

    public void setExamStatusUpdate(Date examStatusUpdate) {
        this.examStatusUpdate = examStatusUpdate;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
