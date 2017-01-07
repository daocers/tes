package co.bugu.tes.domain;

public class PropertyItem {
    private Integer id;

    private String code;

    private Integer idx;

    private String name;

    private Integer propertyId;

    private String value;

    private Integer itemsIdx;


    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getItemsIdx() {
        return itemsIdx;
    }

    public void setItemsIdx(Integer itemsIdx) {
        this.itemsIdx = itemsIdx;
    }
}