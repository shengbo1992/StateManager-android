package com.test.activity;

import com.bobo.StateManager.BaseState;
import com.bobo.StateManager.StateManager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class TestService extends Service {
	private String Tag = "Testservcei";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		StateManager.getDefault().registe("mytage", this); //зЂВс
		//		StateManager.getDefault().post(new BaseState() {
		//		});
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void StateOnPosted(BaseState baseState) {
		//Log.i(Tag, "state on service");
		Toast.makeText(this, "Service receved messgae", 0).show();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		StateManager.getDefault().unregiste(this);
	}
}
