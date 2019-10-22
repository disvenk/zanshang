<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="v-bind" uri="http://www.springframework.org/tags/form" %>

<style>
    .btn-def{
        width: 80px;
        height: 30px;
        background-color: #FFFFFF;
        border-radius: 3px;
        border: 1px dashed  #6A7989;
        cursor: pointer;
    }
    .btn-suc{
        width: 80px;
        height: 30px;
        background-color: #0c91e5;
        border-radius: 3px;
        border: 1px dashed  #6A7989;
        cursor: pointer;
    }

</style>

<div id="control">
	<div class="row form-div" v-if="showform">
		<div class="col-md-offset-3 col-md-6" >
			<div class="portlet light bordered">
	            <div class="portlet-title">
	                    <h3 style="text-align: center">开具发票</h3>
	            </div>
	            <div class="portlet-body">
	            	<form role="form" action="{{m.id?'contractticket/modify':'contractticket/create'}}" @submit.prevent="save">
                        <div class="container-fluid">
                            <h4>发票信息</h4>

                            <div class="row">
                                <div class="col-md-4">品牌</div>
                                <div class="col-md-6">
                                    <%--<input type="text" class="form-control" name="brandName" v-model="brandName" >--%>
                                        <select class="form-control" v-model="brand" v-if="!m.id">
                                            <option v-for="brand in brands">
                                                {{brand.brandName}}
                                            </option>
                                        </select>
                                        <input type="text" class="form-control" name="brandName" v-model="brand" v-if="m.id" >
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">公司名称</div>
                                <div class="col-md-6">
                                    <span>{{bCompanyName}}</span>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">合同编号</div>
                                <div class="col-md-6" v-if="!m.id">
                                    <template v-for="constractNum in constractNumArr">
                                        <input name="constractNum"  class="btn-def" type="button" id={{constractNum}}  v-model="constractNum" @click="choiceConstracNum(constractNum)">
                                    </template>
                                </div>
                                <input type="hidden" name="constractNum" v-model="constractNum" v-if="!m.id">

                                <div class="col-md-6" v-if="m.id">
                                    <input type="text" name="constractNum" v-model="constractNum">
                                </div>


                            </div>

                            <div class="row">
                                <div class="col-md-4">发票类型</div>
                                <div class="col-md-6">
                                    <%--<input type="radio" v-model="m.type" name="type" value="1">普通发票--%>
                                    <input type="radio" v-model="m.type" name="type" value="2" checked>增值税发票
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">发票金额</div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="money" v-model="m.money">
                                    <span v-if="isShow">可开发票的最大金额：{{showMoney}}</span>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">纳税人识别码</div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="taxpayerCode" v-model="m.taxpayerCode">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">注册地址</div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="registeredAddress" v-model="m.registeredAddress">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">注册电话</div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="registeredPhone" v-model="m.registeredPhone">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">开户银行</div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="bankName" v-model="m.bankName">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">银行账号</div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="bankAccount" v-model="m.bankAccount">
                                </div>
                            </div>

                            <h4>收货信息</h4>
                            <div class="row">
                                <div class="col-md-4">收货人</div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="name" v-model="m.name">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">联系电话</div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="phone" v-model="m.phone">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">联系地址</div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="address" v-model="m.address">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">物流单号</div>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="expersage" v-model="m.expersage">
                                </div>
                            </div>

                            <div class="row" v-if="m.id">
                                <div class="col-md-4">物流状态</div>
                                <div class="col-md-6">
                                    <%--<input type="text" class="form-control" name="ticketStatus" v-model="m.ticketStatus">--%>
                                    <input type="radio" v-model="m.ticketStatus" name="ticketStatus" value="0">未开始
                                    <input type="radio" v-model="m.ticketStatus" name="ticketStatus" value="1">运输中
                                    <input type="radio" v-model="m.ticketStatus" name="ticketStatus" value="2">已送达
                                </div>
                            </div>

                        </div>

						<input type="hidden" name="id" v-model="m.id" />
                        <div style="text-align: center">
                            <input class="btn green"  type="submit"  value="保存"/>
                            <a class="btn default" @click="cancel" >取消</a>
                        </div>

					</form>
	            </div>
	        </div>
		</div>
	</div>
	
	<div class="table-div">
		<div class="table-operator">
			<s:hasPermission name="contractticket/add">
			<button class="btn green pull-right" @click="create">开发票</button>
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
				url : "contractticket/list_all",
				dataSrc : ""
			},
			columns : [
				{                 
                    title : "合同编号",
                    data : "contract.constractNum",
                },
                {
                    title : "品牌",
                    data : "contract.brandName",
                },
                {
                    title : "发票类型",
                    data : "type",
                },
                {
                    title : "金额",
                    data : "money",
                },

                {
                    title : "公司名称",
                    data : "contract.bCompanyName",
                },

                {
                    title : "收件人",
                    data : "name",
                },
                {
                    title : "电话",
                    data : "phone",
                },
                {
                    title : "物流单号",
                    data : "expersage",
                    defaultContent: "-----"
                },

                {
                    title : "物流状态",
                    data : "ticketStatus",
                    createdCell:function (td,tdData) {
                        var temp = "--";
                        switch(tdData)
                        {
                        case 1:
                          temp = '运输中';
                          break;
                        case 2:
                          temp = '已送达';
                          break;
                        default:
                         break;
                        }

                        $(td).html(temp)
                    }
                },

				{
					title : "操作",
					data : "id",
					createdCell:function(td,tdData,rowData,row){
						var operator=[
							<s:hasPermission name="contractticket/delete">
							C.createDelBtn(tdData,"contractticket/delete"),
							</s:hasPermission>
							<s:hasPermission name="contractticket/modify">
							C.createCheckBtn(rowData),
							</s:hasPermission>
						];
						$(td).html(operator);
					}
				}],
		});
		
		var C = new Controller(null,tb);
		var vueObj = new Vue({
			el:"#control",
            data:{
                brands:[],//所有的品牌列表
                brand:'',//当前选择的品牌
                bCompanyName:'',//公司名称
                constractNums:'',//多个合同编号字符串
                constractNum:'',//当前最终选择的那个合同
                showMoney:'',//当前选中的合同可以开发票的金额
                isShow:false,
            },
			mixins:[C.formVueMix],
            computed:{
                constractNumArr:function(){//合同编号数组
                    return this.constractNums ? this.constractNums.split(',') : [];
                }

            },
            watch: {
                'brand': function (val, oldVal) {
                    var that = this;
                    for(var i=0;i<that.brands.length;i++){
                        if(val==that.brands[i].brandName){
                            that.bCompanyName = that.brands[i].bCompanyName;
                            that.constractNums = that.brands[i].contractNums;
                            break;
                        }
                    }
                    console.log(that.constractNums);
                },
                'm.money':function (val,oldVal) {
                   if(!this.m.id && val>this.showMoney){
                        toastr.error("可开发票金额小于可开发票。。")
                   }
                }

            },
            methods:{
                create:function () {
                    this.m={};
                    this.openForm();
                    this.initBrand();
                },
                initBrand:function () {
                    var that = this;
                    $.ajax({
                        url:"contract/list_brands",
                        type:'post',
                        success:function (data) {
                            if(data.length==0){
                                toastr.error("还没有创建合同,请先创建合同")
                            }else{
                                that.brands = data;
                            }
                        },
                        error:function () {
                            toastr.error("加载品牌列表出错")
                        }

                    })
                },
                choiceConstracNum:function (constractNum) {
                    this.constractNum = constractNum;
                    var id = "#"+constractNum;
                    console.log(id);
                    $(":input[name='constractNum']").removeClass();
                    $(id).addClass("btn-suc")
                    this.showTickMoney(constractNum);

                },
                showTickMoney:function (constractNum) {
                    var that = this;
                    $.ajax({
                        url:'contract/listByConstractNum',
                        data:{
                            constractNum:constractNum
                        },
                        success:function (result) {
                            console.log(result);
                            if(result.success){
                                that.showMoney = result.data.invoiceMoney;
                                that.isShow = true;
                            }

                        }

                    })
                },
                edit:function(model){
                    this.m= model;
                    console.log(this.m);
                    this.bCompanyName = this.m.contract.bCompanyName ;
                    this.constractNum =this.m.contract.constractNum  ;
                    this.brand =  this.m.contract.brandName ;
                    this.openForm();
                },

            }

		});
		C.vue=vueObj;
	}());
	
	

	
</script>
