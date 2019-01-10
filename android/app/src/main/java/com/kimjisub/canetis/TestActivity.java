package com.kimjisub.canetis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestActivity extends AppCompatActivity {
	
	static TextView TV_log;
	
	void initVar() {
		TV_log = findViewById(R.id.log);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		initVar();
		
		new GetDataList().setDataListener(new GetDataList.onDataListener() {
			
			@Override
			public void onAdd(final fbData d) {
				log(d.toJSON().toString());
			}
			
			@Override
			public void onChange(final fbData d) {
				log(d.toJSON().toString());
			}
		}).run();
		
	}
	
	static void log(String msg) {
		TV_log.append(msg + "\n");
		Log.e("com.kimjisub.log", msg);
	}
	
	public static class GetDataList {
		FirebaseDatabase database;
		DatabaseReference myRef;
		
		private GetDataList.onDataListener dataListener = null;
		
		public interface onDataListener {
			void onAdd(fbData data);
			
			void onChange(fbData data);
		}
		
		
		public GetDataList setDataListener(GetDataList.onDataListener listener) {
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
					log("DB add " + dataSnapshot.getKey());
					try {
						fbData data = dataSnapshot.getValue(fbData.class);
						onAdd(data);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onChildChanged(DataSnapshot dataSnapshot, String s) {
					log("DB changed " + dataSnapshot.getKey());
					try {
						fbData data = dataSnapshot.getValue(fbData.class);
						onChange(data);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onChildRemoved(DataSnapshot dataSnapshot) {
					log("DB removed " + dataSnapshot.getKey());
				}
				
				@Override
				public void onChildMoved(DataSnapshot dataSnapshot, String s) {
					log("DB moved " + dataSnapshot.getKey());
				}
				
				@Override
				public void onCancelled(DatabaseError databaseError) {
					log("DB cancelled " + databaseError.getMessage());
				}
			});
		}
		
	}
}
