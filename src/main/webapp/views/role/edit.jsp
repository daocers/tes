<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../_template/header.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>角色管理</title>
    <link rel="stylesheet" href="../assets/css/ztree/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="../assets/js/jquery.ztree.all.min.js"></script>
    <%--<script type="text/javascript" src="../assets/js/jquery.ztree.excheck.js"></script>--%>
    <%--<script type="text/javascript" src="../assets/js/jquery.ztree.exedit.js"></script>--%>
    <SCRIPT type="text/javascript">
        <!--
        var setting = {
            check: {
                enable: true
            },
            data: {
                key: {
                    title: "title"
                },
                simpleData: {
                    enable: true
                }
            },
        };
        var zNodes = eval(${zNode});


        function setTitle(node) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = node ? [node]:zTree.transformToArray(zTree.getNodes());
            for (var i=0, l=nodes.length; i<l; i++) {
                var n = nodes[i];
                n.title = "[" + n.id + "] isFirstNode = " + n.isFirstNode + ", isLastNode = " + n.isLastNode;
                zTree.updateNode(n);
            }
        }
        function showNodes() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    nodes = zTree.getNodesByParam("isHidden", true);
            zTree.showNodes(nodes);
            setTitle();
        }
        function hideNodes() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    nodes = zTree.getSelectedNodes();
            if (nodes.length == 0) {
                alert("请至少选择一个节点");
                return;
            }
            zTree.hideNodes(nodes);
            setTitle();
            count();
        }

        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            $("#hideNodesBtn").bind("click", {type:"rename"}, hideNodes);
            $("#showNodesBtn").bind("click", {type:"icon"}, showNodes);
            setTitle();
        });
        //-->
    </SCRIPT>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="#">首页</a></li>
            <li><a href="#">品类管理</a></li>
            <li><a href="#" class="active">品类编辑</a></li>
        </ol>
    </div>
    <input type="hidden" value="${type}" id="type">
    <div class="row">
        <div class="col-md-8">
            <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${role.id}">

                <div class="form-group">
                    <label class="control-label col-md-2">名称</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${role.name}" required>
                        <span class="help-block with-errors"></span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">描述</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="description" value="${role.description}" required>
                        <span class="help-block with-errors">描述角色的权利和角色责任以及使用场景</span>
                    </div>
                </div>


                <div class="form-group">
                    <label class="control-label col-md-2">权限</label>
                    <input type="hidden" name="nodeInfo">
                    <div class="col-md-10">
                        <div class="zTreeDemoBackground left">
                            <ul id="treeDemo" class="ztree"></ul>
                        </div>
                    </div>
                </div>

                <div class="button pull-right">
                    <button class="btn btn-primary btn-commit">保存</button>
                    <div class="space">

                    </div>
                    <button class="btn btn-warning btn-cancel">取消</button>
                </div>
            </form>
        </div>

    </div>
</div>

<script>
    $(function () {
        $(".btn-commit").on("click", function () {
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var data = treeObj.transformToArray(treeObj.getCheckedNodes(true));
            $("[name='nodeInfo']").val(data);

            $("form").submit();
        })
    })
</script>
</body>
</html>
