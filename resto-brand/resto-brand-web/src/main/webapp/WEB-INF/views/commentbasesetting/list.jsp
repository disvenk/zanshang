<%@ page language="java" pageEncoding="utf-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@taglib prefix="s" uri="http://shiro.apache.org/tags" %>
		<style>
		.form-group label {
			width: 200px;
		}
		.form-group input{
			width: 80px;
			text-align: center;
			display: inline-block;
			margin-right: 10px;
		}
		</style>
			<div id="control">
				<div class="row " v-if="showform">
					<div class="col-md-offset-3 col-md-6">
						<div class="portlet light bordered">
							<div class="portlet-title">
								<div class="caption">
									<span class="caption-subject bold font-blue-hoki">评论基础设置</span>
								</div>
							</div>
							<div class="portlet-body">
								<form role="form" @submit.prevent="save">
									<div class="form-body">
										<%--<div class="form-group">
											<label class="fl">店铺每日至少获取评论数</label>
											<input type="text" class="form-control" name="commentCount" v-model="formData.commentCount">条
										</div>--%>
										<div class="form-group">
											<label>用户评论间隔时间</label>
											<input type="text" class="form-control" name="distanceTime" v-model="formData.distanceTime">小时
										</div>
										<div class="form-group">
											<label>好评5星最少评论字数</label>
											<input type="text" class="form-control" name="fiveSmall" v-model="formData.fiveSmall">个
										</div>
										<div class="form-group">
											<label>差评(4星及以下)最小评论字数</label>
											<input type="text" class="form-control" name="fourSmall" v-model="formData.fourSmall">个
										</div>

									</div>
									<%--<input type="hidden" name="id" v-model="m.id" />--%>
									<input class="btn green" type="submit" value="保存" />

								</form>
							</div>
						</div>
					</div>
				</div>
				</div>
			<script>
				(function () {
                    var C = new Controller(null,null);
					var vueObj = new Vue({
						el: "#control",
						data: {
							showform: true,
							formData: {
                                commentCount:'',
                                distanceTime:'',
								fourSmall:'',
								fiveSmall:'',
							}
						},
						created:function() {
							//todo 请求获取配置数据
							this.getConf();
						},
						methods: {
							//todo 
                            getConf:function () {
							    var that = this;
								 $.getJSON('commentbasesetting/selectCommentBaseSetting', function (data) {
                                     if(data.success){
                                         C.simpleMsg(data.message);
                                         that.formData.commentCount = data.data.commentCount;
                                         that.formData.distanceTime = data.data.distanceTime;
										 that.formData.fourSmall = data.data.fourSmall;
										 that.formData.fiveSmall = data.data.fiveSmall;
                                     }else{
                                         C.errorMsg(data.message);
                                     }

								 })
							},
							save:function() {
								// todo 
								console.log('aa');
								var options = {
                                    commentCount: this.formData.commentCount,
									distanceTime: this.formData.distanceTime,
									fourSmall: this.formData.fourSmall,
									fiveSmall: this.formData.fiveSmall
								}

                                    $.ajax({
                                        type: 'POST',
                                        url: 'commentbasesetting/createAndUpdate',
                                        dataType: 'json',

                                        contentType: 'application/json; charset=utf-8',
                                        data: JSON.stringify(options),
                                        success: function (res) {
                                            if(res.success){
                                                C.simpleMsg("保存成功");
                                            }else{
                                                C.errorMsg(res.message);
                                            }
                                        }
								})
							}
						}
						
					});
					
				}());
			</script>