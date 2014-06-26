StateManager-android
====================

android 代码片段传递管理器，只需要向Statemanager注册class然后在需要改变状态的时候
调用post方法就会回调到class里面的onPosted方法中，只需要在onPosted方法中处理就行。
