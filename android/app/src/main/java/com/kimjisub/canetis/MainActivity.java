package com.kimjisub.canetis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
	
	Intent recognizerIntent;
	SpeechRecognizer mRecognizer;
	
	TextToSpeech tts;
	
	ImageView IV_setting;
	TextView TV_log;
	TextView TV_obj1;
	TextView TV_obj2;
	TextView TV_obj3;
	TextView TV_search;
	TextView TV_alert;
	View V_button;
	ImageView[] IVs_status;
	int prevIndex = 0;
	int nowIndex = 0;
	
	final int WAITING = 0;
	final int START = 1;
	
	
	ArrayList arrayList;
	
	void initVar() {
		IV_setting = findViewById(R.id.setting);
		TV_log = findViewById(R.id.log);
		TV_obj1 = findViewById(R.id.obj1);
		TV_obj2 = findViewById(R.id.obj2);
		TV_obj3 = findViewById(R.id.obj3);
		TV_search = findViewById(R.id.search);
		TV_alert = findViewById(R.id.alert);
		V_button = findViewById(R.id.button);
		IVs_status = new ImageView[]{
			findViewById(R.id.status_1_waiting),
			findViewById(R.id.status_2_start),
			findViewById(R.id.status_3_listning)
		};
		
		for (ImageView v : IVs_status)
			v.setAlpha(0);
		IVs_status[0].setAlpha(1);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initVar();
		
		TedPermission.with(this)
			.setPermissionListener(new PermissionListener() {
				@Override
				public void onPermissionGranted() {
					start();
				}
				
				@Override
				public void onPermissionDenied(ArrayList<String> deniedPermissions) {
					finish();
				}
			})
			.setRationaleMessage("음성인식을 위해서 오디오 녹음 권한이 필요합니다.\n해당 권한에 대한 허가를 받습니다.")
			.setDeniedMessage("오디오 녹음 장치에 액세스해야합니다.\n[설정] > [권한]에서 권한을 켜십시오.")
			.setPermissions(android.Manifest.permission.RECORD_AUDIO)
			.check();
	}
	
	void start() {
		arrayList = new ArrayList();
		
		tts = new TextToSpeech(getApplicationContext(), status -> {
			if (status != TextToSpeech.ERROR) {
				tts.setLanguage(Locale.KOREAN);
			}
		});
		
		recognizerIntent = new Intent(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
		
		mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		mRecognizer.setRecognitionListener(recognitionListener);
		
		
		V_button.setOnClickListener(view -> startVoice());
		
		IV_setting.setOnClickListener(view -> {
			Intent intent = new Intent(MainActivity.this, TestActivity.class);
			startActivity(intent);
		});
		
		new GetDataList().setDataListener(new GetDataList.onDataListener() {
			
			@Override
			public void onAdd(final fbData d) {
				arrayList.add(d);
				log("DB add " + d.code);
			}
			
			@Override
			public void onChange(final fbData d) {
				int i = 0;
				for (; i < arrayList.size(); i++) {
					fbData tmp = (fbData) arrayList.get(i);
					if (tmp.code == d.code)
						break;
				}
				arrayList.set(i, d);
				log("DB change " + d.code);
			}
		}).run();
	}
	
	private RecognitionListener recognitionListener = new RecognitionListener() {
		@Override
		public void onReadyForSpeech(Bundle bundle) {
			log("onReadyForSpeech");
			changeStatus(START);
			findViewById(R.id.back).animate().alpha(1).setDuration(500).start();
			
		}
		
		@Override
		public void onBeginningOfSpeech() {
			log("onBeginningOfSpeech");
		}
		
		@Override
		public void onRmsChanged(float v) {
			log("onRmsChanged " + v);
		}
		
		@Override
		public void onBufferReceived(byte[] bytes) {
			log("onBufferReceived " + bytes);
		}
		
		@Override
		public void onEndOfSpeech() {
			log("onEndOfSpeech");
			changeStatus(WAITING);
			findViewById(R.id.back).animate().alpha(0).setDuration(500).start();
		}
		
		@Override
		public void onError(int i) {
			//log("onError " + i);
		}
		
		@Override
		public void onResults(Bundle bundle) {
			ArrayList<String> mResult = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			
			String[] rs = new String[mResult.size()];
			mResult.toArray(rs);
			String speak = rs[0];
			
			log("onResults: " + speak);
			TV_search.setText(speak);
			findViewById(R.id.serchui).animate().alpha(1).setDuration(500).start();
			new Handler().postDelayed(() -> runOnUiThread(() -> findViewById(R.id.serchui).animate().alpha(0).setDuration(500).start()), 3000);
			
			boolean found = false;
			int i = 0;
			fbData data = null;
			for (; i < arrayList.size(); i++) {
				data = (fbData) arrayList.get(i);
				if ((speak.replaceAll("\\s+","")).contains(data.name.replaceAll("\\s+",""))) {
					DatabaseReference database = FirebaseDatabase.getInstance().getReference();
					database.child("data").child(data.code + "").child("isCalled").setValue(true);
					found = true;
					break;
				}
			}
			
			if (found) {
				if (data.status) {
					alert(data.name + "을 찾았습니다.");
					
					TV_obj3.setText(TV_obj2.getText());
					TV_obj2.setText(TV_obj1.getText());
					TV_obj1.setText(data.name);
					
				} else {
					alert(data.name + " 장치가 꺼져있습니다.");
				}
			} else {
				alert("찾을 수 없습니다.");
			}
		}
		
		@Override
		public void onPartialResults(Bundle bundle) {
			log("onPartialResults");
		}
		
		@Override
		public void onEvent(int i, Bundle bundle) {
			log("onEvent");
		}
	};
	
	public static class GetDataList {
		FirebaseDatabase database;
		DatabaseReference myRef;
		
		private onDataListener dataListener = null;
		
		public interface onDataListener {
			void onAdd(fbData data);
			
			void onChange(fbData data);
		}
		
		
		public GetDataList setDataListener(onDataListener listener) {
			this.dataListener = listener;
			return this;
		}
		
		void onAdd(fbData data) {
			if (dataListener != null)
				dataListener.onAdd(data);
		}
		
		void onChange(fbData data) {
			if (dataListener != null)
				dataListener.onChange(data);
		}
		
		
		public void run() {
			database = FirebaseDatabase.getInstance();
			myRef = database.getReference("data");
			
			myRef.addChildEventListener(new ChildEventListener() {
				@Override
				public void onChildAdded(DataSnapshot dataSnapshot, String s) {
					try {
						fbData data = dataSnapshot.getValue(fbData.class);
						onAdd(data);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onChildChanged(DataSnapshot dataSnapshot, String s) {
					try {
						fbData data = dataSnapshot.getValue(fbData.class);
						onChange(data);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onChildRemoved(DataSnapshot dataSnapshot) {
				
				}
				
				@Override
				public void onChildMoved(DataSnapshot dataSnapshot, String s) {
				
				}
				
				@Override
				public void onCancelled(DatabaseError databaseError) {
				
				}
			});
		}
		
	}
	
	void changeStatus(int changeIndex) {
		prevIndex = nowIndex;
		nowIndex = changeIndex;
		
		IVs_status[prevIndex].animate().alpha(0).setDuration(500).start();
		IVs_status[nowIndex].animate().alpha(1).setDuration(500).start();
	}
	
	
	void alert(final String msg) {
		TV_alert.setText(msg);
		TV_alert.animate().alpha(1).setDuration(500).start();
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						TV_alert.animate().alpha(0).setDuration(500).start();
					}
				});
			}
		}, 3000);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				tts(msg);
			}
		}, 1000);
	}
	
	void tts(String msg) {
		String utteranceId = this.hashCode() + "";
		tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
	}
	
	void startVoice() {
		mRecognizer.startListening(recognizerIntent);
	}
	
	void log(String msg) {
		Log.d("com.kimjisub.log", msg);
		TV_log.append(msg + "\n");
		TV_log.append(msg + "\n", 0, 0);
	}
	
	public boolean onKeyDown(int keycode, KeyEvent event) {
		switch (keycode) {
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				startVoice();
				break;
			
			case KeyEvent.KEYCODE_VOLUME_UP:
				startVoice();
				break;
		}
		return true;
	}
}
