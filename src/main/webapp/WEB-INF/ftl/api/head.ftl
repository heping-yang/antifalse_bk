    "response_head": {
        "menuid": "${request.request_head.menuid}", 
        "process_code": "${request.request_head.menuid}", 
        "verify_code": "", 
        "resp_time": "${.now?string["yyyyMMddHHmmss"]}", 
        "testflag": "${request.request_head.testflag}", 
        "sequence": {
            "resp_seq": "", 
            "operation_seq": ""
        }, 
        "retinfo": {
            "retcode": "${retinfo.retCode!'999'}", 
            "rettype": "${retinfo.retType!'JSON'}", 
            "retmsg": "${retinfo.retMsg!''}"
        }
    },
