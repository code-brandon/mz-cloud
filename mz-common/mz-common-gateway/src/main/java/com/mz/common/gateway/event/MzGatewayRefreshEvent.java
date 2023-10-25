package com.mz.common.gateway.event;

import org.springframework.context.ApplicationEvent;

public class MzGatewayRefreshEvent extends ApplicationEvent {


	/**
	 * Create a new {@code ApplicationEvent}.
	 *
	 * @param source the object on which the event initially occurred or with
	 *               which the event is associated (never {@code null})
	 */
	public MzGatewayRefreshEvent(Object source) {
		super(source);
	}
}