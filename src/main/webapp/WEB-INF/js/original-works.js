function reUpload(externalUrl, id, on_result) {
    $.ajax({
        url : externalUrl + "original-works/re-upload.json",
        type : 'POST',
        data : JSON.stringify({
            id : id
        }),
        dataType : "json",
        contentType : 'application/json;charset=utf-8',
        cache : false,
        success : function(data) {
            return on_result(externalUrl, data);
        }
    });
    return false;
}

function reUpload_on_result(externalUrl, data) {
    if (data.code === 0) {
        location.href = externalUrl + "original-works/index.html";
        return false;
    }
    alert(data.message);
    return false;
}

function openView(url) {
    setViewCredentialFileImage(url);

    $('#view-credential-file-modal').modal('show');

    return false;
}

var intervalID;
$(document).ready(
        function() {
            var externalUrl = $('#external-url').val();

            var locale = $.fn.bootstrapTable.locales['zh-CN'];
            locale.formatShowingRows = function(pageFrom, pageTo, totalRows) {
                return "";
            };
            locale.formatNoMatches = function() {
                return "";
            };
            $.extend($.fn.bootstrapTable.defaults, locale);

            $('#original-works-table').bootstrapTable(
                    {
                        url : externalUrl + 'original-works/list.json',
                        method : 'post',
                        contentType : 'application/json;charset=utf-8',
                        dataType : 'json',
                        pagination : true,
                        sidePagination : 'server',
                        pageNumber : 1,
                        pageSize : 10,
                        pageList : [ 10, 5 ],
                        queryParamsType : 'limit',
                        queryParams : function queryParams(params) {
                            return JSON.stringify({
                                // 每页多少条数据
                                page_size : params.limit,
                                // 请求第几页
                                current_page_offset : params.offset
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
                        singleSelect : true,
                        locale : 'zh-CN',
                        clickToSelect : true,
                        formatShowingRows : function(pageFrom, pageTo, totalRows) {
                            return 'x显示第 ' + pageFrom + ' 到第 ' + pageTo + ' 条记录，总共 ' + totalRows + ' 条记录';
                        },
                        columns : [
                                {
                                    field : 'name',
                                    title : '作品名称',
                                    halign : 'center',
                                    align : 'center'
                                },
                                {
                                    field : 'category',
                                    title : '作品种类',
                                    halign : 'center',
                                    align : 'center'
                                },
                                {
                                    field : 'creation_time',
                                    title : '创作时间',
                                    halign : 'center',
                                    align : 'center'
                                },
                                {
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
                                            html = '上传失败&nbsp;<a class="color-link" style="font-size:12px;" href="javascript:void(0);" onclick="reUpload(\'' + externalUrl
                                                    + '\', \'' + row.id + '\', reUpload_on_result);return false;">重试</button>';
                                        }
                                        return html;
                                    }
                                },
                                {
                                    field : 'uploading_time',
                                    title : '上传时间',
                                    halign : 'center',
                                    align : 'center'
                                },
                                {
                                    field : 'original_file',
                                    title : '作品文件',
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(value, row, index, field) {
                                        var html = "";
                                        for ( var index in value) {
                                            html = (html + '<a class="color-link" style="font-size:12px;" target="_blank" href="' + externalUrl + value[index].download_uri + '">'
                                                    + value[index].name + '</a><br/>');
                                        }
                                        return html;
                                    }
                                },
                                {
                                    field : 'credential_file',
                                    title : '保管函',
                                    halign : 'center',
                                    align : 'center',
                                    formatter : function(value, row, index, field) {
                                        var html = "";
                                        for ( var index in value) {
                                            html = (html + '<a class="color-link" style="font-size:12px;" href="javascript:void(0);" onclick="openView(\'' + value[index].url
                                                    + '\');return false;">' + value[index].name + '</a><br/>');
                                        }
                                        return html;
                                    }
                                } ]
                    }).on('load-success.bs.table', function(e, data) {
                var rows = data.rows;
                if (rows.length === 0) {
                    $('.img-empty-list').removeClass('hidden');
                } else {
                    $('.img-empty-list').addClass('hidden');
                }
                $.ajax({
                    url : externalUrl + "original-works/fetch-spaces.json",
                    type : 'POST',
                    data : JSON.stringify({}),
                    dataType : "json",
                    contentType : 'application/json;charset=utf-8',
                    cache : false,
                    success : function(data) {
                        $('#data-total').text(data.data_total);
                        $('#used-space').text(data.used_space);
                        $('#free-space').text(data.free_space);
                        $('#total_space').text(data.total_space);
                        return false;
                    }
                });

                // 刷新状态
                /*
                 * $(function(){
                 * 
                 * });
                 */

                try {
                    clearInterval(intervalID);
                } catch (ex) {
                }
                intervalID = setInterval(function() {
                    $.ajax({
                        url : externalUrl + 'original-works/getStatus',
                        type : 'post',
                        dateType : 'json',
                        success : function(data) {
                            // console.log(JSON.stringify(data));
                            if (data.code === 0) {// 用户登陆状态验证
                                var row;
                                var fileStates = data.file_states; // 得到data中文件id和状态的json数据
                                for ( var rowIndex in rows) {// 通过页面每条数据的下标循环
                                    row = rows[rowIndex]; // 得到每条数据
                                    // row.protection_status_value
                                    // alert(row.id+"
                                    // "+fileState.status);
                                    var fileState = fileStates[row.id];
                                    if (fileState !== undefined && fileState !== null) {
                                        if (fileState.status !== row['protection_status_value']) {
                                            $("#original-works-table").bootstrapTable('updateCell', {
                                                index : rowIndex,
                                                field : 'protection_status_value',
                                                value : fileState.status
                                            });

                                            $("#original-works-table").bootstrapTable('updateCell', {
                                                index : rowIndex,
                                                field : 'credential_file',
                                                value : fileState.credential_file
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    });
                }, 3000);
                return false;

            });

            // css
            $(".col-lg-12 text-center .fixed-table-container").css("border", "0px");
            $(".col-lg-12 text-center .bootstrap-table .fixed-table-pagination").css("margin-top", "0px");
            $(".col-lg-12 text-center .bootstrap-table .fixed-table-container .fixed-table-body #original-works-table tbody tr td").css("border-top", "0px");
            $(".col-lg-12 text-center .bootstrap-table .fixed-table-container .fixed-table-body #original-works-table").css("border", "0px");

            $('.add-original-works').click(function() {
                var nameField = $('.add-original-works-dialog .add-original-works-name');
                var categoryField = $('.add-original-works-dialog .add-original-works-category');
                var creationTimeField = $('.add-original-works-dialog .add-original-works-creation-time');
                var originalFileField = $('.add-original-works-dialog .add-original-works-original-file');
                nameField.val('');
                categoryField.val('-1');
                creationTimeField.val('');
                originalFileField.fileinput('reset');
                $('#add-original-works-modal').modal('show');
            });
        });

