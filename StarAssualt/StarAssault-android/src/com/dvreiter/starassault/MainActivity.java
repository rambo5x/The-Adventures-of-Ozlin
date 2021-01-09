package com.dvreiter.starassault;

import org.flixel.FlxAndroidApplication;
import com.dvreiter.starassault.LevelLoader.*;
import android.os.*;
import android.net.*;
import com.google.android.gms.ads.*;
import com.dvreiter.starassault.Levels.*;
import android.widget.*;
import android.widget.RelativeLayout.*;
import android.app.*;
import com.badlogic.gdx.backends.android.*;
import android.view.*;

public class MainActivity extends FlxAndroidApplication
{
	AdController controller;
	protected View gameView;
	private InterstitialAd interstitialAd;
    public MainActivity()
	{
 		super(new StarAssaultGame());
	}
	
	//@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	//	controller = new AdController(this);
		
	}

	public boolean hasInternetConnection() {
        NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
