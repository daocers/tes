package co.bugu.framework.dao;

import java.util.List;

/**
 * Created by daocers on 2016/6/14.
 *
 * 分页插件辅助类
 */
public class PageInfo<E> {
    /**
     * 查询出来的数据
     */
    private List<E> data;
    /**
     * 查询出来的数据总量
     */
    private Integer count;

    /**
     * 每页显示数量
     */
    private Integer showCount;

    /**
     * 页数
     */
    private Integer pageSize;
    /**
     * 当前页码
     */
    private Integer curPage;

    public PageInfo(){
        this.showCount = 10;
    }

    public PageInfo(Integer curPage){
        if(curPage == null){
            curPage = 1;
        }
        this.curPage = curPage;
        this.showCount = 10;
    }

    public PageInfo(Integer showCount, Integer curPage){
        if(curPage == null){
            curPage = 1;
        }
        if(showCount == null){
            showCount = 10;
        }
        this.showCount = showCount;
        this.curPage = curPage;
    }
    public List<E> getData() {
        return data;
    }

    /**
     * 设置数据
     * @param data
     */
    public void setData(List<E> data) {
        this.data = data;

    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
        this.pageSize = count / showCount;
        if(count % showCount > 0){
            pageSize++;
        }
    }

    public Integer getShowCount() {
        return showCount;
    }

    public void setShowCount(Integer showCount) {
        this.showCount = showCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }
}
