package com.mz.common.log.event;

import com.mz.common.log.entity.SysOperLog;
import org.springframework.context.ApplicationEvent;

public class MzLogEvent extends ApplicationEvent {

	public MzLogEvent(SysOperLog source) {
		super(source);
	}

}