<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://shiro.apache.org/tags"%>
<link rel="stylesheet" type="text/css" href="assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
<style>
	.v-select {
		/* max-width: 300px; */
		display: inline-block;
		vertical-align: middle;
		/* padding: 0; */
		width: 100%;
	}
	.v-select .dropdown-toggle {
		height: 34px;
	}
	span.filter-title {
		text-align: right;
		/* padding-right: 20px; */
	}

</style>
<h2 class="text-center">
	<strong>短信记录</strong>
</h2>
<br />
<div class="row">
     <div class="col-md-4 col-md-offset-2">
         <div class="dashboard-stat blue">
             <div class="visual">
                 <i class="fa fa-comments"></i>
             </div>
             <div class="details">
                 <div class="number">
                <span data-counter="counterup" id="isUsed"></span>条
                 </div>
                 <div class="desc"> 已经使用的短信数量 </div>
             </div>
         </div>
     </div>
     <div class="col-md-4">
         <div class="dashboard-stat green">
             <div class="visual">
                 <i class="fa fa-bar-chart-o"></i>
             </div>
             <div class="details">
                 <div class="number">
                     <span data-counter="counterup" id="remnant"></span>条 </div>
                 <div class="desc"> 剩余短信数量 </div>
             </div>
         </div>
     </div>
 </div>
     
<!-- vue对象开始 -->
<div id="control">                    
<div class="row">
	<div class="col-md-8 col-md-offset-2">
		<form class="form-horizontal" id="smsForm">
		  <div class="form-group">
		    <label for="inputEmail3" class="col-sm-2 control-label">开始时间：</label>
		    <div class="col-sm-4">
		   		<input type="text"
						class="form-control form_datetime" id="beginDate" name="beginDate"
						readonly="readonly">
		    </div>
		    <label for="inputPassword3" class="col-sm-2 control-label">结束时间：</label>
		    <div class="col-sm-4">
		    	<input type="text"
						class="form-control form_datetime" id="endDate" name="endDate"
						readonly="readonly">
		    </div>
		  </div>
		  <!-- <div class="form-group">
		    <label for="inputPassword3" class="col-sm-2 control-label">店铺列表：</label>
		    <div class="col-sm-10">
		    	<label class="checkbox-inline" v-for="shop in shops">
				  <input type="checkbox" name="shopId"  value="{{shop.id}}" > {{shop.name}}
				</label>
		    </div>
			</div> -->
			<div class="row">
					
					<span class="col-md-2 filter-title">品牌列表：</span>
					<div class="col-md-4">
						<v-select :on-change="setCurrentBrand" label="brandName" :options="brandList" >
								<span slot="no-options">未配置品牌</span>
						</v-select>
					</div>
					<span class="col-md-2 filter-title">店铺列表：</span>
					<div class="col-md-4">
						<v-select :on-change="setCurrentShop" label="name" :options="shopList" :clearSearchOnSelect="true">
								<span slot="no-options">未配置门店</span>
						</v-select>
					</div>
					
		</div>
		<div class="row">
			<br>
			<br>
			<div class="col-md-6  col-md-offset-3" >
				<button type="button" class="btn btn-primary btn-sm btn-block" @click="querySms">查询</button>
			</div>
		</div>
		</form>
		
	</div>
</div>
<br />
<br />


<!-- datatable开始 -->
<div class="panel panel-default">
	<div class="panel-heading">短信记录详情</div>
	<div class="panel-body">
		<div class="table-body">
			<table class="table table-striped table-hover table-bordered"
				id="selectList"></table>
		</div>
	</div>
</div>
<!-- datatable结束 -->
</div>
<!-- vue对象结束 -->


<!-- 日期框 -->
<script src="assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	
<script src="assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<!-- <script src="assets/global/plugins/vue-select/vue-select.js"></script> -->
<script src="assets/global/plugins/vue-select/vue-select.js"></script>

