/**
 * Created by daocers on 2016/7/19.
 */


$(function () {
    /**
     * 全选
     */
    $(".selectAll").on("click", function () {
        var checked = $(this).prop('checked');
        console.log(checked);
        $("table").find("input[type='checkbox']").prop("checked", checked);
    }); 
})


/**
 * 如果查看详情，禁用掉所有的 按钮和输入框
 */
$(function () {
    var type = $("#type").val();
    if (type == "detail") {
        $("input").attr("readonly", true);
        $("select").attr("readonly", true);
        $("button").attr("disabled", "disabled");
    }
});

