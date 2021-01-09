/*package com.dvreiter.starassault;
import android.app.*;
import android.os.*;
import com.dvreiter.starassault.Levels.*;
import com.google.android.gms.ads.*;
import android.widget.*;
import android.net.*;
import android.view.*;
import android.view.ViewGroup.*;

public class TestActivity extends Activity
{
	
	//AdController adCtrl;
	private InterstitialAd interstitialAd;
	private AdView adView;
    private boolean running = false;
	public PlayState lvl1;
	//private InterstitialAd interstitialAd;
	Button TM;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.new_game);
		TM = (Button)findViewById(R.id.TM);
        TM.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(interstitialAd.isLoaded()){
						interstitialAd.show();
						Toast.makeText(getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
					}
				}
			});
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
		Toast.makeText(getApplicationContext(), "Instantiating Interstitial", Toast.LENGTH_SHORT).show();
		AdRequest adRequest = new AdRequest.Builder().build();
		interstitialAd.loadAd(adRequest);
		Toast.makeText(getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
	//	showOrLoadInterstital();
	/*	setContentView(R.layout.new_game);
		TM = (Button)findViewById(R.id.TM);
        TM.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(interstitialAd.isLoaded()){
						interstitialAd.show();
						Toast.makeText(getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
						
					}
				}
			});
			
		try {
			//		Toast.makeText(getApplicationContext(), "Instantiating Interstitial", Toast.LENGTH_SHORT).show();
			interstitialAd = new InterstitialAd(this);
			interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
			Toast.makeText(getApplicationContext(), "Instantiating Interstitial", Toast.LENGTH_SHORT).show();
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitialAd.loadAd(adRequest);
			Toast.makeText(getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
		}catch ( Exception error) {
			Toast.makeText(getApplicationContext(),error.getMessage() , Toast.LENGTH_SHORT).show();
			}
    }
	
	public void showOrLoadInterstital() {
		try {
			runOnUiThread(new Runnable() {
					public void run() {
						if (interstitialAd.isLoaded()) {
					//		Toast.makeText(getApplicationContext(), "Cock started!", Toast.LENGTH_SHORT).show();
							interstitialAd.show();
							Toast.makeText(getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
						}
						else {
					//		Toast.makeText(getApplicationContext(), "Cock loading?...", Toast.LENGTH_SHORT).show();
							AdRequest interstitialRequest = new AdRequest.Builder().build();
							interstitialAd.loadAd(interstitialRequest);
							Toast.makeText(getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
						}
					}
				});
		} catch (Exception e) {
		}
	}
			
    }	

/*
	 public void ClickTM(View v){
	 // setContentView(R.layout.TM);
	 showOrLoadInterstital();			
	 }
	 /*  public void run() {
	 if (!running) {
	 if (hasInternetConnection()) {
	 running = true;
	 createAdView();
	 showOrLoadInterstital();
	 //	testState();
	 /*      LayoutParams adNinjaLayoutParams = new LayoutParams(-2, -2);
	 adNinjaLayoutParams.addRule(10);
	 activity.addContentView(interstitialAd, adNinjaLayoutParams);
	 //	showOrLoadInterstital();
	 //     nextAd();
	 return;
	 }
	 }
	 }

    private void createAdView() {
		Toast.makeText(getApplicationContext(), "Cock initialized!", Toast.LENGTH_SHORT).show();
        interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-6916351754834612/3808499421");
		interstitialAd.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {
					Toast.makeText(getApplicationContext(), "Finished Loading Interstitial", Toast.LENGTH_SHORT).show();
				}
				@Override
				public void onAdClosed() {
					Toast.makeText(getApplicationContext(), "Closed Interstitial", Toast.LENGTH_SHORT).show();
				}
			});
    }

	private void testState(){
		//	lvl1 = new PlayState(this);
	}

	public void showOrLoadInterstital() {
		try {
			runOnUiThread(new Runnable() {
					public void run() {
						if (interstitialAd.isLoaded()) {
							Toast.makeText(getApplicationContext(), "Cock started!", Toast.LENGTH_SHORT).show();
							interstitialAd.show();
							Toast.makeText(getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(getApplicationContext(), "Cock loading?...", Toast.LENGTH_SHORT).show();
							AdRequest interstitialRequest = new AdRequest.Builder().build();
							interstitialAd.loadAd(interstitialRequest);
							Toast.makeText(getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
						}
					}
				});
		} catch (Exception e) {
		}
	}


	public boolean hasInternetConnection() {
        NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
*/
