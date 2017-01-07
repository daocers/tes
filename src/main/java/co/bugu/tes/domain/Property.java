package co.bugu.tes.domain;

import java.util.List;

public class Property {
    private Integer id;

    private String code;

    private String description;

    private Integer idx;

    private String name;

    private Integer status;

    private List<PropertyItem> propertyItemList;

    public List<PropertyItem> getPropertyItemList() {
        return propertyItemList;
    }

    public void setPropertyItemList(List<PropertyItem> propertyItemList) {
        this.propertyItemList = propertyItemList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}