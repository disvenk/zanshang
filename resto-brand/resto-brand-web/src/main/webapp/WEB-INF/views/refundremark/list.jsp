<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://shiro.apache.org/tags" %>
<div id="control">
    <div class="row form-div" v-if="showform">
        <div class="col-md-offset-3 col-md-6">
            <div class="portlet light bordered">
                <div class="portlet-title">
                    <div class="caption">
                        <span class="caption-subject bold font-blue-hoki"> 表单</span>
                    </div>
                </div>
                <div class="portlet-body">
                    <form role="form" action="{{m.id?'refundremark/modify':'refundremark/create'}}"
                          @submit.prevent="save">
                        <div class="form-body">
                            <div class="form-group">
                                <label>退菜原因</label>
                                <input type="text" class="form-control" name="name" v-model="m.name">
                            </div>

                            <div class="form-group">
                                <label>序号</label>
                                <input type="text" class="form-control" name="sort" v-model="m.sort">
                            </div>
                        </div>
                        <input type="hidden" name="id" v-model="m.id"/>
                        <input class="btn green" type="submit" value="保存"/>
                        <a class="btn default" @click="cancel">取消</a>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="table-div">
        <div class="table-operator">
            <s:hasPermission name="refundremark/add">
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
    (function () {
        var cid = "#control";
        var $table = $(".table-body>table");
        var tb = $table.DataTable({
            ajax: {
                url: "refundremark/list_all",
                dataSrc: ""
            },
            columns: [
                {
                    title: "退菜原因",
                    data: "name",
                },
                {
                    title: "序号",
                    data: "sort",
                },
                {
                    title: "状态",
                    data: "state",
                    createdCell: function (td, tdData) {
                        if (tdData == 1) {
                            $(td).html("开启");
                        } else if (tdData == 0) {
                            $(td).html("未开启")
                        }
                    }
                },
                {
                    title: "操作",
                    data: "id",
                    createdCell: function (td, tdData, rowData, row) {
                        var operator = [];
                        <s:hasPermission name="refundremark/delete">
                            operator.push(C.createBtn(rowData).html("删除"));
                        </s:hasPermission>
                        $(td).html(operator);
                    }
                }],
        });

        var C = new Controller(null, tb);
        var vueObj = new Vue({
            el: "#control",
            mixins: [C.formVueMix]
        });
        C.vue = vueObj;
    }());


</script>
