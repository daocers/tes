package co.bugu.framework.dao;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

/**
 * Created by user on 2017/1/4.
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class SearchInterceptor implements Interceptor {
    private Logger logger = LoggerFactory.getLogger(SearchInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Map<String, Object> searchParameter = ThreadLocalUtil.get();
//        没有额外的查询参数
        if (searchParameter == null) {
            return invocation.proceed();
        }
        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
            // 可以分离出最原始的的目标类)
            while (metaStatementHandler.hasGetter("h")) {
                Object object = metaStatementHandler.getValue("h");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            // 分离最后一个代理对象的目标类
            while (metaStatementHandler.hasGetter("target")) {
                Object object = metaStatementHandler.getValue("target");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            //分页信息if (localPage.get() != null) {
//            Page page = localPage.get();
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            // 分页参数作为参数对象parameterObject的一个属性
            String sql = boundSql.getSql();
            // 重写sql
//            String pageSql = buildPageSql(sql, page);
            String newSql = sql + " and username = 'allen'";
            //重写分页sql
//            metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
            metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
//            Connection connection = (Connection) invocation.getArgs()[0];
            // 重设分页参数里的总页数等
//            setPageParameter(sql, connection, mappedStatement, boundSql, page);
            // 将执行权交给下一个拦截器
            return invocation.proceed();
        } else if (invocation.getTarget() instanceof ResultSetHandler) {
            Object result = invocation.proceed();
//            Page page = localPage.get();
//            page.setResult((List) result);
            return result;
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {

        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String name = properties.getProperty("name");
    }



//    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
//                                  BoundSql boundSql, Page page) {
//        // 记录总记录数
//        String countSql = "select count(0) from (" + sql + ")";
//        PreparedStatement countStmt = null;
//        ResultSet rs = null;
//        try {
//            countStmt = connection.prepareStatement(countSql);
//            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
//                    boundSql.getParameterMappings(), boundSql.getParameterObject());
//            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
//            rs = countStmt.executeQuery();
//            int totalCount = 0;
//            if (rs.next()) {
//                totalCount = rs.getInt(1);
//            }
//            page.setTotal(totalCount);
//            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
//            page.setPages(totalPage);
//        } catch (SQLException e) {
//            logger.error("Ignore this exception", e);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                rs.close();
//            } catch (SQLException e) {
//                logger.error("Ignore this exception", e);
//            }
//            try {
//                countStmt.close();
//            } catch (SQLException e) {
//                logger.error("Ignore this exception", e);
//            }
//        }
//    }
}
