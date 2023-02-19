package com.mz.common.log.event;

import com.mz.common.log.entity.SysOperLog;
import com.mz.common.log.listener.MzLogProduceListener;
import com.mz.common.utils.MzUtils;
import com.mz.common.utils.SpringContextHolderUtils;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MzLogEventListener {

	@Order
	@EventListener(MzLogEvent.class)
	public void saveMzLog(MzLogEvent event) {
		SysOperLog sysLog = (SysOperLog) event.getSource();
		Map<String, MzLogProduceListener> beans = SpringContextHolderUtils.getBeans(MzLogProduceListener.class);
		if (MzUtils.isEmpty(beans)) {
			throw new IllegalStateException("MzLogProduceListener未注入");
		} else if (beans.size() > 1) {
			throw new IllegalStateException(String.format("MzLogProduceListener存在 %d 个Bean，应当为1个Bean", beans.size()));
		}

		for (MzLogProduceListener produceListener : beans.values()) {
			produceListener.produceLog(sysLog);
		}
	}

}