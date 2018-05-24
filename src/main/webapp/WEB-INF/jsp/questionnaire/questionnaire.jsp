<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no, email=no">
<link href="${pageContext.request.contextPath}/resources/questionnaire/style/cssreset.css" rel="stylesheet" />
</head>
<body>
	<div id="page_cont">
		<div class="header clearfix">
			<a class="wap-arrow" href="#"><img
				src="${pageContext.request.contextPath}/resources/questionnaire/images/header_arrow_l.png"></a>
			<p>问卷调查</p>
		</div>
		<div class="order_receiving">
			<article class="total sms_list">
				<p class="topic">1、单选、多选、矩阵、排序、量表、比重、表格文件 上传等多种题型</p>
				<div class="tab_cont on">
					<ul class="clearfix order_list">
						<li class="item">
							<div class="item_wrap clearfix pr">
								<span for="" class="icon_select"></span>
								<div class="item_cont l">
									<p class="item_address">A.银川市金</p>
								</div>
							</div>
						</li>
						<li class="item">
							<div class="item_wrap clearfix pr">
								<span for="" class="icon_select"></span>
								<div class="item_cont l">
									<p class="item_address">B.银川市金城大众巷凤</p>
								</div>

							</div>
						</li>
						<li class="item">
							<div class="item_wrap clearfix pr">
								<span for="" class="icon_select"></span>
								<div class="item_cont l">
									<p class="item_address">C.银川市金城大众巷凤区新</p>
								</div>
							</div>
						</li>
						<li class="item">
							<div class="item_wrap clearfix pr">
								<span for="" class="icon_select"></span>
								<div class="item_cont l">
									<p class="item_address">D.银川市金城大众</p>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</article>
		</div>
		<div class="order_receiving ">
			<article class="total sms_list">
				<p class="topic">1、单选、多选、矩阵、排序、量表、比重、表格文件上传等多种题型</p>
				<div class="tab_cont on">
					<ul class="clearfix order_list">
						<li class="item">
							<div class="item_wrap clearfix pr">
								<span for="" class="icon_select"></span>
								<div class="item_cont l">
									<p class="item_address">A.银川市金</p>
								</div>
							</div>
						</li>
						<li class="item">
							<div class="item_wrap clearfix pr">
								<span for="" class="icon_select"></span>
								<div class="item_cont l">
									<p class="item_address">B.银川市金城大众巷凤</p>
								</div>
							</div>
						</li>
						<li class="item">
							<div class="item_wrap clearfix pr">
								<span for="" class="icon_select"></span>
								<div class="item_cont l">
									<p class="item_address">C.银川市金城大众巷凤区新</p>
								</div>
							</div>
						</li>
						<li class="item">
							<div class="item_wrap clearfix pr">
								<span for="" class="icon_select"></span>
								<div class="item_cont l">
									<p class="item_address">D.银川市金城大众</p>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</article>
		</div>
		<div class="btn_box" style="margin: 20px 5%;">
			<div class="btn2 btn_outline selectMaster">提交</div>
		</div>

		<div class="modal_bg"></div>

		<div class="modal_suc modal_master">
			<div class="modal_body">
				<div class="modal_close"></div>
				<h3 class="modal_tit">提示</h3>
				<p class="msg_affirm">你确认提交本次问卷调查吗？</p>

				<div class="btn_box" style="margin: 20px 5%;">
					<div class="btn btn_outline">取消</div>
					<div class="btn btn_outline">确认</div>
				</div>
			</div>
		</div>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/questionnaire/js/jquery-2.1.4.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/questionnaire/js/jquery.tap.min.js"></script>

		<script>
				$(document).ready(function() {

					//是否全选
					function allSelected() {
						var allOrder = $(".order_list li").length;
						var activeOrder = $(".order_list .active").length;
						//console.log(allOrder,activeOrder);
						if(activeOrder == allOrder) {
							$(".all_select_btn").addClass("selected")
						} else {
							$(".all_select_btn").removeClass("selected")
						}
					}
					//选单
					$(".item").tap(function() {
						//console.log("点击")
						var that = $(this);
						//console.log(that)
						if(that.hasClass("active")) {
							that.removeClass("active")
						} else {
							that.addClass("active")
						}
						allSelected()
					})
					
					
					//弹出框部分
					var modal_suc = function() {
						$('.modal_bg').show();
					}
					var modal_close = function() {
						$('.modal_bg').fadeOut();
						$('.modal_suc').hide();
					}

					//关闭
					$(".modal_bg").tap(function() {
						modal_close();
						$(".share").hide()
					})
					//关闭
					$(".btn_cancel").tap(function() {
						modal_close();
					})
					$(".modal_close").tap(function() {
						modal_close();
					})

				 //
				$(".repair_btn").tap(function(){
					modal_suc();
					$(".modal_repair").show()
				})
				
			   //提交
				$(".selectMaster").tap(function() {
					modal_suc();
					$(".modal_master").show();
				})
				$(".master_list .item").tap(function() {
					$(".master_list li").removeClass("active");
					$(this).parents("li").addClass("active");
					var selectItemVal = $(this).text();
					modal_close()
					$(".selectMaster").find("p").html(selectItemVal)
				})
				})
			</script>
</body>
</html>