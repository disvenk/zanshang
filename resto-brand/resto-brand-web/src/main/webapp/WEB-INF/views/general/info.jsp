<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 评论报表样式 -->
<link rel="stylesheet" href="assets/pages/css/comment-report.css" />
<div id="control">
    <h2 class="text-center"><strong>门店评论报表</strong></h2><br/>
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
                <button type="button" class="btn btn-primary" @click="downLoad()">下载榜单</button>
            </form>
        </div>
    </div>
    <br/>
    <br/>
    <div role="tabpanel" class="tab-pane">
        <div class="panel panel-primary" style="border-color:write;">
            <div class="panel panel-info">
                <div class="panel-heading text-center">
                    <strong style="font-size:22px;">门店评论报表</strong>

                </div>
                <div class="panel-body">
                    <table id="cardSummaryTable" class="table table-striped table-bordered table-hover dataTable no-footer" width="100%" role="grid"
                           aria-describedby="cardSummaryTable_info" style="width: 100%;">
                        <thead>
                        <tr role="row">
                            <th class="sorting_disabled" rowspan="1" colspan="1" >品牌</th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" >店铺</th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" >业态</th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" >评价单数</th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" >平均分</th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" >服务</th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" >环境</th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" >性价比</th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" >氛围</th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" >出品</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr role="row" class="odd">
                            <td>{{cardSummaryTable.brandName}}</td>
                            <td>{{cardSummaryTable.shopName}}</td>
                            <td>{{cardSummaryTable.formats}}</td>
                            <td>{{cardSummaryTable.appraiseCount}}</td>
                            <td>{{cardSummaryTable.totalScore}}</td>
                            <td>{{cardSummaryTable.serviceScore}}</td>
                            <td>{{cardSummaryTable.environmentalScore}}</td>
                            <td>{{cardSummaryTable.costPerformanceScore}}</td>
                            <td>{{cardSummaryTable.atmosphereScore}}</td>
                            <td>{{cardSummaryTable.produceScore}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div role="tabpanel" class="tab-pane">
        <div class="panel panel-primary" style="border-color:write;">
            <div class="panel panel-info">
                <div class="panel-heading text-center">
                    <strong style="font-size:22px">${brandName}评论详情</strong>
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
<script>
//创建vue对象
var brandId = "${brandId}";
var shopId = "${shopId}";
var brandName = "${brandName}";
var shopName = "${shopName}";
var isToDay = "${isToDay}";
var apiAddr = 'http://shop.zs.restoplus.cn'; //shop接口端口
//var apiAddr = 'http://localhost:8380'; //shop接口端口
//var apiAddr = 'http://shop.space.restoplus.cn';
$(function (){
    //时间插件
    $('.form_datetime').datetimepicker({
        endDate:new Date(),
        // minView:"month",
        minView:"hour",
        startView: 1,
        // maxView:"month",
        autoclose:true,//选择后自动关闭时间选择器
        todayBtn:true,//在底部显示 当天日期
        todayHighlight:true,//高亮当前日期
        format:"yyyy-mm-dd hh:ii:ss",
        // startView:"month",
        language:"zh-CN"
    });
    Vue.config.debug = true;
    var vueObj =  new Vue({
        el:"#control",
        data:{
            searchDate : {
                beginDate : "",
                endDate : "",
            },
            searchTxt: '',
            cardCustomerTable : {}, //各档口风报表
            cardSummaryTable: {test: '测试文字'},   //总报表(单条)
            isFinishSearch: false
        },
        created : function() {
            var date0 =  new Date().format("yyyy-MM-dd 00:00:00"); 
            var date = new Date().format("yyyy-MM-dd hh:mm:ss");
            this.searchDate.beginDate = date0;
            this.searchDate.endDate = date;
            this.createAppriseTable();
            this.initCardSummaryTable();
        },
        methods:{

            initCardSummaryTable: function() {
                var that = this;
                $.getJSON('general/selectRankings', {currentBrandId:brandId,currentShopId:shopId,startDate: that.searchDate.beginDate, endDate: that.searchDate.endDate}, function(response) {
                    if (response.data) {
                        that.cardSummaryTable = response.data;
                    } else {
                        that.cardSummaryTable = {};                        
                    }
                    that.isFinishSearch = true;
                })
            },
            createAppriseTable : function () {

                var that = this;
                that.cardCustomerTable=$("#cardCustomerTable").DataTable({
                    lengthMenu: [ [10,25, 50, 100], [10,25, 50, 100] ],
                    //order: [[ 1, "desc" ]],
                     serverSide: true,
                    // bFilter: false, //过滤功能
                    //bSort: false, //排序功能
                    ajax: {
                        // "url": 'brand/brand_data',
                        "url": apiAddr + '/shop/BocommentInfo/list_all',
                        "type": 'get',
                        "data":function ( d ) {
                             //添加额外的参数传给服务器
                            d.searchTxt = that.searchTxt;
                            d.brandId=brandId;
                            d.currentShopId=shopId;
                            d.isToDay = isToDay;
                            d.startDate = that.searchDate.beginDate;
                            d.endDate = that.searchDate.endDate;
                        },
                        //"dataSrc": function(response) {
                            // 统计数据
                            // that.cardSummaryTable = response.data.brand ? response.data.brand : {};
                            // 表格数据
                           // return response;
                            // return [
                            //     {test: '测试01', score: 1},
                            //     {test: '测试', score: 2},
                            //     {test: '测试', score: 3},
                            //     {test: '测试', score: 1},
                            // ]
                       // }
                    },
                    columns : [
                        {
                            title : "评论时间",
                            data : "createTime",
                            defaultContent: '--'
                        },
                        {
                            title : "手机号",
                            data : "phoneNumber",
                            defaultContent: '--'
                        }, {
                            width: "20px",
                            title : "店铺满意度",
                            data : "satisfaction"
                        },
                        {
                            title : "平均分",
                            data : "grade"
                        },
                        {
                            title : "服务",
                            data : "serverGrade",
                        },
                        {
                            title : "出品",
                            data : "productGrade"
                        },
                        {
                            title : "氛围",
                            data : "atmosphereGrade"
                        },
                        {
                            title: "环境",
                            data: "envGrade"
                        },
                        {
                            title: "性价比",
                            data: "performanceGrade"
                        },
                        {
                            title: "评论详情",
                            data: "content",
                            defaultContent: '--'
                        },
                        {
                            title: "赞的菜",
                            data: "zanArticle"
                        },
                        {
                            title: "踩的菜",
                            data: "caiArticle"
                        }
                    ]
                });
            },
            searchInfo: function() {
                this.initCardSummaryTable();
                this.cardCustomerTable.ajax.reload();
                if (this.isFinishSearch) {
                    toastr.clear();
                    toastr.success('查询完成');
                }
            },
            doSearch : function() {
                this.cardCustomerTable.ajax.reload();
            },
            // 下载榜单
            downLoad: function(type) {
                return window.location.href = apiAddr + '/shop/BocommentInfo/exprotExcel?isToDay='+isToDay+'&brandId='+brandId+'&currentShopId='+shopId+'&brandName='+brandName+'&shopName='+shopName+'&startDate='+ this.searchDate.beginDate+'&endDate='+this.searchDate.endDate;
            }
        }
    })
});

</script>
