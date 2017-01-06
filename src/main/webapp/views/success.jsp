<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/28
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录成功</title>
</head>
<body>
欢迎你${user.username }
<a href="${pageContext.request.contextPath }/user/logout.do">退出</a>
<shiro:hasRole name="admin">系统管理员</shiro:hasRole>
<shiro:hasRole name="会员">会员</shiro:hasRole>
</body>
</html>
