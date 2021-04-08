function removeOriginalWork(externalUrl, evidChainNodeId, originWorkId) {
    $.ajax({
        url : externalUrl + "evid-chain/remove-original-works.json",
        type : 'POST',
        data : JSON.stringify({
            original_works_id : originWorkId,
            chain_node_id : evidChainNodeId,
        }),
        dataType : 'json',
        contentType : 'application/json;charset=utf-8',
        cache : false,
        success : function(data) {
            if (data.code == 0) {
                $.alert({
                    title : '提示消息',
                    content : "取消关联成功",
                    theme : 'material',
                    buttons : {
                        ok : {
                            text : "确定",
                            btnClass : 'btn-primary',
                            action : function() {
                                window.location.reload();
                            }
                        }
                    }
                })
                return false;
            }
            $.alert({
                title : '取消关联失败',
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
            return false;
        }
    })
}
$(document).ready(function() {
    var externalUrl = $('#external-url').val();

    var content = document.getElementById("right-content");
    var lastHasFileNode;
    var ulHeight = $($(content).find("ul")[0]).height();
    var ulLength = $(content).find("ul").length;
    $(content).find("ul").each(function() {
        var fileNum = $(this).find(".browse").children().children().length;// 节点下的文件个数
        var isMasterPic = $(this).find("#is-master-pic").length;// 主图
        if (fileNum != 0) {
            var width = $($(this).find(".browse").children().children()[0]).width();
            $($(this).find(".browse").children()[0]).width((width + 27) * fileNum);
            lastHasFileNode = $(this);
            if (isMasterPic != 0) {
                $(this).find("li .to-add-original-works").remove();// 主图只可上传一张
            }
        }
    });
    var olHeight = $($(content).find("ol")[0]).height();
    var headHeight = $($(content).find("#evid-chain-head")[0]).height();
    var totalHeight = olHeight + headHeight + (ulHeight + 24) * ulLength + 38 + 40 + 8;
    $(content).height(totalHeight);
    $($(content).prev()).height(totalHeight);
    $($(content).parent()).height(totalHeight);
    if (lastHasFileNode != undefined) {
        var nodes = lastHasFileNode.prevAll("ul");
        nodes.each(function() {
            var nodeName = $(this).find("li b").text();// 节点名称
            if (nodeName != "" && nodeName != undefined) {
                var fileNum = $(this).find(".browse").children().children().length;// 节点下的文件个数
                if (fileNum == 0) {
                    $(this).find("li .to-add-original-works").remove();
                }
            }
        })
    }

    $(".to-add-original-works").click(function() {
        var node = $(this).parent().parent();// 当前节点ul
        var prevs = node.prevAll("ul");// 当前节点之前的所有ul节点
        var warnNodeName = "";
        prevs.each(function() {
            var nodeName = $(this).find("li b").text();// 节点名称
            if (nodeName != "" && nodeName != undefined) {
                var fileNum = $(this).find(".browse").children().children().length;// 节点下的文件个数
                var addBtn = $(this).find("li .to-add-original-works").length;
                if (fileNum == 0 && addBtn != 0) {
                    warnNodeName += nodeName + "，";
                }
            }
        })
        if (warnNodeName.length > 0) {
            warnNodeName = warnNodeName.substring(0, warnNodeName.length - 1);
            // 弹出警告窗
            $("#warnAddOriginalModal .warn-node-name").text(warnNodeName);
            $("#warnAddOriginalModal").modal("show");
        }
        $('#add-original-works-table').bootstrapTable("refresh");
        $("#evid-chain-node-id").val($(this).val());
        $("#addOriginWorksModal").modal("show");
        return false;
    });

    $(".confirm-warn-add-original").click(function() {
        $("#warnAddOriginalModal").modal("hide");
        return false;
    })

    $(".search-add-original-works-name").click(function() {
        $("#add-original-works-table").bootstrapTable("refresh");
        return false;
    })

    $(".add-original-works-submit").click(function() {
        var selections = $("#add-original-works-table").bootstrapTable("getSelections");
        if (selections.length == 0) {
            $.alert({
                title : '提示消息',
                content : "请选择关联的作品",
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
            return false;
        }
        var ids = "";
        for ( var i in selections) {
            ids += selections[i].id + ",";
        }
        $.ajax({
            url : externalUrl + "evid-chain/add-original-works.json",
            type : 'POST',
            data : JSON.stringify({
                chian_id : $("#evid-chain-id").val(),
                original_works_ids : ids,
                chain_node_id : $("#evid-chain-node-id").val(),
            }),
            dataType : 'json',
            contentType : 'application/json;charset=utf-8',
            cache : false,
            success : function(data) {
                if (data.code == 0) {
                    $.alert({
                        title : '提示消息',
                        content : "关联成功",
                        theme : 'material',
                        buttons : {
                            ok : {
                                text : "确定",
                                btnClass : 'btn-primary',
                                action : function() {
                                    window.location.reload();
                                }
                            }
                        }
                    })
                    return false;
                }
                $.alert({
                    title : '关联失败',
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
                return false;
            }
        })
    })

    $('#add-original-works-table').bootstrapTable({
        url : externalUrl + 'original-works/list.json',
        method : 'post',
        contentType : 'application/json;charset=utf-8',
        dataType : 'json',
        pagination : true,
        sidePagination : 'server',
        pageNumber : 1,
        pageSize : 5,
        pageList : [ 10, 50 ],
        queryParamsType : 'limit',
        queryParams : function queryParams(params) {
            return JSON.stringify({
                // 每页多少条数据
                page_size : params.limit,
                // 请求第几页
                current_page_offset : params.offset,
                name : $("#search-original-name").val(),
                add_to_node : true,
                name : $("#add-original-works-name").val(),
            });
        },
        rowStyle : function(row, index) {
            return {
                classes : (index % 2 === 0) ? 'table-row-odd' : 'table-row-even'
            }
        },
        dataField : 'rows',
        totalField : 'total',
        showRefresh : false,
        showColumns : false,
        idField : 'id',
        singleSelect : false,
        locale : 'zh-CN',
        clickToSelect : true,
        formatShowingRows : function(pageFrom, pageTo, totalRows) {
            return 'x显示第 ' + pageFrom + ' 到第 ' + pageTo + ' 条记录，总共 ' + totalRows + ' 条记录';
        },
        columns : [ {
            field : 'checkStatus',
            checkbox : true
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
            field : 'protection_status',
            title : '保护状态',
            halign : 'center',
            align : 'center',
            formatter : function(value, row, index, field) {
                var html = "";
                if (row.protection_status_value === 1) {
                    html = '上传中&nbsp;<img alt="" src="' + externalUrl + 'assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/img/loading-sm.gif">';
                } else if (row.protection_status_value === 2) {
                    html = "保护中";
                } else if (row.protection_status_value === 3) {
                    html = '上传失败';
                }
                return html;
            }
        }, {
            field : 'uploading_time',
            title : '上传时间',
            halign : 'center',
            align : 'center'
        } ]
    });
});