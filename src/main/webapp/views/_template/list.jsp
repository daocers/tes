<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	pageContext.setAttribute("path", path);
%>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>

	<ul class="breadcrumb">
		<li><i class="icon-home home-icon"></i> <a href="${path }">首页</a></li>

		<li>图书管理</li>
		<li class="active">出版单位管理</li>
	</ul>
</div>

<div class="page-content">
	<div class="page-header">
		<h1>
			<i class="icon-double-angle-right"></i> 出版单位列表
		</h1>
	</div>
	<!-- /.page-header -->
	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<div class="row-fluid">
				<div class="col-xs-12">
					<!-- 检索  -->
					<form action="${path }/control/publisher_list?keyMenu=${param.keyMenu}&menu=${param.menu}" method="post" name="userForm" id="userForm">
						<table>
							<tr>
								<td><span class="input-icon"><input autocomplete="off" class="form-control search-query" id="nav-search-input" type="text" name="name" id="name" value="${name }" placeholder="这里输入出版社名称" />
								<i class="icon-book blue" ></i></span></td>
								<td><label for="province">所在省份: &nbsp;</label></td>
								<td><select class="form-control" name="province" id="province">
										<option value="0">请选择</option>
										<c:forEach items="${area }" var="item">
											<option value="${item.areaId }" <c:if test="${item.areaId == province }"> selected="selected"</c:if>>${item.areaNameZh }</option>
										</c:forEach>
									</select>
								</td>
								<td>
								<span class="input-group-btn"  data-rel="tooltip" data-placement="top" data-original-title="搜索">
									<button type="button" class="btn btn-purple btn-sm" onclick="query();">
										搜索
										<i class="icon-search icon-on-right bigger-110"></i>
									</button>
								</span>
								</td>
								<td>
								<span class="input-group-btn"  data-rel="tooltip" data-placement="top" data-original-title="批量删除">
									<button type="button" class="btn btn-danger btn-sm" onclick="delAll();" >
										批量删除
										<i class="icon-trash icon-on-right bigger-110"></i>
									</button>
								</span>
								</td>
								<td>
								<span class="input-group-btn"  data-rel="tooltip" data-placement="top" data-original-title="新增">
									<button type="button" class="btn btn-primary btn-sm" onclick="add();">
										新增
										<i class=" icon-plus icon-on-right bigger-110"></i>
									</button>
								</span></td>
							</tr>
						</table>
					</form>
					<!-- 检索  -->
					<div class="table-responsive">
						<table id="sample-table-1"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">
									<label><input type="checkbox" class="ace" onclick="checkAll(this);" /> <span class="lbl">全选</span></label></th>
									<th>出版社名称</th>
									<th>出版社编码</th>
									<th>社长</th>
									<th>出版社电话</th>
									<th>所在省</th>
									<th>所在市</th>
									<th>地址</th>
									<th width="6%">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${publisherList }" var="item">
									<tr>
										<td class="center"><label><input type="checkbox" name="bookId_check"
												class="ace" value="${item.publisherId }" /><span class="lbl"></span>
										</label></td>
										<td class="text-overflow" title="${item.name }"><span class="label-sm">${item.name }</span></td>
										<td><span class="label-sm">${item.code }</span></td>
										<td><span class="label-sm">${item.manager }</span></td>
										<td class="hidden-480"><span class="label-sm">${item.phone }</span></td>
										<td class="hidden-480"><span
											class="label label-sm label-warning">${item.province }</span></td>
										<td><span class="label-sm">${item.city }</span></td>
										<td><span class="label-sm">${item.address }</span></td>
										<td>
											<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
												<a class="green tooltip-success" data-rel="tooltip" data-placement="top" data-original-title="编辑出版社信息" href="${path }/control/publisher_preModify?keyMenu=${param.keyMenu}&menu=${param.menu}&id=${item.publisherId }&province=${item.provinceCode}">
												<i class="icon-edit bigger-130"></i>
												</a> 
												<a class="red tooltip-success" data-rel="tooltip" data-placement="left" data-original-title="删除出版社信息" href="javascript:confirmDel('${item.publisherId }');">
													<i class="icon-trash bigger-130"></i>
												</a>
											</div>
											<div class="visible-xs visible-sm hidden-md hidden-lg">
												<div class="inline position-relative">
													<button class="btn btn-minier btn-primary dropdown-toggle"
														data-toggle="dropdown">
														<i class="icon-cog icon-only bigger-110"></i>
													</button>

													<ul
														class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
														<li><a href="#" class="tooltip-info"
															data-rel="tooltip" title="View"> <span class="blue">
																	<i class="icon-zoom-in bigger-120"></i>
															</span>
														</a></li>

														<li><a href="#" class="tooltip-success"
															data-rel="tooltip" title="Edit"> <span class="green">
																	<i class="icon-edit bigger-120"></i>
															</span>
														</a></li>

														<li><a href="#" class="tooltip-error"
															data-rel="tooltip" title="Delete"> <span class="red">
																	<i class="icon-trash bigger-120"></i>
															</span>
														</a></li>
													</ul>
												</div>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- /.table-responsive -->
					<div class="modal-footer no-margin-top">${page.pageStr}</div>
				</div>
				<!-- /span -->
			</div>
			<!-- /row -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->
