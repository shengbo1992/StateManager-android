package com.bobo.StateManager;

/**
 * ������Ϣ
 * @author bobo
 *
 */
public class QueueMessage {
	public BaseState baseState; //�����¼�
	public String tag;//�¼����

	public QueueMessage(String tag, BaseState baseState) {
		// TODO Auto-generated constructor stub
		this.tag = tag;
		this.baseState = baseState;
	}
	
}