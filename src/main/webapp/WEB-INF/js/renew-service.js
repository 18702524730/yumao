$(document).ready(
        function() {
            var externalUrl = $('#external-url').val();
            var returnMapping = $('#return-mapping').val();
            var step = $('#step').val();

            var locale = $.fn.bootstrapTable.locales['zh-CN'];
            locale.formatShowingRows = function(pageFrom, pageTo, totalRows) {
                return "";
            };
            $.extend($.fn.bootstrapTable.defaults, locale);
            if ('1' === step) {
                $('#payment').parent('.open-service-frame .tab-content').find('.open-service-frame .tab-pane').removeClass('active');
                $('#payment').addClass('active');
            } else if ('2' === step) {
                $('#payment-result').parent('.open-service-frame .tab-content').find('.open-service-frame .tab-pane').removeClass('active');
                $('#payment-result').addClass('active');
            }

            $('#service-table').bootstrapTable({

                url : externalUrl + 'service/price-package-list.json',
                method : 'post',
                contentType : 'application/json;charset=utf-8',
                dataType : 'json',
                pagination : true,
                sidePagination : 'server',
                pageNumber : 1,
                pageSize : 10,
                pageList : [ 10, 50 ],
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
                clickToSelect : true,
                locale : 'zh-CN',
                columns : [ {
                    checkbox : true
                }, {
                    field : 'service_name',
                    title : '服务名称',
                    halign : 'center',
                    align : 'center'
                }, {
                    field : 'name',
                    title : '套餐名称',
                    halign : 'center',
                    align : 'center'
                }, {
                    field : 'unit_price',
                    title : '单价',
                    halign : 'center',
                    align : 'center'
                }, {
                    field : 'valid_times',
                    title : '有效期',
                    halign : 'center',
                    align : 'center'
                }, {
                    field : 'price',
                    title : '价格',
                    halign : 'center',
                    align : 'center'
                } ]
            }).on('check.bs.table', function(e, row) {
                var allPriceField = $('.open-service-frame #all-price');
                var allPrice = parseFloat(allPriceField.text());

                var checkedPrice = parseFloat(row.price_value);
                allPriceField.text(getPriceDisplay(allPrice + checkedPrice));
            }).on('uncheck.bs.table', function(e, row) {
                var allPriceField = $('.open-service-frame #all-price');
                var allPrice = parseFloat(allPriceField.text());

                var checkedPrice = parseFloat(row.price_value);
                allPriceField.text(getPriceDisplay(allPrice - checkedPrice));
            }).on('check-all.bs.table', function(e, rows) {
                var allPriceField = $('.open-service-frame #all-price');

                var allPrice = 0;
                for ( var rowIndex in rows) {
                    allPrice += parseFloat(rows[rowIndex].price_value);
                }
                allPriceField.text(getPriceDisplay(allPrice));
            }).on('uncheck-all.bs.table', function(e, rows) {
                var allPriceField = $('.open-service-frame #all-price');

                allPriceField.text(getPriceDisplay(0));
            }).on('load-success.bs.table', function(e, data) {
                $('#service-table').bootstrapTable('checkAll');
            });

            $('.do-renew-service').click(function (){
                $("#open-service-payment").modal("show");
                return false;
            });
            
            $('.btn-step1').click(
                    function() {

                        // 点击按钮后锁定
                        var $btn = $(this).button('loading');

                        var rows = $('#service-table').bootstrapTable('getSelections');
                        if (rows.length < 1) {
                            alert('请先选择服务套餐');
                            // 解开loading
                            $btn.button('reset');
                            return false;
                        }
                        var ids = "";
                        for ( var i in rows) {
                            ids = (ids + ';');
                            ids = (ids + rows[i].id);
                        }
                        ids = ids.substr(1);

                        var agreementField = $('.open-service-frame .payment-agreement');

                        var agreement = agreementField.prop('checked');
                        if (false === agreement) {
                            alert('请先同意协议');
                            // 解开loading
                            $btn.button('reset');
                            return;
                        }
                        window.location.href = externalUrl + "alipay/submit.html?return_mapping=" + encodeURIComponent(encodeURIComponent($('#return-mapping').val()))
                                + "&step=3&ids=" + encodeURIComponent(encodeURIComponent(ids));
                        return false;
                    });

            $('.open-service-frame .btn-step2').click(function(e) {
                window.location.href = externalUrl + 'original-works/index.html';

                return false;
            });
        });