<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://shiro.apache.org/tags" %>
<div id="control">
	<!-- 添加 Brand 信息  Modal  start -->
	<div class="modal fade" id="brandInfoModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<form role="form" class="form-horizontal" id="brandForm" action="{{brandInfo.id?'brand/modify':'brand/create'}}"  @submit.prevent="save">
						<h4 class="text-center"><strong>品牌基本信息</strong></h4>
						<div class="form-group">
							<label class="col-sm-3 control-label">品牌名称：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" required name="brandName" v-model="brandInfo.brandName">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">品牌标识：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" required name="brandSign" v-model="brandInfo.brandSign">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">是否启用:</label>
							<div class="col-sm-8">
								<label><input type="radio" style="cursor:pointer" class="form-control" name="brandInfo.state" required v-model="brandInfo.state" value="1" >启用</label>
								<label><input type="radio" style="cursor:pointer" class="form-control" name="brandInfo.state" required v-model="brandInfo.state" value="0" >不启用</label>
							</div>
						</div>
						<div class="text-center">
							<input type="hidden" name="id" v-model="brandInfo.id" />
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
							<input class="btn green" type="submit" value="保存"/>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 品牌订单 -->
	<div class="panel panel-info">
		<div class="panel-heading text-center">
			<strong style="margin-right:100px;font-size:22px">品牌总览</strong>
		</div>
		<div class="panel-body">
			<table id="brandOrderTable" class="table table-striped table-bordered table-hover" width="100%">
				<thead>
				<tr>
					<th>品牌总数</th>
					<th>门店总数</th>
					<th>已启用品牌数</th>
					<th>未启用品牌数</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td>{{brandDataDto.brandCount}}</td>
					<td>{{brandDataDto.shopCount}}</td>
					<td>{{brandDataDto.enableBrandCount}}</td>
					<td>{{brandDataDto.notEnableBrandCount}}</td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>

	<!-- 表格   start-->
	<div class="table-div">
		<div class="table-operator">
			<s:hasPermission name="brand/add">
				<button class="btn green pull-right" @click="create">新建</button>
			</s:hasPermission>
		</div>
		<div class="clearfix"></div>
		<div class="table-filter">&nbsp;</div>
		<div class="table-body">
			<table class="table table-striped table-hover table-bordered "></table>
		</div>
	</div>
	<!-- 表格   end-->
</div>


<script>
	$(document).ready( function () {
		//载入 表格数据
		tb = $('.table-body>table').DataTable({
			ordering: false,//禁止排序
			ajax : {
				url : "brand/list_all",
				dataSrc : ""
			},
			columns : [
				{
					title : "品牌ID",
					data :  null,
				},
				{
					title : "品牌名称",
					data : "brandName",
				},
				{
					title : "品牌标识",
					data : "brandSign",
				},
				{
					title : "是否开启",
					data : "state",
					createdCell:function (td,tdData) {
						var temp = "";
						if(tdData==1){
							temp = "是";
						}else if(tdData==0){
							temp = "否"
						}
						$(td).html(temp);
					}
				},
				{
					title : "操作",
					data : "id",
					createdCell:function(td,tdData,rowData){
						var operator=[
							<s:hasPermission name='brand/delete'>,
							C.createDelBtn(tdData,"brand/delete"),
							</s:hasPermission>,
							<s:hasPermission name='brand/edit'>,
							createEditBtn(tdData,rowData),
							/*createModuleEditBtn(tdData),*/
							</s:hasPermission>
							<%--<s:hasPermission name='brand/createToken'>,--%>
							<%--createTokenBtn(rowData),//自定义方法生成token--%>
							<%--</s:hasPermission>--%>
							/*createBlackPeopleBtn(tdData),
							 createWhitePeopleBtn(tdData)*/
						];
						$(td).html(operator);
					}
				}],
			"fnRowCallback" : function(nRow, aData, iDisplayIndex){
				$("td:first", nRow).html(iDisplayIndex +1);//设置序号位于第一列，并顺次加一
				return nRow;
			}
		});

		var C = new Controller("#control",tb);

		var vueObj = new Vue({
			el:"#control",
			data:{
				brandInfo:{state:1},
				brandDataDto:{}
			},
			created: function () {
				var that=this;
				$.post("brand/brand_data", function (result) {
					that.brandDataDto = result[0];
				});
			},
			methods:{
				create:function(){
					var brandInfo = {};
					brandInfo.brandName="";
					brandInfo.state=1;
					this.brandInfo = brandInfo;
					$("#brandInfoModal").modal();
				},
				showDetailInfo:function(brandInfo){
					this.brandInfo = brandInfo;
				},
				edit:function(brandId,brandInfo){
					
					// debugger
					brandInfo.state = 0;
					this.brandInfo = brandInfo;
					// console.log(this)
					this.$nextTick(function() {
						$("#brandInfoModal").modal();
					})
				},
				save:function(e){
					var brandSign = this.brandInfo.brandSign;
					var brandId = this.brandInfo.id;
					$.post("brand/validateInfo",{brandId:brandId,brandSign:brandSign},function(result){
						if(result.success){
							C.ajaxFormEx(e.target,function(){
								$("#brandInfoModal").modal("hide");//关闭模态框
								tb.ajax.reload();
							});
						}else{
							vueObj.showMsg = true;
						}
					});
				},
			}
		});

		//创建 修改按钮
		function createEditBtn(brandId,brandInfo){
			var button = $("<button class='btn btn-xs btn-primary'>编辑</button>");
			button.click(function(){
				vueObj.edit(brandId,brandInfo);
			});
			return button;
		}

	} );

</script>