<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <!-- 评论报表样式 -->
    <link rel="stylesheet" href="assets/pages/css/comment-report.css" />

    <div id="control">
        <h2 class="text-center"><strong>好餐厅历史总榜</strong></h2><br/>
        <div class="row" id="searchTools">
            <div class="col-md-12">
                <form class="form-inline">
                    <div class="form-group" style="margin-right: 50px;">
                        <label for="beginDate">开始时间：</label>
                        <input type="text" class="form-control form_datetime" id="beginDate" v-model="searchDate.beginDate"   readonly="readonly">
                    </div>
                    <div class="form-group" style="margin-right: 50px;">
                        <label for="endDate">结束时间：</label>
                        <input type="text" class="form-control form_datetime" id="endDate" v-model="searchDate.endDate"   readonly="readonly">
                    </div>
                    <button type="button" class="btn btn-primary" @click="searchInfo">查询</button>&nbsp;
                </form>
            </div>
        </div>
        <br/>
        <br/> 
        <div role="tabpanel" class="tab-pane">
            <div class="panel panel-primary" style="border-color:write;">
                <div class="panel panel-info">
                    <div class="panel-heading text-center">
                        <strong style="font-size:22px">好餐厅历史总榜</strong>
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

    <!-- <iframe src="http://localhost:8082/shop/general/list" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" width="100%" style="min-height:740px;"></iframe> -->
    <script src="assets/customer/date.js" type="text/javascript"></script>
    <script>
    //时间插件
    $('.form_datetime').datetimepicker({
        endDate:new Date(),
        //minView:"month",
        //maxView:"month",
        //startView:"month",
         minView:"hour",
         startView: 1,
        autoclose:true,//选择后自动关闭时间选择器
        todayBtn:true,//在底部显示 当天日期
        todayHighlight:true,//高亮当前日期
        format:"yyyy-mm-dd hh:ii:ss",
        language:"zh-CN"
    });
    
    //创建vue对象
    Vue.config.debug = true;
    var vueObj =  new Vue({
        el:"#control",
        data:{
            searchDate : {
                beginDate : "",
                endDate : "",
            },
            searchTxt: '',
            cardCustomerTable : {}, //报表
            isFinishSearch: false,
        },
        created : function() {
            var date0 =  new Date().format("yyyy-MM-dd 00:00:00");
            var date = new Date().format("yyyy-MM-dd hh:mm:ss");
            this.searchDate.beginDate = date0;
            this.searchDate.endDate = date;
          /*  var date = new Date().format("yyyy-MM-dd hh:ii:ss");
            this.searchDate.beginDate = date;
            this.searchDate.endDate = date;*/
            this.createAppriseTable();
        },
        methods:{
            createAppriseTable : function () {
                var that = this;
                that.cardCustomerTable=$("#cardCustomerTable").DataTable({
                    lengthMenu: [ [10,25, 50, 100], [10,25, 50, 100] ],
                    //order: [[ 1, "desc" ]],
                    // serverSide: true,
                    // bFilter: false, //过滤功能
                    //bSort: false, //排序功能
                    serverSide: true,
                    ajax: {
                        "url": 'general/selectAll',
                        "type": 'post',
                        "data":function ( d ) {
                            //d.choose = 1;
                            d.startDate = that.searchDate.beginDate;
                            d.endDate = that.searchDate.endDate;
                        },
                        // "dataSrc": function(response) {
                        //     // 表格数据
                        //     that.isFinishSearch = true;
                        //     return response;
                        // }
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
                        // {
                        //     title : "总排行",
                        //     data : "orderGrade",
                        //     defaultContent: '--'
                        // },
                        // {
                        //     title : "升降",
                        //     data : "foatingNumber",
                        //     defaultContent: '--',
                        //     render: function(data, type, row, meta) {
                        //         // console.log(row)
                        //         if(row['foatingNumber'] == 0) {
                        //             return '--';
                        //         } else {
                        //             return row['foatingNumber'];
                        //         }
                        //     }
                        // },
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
                        // {
                        //     title : "业态",
                        //     data : "formats",
                        // },
                        {
                            title : "平均分",
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
                                    $('.page-content-body').load('general/info?isToDay=0&brandId='+rowData['brandId']+'&shopDetailId='+rowData['shopDetailId']+'&brandName='+rowData['brandName']+'&shopName='+rowData['shopName']);
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
               // return window.location.href = 'general/exportExcel?isHistory=1';
                return window.location.href = 'general/exportExcelNew?startDate='+this.searchDate.beginDate+"&endDate="+this.searchDate.endDate;
            },
            searchInfo: function() {
                this.cardCustomerTable.ajax.reload();
                if (this.isFinishSearch) { 
                    toastr.clear();
                    toastr.success('查询完成');
                }
            }
        }
    })
    </script>

