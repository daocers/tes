<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	pageContext.setAttribute("basePath", basePath);
%>
<link rel="stylesheet" href="${basePath }js/assets/css/ishuke.css" />
<link rel="stylesheet" href="${basePath }js/assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="${basePath }js/assets/css/font-awesome.min.css" />
<link rel="stylesheet" href="${basePath }js/assets/css/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" href="${basePath }js/assets/css/chosen.css" />
<link rel="stylesheet" href="${basePath }js/assets/css/jquery.gritter.css" />
<link rel="stylesheet" href="${basePath }js/assets/css/ace.min.css" />
<link rel="stylesheet" href="${basePath }js/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" href="${basePath }js/assets/css/ace-skins.min.css" />

<link rel="stylesheet" href="${basePath }js/assets/js/autovalidate/style.css" />
<script type="text/javascript">window.basePath = "${basePath}";</script>
<script type="text/javascript">window.fastdfsAddr = "${sessionScope.fastdfsAddr}";</script>
<script  type="text/javascript" src="${basePath }js/assets/js/ace-extra.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery-2.0.3.min.js"></script>
<script  type="text/javascript" src="${basePath }js/jquery-1.8.2.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/bootstrap.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/typeahead-bs2.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.ui.touch-punch.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/bootbox.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.easy-pie-chart.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.gritter.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/ace-elements.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/ace.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.knob.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.autosize.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.inputlimiter.1.3.1.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.maskedinput.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.slimscroll.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.sparkline.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.dataTables.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.dataTables.bootstrap.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/jquery.ui.touch-punch.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/chosen.jquery.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/fuelux/fuelux.spinner.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/flot/jquery.flot.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/flot/jquery.flot.pie.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/flot/jquery.flot.resize.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/bootstrap-tag.min.js"></script>
<script  type="text/javascript" src="${basePath }js/assets/js/autovalidate/validate.js"></script>
<script type="text/javascript" src="${basePath }js/artDialog4.1.7/jquery.artDialog.js?skin=twitter"></script>