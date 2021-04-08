function initializeMobile() {
    selectTortIds = [];
    ids = "";
    $(".add-tort-info-one").css("display", "block");
    $(".add-tort-info-two").css("display", "none");
    $(".add-tort-info-success").css("display", "none");
    $("#goods-url").val("");
    $("#tort-info-id").val("");
    $("#search-original-works-name").val("");
    $(".search-original-works-btn").click();
}

// 撤回
function tortInfoRecall(externalUrl, id) {
    $.ajax({
        url : externalUrl + 'tort/to-withdraw.json',
        type : 'POST',
        data : JSON.stringify({
            id : id
        }),
        dataType : 'JSON',
        contentType : 'application/json;charset=utf-8',
        cache : false,
        success : function(data) {
            if (data.code === 0) {
                $("#tort-info-table").bootstrapTable('refresh');
                return false;
            }
            alert(data.message);
            return false;
        }
    });
}

// 删除
function tortInfoDelete(externalUrl, id) {
    var isdel = window.confirm('您确定删除吗？');
    if(isdel){
        $.ajax({
            url : externalUrl + 'tort/del-tort-info.json',
            type : 'POST',
            data : JSON.stringify({
                id : id
            }),
            dataType : 'JSON',
            contentType : 'application/json;charset=utf-8',
            cache : false,
            success : function(data) {
                if (data.code === 0) {
                    $("#tort-info-table").bootstrapTable('refresh');
                    alert("删除成功");
                    return false;
                }
                alert(data.message);
                return false;
            }
        });
    }

}

// 提交
function tortInfoSubmit(externalUrl, id) {
    initializeMobile();
    $.ajax({
        url : externalUrl + 'tort/to-submit.json',
        type : 'POST',
        data : JSON.stringify({
            id : id
        }),
        dataType : 'JSON',
        contentType : 'application/json;charset=utf-8',
        cache : false,
        success : function(data) {
            if (data.code === 0) {
                $("#tort-info-table-1").bootstrapTable('checkBy', {
                    field : 'id',
                    values : data.original_works_ids
                });
                selectTortIds = data.original_works_ids;
                $("#goods-url").val(data.goods_url);
                $("#tort-info-id").val(id);
                $("#add-tort-info-modal").modal('show');
                return false;
            }
            alert(data.message);
            return false;
        }
    });
}

$(function() {
    var externalUrl = $('#external-url').val();

    $(".search-tort-btn").click(function() {
        $('#tort-info-table').bootstrapTable("refresh");
        return false;
    })

    // 管理维权内容
    $('#tort-info-table')
            .bootstrapTable(
                    {
                        url : externalUrl + 'tort/list.json',
                        contentType : 'application/json;charset=utf-8',
                        method : "post",
                        showHeader : true,
                        showColumns : false,
                        showRefresh : false,
                        pagination : true,// 分页
                        search : true,
                        searchOnEnterKey : true,
                        sidePagination : 'server',// 客户端分页
                        queryParamsType : 'limit',
                        queryParams : function queryParams(params) {
                            return JSON.stringify({
                                // 每页多少条数据
                                page_size : params.limit,
                                // 请求第几页
                                current_page_offset : params.offset,
                                name : $("#search-tort-name").val(),
                            });
                        },
                        onLoadSuccess : function(data) {
                            $("#tort-total").text(data.total);
                        },
                        pageNumber : 1,
                        pageSize : 10,
                        pageList : [ 10, 5 ],// 分页步进值
                        search : false,// 不显示搜索框
                        columns : [
                                {
                                    field : 'goods_url',
                                    title : '商品链接',
                                    halign : 'center',
                                    align : 'center',
                                    // width:500,
                                    //cellStyle:{css:{'word-break':'break-all'}}
                                    cellStyle:{css:{'overflow':'hidden','white-space':'nowrap','text-overflow':'ellipsis','max-width':'250px'}},

                                    formatter:function (value, row, index) {
                                            var values = row.goods_url;
                                            var span = document.createElement('span');
                                            span.setAttribute('title', values);
                                            span.innerHTML = values;
                                            //return span.outerHTML;
                                             return "<a target='_blank' href='" + values + " '> "+ span.outerHTML + "</a>";
                                        }

                                },
                                {
                                    field : 'works_name',
                                    title : '相关作品名称',
                                    halign : 'center',
                                    align : 'center',
                                    width: 300
                                },
                                {
                                    title : '维权进度',
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(value, row, index) {
                                        var html = "";
                                        if (typeof (row.tort_log) != 'undefined') {
                                            html += '<table>';
                                            $.each(row.tort_log, function(i, log) {
                                                html += '<tr>';
                                                html += '<td>';
                                                html += log.operation_time;
                                                html += '&nbsp;&nbsp;&nbsp;&nbsp;</td>';
                                                html += '<td>';
                                                html += log.status;
                                                html += '&nbsp;&nbsp;&nbsp;&nbsp;</td>';
                                                html += '<td>';
                                                html += log.modify_reason;
                                                html += '&nbsp;&nbsp;&nbsp;&nbsp;</td>';
                                                html += '</tr>';
                                            });
                                            html += '</table>';
                                        }
                                        return "<a href='javascript:void(0)' class='status-popover' data-html='true' data-trigger='hover' data-container='body' data-toggle='popover' data-placement='top' data-content='"
                                                + html + "'>" + row.rights_protection_status_str + "</a>";
                                    }
                                }, {
                                    field : 'submit_time',
                                    title : '提交时间',
                                    halign : 'center',
                                    align : 'center',
                                    width: 200
                                }, {
                                    title : '作品文件',
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(value, row, index) {
                                        return "<a href='" + externalUrl + "tort/download-original-works.html?id=" + row.id + "'>下载</a>";
                                    }
                                }, {
                                    title: '操作',
                                    halign: 'center',
                                    align: 'center',
                                    width: 120,
                                    formatter: function (value, row, index) {
                                        var hasRecall = false;
                                        var hasSubmit = false;
                                        var hasDelete = false;
                                        switch (row.rights_protection_status) {
                                            case 1:
                                                hasRecall = true;
                                                break;
                                            case 2:
                                                hasRecall = true;
                                                break;
                                            case 7:
                                                hasSubmit = true;
                                                hasDelete = true;
                                                break;
                                        }
                                        var buttons = [];
                                        if (hasRecall) {
                                            buttons.push("<a href='javascript:void(0)' onclick='tortInfoRecall(\"" + externalUrl + "\",\"" + row.id + "\")'>撤回</a>");
                                        }
                                        if (hasSubmit) {
                                            buttons.push("<a href='javascript:void(0)' onclick='tortInfoSubmit(\"" + externalUrl + "\",\"" + row.id + "\")'>提交</a>");
                                        }
                                        if (hasDelete) {
                                            buttons.push("<a href='javascript:void(0)' onclick='tortInfoDelete(\"" + externalUrl + "\",\"" + row.id + "\")'>删除</a>");
                                        }
                                        return buttons.join(' ');
                                    }

                                } ]
                    }).on('load-success.bs.table', function(e, data) {
                $('[data-toggle="popover"]').popover();
                var rows = data.rows;
                if (rows.length === 0) {
                    $('.tort-info-empty-list').removeClass('hidden');
                } else {
                    $('.tort-info-empty-list').addClass('hidden');
                }
            });
    ;
});