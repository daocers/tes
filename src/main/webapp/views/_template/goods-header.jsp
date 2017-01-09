<%--
  Created by IntelliJ IDEA.
  User: daocers
  Date: 2016/7/10
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <title>goods header</title>
    <style>
        .head{
            line-height: 65px;
        }
        .head > *{
            vertical-align: middle;
        }
        .button-group{
            margin-left: 30px;
        }
        .to-home{
            margin-top: 15px;
        }
    </style>
</head>
<body>
<div class="row head">
    <input type="hidden" id="type" value="${type}">
    <div class="logo center-block pull-left">
        商品管理
    </div>
    <div class="button-group pull-left">
        <a class="btn btn-sm btn-success" href="/kacha-admin-retailer/mall/mallGoods/list.do">商品浏览</a>
        <a class="btn btn-sm btn-success" href="/kacha-admin-retailer/mall/mallGoods/import.do">信息导入</a>
        <a class="btn btn-sm btn-success" href="/kacha-admin-retailer/mall/mallCategory/list.do">商品类型</a>
        <%--<a class="btn btn-sm btn-success" href="/mall/mallProp/list.do">商品属性</a>--%>
        <a class="btn btn-sm btn-success" href="/kacha-admin-retailer/mall/mallGuarantee/list.do">服务保障</a>
        <a class="btn btn-sm btn-success" href="/kacha-admin-retailer/mall/mallMerchant/list.do">供应商</a>
        <a class="btn btn-sm btn-success" href="/kacha-admin-retailer/mall/mallBrand/list.do">品牌</a>
        <a class="btn btn-sm btn-success" href="#">运费模板</a>
    </div>
    <div class="to-home pull-right">
        <button class="btn btn-sm btn-info">返回首页</button>
    </div>
</div>
</body>
</html>
