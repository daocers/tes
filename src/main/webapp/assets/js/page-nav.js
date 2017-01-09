/**
 * Created by daocers on 2016/7/18.
 */
//页面跳转
function toPage(page) {
    var showCount = $(".show-count").val()
    window.location.href = "list.do?curPage=" + page + "&showCount=" + showCount;
    return false;
}

//输入页面后跳转到对应页面
$(".btn-to-page").on("click", function () {
    var $page = $("#to-page");
    var page = $page.val();
    var max = $page.prop("max");
    console.log("max:", max);
    console.log("page:", page);
    console.log(page - max);

    if(page - max > 0 || page < 1){
        $page.select().focus();
    }else{
        window.location.href = "list.do?curPage=" + page + "&showCount=" + $(".show-count").val();
    }
    return false;
});


//文档加载完成后主动绑定该方法
$(function () {
    //修改显示条数后跳转
    $(".show-count").on("change", function () {
        console.log("showcount changed!")
        window.location.href = "list.do?curPage=1&showCount=" + $(this).val();
        return false;
    });
})
