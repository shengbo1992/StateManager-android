package com.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bobo.StateManager.BaseState;
import com.bobo.StateManager.StateManager;
import com.example.mylib.R;

public class MainActity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlayout);
		StateManager.getDefault().registe(this);

		Intent intent = new Intent(MainActity.this, TestService.class);
		startService(intent);
		
	//	StateManager.getDefault().registe("mytage", this);//注册带tag
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		StateManager.getDefault().unregiste(this);
	}

	public void onclick(View view) {

		switch (view.getId()) {
		case R.id.sendnotag:
			StateManager.getDefault().post(new Mystate(true)); //发送不带tag的状态
			break;
		case R.id.senddef:
			StateManager.getDefault().post(new Yourstate()); //发送不带tag的不同状态
			break;
		case R.id.sendtag:
			StateManager.getDefault().post("mytage", new Mystate(true)); //发送带tag的状态
			break;
		}
	}

	class Mystate extends BaseState {
		public boolean is;
		public String HH = "my state hello";

		public Mystate(boolean is) {
			// TODO Auto-generated constructor stub
			super();
			this.is = is;
		}
	}

	class Yourstate extends BaseState {
		public String Tag = "YourState";
	}

	
	/**
	 * 回调方法
	 * @param mystate
	 */
	public void StateOnPosted(BaseState baseState) {
		if (baseState instanceof Mystate) {
			Mystate mystate = (Mystate) baseState;
			Toast.makeText(this, "hello  this is mystate" + mystate.is + " " + mystate.HH, 0).show();
		} else if (baseState instanceof Yourstate) {
			Yourstate yourtast = (Yourstate) baseState;
			Toast.makeText(this, "hello  this is yourstate" + yourtast.Tag, 0).show();
		} else {
			Toast.makeText(this, "hello  this is callback", 0).show();
		}
	}

}
