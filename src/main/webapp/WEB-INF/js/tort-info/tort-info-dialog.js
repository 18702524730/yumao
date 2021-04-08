var selectTortIds = [];

$(document).ready(function() {
    var externalUrl = $('#external-url').val();
    var ids = "";
    // 删除指定val值的数组
    // Array.prototype.remove = function(val) {
    // var index = this.indexOf(val);
    // if (index > -1) {
    // this.splice(index, 1);
    // }
    // };
    // 初始化
    function initializeMobile() {
        selectTortIds = [];
        ids = "";
        $(".add-tort-info-one").css("display", "block");
        $(".add-tort-info-two").css("display", "none");
        $(".add-tort-info-success").css("display", "none");
        $("#goods-url").val("");
        $("#tort-info-id").val("");
        $("#search-original-works-name").val("");
        $("#tort-info-table-1").bootstrapTable('refresh');
    }
    
    $(".go-on-btn").click(function(){
        initializeMobile();
    });

    $(".add-tort-info").click(function() {
        initializeMobile();
        $("#tort-info-table-1").bootstrapTable('refresh');
        $("#add-tort-info-modal").modal('show');
        return false;
    });
    
    $(".search-original-works-btn").click(function(){
        $("#tort-info-table-1").bootstrapTable('refresh');
        return false;
    });

    // 下一步
    $(".next-btn").click(function() {
        var selectIds = $("#tort-info-table-1").bootstrapTable("getSelections");
        if (selectIds.length <= 0) {
            alert("请至少选择一条原创作品");
            return false;
        }

        $(".add-tort-info-one").css("display", "none");
        $(".add-tort-info-two").css("display", "block");
        ids = "";
        for (var int = 0; int < selectTortIds.length; int++) {
            ids += selectTortIds[int] + ",";
        }
        $("#tort-info-table-2").bootstrapTable('refresh');
    });

    // 上一步
    $(".btn-last-step").click(function() {
        $(".add-tort-info-one").css("display", "block");
        $(".add-tort-info-two").css("display", "none");
    });

    // submit
    $(".btn-submit-tort").click(function() {
        $.ajax({
            url : externalUrl + 'tort/submit-tort-info.json',
            type : "post",
            data : JSON.stringify({
                tort_info_id : $("#tort-info-id").val(),
                goods_url : $("#goods-url").val(),
                original_works_ids : ids
            }),
            dataType : 'JSON',
            contentType : 'application/json;charset=utf-8',
            cache : false,
            success : function(data) {
                if (data.code === 0) {
                    $(".add-tort-info-success").css("display", "block");
                    $(".add-tort-info-two").css("display", "none");
                    $("#tort-info-table").bootstrapTable('refresh');
                    // $("#add-tort-info-modal").modal('toggle');
                    return false;
                }
                alert(data.message);
                return false;
            }
        });
    });

    // 选择侵权作品
    $('#tort-info-table-1').bootstrapTable({
        url : externalUrl + 'original-works/list.json',
        method : 'post',
        contentType : 'application/json;charset=utf-8',
        dataType : 'json',
        pagination : true,
        sidePagination : 'server',
        checkboxHeader : true,
        clickToSelect : true,
        pageNumber : 1,
        pageSize : 5,
        responseHandler : responseHandler,
        pageList : [ 5, 10 ],
        queryParamsType : 'limit',
        queryParams : function queryParams(params) {
            return JSON.stringify({
                // 每页多少条数据
                page_size : params.limit,
                // 请求第几页
                current_page_offset : params.offset,
                name : $("#search-original-works-name").val()
            });
        },
        rowStyle : function(row, index) {
            return {
                /*
                 * classes : (index % 2 === 0) ? 'table-row-odd' :
                 * 'table-row-even'
                 */// 隔行变色
                css : {
                    "margin" : "0px 20px 0px 20px"
                }
            }
        },
        dataField : 'rows',
        totalField : 'total',
        showRefresh : false,
        showColumns : false,
        idField : 'id',
        locale : 'zh-CN',
        columns : [ {
            field : 'checkStatus',
            checkbox : true
        }, {
            field : 'id',
            visible : false
        }, {
            field : 'name',
            title : '作品名称',
            halign : 'center',
            align : 'center'
        }, {
            field : 'category',
            title : '作品种类',
            halign : 'center',
            align : 'center'
        }, {
            field : 'creation_time',
            title : '创作时间',
            halign : 'center',
            align : 'center'
        }, {
            field : 'uploading_time',
            title : '上传时间',
            halign : 'center',
            align : 'center'
        }, ]
    }).on('check.bs.table', function(row, element) {// 选择一行
        var id = element.id;
        selectTortIds.push(id);
    }).on('uncheck.bs.table', function(row, element) {// 反选一行
        var id = element.id;
        // selectTortIds.remove(id);
        if (selectTortIds.indexOf(id) > -1) {
            selectTortIds.splice(selectTortIds.indexOf(id), 1);
        }
    }).on('check-all.bs.table', function(rows, element) { // 全选
        // console.log(rows,element)
        for (var i = 0; i < element.length; i++) {
            // selectTortIds.remove(element[i].id);
            if (selectTortIds.indexOf('id') > -1) {
                selectTortIds.splice(selectTortIds.indexOf('id'), 1);
            }
        }
        for (var i = 0; i < element.length; i++) {
            selectTortIds.push(element[i]['id']);
        }
    }).on('uncheck-all.bs.table', function(rows, element) {// 全反选
        for (var int = 0; int < element.length; int++) {
            // selectTortIds.remove(element[int].id);
            if (selectTortIds.indexOf('id') > -1) {
                selectTortIds.splice(selectTortIds.indexOf('id'), 1);
            }
        }
    });
    // 选中事件操作数组
    var union = function(array, ids) {
        $.each(ids, function(i, id) {
            if ($.inArray(id, array) == -1) {
                array[array.length] = id;
            }
        });
        return array;
    };
    // 取消选中事件操作数组
    var difference = function(array, ids) {
        $.each(ids, function(i, id) {
            var index = $.inArray(id, array);
            if (index != -1) {
                array.splice(index, 1);
            }
        });
        return array;
    };
    var _ = {
        "union" : union,
        "difference" : difference
    };
    // 绑定选中事件、取消事件、全部选中、全部取消
    $("#tort-info-table-1").on('check.bs.table check-all.bs.table uncheck.bs.table uncheck-all.bs.table', function(e, rows) {
        var ids = $.map(!$.isArray(rows) ? [ rows ] : rows, function(row) {
            return row.id;
        });
        func = $.inArray(e.type, [ 'check', 'check-all' ]) > -1 ? 'union' : 'difference';
        selectTortIds = _[func](selectTortIds, ids);
    });

    // 展示侵权作品
    $('#tort-info-table-2').bootstrapTable({
        url : externalUrl + 'original-works/list.json',
        method : 'post',
        contentType : 'application/json;charset=utf-8',
        dataType : 'json',
        pagination : true,
        sidePagination : 'server',
        checkboxHeader : true,
        clickToSelect : true,
        pageNumber : 1,
        pageSize : 5,
        responseHandler : responseHandler,
        pageList : [ 5, 10 ],
        queryParamsType : 'limit',
        queryParams : function queryParams(params) {
            return JSON.stringify({
                // 每页多少条数据
                page_size : params.limit,
                // 请求第几页
                current_page_offset : params.offset,
                // in ids
                ids : ids,
            });
        },
        rowStyle : function(row, index) {
            return {
                // / 隔行变色
                css : {
                    "margin" : "0px 20px 0px 20px"
                }
            }
        },
        dataField : 'rows',
        totalField : 'total',
        showRefresh : false,
        showColumns : false,
        idField : 'id',
        locale : 'zh-CN',
        columns : [ {
            field : 'name',
            title : '作品名称',
            halign : 'center',
            align : 'center'
        }, {
            field : 'category',
            title : '作品种类',
            halign : 'center',
            align : 'center'
        }, {
            field : 'creation_time',
            title : '创作时间',
            halign : 'center',
            align : 'center'
        }, {
            field : 'uploading_time',
            title : '上传时间',
            halign : 'center',
            align : 'center'
        }, ]
    });

});
// 表格分页之前处理多选框数据
function responseHandler(res) {
    $.each(res.rows, function(i, row) {
        row.checkStatus = $.inArray(row.id, selectTortIds) != -1; // 判断当前行的数据id是否存在与选中的数组，存在则将多选框状态变为true
    });
    return res;
}