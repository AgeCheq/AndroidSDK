package com.example.agecheqsample_0;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.agecheq.agecheqlib.AgeCheqApi;
import com.agecheq.lib.AgeCheqApi;
import com.agecheq.lib.AgeCheqServerInterface;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;

public class MainActivity extends Activity implements AgeCheqServerInterface {

	
	//global variables
	private String DevKey   = "06c3a8ba-8d2e-429c-9ce6-4f86a70815d6";
	private String AppId    = "1c1f66dd-bb5b-499b-bf61-b39eb02a1819";
	private String DeviceId = "";
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//set the view
		setContentView(R.layout.activity_main);
		
		//lock in portrait
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 	


	}

	
	public void check_Click(View view){
		
		doCheck();
		
	}
	

	
	public void infoBox(String message, String title) {
		
    	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
	    dlgAlert.setMessage(message);
	    dlgAlert.setTitle(title);              
	    dlgAlert.setPositiveButton("OK", null);
	    dlgAlert.setCancelable(true);
	    dlgAlert.create().show();
		
	}
	

	//----------------------------------------
	// AgeCheq Commands
	//----------------------------------------

	

	private void doCheck() {
		if (!DeviceId.equals("") )
		{
			AgeCheqApi.check(this, DevKey, DeviceId, AppId);


			
		}
		else
		{
			infoBox("There is no device ID","No Device ID");
		}
		
	}
	
	private void doAssociateData() {
		if (!DeviceId.equals("") )
		{
			//get the edit fields
			EditText txtDataKey = (EditText)findViewById(R.id.editText2);
			EditText txtDataValue = (EditText)findViewById(R.id.editText3);

            AgeCheqApi.associate(this,DevKey,AppId,"",txtDataValue);

		}
		else
		{
			infoBox("There is no device ID","No Device ID");
		}
		
	}

	
	//----------------------------------------
	// Response Handlers
	//----------------------------------------
    

    
    @Override
    public void onCheckResponse(String rtn, String rtnmsg, int checktype, 
			  Boolean deviceregistered, 
			  Boolean appauthorized,
			  Boolean appblocked,
			  int agecheq_parentverified,	  
			  Boolean agecheq_under13,
			  Boolean agecheq_under18,
			  Boolean agecheq_underdevage,
			  int agecheq_trials,
			  Boolean agegate_deviceregistered,
			  Boolean agegate_under13,	  
			  Boolean agegate_under18,
			  Boolean agegate_underdevage,
			  String associateddata)
    {

    	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
	    dlgAlert.setMessage("Check Response = " + rtn 
	    		+"- DeviceRegistered=" + deviceregistered.toString()
	    		+ ", AppAuthorized=" + appauthorized.toString() 
	    		+ ", AppBlocked=" + appblocked.toString()
	    		+ ", ParentVerified=" + Integer.toString(agecheq_parentverified)
	    		+ ", CheckType= " + Integer.toString(checktype)
	    		+ ", agecheq_under13=" + agecheq_under13.toString()
	    		+ ", agecheq_under18=" + agecheq_under18.toString()
	    		+ ", agecheq_underdevage=" + agecheq_underdevage.toString()
	    		+ ", agecheq_trials=" + Integer.toString(agecheq_trials)
	    		+ ", agegate_deviceregistered=" + agegate_deviceregistered.toString()
	    		+ ", agegate_under13=" + agegate_under13.toString()
	    		+ ", agegate_under18=" + agegate_under18.toString()
	    		+ ", agegate_underdevage=" + agegate_underdevage.toString()
	    		+ ", associateddata=" + associateddata
	    		);
	    dlgAlert.setTitle("CHECK");              
	    dlgAlert.setPositiveButton("OK", null);
	    dlgAlert.setCancelable(true);
	    dlgAlert.create().show();
    	
    } 
    
    @Override
    public void onAssociateDataResponse(String rtn, String rtnmsg) {
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
	    dlgAlert.setMessage("AssociateData Response = " + rtn +"-" + rtnmsg);
	    dlgAlert.setTitle("ASSOCIATEDATA");              
	    dlgAlert.setPositiveButton("OK", null);
	    dlgAlert.setCancelable(true);
	    dlgAlert.create().show();
    }    


    @Override
    public void onAgeCheqServerError(String paramString) {

    }
    
    

    

}
