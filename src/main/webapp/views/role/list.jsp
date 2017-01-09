<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../_template/header.jsp" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理</title>
    <style>
        .cell-edit{

        }
    </style>
    <script>
        $(function () {
            $("th.cell-edit").each(function (idx, e) {
                console.log("e", e);

                var index = $(e).parents("tr").find("th").index($(e));
                console.log("index", index);
                $("tr td:eq(" + index + ")").each(function (idx1, e1) {
                    var value = $(e1).text();
                    $(e1).html("<input type='text' value='" + value + "' class='form-control form-control-intable'>");
                });
            });
//            $("td.cell-edit").each(html("<input class='form-control' value='" + $(this).val() + "'>");

        })
    </script>
</head>
<body>
<div class="container">

    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="#">首页</a></li>
            <li><a href="#" class="active">商品管理</a></li>
        </ol>
    </div>
    <div class="row info-search">
        <div class="pull-right form-inline">
            <input type="text" class="form-control" placeholder="输入关键词，例如名称、品牌、序号、供应商等">
            <!--<span class="input-group-btn">-->
            <button class="btn btn-info" type="button">搜索</button>
        </div>
    </div>

    <div class="row pre-table">
        <div class="pull-right">
            <jsp:include page="../_template/page-nav.jsp"/>
        </div>
    </div>
    <div class="row table-responsive">
        <table class="table table-bordered  table-editable">
            <thead>
            <tr>
                <th><input type="checkbox" class="selectAll"></th>
                <th class="cell-edit">角色名称</th>
                <th>描述</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pi.data}" var="role" varStatus="line">
                <tr>
                    <td><input type="checkbox" objId="${role.id}"></td>
                    <td class="cell-edit">${role.name}</td>
                    <td>${role.description}</td>
                    <td>
                        <a href="edit.do?id=${role.id}&type=detail" class="opr">详情</a>
                        <a href="edit.do?id=${role.id}" class="opr">修改</a>
                        <a href="javascript:del(${role.id})" class="opr">删除</a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>

    <div id="info">
        ${info}
    </div>

    <div class="row after-table">
        <div class="pull-left form-inline">
            <select class="form-control show-count">
                <option value="10" <c:if test="${ pi.showCount == 10 }">selected</c:if>>10</option>
                <option value="25" <c:if test="${ pi.showCount == 25}">selected</c:if>>25</option>
                <option value="50" <c:if test="${ pi.showCount == 50}">selected</c:if>>50</option>
            </select>
            <div>条/页</div>

        </div>
        <div class="pull-right">

        </div>
    </div>
</div>
<script type="javascript">
    $()
</script>
</body>
</html>