<script>
	$(function() {
		Vue.component('v-select', VueSelect.VueSelect);
		//查询品牌已使用短信和剩余短信
		$.ajax({
			url:"smsacount/selectSmsAcount",
			success:function(result){
				// console.log(result);
				$("#remnant").html(result.data.remainderNum);
				$("#isUsed").html(result.data.usedNum);
			}
		})
		
		// var C = new Controller(null,tb);
		var vueObj = new Vue({
			el:"#control",
			// mixins:[C.formVueMix],
			data:{
				// shops:{},
				brandCurr: 0,	// 当前品牌
				shopCurr: 0, 	// 当前门店
				brandList: [],	// 品牌列表
				shopList: [],		// 店铺列表
				allShopList: [],// 所有的店铺列表
				table: {},			// 报表
			},
			created : function(){
				//初始化多选框按钮 和 时间插件
				//时间默认值
				$('.form_datetime').val(new Date().format("yyyy-MM-dd"));
				this.initTime();
				this.initShopName();
				this.initTable();
				//默认选中所有的店铺
				// this.checked();
			},
			methods:{
				initTable: function() {
					var that = this;
					var cid = "#control";
					var $table = $(".table-body>table");
					that.table = $table.DataTable({
						//"order": [[ 3, 'desc' ]],
                        serverSide: true,
						ajax : {
							url : "smsloginfo/listByShopsAndDate",
							type : "POST",
							data : function(d) {
								d.begin = $("#beginDate").val();
								d.end = $("#endDate").val();

								d.brandId = that.brandCurr;
								d.shopId = that.shopCurr;

								// return d;
							},
						},
						columns : [ {
							title : "品牌名称",
							data : "brandName",
						}, {
							title : "店铺名称",
							data : "shopName",
						},{
							title : "手机号",
							data : "phone",
						}, {
							title : "内容",
							data : "content",
						}, {
							title : "发送类型",
							data : "smsLogTyPeName",
						},
						{
							title : "创建时间",
							data : "createTime",
							createdCell : function(td, tdData) {
								$(td).html(new Date(tdData).format("yyyy-MM-dd hh:mm:ss"));
							}
						},
						{
							title : "是否成功",
							data : "isSuccess",
							createdCell : function(td,tdData){
								$(td).html(tdData==1?'是':'否')			
							}
						} ]

					})
				},
				initTime : function(){
					$('.form_datetime').datetimepicker({
						endDate : new Date(),
						minView : "month",
						maxView : "month",
						autoclose : true,//选择后自动关闭时间选择器
						todayBtn : true,//在底部显示 当天日期
						todayHighlight : true,//高亮当前日期
						format : "yyyy-mm-dd",
						startView : "month",
						language : "zh-CN"
					});
				},
				initShopName : function(){
					var that = this;
					// $.ajax({
					// 	url:"smsloginfo/shopName",
					// 	success:function(data){
					// 			that.shops = data;
					// 	}
					// });
					// 获取所有品牌列表
					$.ajax({
						url:"smsloginfo/brandList",
						success:function(data){
								// console.log(data);
								data = data || []
								data.unshift({id:0, brandName: '全部'})
								that.brandList = data;
						}
					});
					// 获取所有店铺列表
					$.ajax({
						url:"smsloginfo/shopList",
						success:function(data){
								// console.log(data);
								data = data || []
								data.unshift({id:0, name: '全部'})
								that.allShopList = data;
								that.shopList = data;
						}
					});
				},
				querySms : function(){
					var that = this;
					// console.log(that.brandCurr);
					// console.log(that.shopCurr);
					var begin = $("#beginDate").val();
					var end = $("#endDate").val();
					//判断时间是否合法
					if(begin>end){
						toastr.error("开始时间不能大于结束时间");
						return ;
					}
					//检验是否选择了店铺
					// var checkboxes =$("input[type='checkbox']");
					// if(!checkboxes.is(":checked")){
					// 	toastr.error("请至少选择一个店铺");
					// 	return ;
					// }
					this.table.ajax.reload();
				},
				// 选择品牌
				setCurrentBrand: function(curr) {
					var that = this;
					this.brandCurr = curr !== null ? curr.id : 0;
					this.shopCurr = 0;	// 重置为0
					// shopList联动
					// console.log(curr)
					if (this.brandCurr !== 0) {
						$.ajax({
							url:"smsloginfo/shopName",
							data: {brandId: curr.id},
							success:function(response){
								that.shopList = response;
							}
						});
					} else {
						that.shopList = that.allShopList;
					}
				},
				// 选择门店
				setCurrentShop: function(curr) {
					// console.log(curr);
					this.shopCurr = curr !== null ? curr.id : 0;
				}
			}
		});
	})
</script>
