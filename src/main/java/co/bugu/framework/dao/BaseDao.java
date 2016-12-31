package co.bugu.framework.dao;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2016/12/31.
 */
public class BaseDao<T> extends SqlSessionDaoSupport {
    private static Logger logger = LoggerFactory.getLogger(BaseDao.class);

    public <T> T selectOne(String statement) {
        return this.getSqlSession().selectOne(statement);
    }

    public <T> T selectOne(String statement, Object parameter) {
        return this.getSqlSession().selectOne(statement, parameter);
    }

    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return this.getSqlSession().selectMap(statement, mapKey);
    }

    public <K, V> Map<K, V> selectMap(String statement, String parameter, String mapKey) {
        return this.getSqlSession().selectMap(statement, parameter, mapKey);
    }

    public <K, V> Map<K, V> selectMap(String statement, Object parameter,
                                      String mapKey, RowBounds rowBounds) {
        return this.getSqlSession().selectMap(statement, parameter, mapKey, rowBounds);
    }

    public <E> List<E> selectList(String statement) {
        return this.getSqlSession().selectList(statement);
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        return this.getSqlSession().selectList(statement, parameter);
    }

    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return this.getSqlSession().selectList(statement, parameter, rowBounds);
    }

    public int insert(String statement) {
        return this.getSqlSession().insert(statement);
    }

    /**
     * {@inheritDoc}
     */
    public int insert(String statement, Object parameter) {
        return this.getSqlSession().insert(statement, parameter);
    }

    /**
     * {@inheritDoc}
     */
    public int update(String statement) {
        return this.getSqlSession().update(statement);
    }

    /**
     * {@inheritDoc}
     */
    public int update(String statement, Object parameter) {
        return this.getSqlSession().update(statement, parameter);
    }

    /**
     * {@inheritDoc}
     */
    public int delete(String statement) {
        return this.getSqlSession().delete(statement);
    }

    /**
     * {@inheritDoc}
     */
    public int delete(String statement, Object parameter) {
        return this.getSqlSession().delete(statement, parameter);
    }

    /**
     * 分页方法
     *
     * @param statement
     * @param parameter
     * @param pageNo
     * @return
     */
    public <E> List<E> selectListByPage(String statement, Object parameter, String pageNo) {
        if (pageNo == null) {
            pageNo = "1";
        }
        int no = Integer.valueOf(pageNo);
        int offset = (no - 1) * 10;
        RowBounds rowBounds = new RowBounds(offset, 10);
        return this.selectList(statement, parameter, rowBounds);
    }

    /**
     * 列表查询，根据传入的domain实例
     *
     * @param statement
     * @param parameter
     * @param pageInfo
     * @param <E>
     * @return
     * @throws Exception
     */
    public <E> PageInfo<E> listByObject(String statement, Object parameter, PageInfo<E> pageInfo) throws Exception {
        RowBounds rowBounds = new RowBounds((pageInfo.getCurPage() - 1) * pageInfo.getShowCount(),
                pageInfo.getShowCount());
        List<E> list = this.selectList(statement, parameter, rowBounds);
        pageInfo.setData(list);

        //设置记录总数，顺便更新页码等信息
        Integer count = getCount(statement, parameter);
        pageInfo.setCount(count);
        return pageInfo;
    }

    public <E> PageInfo<E> searchByParam(String statement, Map<String, Object> searchParam, PageInfo<E> pageInfo) throws SQLException {
        String where = processSearchCondition(searchParam);
        MappedStatement mappedStatement = this.getSqlSession().getConfiguration().getMappedStatement(statement);
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Connection connection = this.getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();

        String sql = boundSql.getSql();



        return pageInfo;
    }

    /**
     * 处理查询条件，
     * @param searchParam
     * @return where子句
     */
    private String processSearchCondition(Map<String, Object> searchParam) {
        StringBuffer buffer = new StringBuffer();
        Iterator<Map.Entry<String, Object>> iter = searchParam.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, Object> entry = iter.next();
            String key = entry.getKey();
            if(key.contains("_")){
                String[] parts = key.split("_");
                String operation = parts[0];
                String field = parts[1];
                this.getSqlSession().getConfiguration().getre
            }
        }

    }

    /**
     * 获取查询总记录
     *
     * @param statement
     * @param parameterObject
     * @return
     * @throws Exception
     */
    private int getCount(String statement, Object parameterObject) throws Exception {

        MappedStatement mappedStatement = this.getSqlSession().getConfiguration().getMappedStatement(statement);
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        Connection connection = this.getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();

        String sql = boundSql.getSql();
        sql = sql.toLowerCase();

        String newSql = "select count(0) as cnt from " + sql.split("from")[1];

        logger.debug("执行分页，查询语句为： {}", newSql);

        // 执行统计SQL
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), newSql, boundSql.getParameterMappings(), parameterObject);
        DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, newBoundSql);
        PreparedStatement ps = null;
        int count = 0;
        try {
            ps = connection.prepareStatement(newSql);
            parameterHandler.setParameters(ps);
            ResultSet rs = ps.executeQuery();
            count = (rs.next()) ? rs.getInt("cnt") : 0;
            rs.close();
        } catch (SQLException e) {
            throw new Exception("执行记录总数SQL时发生异常", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                throw new Exception("关闭状态时发生异常", e);
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                throw new Exception("关闭连接发生异常", e);
            }
        }

        return count;
    }


}
