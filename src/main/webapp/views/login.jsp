<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/28
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <form about="login.do" method="post">
        username: <input type="text" name="username"/> <br/>
        password: <input type="password" name="password"/><br/>
        <input type="submit" value="登录">${error}
    </form>

</body>
</html>
