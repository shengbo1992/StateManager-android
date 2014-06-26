package com.bobo.StateManager;

import android.os.Handler;
import android.os.Message;

public class StateHandler extends Handler {
	private PostQueue postQueue = new PostQueue();

	/**
	 * ��Ӳ���
	 */
	public void enQuene(QueueMessage baseState) {
		postQueue.add(baseState);
		sendMessage(obtainMessage());
	}

	@Override
	public void handleMessage(Message arg0) {
		// TODO Auto-generated method stub
		super.handleMessage(arg0);
		while (true) {
			QueueMessage message = postQueue.poll();
			if (message == null)
				return;
			else {
				if (message.tag == null) //������¼�û�н���tagע���ʱ��֪ͨȫ������
					StateManager.getDefault().invokeEvent(message);
				else
					//�Խ�����tag������·�
					StateManager.getDefault().invokeEventByTag(message);
			}
		}
	}
}
