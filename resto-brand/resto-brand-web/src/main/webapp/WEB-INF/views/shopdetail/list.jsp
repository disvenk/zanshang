<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://shiro.apache.org/tags" %>

<div id="control">
    <div class="row form-div" v-if="showform">
        <div class="col-md-offset-3 col-md-6">
            <div class="portlet light bordered">
                <div class="portlet-title text-center">
                    <div class="caption">
                        <strong>店铺详情</strong>
                    </div>
                </div>
                <div class="portlet-body">
                    <form role="form" class="form-horizontal" action="{{m.id?'shopdetail/modify':'shopdetail/create'}}" @submit.prevent="save" >
                        <div class="form-body" >
                            <div class="form-group">
                                <label class="col-sm-4 control-label">请选择品牌：</label>
                                <div class="col-sm-6">
                                    <select class="form-control" name="brandId" :value="m.brandId">
                                        <option v-for="brand in brands" :value="brand.id">
                                            {{brand.brandName}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">店铺名称：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" name="name" v-model="m.name" placeholder="必填"  required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">店铺业态：</label>
                                <div class="col-sm-6">
                                    <select class="form-control" name="businessFormatId"
                                            :value="m.businessFormatId">
                                        <option v-for="type in  allType" :value="type.id">
                                            {{type.name}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">店铺序号：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" name="shopDetailIndex" v-model="m.shopDetailIndex">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">是否开启店铺：</label>
                                <div class="col-sm-6">
                                    <select class="form-control" name="isOpen" :value="m.isOpen">
                                        <option v-for="isOpenName in  isOpenNames" :value="isOpenName.id">
                                            {{isOpenName.value}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">店铺电话：</label>
                                <div class="col-sm-6">
                                    <input type="tel" class="form-control" name="phone" v-model="m.phone">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">店铺地址：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" name="address" v-model="m.address"
                                           @blur="showjwd" placeholder="必填" required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">所在城市：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" name="city" v-model="m.city" placeholder="必填"
                                           required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">经度：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" readonly name="longitude" v-model="m.longitude"
                                           placeholder="请填写完店铺地址跟所在城市后自动获取经纬度">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">纬度：</label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control" readonly name="latitude" v-model="m.latitude"
                                           placeholder="请填写完店铺地址跟所在城市后自动获取经纬度">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">营业时间：</label>
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <input type="text" class="form-control timepicker timepicker-no-seconds" name="openTime" @focus="initTime" v-model="m.openTime" readonly="readonly">
                                        <span class="input-group-btn">
                                            <button class="btn default" type="button">
                                                <i class="fa fa-clock-o"></i>
                                            </button>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">关门时间：</label>
                                <div class="col-sm-6">
                                    <div class="input-group">
                                        <input type="text" class="form-control timepicker timepicker-no-seconds" name="closeTime" v-model="m.closeTime" @focus="initTime" readonly="readonly">
                                        <span class="input-group-btn">
                                            <button class="btn default" type="button">
                                                <i class="fa fa-clock-o"></i>
                                          </button>
	                                    </span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">店铺图片：</label>
                                <div class="col-sm-6">
                                    <input type="hidden" name="photo" v-model="m.photo">
                                    <cert-file-upload class="form-control" @success="uploadSuccess"
                                                      @error="uploadError"></cert-file-upload>
                                    <img v-if="m.photo" :src="m.photo" :alt="m.name" onerror="this.src='assets/pages/img/defaultImg.png'" width="80px" height="40px" class="img-rounded">
                                </div>
                            </div>

                            <div class="form-group" v-if="m.id != null">
                                <label class="col-sm-4 control-label">下载店铺二维码：</label>
                                <div class="col-sm-6">
                                    <input type="button" class="btn green " @click="downloadQRcode" value="下载" />
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="id" id="codeId" v-model="m.id"/>
                        <div class="text-center">
                            <input class="btn green" type="submit" value="保存"/>&nbsp;&nbsp;&nbsp;
                            <a class="btn default" @click="cancel">取消</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="table-div">
        <div class="table-operator">
            <s:hasPermission name="shopdetail/add">
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
    Vue.component('cert-file-upload', {
        mixins: [fileUploadMix],
        data: function () {
            return {
                types: [".jpg"],
                postUrl: "shopdetail/uploadFile"
            }
        }
    });
    Vue.component('cert-file-upload2',{
        mixins:[fileUploadMix],
        data:function(){
            return {
                types:[".p12"],
                postUrl:"wechatconfig/uploadFile"
            }
        }
    });
    $(document).ready(function () {
        var vueObj;
        var C
        var cid = "#control";
        var $table = $(".table-body>table");
        var tb = $table.DataTable({
            ajax: {
                url: "shopdetail/list_all",
                dataSrc: ""
            },
            columns: [
                {
                    title: "品牌",
                    data: "brandName",
                },
                {
                    title: "店铺名称",
                    data: "name",
                },
                /*{
                    title: "店铺模式",
                    data: "shopmodeName",
                },*/
                {
                    title: "是否开启",
                    data: "isOpen",
                    createdCell: function (td, tdData) {
                        $(td).html(tdData ? "是" : "否");
                    }
                },
                {
                    title: "店铺地址",
                    data: "address",
                },
                {
                    title: "所在城市",
                    data: "city",
                },
                {
                    title: "店铺电话",
                    data: "phone",
                },
                {
                    title: "店铺开门时间",
                    data: "openTime",
                    "render": function (data, td, row) {
                        var str = "暂无数据";
                        if (data != null) {
                            str = new Date(data).format("hh:mm");
                        }
                        return str;
                    },
                },
                {
                    title: "店铺关门时间",
                    data: "closeTime",
                    "render": function (data, td, row) {
                        var str = "暂无数据";
                        if (data != null) {
                            str = new Date(data).format("hh:mm");

                        }
                        return str;
                    },
                },
                {
                    title: "店铺序号",
                    data: "shopDetailIndex",
                },
                {
                    title: "操作",
                    data: "id",
                    createdCell: function (td, tdData, rowData, row) {
                        var operator = [
                            <s:hasPermission name="shopdetail/delete">
                            C.createDelBtn(tdData, "shopdetail/delete"),
                            </s:hasPermission>
                            <s:hasPermission name="shopdetail/edit">
                            C.createEditBtn(rowData),
                            </s:hasPermission>
                        ];
                        $(td).html(operator);
                    }
                }],
        });

        C = new Controller(null, tb);

        var vueObj = new Vue({
            el: "#control",
            mixins: [C.formVueMix],
            data: {
                //allModel: [],
                brands: [],
                isOpenNames: [{"id": "true", "value": "是"}, {"id": "false", "value": "否"}],
                wxServers:[],
                allMq:[],
                allType: []
            },
            created: function () {
                var that = this;
                $.post("wxServer/list_all", null, function (data) {
                    that.wxServers = data;
                });
                //that.initShopMode();
                that.initBrandMode();
                that.initMq();
                that.businessFormat();
            },
            watch: {
                'm.address': 'findJWnumber',
                'm.city': 'findJWnumber'
            },
            methods: {
                downloadQRcode: function () {
                    var id=$("#codeId").val();
                    $.ajax({
                        url: "shopdetail/openQRCode",
                        type: "post",
                        data: {"shopId": id},
                        success: function (data) {
                            debugger
                            window.open(data);
                        }
                    })
                },
                findJWnumber: function () {
                    var that = this;
                    var myGeo = new BMap.Geocoder();
                    myGeo.getPoint(this.m.address, function (point) {
                        if (point) {
                            that.m.longitude = point.lng;
                            that.m.latitude = point.lat;
                        } else {
                            console.log("您选择地址没有解析到结果!");
                        }
                    }, this.m.city);
                },
                uploadSuccess: function (url) {
                    $("[name='photo']").val(url).trigger("change");
                    C.simpleMsg("上传成功");
                },
                uploadError: function (msg) {
                    C.errorMsg(msg);
                },
                edit: function (model) {
                    this.m = model;
                    var tem1 = this.m.openTime;
                    var tem2 = this.m.closeTime;
                    var open;
                    var close;
                    open = new Date(tem1).format("hh:mm");
                    close = new Date(tem2).format("hh:mm");
                    if (open == 'aN:aN') {
                        open = tem1;
                    }
                    if (close == 'aN:aN') {
                        close = tem2;
                    }
                    this.m.openTime = open;
                    this.m.closeTime = close;
                    this.openForm();
                },
                showjwd: function () {
                    //  百度地图API功能
                    var map = new BMap.Map("allmap");
                    var point = new BMap.Point(116.331398, 39.897445);
                    map.centerAndZoom(point, 12);
                    // 创建地址解析器实例
                    var myGeo = new BMap.Geocoder();
                    // 将地址解析结果显示在地图上,并调整地图视野
                    myGeo.getPoint("北京市海淀区上地10街", function (point) {
                        if (point) {
                            map.centerAndZoom(point, 16);
                            map.addOverlay(new BMap.Marker(point));
                        } else {
                            alert("您选择地址没有解析到结果!");
                        }
                    }, "上海市");
                    console.log(point);
                    this.m.longitude = point.lng;
                    this.m.latitude = point.lat;
                },

                initTime: function () {
                    $(".timepicker-no-seconds").timepicker({
                        autoclose: true,
                        showMeridian: false,
                        minuteStep: 5
                    });
                },
                initShopMode: function () {
                    var that = this;
                    $.ajax({
                        url: "shopmode/list_all",
                        type: "post",
                        dataType: "json",
                        success: function (data) {
                            that.allMode = data;
                        }

                    });
                },
                initMq: function () {
                    var that = this;
                    $.ajax({
                        url: "mqConfig/list_all",
                        type: "post",
                        dataType: "json",
                        success: function (data) {
                            that.allMq = data.data;
                        }

                    });

                },
                initBrandMode: function () {
                    var that = this;
                    $.ajax({
                        url: "brand/list_all",
                        type: "post",
                        dataType: "json",
                        success: function (data) {
                            that.brands = data;
                        }
                    })
                },
                businessFormat: function () {
                    var that = this;
                    $.ajax({
                        url: "businessformat/list_all",
                        type: "post",
                        dataType: "json",
                        success: function (data) {
                            that.allType = data;
                        }

                    });

                }
            },
        });

        C.vue = vueObj;

    }());

</script>
