package com.bobo.StateManager;

import java.util.ArrayList;
import java.util.List;

/**
 * post queue
 * @author bobo
 *
 */
public class PostQueue {

	private List<QueueMessage> messagequeues = new ArrayList<QueueMessage>();
	public void add(QueueMessage baseState) {
		messagequeues.add(baseState);
	}

	public int size() {
		return messagequeues.size();
	}

	public void remove(BaseState baseState) {
		messagequeues.remove(baseState);
	}

	public void removeall() {
		messagequeues.clear();
	}

	public void remove(int position) {
		messagequeues.remove(position);
	}

	public QueueMessage poll() {
		if (messagequeues.size() >= 1)
			return messagequeues.remove(0);
		else
			return null;
	}
}
