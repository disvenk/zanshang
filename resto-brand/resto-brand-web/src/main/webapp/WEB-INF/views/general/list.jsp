<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <!-- 评论报表样式 -->
    <link rel="stylesheet" href="assets/pages/css/comment-report.css" />

    <div id="control">
            <br/>
            <br/> 
            <div role="tabpanel" class="tab-pane">
                <div class="panel panel-primary" style="border-color:write;">
                    <div class="panel panel-info">
                        <div class="panel-heading text-center">
                            <strong style="font-size:22px">好餐厅今日榜单</strong>
                            <button type="button" class="btn btn-primary pull-right" @click="downLoad()">下载榜单</button>
                        </div>
                        <div class="panel-body">
                            <!-- 搜索框 -->
                            <!-- <div id="cardCustomerTable_filter" class="search-box">
                                <label>搜索:<input type="text" class="form-control input-sm input-small input-inline" placeholder="" v-model="searchTxt" @keyup.enter.native="doSearch"></label>
                            </div> -->
                            <table id="cardCustomerTable" class="table table-striped table-bordered table-hover" width="100%">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
    </div>

    <!-- <iframe src="http://localhost:8082/shop/general/list" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" width="100%" style="min-height:740px;"></iframe> -->
    <script>
    
    //创建vue对象
    Vue.config.debug = true;
    var vueObj =  new Vue({
        el:"#control",
        data:{
            searchTxt: '',
            cardCustomerTable : {}, //报表
        },
        created : function() {
            this.createAppriseTable();
        },
        methods:{
            createAppriseTable : function () {
                var that = this;
                that.cardCustomerTable=$("#cardCustomerTable").DataTable({
                    lengthMenu: [ [10,25, 50, 100], [10,25, 50, 100] ],
                    order: [[ 1, "desc" ]],
                    // serverSide: true,
                    // bFilter: false, //过滤功能
                    bSort: false, //排序功能
                    ajax: {
                        // "url": 'brand/brand_data',
                        "url": 'general/selectAllOrderByGrade',
                        "type": 'post',
                        "data":function ( d ) {
                            //添加额外的参数传给服务器
                            d.searchTxt = that.searchTxt;
                            d.choose = 1;
                        },
                        "dataSrc": function(response) {
                            // 统计数据
                            // that.cardSummaryTable = response.data.brand ? response.data.brand : {};
                            // 表格数据
                            return response.data;
                            // return [
                            //     {test: '测试01', score: 1},
                            //     {test: '测试', score: 2},
                            //     {test: '测试', score: 3},
                            //     {test: '测试', score: 1},
                            //     {test: '测试', score: 1},
                            //     {test: '测试', score: 1},
                            //     {test: '测试', score: 1},
                            //     {test: '测试', score: 1},
                            //     {test: '测试', score: 1},
                            //     {test: '测试', score: 1},
                            //     {test: '测试', score: 1},
                            //     {test: '测试', score: 1},
                            //     {test: '测试', score: 1},
                            //     {test: '测试', score: 1}
                            // ]
                        }
                    },
                    "createdRow": function ( row, data, index ) {
                        // console.log(data)
                        if ( data['type'] == true && data['foatingNumber'] != 0) {
                            $('td', row).eq(1).addClass('up');
                        } else if ( data['type'] == false && data['foatingNumber'] != 0) {
                            $('td', row).eq(1).addClass('down');
                        }
                    },
                    columns : [
                        {
                            title : "总排行",
                            data : "orderGrade",
                            defaultContent: '--'
                        },
                        {
                            title : "升降",
                            data : "foatingNumber",
                            defaultContent: '--',
                            render: function(data, type, row, meta) {
                                // console.log(row)
                                if(row['foatingNumber'] == 0) {
                                    return '--';
                                } else {
                                    return row['foatingNumber'];
                                }
                            }
                        },
                        {
                            title : "品牌",
                            data : "brandName"
                        },
                        {
                            title : "店铺名称",
                            data : "shopName",
                        },
                        {
                            title : "评论数量",
                            data : "appraiseCount"
                        },
                        {
                            title : "业态",
                            data : "formats",
                        },
                        {
                            title : "总评分",
                            data : "totalScore"
                        },
                        {
                            title: "服务",
                            data: "serviceScore"
                        },
                        {
                            title: "出品",
                            data: "produceScore"
                        },
                        {
                            title: "氛围",
                            data: "atmosphereScore"
                        },
                        {
                            title: "环境",
                            data: "environmentalScore"
                        },
                        {
                            title: "性价比",
                            data: "costPerformanceScore"
                        },
                        {
                            title: "查看详情",
                            data: "brandId",
                            orderable : false,
                            createdCell: function (td, tdData, rowData) {
                                var button = $("<button type='button' class='btn blue'>查看 </button> ");
                                button.click(function(){
                                    $('.page-content-body').load('general/info?isToDay=1&brandId='+rowData['brandId']+'&shopDetailId='+rowData['shopDetailId']+'&brandName='+rowData['brandName']+'&shopName='+rowData['shopName']);
                                });
                                $(td).html(button);
                            }
                        },
                    ]
                });
            },
            doSearch: function() {
                // alert(1)
                this.cardCustomerTable.ajax.reload();                
            },
            
            // 下载榜单
            downLoad: function() {
                return window.location.href = 'general/exportExcel?choose=1';
            }
        }
    })
    </script>

