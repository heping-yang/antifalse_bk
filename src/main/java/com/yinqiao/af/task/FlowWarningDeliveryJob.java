package com.yinqiao.af.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yinqiao.af.utils.PushMsgUtil;
import com.yinqiao.af.weixin.msg.FlowWarningTemplateMessage;

public class FlowWarningDeliveryJob{
	
	private static final String FLOW_WARNING_TEMPLATE_ID = "860010006";
	
	private  int tryCount = 0 ;
	
	private Logger log = LoggerFactory.getLogger(FlowWarningDeliveryJob.class);
	
	public FlowWarningDeliveryJob(){		
	}
	
	private static Logger logger = LoggerFactory.getLogger(DemoTask.class);
	
	/*@Resource(name="redisTemplate")
	private ValueOperations<String, Object> valOps;*/
	
	public void execute(){
		logger.info("任务开始执行。。。");
		try {
			pushFlowWarning();
		} catch (Exception e) {
			//投递为成功，重试
			tryCount ++;
			if(tryCount > 4){
				tryCount = 0;
				return;
			}
			log.error("An Exception occured when Push FlowWarning! The tryCount is :{}. ",tryCount);
			log.error(e.getMessage());
			execute();			
		}
	}

	private void pushFlowWarning() throws Exception {
		
//		User user = userService.findByPhone(flow.getPhone_no());
//		if (user == null || StringUtil.checkNull(user.getOpenid())){
//			return;
//		}
		
//		MsgTemplate msgTemp = msgTemplateService.findByMsgTemplateId(FLOW_WARNING_TEMPLATE_ID);
		FlowWarningTemplateMessage template = new FlowWarningTemplateMessage();
//		
//		String [] array = msgTemp.getMessage_template_content().split(PublicConstants.C_PUB_CRSET_MESSAGE_SEPARATE);
//		String first = array[0];
//		String remark = array[1];
//		String url = msgTemp.getMessage_template_url();
//		String date = DateUtil.getCurrentDate("MM月dd日");
		
		
//		first = first.replace("#{date}", date);
//		template.setToUserName(user.getOpenid());
//		template.setUrl(url);
//		template.setData(
//				template.getData()
//					.replace("FIRST", first)
//					.replace("TEL", "")
//					.replace("USED1", "M")
//					.replace("SURPLUS1", "M")
//					.replace("REMARK", remark));
//		PushMsgUtil.push(template);	
	}
}
