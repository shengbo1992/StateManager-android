package com.bobo.StateManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.os.Looper;
import android.util.Log;

/**
 * 为andorid的activity或者fragment提供或者任意代码片段提供交互
 * @author bobo
 *
 */
public class StateManager {
	/**
	 * 描述类
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

	//	private ExecutorService executorService = Executors.newCachedThreadPool();//创建线程池
	private List<Register> registereds;//当前注册的registered
	private static volatile StateManager defaultInstance;//单例

	private static String TAG = "StateManagerTag";

	private StateHandler statehandler;

	private String CallMethod = "StateOnPosted";

	/**
	 * 反射调用方法
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
	 * 反射调用方法
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
	 * 找到调用的方法
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
	 * 发送post通知
	 * @param manager
	 */
	public void post(BaseState baseState) {
		post(null, baseState);
	}

	/**
	 * 更据tag发送post通知
	 * @param tag
	 * @param baseState
	 */
	public void post(String tag, BaseState baseState) {
		statehandler.enQuene(new QueueMessage(tag, baseState));
	}

	/**
	 * 注册Tag
	 * @param Tag
	 * @param object
	 */
	public synchronized void registe(String Tag, Object object) {
		if (null == findDescribe(Tag))//防止重复注册
			registereds.add(new Register(Tag, object));
		else {
			Log.i(TAG, Tag + "重复注册");
		}
	}

	public void registe(Object object) {
		registe(object.getClass().getSimpleName(), object);
	}

	/**
	 * 注销注册的代码片段
	 * @param object
	 */
	public void unregiste(Object object) {
		if (registereds != null && registereds.size() >= 1) {
			registereds.remove(object);
			Log.i(TAG, "unregiste " + object.toString());
		}
	}

	/**
	 * 判断是否已注册
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
	 * 私有构造方法
	 */
	private StateManager() {
		registereds = new ArrayList<Register>();
		statehandler = new StateHandler();
	}

	/**
	 * 单例方法
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
