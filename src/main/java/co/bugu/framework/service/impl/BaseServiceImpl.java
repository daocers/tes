package co.bugu.framework.service.impl;

import co.bugu.framework.dao.BaseDao;
import co.bugu.framework.dao.PageInfo;
import co.bugu.framework.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by user on 2017/1/5.
 */
public class BaseServiceImpl<T> implements IBaseService<T> {
    @Autowired
    BaseDao<T> baseDao;

    private String nameSpace = "";

    {
        Class clazz = this.getClass();
        ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        String simpleName = types[0].getTypeName();
        nameSpace = "tes." + simpleName.substring(0,1).toLowerCase() + simpleName.substring(1) + ".";
        
    }

    @Override
    public int save(T record) {
        return baseDao.insert(nameSpace + "insert", record);
    }

    @Override
    public int updateById(T record) {
        return baseDao.update(nameSpace + "updateById");
    }

    @Override
    public int delete(T record) {
        return baseDao.delete(nameSpace + "deleteById", record);
    }

    @Override
    public T findById(Integer id) {
        return baseDao.selectOne(nameSpace + "selectById", id);
    }

    @Override
    public List<T> findByObject(T record){
        return baseDao.selectList(nameSpace + "findByObject", record);
    }

    @Override
    public PageInfo findByObject(T record, PageInfo<T> pageInfo) throws Exception {
        return baseDao.listByObject(nameSpace + "findByObject", record, pageInfo);
    }

    @Override
    public PageInfo findByObject(T record, Integer showCount, Integer curPage) throws Exception {
        PageInfo<T> pageInfo = new PageInfo<T>(showCount, curPage);
        baseDao.listByObject(nameSpace + "findByObject", record, pageInfo);
        return pageInfo;
    }

}
