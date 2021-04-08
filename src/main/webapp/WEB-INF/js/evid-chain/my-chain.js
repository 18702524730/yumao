function deleteById(externalUrl, id) {
    $.alert({
        title : '提示消息',
        content : "确定删除？",
        theme : 'material',
        buttons : {
            ok : {
                text : "确定",
                btnClass : 'btn-primary',
                action : function() {
                    $.ajax({
                        url : externalUrl + "evid-chain/delete.json",
                        type : 'POST',
                        data : JSON.stringify({
                            id : id
                        }),
                        dataType : "json",
                        contentType : 'application/json;charset=utf-8',
                        cache : false,
                        success : function(data) {
                            if (data.code == 0) {
                                $("#my-chain-table").bootstrapTable("refresh");
                                return false;
                            }
                            $.alert({
                                title : '删除失败',
                                content : data.message,
                                theme : 'material',
                                buttons : {
                                    ok : {
                                        text : "确定",
                                        btnClass : 'btn-primary',
                                        action : function() {
                                        }
                                    }
                                }
                            })
                        }
                    })
                }
            },
            no : {
                text : "取消",
                btnClass : 'btn-primary',
                action : function() {
                }
            }
        }
    })
    return false;
}

$(document).ready(function() {
    var externalUrl = $('#external-url').val();

    $(".to-add-chain").click(function() {
        $("#addChainModal").modal("show");
        return false;
    });

    $(".search-chain").click(function() {
        $("#my-chain-table").bootstrapTable("refresh");
        return false;
    })

    $(".add-evid-chain").click(function() {
        $.ajax({
            url : externalUrl + "evid-chain/add.json",
            type : 'POST',
            data : JSON.stringify({
                name : $("#add-chain-name").val(),
            }),
            dataType : 'json',
            contentType : 'application/json;charset=utf-8',
            cache : false,
            success : function(data) {
                if (data.code == 0) {
                    $("#addChainModal").modal("hide");
                    $("#my-chain-table").bootstrapTable("refresh");
                    return false;
                }
                commonModal("添加失败", data.message);
                return false;
            }
        })
    })

    $("#my-chain-table").bootstrapTable({
        url : externalUrl + 'evid-chain/my-chain-list.json',
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
                name : $("#search-chain-name").val(),
            });
        },
        pageNumber : 1,
        pageSize : 10,
        pageList : [ 10, 20 ],// 分页步进值
        search : false,// 不显示搜索框
        columns : [ {
            field : 'chain_name',
            title : '证据链名称',
            halign : 'center',
            align : 'center'
        }, {
            field : 'category',
            title : '分类',
            halign : 'center',
            align : 'center'
        }, {
            field : 'create_time',
            title : '创建时间',
            halign : 'center',
            align : 'center',
        }, {
            field : 'percent',
            title : '完整度',
            halign : 'center',
            align : 'center'
        }, {
            title : '作品包',
            halign : 'center',
            align : 'center',
            formatter : function(value, row, index) {
                if (row.has_original_file) {
                    return "<a href='" + externalUrl + "evid-chain/download-original-works.html?id=" + row.id + "'>下载</a>";
                }
            }
        }, {
            title : '保管涵',
            halign : 'center',
            align : 'center',
            formatter : function(value, row, index) {
                if (row.has_credential_file) {
                    return "<a href='" + externalUrl + "evid-chain/download-credential-file.html?id=" + row.id + "'>下载</a>";
                }
            }
        }, {
            title : '操作',
            halign : 'center',
            align : 'center',
            formatter : function(value, row, index) {
                var percent = row.percent;
                var buttons = [];
                if (percent === '0%') {
                    buttons.push("<a target='_blank' href='" + externalUrl + "evid-chain/detail.html?id=" + row.id + "'>完善</a>");
                    buttons.push("<span style='margin:0 5px;'>|</span>");
                    buttons.push("<a href='javascript:void(0)' onclick='deleteById(\"" + externalUrl + "\",\"" + row.id + "\")' style='color:#FF5169'>删除</a>");
                } else if (percent === '100%') {
                    buttons.push("<a target='_blank' href='" + externalUrl + "evid-chain/detail.html?id=" + row.id + "'>查看</a>");
                } else {
                    buttons.push("<a target='_blank' href='" + externalUrl + "evid-chain/detail.html?id=" + row.id + "'>完善</a>");
                }
                return buttons.join(' ');
            }
        } ]
    });
});