package com.example.reviews;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.gc.android.market.api.MarketSession;
import com.gc.android.market.api.MarketSession.Callback;
import com.gc.android.market.api.model.Market.CommentsRequest;
import com.gc.android.market.api.model.Market.CommentsResponse;
import com.gc.android.market.api.model.Market.ResponseContext;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		int startIndex = 1;
		int max = 100;
		int entriesCount=500;
		ArrayList<String> arrayList = new ArrayList<String>();
		
		while (max <=  entriesCount) {
		
			
		arrayList = search(startIndex,max,arrayList);
		
		startIndex = startIndex+100;
		max = max+100;
		 try {
			Thread.sleep(2500);
			System.out.println("sleep");
			Thread.sleep(2500);
			System.out.println("sleep");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
		writeToFile(arrayList);
		
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		tv1.setText("DoneTotal");
		System.out.println("DoneTotal");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public ArrayList<String> search(int startIndex, int max,final ArrayList<String> arrayList) {
		Log.d("Market API", "Started");

	

		String email = "malamondiamedia@googlemail.com";
		String pass = "nightfire123";

		MarketSession session = new MarketSession();
		session.login(email, pass);

		session.getContext().setAndroidId("dead000beef");

		while (startIndex <= max) {
			CommentsRequest commentsRequest = CommentsRequest.newBuilder()

			.setAppId("com.whatsapp").setStartIndex(startIndex)

			.setEntriesCount(10).build();

			session.append(commentsRequest, new Callback<CommentsResponse>() {
				@Override
				public void onResult(ResponseContext context,
						CommentsResponse response) {

					System.out.println("Response : " + response);

					Log.d("Market API", "Got response");
					String res = new String(response.toString());
					arrayList.add(res);
				}
			});

			startIndex = startIndex + 10;
			System.out.println("startIndex:" + startIndex);
		}

		System.out.println("doneTOTAL");
		session.flush();
		return arrayList;
	}

	private void writeToFile(List<String> arrayList) {

		File root = android.os.Environment.getExternalStorageDirectory();

		File dir = new File(root.getAbsolutePath() + "/studie");
		dir.mkdirs();
		File file = new File(dir, "myDataWhatsApp.txt");
		try {
			FileOutputStream f = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(f);

			for (String s : arrayList) {

				pw.println(s);

			}

			pw.flush();
			pw.close();
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
