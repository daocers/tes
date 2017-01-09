/**
 * Created by daocers on 2016/7/5.
 */
//表格可编辑
var defaultOps = {
    //可以编辑的表格的校验符,默认为.cell-edit
    cellIdentify: ".cell-edit"
}
// jQuery.editable = function (options) {
//     $(this).find("th" + options.cellIdentify).each(function (idx, evt) {
//         console.log("index:", idx);
//         console.log("evt", evt);
//     });
// }

jQuery.fn.extend({
    editable: function (options) {
        if(options == null){
            options = defaultOps;
        }
        var $this = $(this);
        // console.log("this", $this);
        $this.find("th" + options.cellIdentify).each(function (idx, evt) {
            // console.log("index:", idx);
            // console.log("evt", evt);
            var index = $("th").index(evt);
            // console.log("index: ", index);
            $this.find("tr").each(function (i1, e1) {
                var $td = $(e1).find("td:eq(" + index + ")");
                if($td.attr("class") == undefined){
                    // console.log("$td-000", $td.attr("class"));
                    var text = $td.text();
                    $td.addClass("cell-edit");
                    $td.wrapInner("<div class='cell-label'></div>");
                    $td.append("<div class='cell-input'>" +
                        "<input type='text' class='form-control' value='" + text + "'>" +
                        "</div>");
                }
                // if($td.attr("class").indexOf(options.cellIdentify) == -1){
                //     console.log("$td-000", $td.attr("class"));
                //     console.log("td", $td.text());
                //     var text = $td.text();
                //     $td.addClass("cell-editable");
                //     $td.wrapInner("<div class='cell-label'></div>");
                //     $td.append("<div class='cell-input'>" +
                //         "<input type='text' class='form-control' value='" + text + "'>" +
                //         "</div>");
                // }

            });
            // var $td = $this.find("tr > td:eq(" + index + ")");
            // console.log("td", $td.text())
            // var text = $td.text();
            // $td.addClass("cell-editable");
            // $td.wrapInner("<div class='cell-label'></div>");
            // $td.append("<div class='cell-input'>" +
            //                 "<input type='text' class='form-control' value='" + text + "'>" +
            //             "</div>");
            // var info = $this.find("tr > td:eq(" + index + ")").text();
            // console.log("info", info)


            $(document).on("click", "td > .cell-label", function (e) {
                // console.log("按键", e.which)
                // console.log("事件类型：", e.type)
                showInput($(this));
            }).on("blur", "td > .cell-input", function (e) {
                processInput($(this))
            }).on("keydown", "td > .cell-input", function (e) {
                if(e.which == 9){
                    processInput($(this));
                    // console.log($(this)[0].tagName, $(this)[0].className);
                    // console.log($(this).html());
                    var $cellLabel = $(this).parentsUntil("td").nextUntil("td.cell-editable").find(".cell-label").trigger("click");
                    // console.log($cellLabel.length)
                    // showInput($cellLabel);
                }
            });

            function processInput($this) {
                $this.prev().css("z-index", 100);
                $this.css("z-index", 0);
                // console.log("this value", $this.val())
                $this.prev().text($this.find("input").val());
            }
            //显示表格中的输入框
            function showInput($this) {
                $this.next().css("z-index", 100).find("input").focus().select();
                $this.css("z-index", 0);
            }
        });
    }
});

$(function () {
    $("table.editable-table").editable();
});
