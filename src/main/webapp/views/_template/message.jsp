<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2017/1/9
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="hide" id="msg">
       ${msg}
    </div>
    <div class="hide" id="err">
        ${err}
    </div>

    <script>
        $(function () {
            var msg = $("#msg").text().trim();
            if(msg && msg != ''){
                zeroModal.alert({
                    content: '提示',
                    contentDetail: msg,
                    okFn: function() {
                    }
                });
            }

            var err = $("#err").text().trim();
            if(err && err != ''){
                zeroModal.error({
                    content: "异常",
                    contentDetail: err
                })
            }

        })
    </script>





</body>
</html>
