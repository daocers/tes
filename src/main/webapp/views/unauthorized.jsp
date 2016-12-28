<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/28
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>未认证</title>
</head>
<body>
认证未通过，或者权限不足
<a href="${pageContext.request.contextPath }/user/logout.do">退出</a>
</body>
</html>
