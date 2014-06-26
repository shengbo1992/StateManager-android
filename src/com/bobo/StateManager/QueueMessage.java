package com.bobo.StateManager;

/**
 * 队列信息
 * @author bobo
 *
 */
public class QueueMessage {
	public BaseState baseState; //队列事件
	public String tag;//事件标记

	public QueueMessage(String tag, BaseState baseState) {
		// TODO Auto-generated constructor stub
		this.tag = tag;
		this.baseState = baseState;
	}
	
}