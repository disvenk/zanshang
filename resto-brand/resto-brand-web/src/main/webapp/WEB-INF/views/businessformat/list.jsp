<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://shiro.apache.org/tags" %>
<div id="control">
	<div class="row form-div" v-if="showform">
		<div class="col-md-offset-3 col-md-6" >
			<div class="portlet light bordered">
	            <div class="portlet-title">
	                <div class="caption">
	                    <span class="caption-subject bold font-blue-hoki"> 表单</span>
	                </div>
	            </div>
	            <div class="portlet-body">
	            	<form role="form" action="{{m.id?'businessformat/modify':'businessformat/create'}}" @submit.prevent="save">
						<div class="form-body">
							<div class="form-group">
    <label>业态名称</label>
    <input type="text" class="form-control" name="name" v-model="m.name">
</div>
<div class="form-group">
    <label>业态描述</label>
    <input type="text" class="form-control" name="description" v-model="m.description">
</div>

						</div>
						<input type="hidden" name="id" v-model="m.id" />
						<input class="btn green"  type="submit"  value="保存"/>
						<a class="btn default" @click="cancel" >取消</a>
					</form>
	            </div>
	        </div>
		</div>
	</div>
	
	<div class="table-div">
		<div class="table-operator">
			<s:hasPermission name="businessformat/add">
			<button class="btn green pull-right" @click="create">新建</button>
			</s:hasPermission>
		</div>
		<div class="clearfix"></div>
		<div class="table-filter">&nbsp;</div>
		<div class="table-body">
			<table class="table table-striped table-hover table-bordered "></table>
		</div>
	</div>
</div>


<script>
	(function(){
		var cid="#control";
		var $table = $(".table-body>table");
		var tb = $table.DataTable({
			ajax : {
				url : "businessformat/list_all",
				dataSrc : ""
			},
			columns : [
                {
                    title : "业态ID",
                    data : null,
                },
                {
	title : "业态名称",
	data : "name",
},                 
{                 
	title : "业态描述",
	data : "description",
},                 

				{
					title : "操作",
					data : "id",
					createdCell:function(td,tdData,rowData,row){
						var operator=[
							<s:hasPermission name="businessformat/delete">
							C.createDelBtn(tdData,"businessformat/delete"),
							</s:hasPermission>
							<s:hasPermission name="businessformat/modify">
							C.createEditBtn(rowData),
							</s:hasPermission>
						];
						$(td).html(operator);
					}
				}],
            "fnRowCallback" : function(nRow, aData, iDisplayIndex){
                $("td:first", nRow).html(iDisplayIndex +1);//设置序号位于第一列，并顺次加一
                return nRow;
            }
		});
		
		var C = new Controller(null,tb);
		var vueObj = new Vue({
			el:"#control",
			mixins:[C.formVueMix]
		});
		C.vue=vueObj;
	}());
	
	

	
</script>
