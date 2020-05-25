package com.system.springbootv1.common.log;

import com.system.springbootv1.project.model.SysLog;
import com.system.springbootv1.project.service.SysLogService;

/**
 * @description:
 * @author: yy 2020/05/08
 **/
public class SaveLogThread implements Runnable {
    private SysLog sysLog;
    private SysLogService sysLogService;

    public SaveLogThread(SysLog sysLog, SysLogService sysLogService) {
        this.sysLog = sysLog;
        this.sysLogService = sysLogService;
    }

    @Override
    public void run() {
        sysLogService.insert(sysLog);
    }
}