<!-- /.main-container -->

<!-- inline scripts related to this page -->
<script type="text/javascript">

function checkAll(obj){
	if(obj.checked){
		$("input[name='bookId_check']").attr("checked",true);  
	}else{
		$("input[name='bookId_check']").attr("checked",false);  
	}
}

	function query(){
		window.location.href = basePath + "/control/publisher_list?keyMenu=${param.keyMenu}&menu=${param.menu}&" + $("#userForm").serialize();
	}
	
	function confirmDel(id) {
		art.dialog({
			title:"消息",
		    content: '确定删除吗？',
		    ok: function() {
				$.post(basePath + "/control/publisher_canBeDelete?keyMenu=${param.keyMenu}&menu=${param.menu}", {"id" : id},
						function(data){
							if(data.stateBook == 1){
								artDialog({
									title:"消息",
									content:"所选出版社中含有相关图书，请先将其关系解除！",
									icon: 'warning',
									ok: function(){
									   return true;
									}
								});
							}
							if(data.stateSample == 1){
								artDialog({
									title:"消息",
									content:"所选出版社中含有相关管理用户，请先将其关系解除！",
									icon: 'warning',
									ok: function(){
									   return true;
									}
								});
							}else{
								window.location.href = basePath + "/control/publisher_delete?keyMenu=${param.keyMenu}&menu=${param.menu}&id=" + id;
							}
						});
		      //  return true;
		    },
		    cancelVal: '关闭',
		    cancel: true//为true等价于function(){}
		});
	}
	
	function add(){
		window.location.href = basePath + "/control/publisher_preAdd?keyMenu=${param.keyMenu}&menu=${param.menu}";
	}
	
	function delAll(){
		var ids = "";
		var idss = "";
/* 		$(".list-con td:gt(0) :checkbox[checked]").each(function(){
			idss += $(this).val()+",";
		}); */
		
		$('input[name="bookId_check"]').each(function(index){
			if($(this).prop('checked')){
				idss += $(this).val()+",";
			}
		});
		
		ids = idss.substr(0,idss.length-1);
		if(ids == ""){
	/* 		artDialog({
				title:"消息",
				content:"请选择需要删除的条目！",
				icon: 'warning',
				ok: function(){
				   return true;
				}
			}); */
			$.dialog.notice({icon : 'warning',content : '请选择需要删除的条目！',title : '3秒后自动关闭',time : 3});
		} else {
			art.dialog({
				title:"消息",
			    content: '您确认删除选中的记录吗？',
			    ok: function() {
					$.post(basePath + "/control/publisher_canBeDelete", {"id" : ids}, function(data){
						if(data.stateBook == 1){
							artDialog({
								title:"消息",
								content:"所选出版社中含有相关图书，请先将其关系解除！",
								icon: 'warning',
								ok: function(){
								   return true;
								}
							});
						}
						
						else if(data.stateSample == 1){
							artDialog({
								title:"消息",
								content:"所选出版社中含有相关管理用户，请先将其关系解除！",
								icon: 'warning',
								ok: function(){
								   return true;
								}
							});
						} else {
							window.location.href = basePath + "/control/publisher_delete?keyMenu=${param.keyMenu}&menu=${param.menu}&id=" + ids;
						}
					});
			      //  return true;
			    },
			    cancelVal: '关闭',
			    cancel: true//为true等价于function(){}
			});
		}
	}
	
	$(function(){
		$('[data-rel=tooltip]').tooltip();
		$(".text-overflow").each(function(){
           	var maxwidth=12;
           	if($(this).text().length>maxwidth){
           	$(this).text($(this).text().substring(0,maxwidth));
           	$(this).html($(this).html()+'…');
           	}
		});
	});
</script>