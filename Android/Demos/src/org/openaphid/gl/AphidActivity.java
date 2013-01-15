/*
Copyright 2012 Aphid Mobile

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 
   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.openaphid.gl;

import org.openaphid.internal.AppDelegate;
import org.openaphid.internal.utils.AphidLog;
import org.openaphid.internal.utils.Net;
import demo.openaphid.binding.*;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class AphidActivity extends Activity {

	private AphidGLSurfaceView glSurfaceView;

	static {
		System.loadLibrary("OpenAphid");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		String scriptFilename = getIntent().getExtras().getString("script");
		int orientation = getIntent().getExtras().getInt("orientation", ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setRequestedOrientation(orientation);

		AppDelegate.initialize(this, Net.newURL("http://129.158.217.36:18080"), "demo.bundle", false);

		glSurfaceView = new AphidGLSurfaceView(this);

		setContentView(glSurfaceView);

		glSurfaceView.requestFocus();
		glSurfaceView.setFocusableInTouchMode(true);
		
		glSurfaceView.getAphidRenderer().setScriptBinding("test1", new BindingTest1(), false);
		glSurfaceView.getAphidRenderer().setScriptBinding("test2", new BindingTest2(), false);
		glSurfaceView.getAphidRenderer().setScriptBinding("test3", new BindingTest3(), true);
		glSurfaceView.getAphidRenderer().setScriptBinding("test4", new BindingTest4(), false);

		glSurfaceView.getAphidRenderer().evaluateScriptFile(scriptFilename);
	}

	/*
	@Override
	protected void onResume() {
		//glSurfaceView.onResume();
		//TODO: handle texture re-creation
		super.onResume();
	}

	@Override
	protected void onPause() {
		//glSurfaceView.onPause();
		//TODO: the default implementation in GLSurfaceView will destroy the surface when paused, which causes problems to underline engine.
		super.onPause();
	}*/

	@Override
	protected void onDestroy() {
		AphidLog.i("AphidActivity is destroyed");
		AppDelegate.onAphidActivityDestroyed(this);
		super.onDestroy();
	}
}
