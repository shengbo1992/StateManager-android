package com.bobo.StateManager;

import android.os.Handler;
import android.os.Message;

public class StateHandler extends Handler {
	private PostQueue postQueue = new PostQueue();

	/**
	 * 入队操作
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
				if (message.tag == null) //当这个事件没有进行tag注册的时候通知全部发送
					StateManager.getDefault().invokeEvent(message);
				else
					//对进行了tag处理的下发
					StateManager.getDefault().invokeEventByTag(message);
			}
		}
	}
}
