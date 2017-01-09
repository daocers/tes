/**
 * Created by daocers on 2016/8/2.
 */
/**
 * 全选
 */
$(".selectAll").on("click", function () {
    var checked = $(this).prop('checked');
    console.log(checked);
    $("table").find("input[type='checkbox']").prop("checked", checked);
});

/**
 * 获取全部选中的行
 */
function getCheckedLine() {
    var ids = new Array();
    $("table").find("input[type='checkbox']").each(function (idx, e) {
        if($(e).prop("checked") && $(e).attr("goodsId")){
            ids.push($(e).attr("goodsId"));
        }
    });
    return ids;
};

/**
 * 删除选定的行 
 */
function del(id) {
    $.ajax({
        url:"delete.do",
        type: "post",
        data: {"id": id},
        success: function (data) {
            if(data == "0"){
                $("input[id=" + id + "]").parents("tr").remove();
            }else{
                alert("删除失败");
            }
        },
        error: function () {
            alert("请求删除失败");
        }
    });
    return false;
}
