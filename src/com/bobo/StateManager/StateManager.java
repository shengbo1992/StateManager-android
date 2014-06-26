package com.bobo.StateManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.os.Looper;
import android.util.Log;

/**
 * Ϊandorid��activity����fragment�ṩ�����������Ƭ���ṩ����
 * @author bobo
 *
 */
public class StateManager {
	/**
	 * ������
	 * @author bobo
	 *
	 */
	class Register {
		public String Tag;
		public Object regist;

		public Register(String tag, Object regist) {
			// TODO Auto-generated constructor stub
			this.Tag = tag;
			this.regist = regist;
		}
	}

	//	private ExecutorService executorService = Executors.newCachedThreadPool();//�����̳߳�
	private List<Register> registereds;//��ǰע���registered
	private static volatile StateManager defaultInstance;//����

	private static String TAG = "StateManagerTag";

	private StateHandler statehandler;

	private String CallMethod = "StateOnPosted";

	/**
	 * ������÷���
	 */
	public void invokeEvent(QueueMessage message) {
		//		if(Looper.myLooper() != Looper.getMainLooper())
		//			methed = "StateOnBackgroundThread";
		for (Register key : registereds) {
			Object register = key.regist;
			Method methodi = findinvokeMethed(register);
			//methodi = cla.getMethod("StateOnMainThread", BaseState.class);
			try {
				if (null != methodi)
					methodi.invoke(register, message.baseState);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ������÷���
	 * @param tag
	 * @param baseState
	 */
	public void invokeEventByTag(QueueMessage message) {
		Register register = findDescribe(message.tag);
		if (register != null) {
			Method methodi = findinvokeMethed(register.regist);
			if (methodi != null)
				try {
					methodi.invoke(register.regist, message.baseState);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
		}
	}

	/**
	 * �ҵ����õķ���
	 * @param register
	 * @return
	 */
	private Method findinvokeMethed(Object register) {
		Class<?> cla = register.getClass();
		Method methodi = null;
		try {
			Method methods[] = cla.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(CallMethod)) {
					methodi = method;
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return methodi;

	}

	/**
	 * ����post֪ͨ
	 * @param manager
	 */
	public void post(BaseState baseState) {
		post(null, baseState);
	}

	/**
	 * ����tag����post֪ͨ
	 * @param tag
	 * @param baseState
	 */
	public void post(String tag, BaseState baseState) {
		statehandler.enQuene(new QueueMessage(tag, baseState));
	}

	/**
	 * ע��Tag
	 * @param Tag
	 * @param object
	 */
	public synchronized void registe(String Tag, Object object) {
		if (null == findDescribe(Tag))//��ֹ�ظ�ע��
			registereds.add(new Register(Tag, object));
		else {
			Log.i(TAG, Tag + "�ظ�ע��");
		}
	}

	public void registe(Object object) {
		registe(object.getClass().getSimpleName(), object);
	}

	/**
	 * ע��ע��Ĵ���Ƭ��
	 * @param object
	 */
	public void unregiste(Object object) {
		if (registereds != null && registereds.size() >= 1) {
			registereds.remove(object);
			Log.i(TAG, "unregiste " + object.toString());
		}
	}

	/**
	 * �ж��Ƿ���ע��
	 * @param object
	 * @return
	 */
	private Register findDescribe(String object) {
		Register registe = null;
		if (registereds != null && registereds.size() >= 1)
			for (Register register : registereds) {
				if (register.Tag.equals(object)) {
					registe = register;
					break;
				}
			}
		return registe;
	}

	/**
	 * ˽�й��췽��
	 */
	private StateManager() {
		registereds = new ArrayList<Register>();
		statehandler = new StateHandler();
	}

	/**
	 * ��������
	 * @return
	 */
	public static StateManager getDefault() {
		if (defaultInstance == null) {
			synchronized (StateManager.class) {
				if (defaultInstance == null) {
					defaultInstance = new StateManager();
				}
			}
		}
		return defaultInstance;
	}
}
