$(function() {
	console.log("进入章节" + chapter + ", 2秒后开始计时");
	setTimeout(startStudy, 2 * 1000);
});
$(window).bind('beforeunload', function() {
	if (isStudy && !!startTime) {
		finishStudy();
	}
});
var isStudy = false;
var timer = null;
var startTime = null;
var token = null;
var INTERVAL  = 30;

function startStudy() {
	console.log("开始学习...")
	$.post("/antifalse/onlinestudy/start/" + chapter, {
		"lid" : chapter,
		"lname" : chapterName,
		"period" : period
	}, function(ret) {
		console.log(ret);
		if (ret.ret_code == "0") {
			isStudy = true;
			startTime = new Date();
			token = ret.token;
			timer = setTimeout(heartbeat, INTERVAL * 1000);
		}
	});
}

function finishStudy() {
	console.log("学习结束");
	$.post("/antifalse/onlinestudy/finish/" + chapter, {
		"token" : token
	}, function(ret) {
		console.log(ret);
		if (ret.ret_code == "0") {
			isStudy = false;
			if (!!timer) {
				clearTimeout(timer);
			}
		}
	});
}

function heartbeat() {
	if (!isStudy) {
		return;
	}
	$.post("/antifalse/onlinestudy/heartbeat/" + chapter, {
		"token" : token
	}, function(ret) {
		console.log(ret);
		if (ret.ret_code == "0") {
			// 30秒发一次心跳包
			timer = setTimeout(heartbeat, INTERVAL * 1000);
		}
	});

}