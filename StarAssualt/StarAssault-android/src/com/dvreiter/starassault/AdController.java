package com.dvreiter.starassault;

import android.widget.RelativeLayout.LayoutParams;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import org.flixel.FlxG;
import com.google.android.gms.ads.*;
import android.app.*;
import com.dvreiter.starassault.LevelLoader.*;
import com.dvreiter.starassault.Levels.*;
import android.widget.*;

public class AdController implements Runnable, ActionResolver {
    private MainActivity activity;
  //  private AdView adView;
	private InterstitialAd interstitialAd;
    private boolean running = false;
	private PlayState lvl1;

    public AdController(MainActivity owner) {
        activity = owner;
    }

    public void run() {
        if (!running) {
            if (activity.hasInternetConnection()) {
                running = true;
                createAdView();
	//			showOrLoadInterstital();
	//			showOrLoadInterstital();
	//			testState();
			//	showOrLoadInterstital();
           //     nextAd();
                return;
            }
 //           System.out.println("No network connection, checking again in 5s");
//            activity.handler.postDelayed(this, 5000);
        }
    }

    private void createAdView() {
        interstitialAd = new InterstitialAd(activity);
		interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
		AdRequest adRequest = new AdRequest.Builder().build();
		interstitialAd.loadAd(adRequest);
		Toast.makeText(activity.getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
    }
	
	/*private void testState(){
		lvl1 = new PlayState(this);
	}*/

	public void showOrLoadInterstital() {
		try {
			activity.runOnUiThread(new Runnable() {
					public void run() {
						if (interstitialAd.isLoaded()) {
							interstitialAd.show();
							Toast.makeText(activity.getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
						}
						else {
							AdRequest interstitialRequest = new AdRequest.Builder().build();
							interstitialAd.loadAd(interstitialRequest);
							Toast.makeText(activity.getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
						}
					}
				});
		} catch (Exception e) {
			Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
 /*   private void nextAd() {
       interstitialAd.loadAd(new Builder().build());
    }*/

}
