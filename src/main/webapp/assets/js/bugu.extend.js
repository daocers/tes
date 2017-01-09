/**
 * Created by daocers on 2016/6/18.
 */
jQuery.foo = function () {
    alert("添加一个全新的全局函数");
}

jQuery.bar = function () {
    alert("另外一个全局函数");
}


/**
 * 使得表格可编辑
 * 使用须知：
 * 1 表格中对应的列需要添加class='editable'
 * 2 文档加载完成后执行tableEdit(tableClass)方法，其中，tableClass是对应的表格的class或者id，需要填写.或者#
 * 3 数据的更新入库操作需要用户自行处理
 * @param tableClass
 */
jQuery.tableEdit = function (tableClass) {
    // $(tableClass).find(".cell-edit").each(function(idx, evt){
    //     var index = $(tableClass).find("th").index($(evt));
    //     $(tableClass).find("tr > td:eq(" + index + ")").wrapInner('<div class="cell-label"></div>')
    //         .append('<input class="cell-input" type="text" value="'+value+'">');
    //         // .each(function (i, e) {
    //         // var $e = $(e);
    //         // var value = $e.html();
    //         // $e.wrapInner('<div class="cell-label"></div>')
    //         //     .append('<input class="cell-input" type="text" value="'+value+'">');
    //     })
    //
    // }
    var $table = $(tableClass);
    $(tableClass).parentsUntil("form").find("tr")
    $table.find("td.editable").each(function (index, e) {
        var $e = $(e);
        var value = $e.html();
        $e.wrapInner('<div class="cell-label"></div>')
            .append('<input class="cell-input" type="text" value="'+value+'">');

    });
}
jQuery.extend({
    foo1:function () {
        alert("extend定义全局函数1");
    },
    bar1:function () {
        alert("extends定义全局函数2");
    }
})