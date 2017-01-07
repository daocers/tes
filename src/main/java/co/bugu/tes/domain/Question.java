package co.bugu.tes.domain;

import java.util.List;

public class Question {
    private Integer id;

    private String answer;

    private String content;

    private String extraInfo;

    private Integer metaInfoId;

    private Integer questionBankId;

    private String title;

    private String propItemIdInfo;


    private QuestionMetaInfo questionMetaInfo;

    public QuestionMetaInfo getQuestionMetaInfo() {
        return questionMetaInfo;
    }

    public void setQuestionMetaInfo(QuestionMetaInfo questionMetaInfo) {
        this.questionMetaInfo = questionMetaInfo;
    }

    private List<PropertyItem> propertyItemList;


    public String getPropItemIdInfo() {
        return propItemIdInfo;
    }

    public void setPropItemIdInfo(String propItemIdInfo) {
        this.propItemIdInfo = propItemIdInfo;
    }

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Integer getMetaInfoId() {
        return metaInfoId;
    }

    public void setMetaInfoId(Integer metaInfoId) {
        this.metaInfoId = metaInfoId;
    }

    public Integer getQuestionBankId() {
        return questionBankId;
    }

    public void setQuestionBankId(Integer questionBankId) {
        this.questionBankId = questionBankId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}