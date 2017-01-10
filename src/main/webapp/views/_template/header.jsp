<%--
  Created by IntelliJ IDEA.
  User: daocers
  Date: 2016/7/6
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <link href="../assets/css/bootstrap.css" rel="stylesheet" >
    <script src="../assets/js/jquery-2.2.0.min.js"></script>
    <script src="../assets/js/bootstrap.js"></script>
    <script src="../assets/js/bugu.extend.js"></script>

    <link href="../assets/css/bootstrap-switch.css" rel="stylesheet">
    <script src="../assets/js/bootstrap-switch.js"></script>

    <link href="../assets/css/jquery.filer.css" type="text/css" rel="stylesheet"/>
    <link href="../assets/css/jquery.filer-dragdropbox-theme.css" type="text/css" rel="stylesheet"/>
    <script src="../assets/js/jquery.filer.js"></script>
    
    <%--<link href="../assets/css/kacha-table-editable.css" rel="stylesheet"/>--%>
    <%--<script src="../assets/js/bugu.editable-table.js"></script>--%>

    <script src="../assets/js/eModal.js"></script>
    
    <script src="../assets/js/bootstrap-datetimepicker.min.js"></script>
    <%--<script src="../assets/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>--%>
    <link href="../assets/css/bootstrap-datetimepicker.min.css" rel="stylesheet" >

    <%--<link href="../assets/css/kacha.css" rel="stylesheet">--%>


    <%--表单校验--%>
    <script src="../assets/js/validator.js"></script>

    <%--b表单优化插件--%>
    <link rel="stylesheet" href="../assets/css/jquery-filestyle.css">
    <script src="../assets/js/jquery-filestyle.min.js"></script>

    <%--定时器--%>
    <link rel="stylesheet" href="../assets/css/jquery.syotimer.css">
    <script src="../assets/js/jquery.syotimer.js"></script>

    <%--弹出框--%>
    <link rel="stylesheet" href="../assets/css/sweetalert2.css">
    <script src="../assets/js/sweetalert2.js"></script>

    <%--另外一个定时器--%>
    <script src="../assets/js/jquery.countdown.js"></script>

    <link href="../assets/css/my.css" rel="stylesheet">

    <script src="../assets/js/page-nav.js"></script>
    <script src="../assets/js/my.js"></script>
    <script src="../assets/js/common.js"></script>
    <%--美化checkbox和radio--%>
    <%--<link href="../assets/css/square/blue.css" rel="stylesheet">--%>
    <%--<script src="../assets/js/icheck.min.js"></script>--%>


    <%--模态对话框--%>
    <link href="../assets/css/zeroModal.css" rel="stylesheet">
    <script src="../assets/js/zeroModal.min.js"></script>


    <%--日期选择框--%>
    <link href="../assets/css/flatpickr.material_blue.min.css" rel="stylesheet">
    <script src="../assets/js/flatpickr.min.js"></script>
    <script src="../assets/js/flatpickr.l10n.zh.js"></script>
</head>
<body>

    <jsp:include page="/views/_template/message.jsp"></jsp:include>
    <script>
        $(function () {
            var err = $("#err").text();
            var msg = $("#msg").text();
            console.log("err:", err);
            console.log("msg:", msg);
            if(err.length > 0){
//                zeroModal.show({content: 'show somethings'});
            }
            if(msg.length > 0){
//                zeroModal.show("something");
//                zeroModal.loading("1")
//                zeroModal.alert("我去")
//                zeroModal.error("错啦错啦")
//                zeroModal.success("干的漂亮")
//                zeroModal.confirm("确定继续吗？");

            }
        });




        function _basic() {
            zeroModal.show();
        }

        function _params() {
            zeroModal.show({
                title: 'hello world',
                content: 'this is zeroModal'
            });
        }

        function _button() {
            zeroModal.show({
                title: 'hello world',
                content: 'this is zeroModal',
                ok: true,
                cancel: true,
                okFn: function() {
                    alert('clicked ok and not close');
                    return false;
                }
            });
        }

        function _setsize() {
            zeroModal.show({
                title: 'hello world',
                content: 'this is zeroModal',
                width: '60%',
                height: '40%'
            });
        }

        function _notoverlay() {
            zeroModal.show({
                title: 'hello world',
                content: 'this is zeroModal',
                width: '60%',
                height: '40%',
                overlay: false
            });
        }

        function _iframe() {
            zeroModal.show({
                title: 'hello world',
                iframe: true,
                url: 'http://www.baidu.com',
                width: '80%',
                height: '80%',
                cancel: true
            });
        }

        /**
         * esc退出
         * */
        function _esc() {
            zeroModal.show({
                title: 'hello world',
                content: 'this is zeroModal',
                esc: true
            });
        }

        function _resize() {
            zeroModal.show({
                title: 'hello world',
                content: 'this is zeroModal',
                width: '60%',
                height: '40%',
                resize: true
            });
        }
/*允许最大化*/
        function _max() {
            zeroModal.show({
                title: 'hello world',
                content: 'this is zeroModal',
                width: '60%',
                height: '40%',
                max: true
            });
        }

        function _loading(type) {
            zeroModal.loading(type);
        }

        function _progress() {
            zeroModal.progress();
        }

        function _alert1() {
            zeroModal.alert('请选择数据进行操作!');
        }

        function _alert2() {
            zeroModal.alert({
                content: '操作提示!',
                contentDetail: '请选择数据后再进行操作',
                okFn: function() {
                    alert('ok callback');
                }
            });
        }

        function _confirm1() {
            zeroModal.confirm("确定提交审核吗？", function() {
                alert('ok');
                //return false;
            });
        }

        function _confirm2() {
            zeroModal.confirm({
                content: '确定提交审核吗？',
                contentDetail: '提交后将不能进行修改。',
                okFn: function() {
                    alert('ok');
                },
                cancelFn: function() {
                    alert('cancel');
                }
            });
        }

        function _error() {
            zeroModal.error('请选择数据进行操作!');
        }

        function _success() {
            zeroModal.success('操作成功!');
        }

        function _setOpacity() {
            zeroModal.show({
                title: 'hello world',
                content: 'this is zeroModal',
                width: '60%',
                height: '40%',
                opacity: 0.8
            });
        }

    </script>

    <input type="text" id="date" class="date form-control" dateFormat="yyyy-mm-dd" enableTime="true">
    <script>
        /**
         * 日期类型
         */
        $(".date").flatpickr({
        });
        /**
         * 时间类型，默认打开时间显示
         */
        $(".time").flatpickr({
            enableTime: true,
        })
    </script>
</body>
</html>
