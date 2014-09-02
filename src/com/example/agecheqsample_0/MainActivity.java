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

import com.agecheq.lib.AgeCheqApi;
import com.agecheq.lib.AgeCheqServerInterface;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;

public class MainActivity extends Activity implements AgeCheqServerInterface {

	
	//global variables
	private String DevKey   = "06c3a8ba-8d2e-429c-9ce6-4f86a70815d6";
	private String AppId    = "21cdc227-48ad-4cf5-a67c-92f00a3dbef7";
	private String DeviceId = "";
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//set the view
		setContentView(R.layout.activity_main);
		
		//lock in portrait
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 	

		//get that device id
	    new GetDeviceId().execute("");
		
	}
	
	public void isRegistered_Click(View view) {
		doIsRegistered();
	}
	
	public void register_Click(View view) {
		doRegister();
	}
	
	public void check_Click(View view){
		
		doCheck();
		
	}
	
	public void agegate_Click(View view) {
		
		doAgeGateData();
	}
	
	public void associateData_Click(View view) {
		
		doAssociateData();
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
	private void doIsRegistered() {

		if (!DeviceId.equals("") )
		{
			AgeCheqApi.isRegistered(this, DevKey, DeviceId);
		}
		else
		{
			infoBox("There is no device ID","No Device ID");
		}
	}
	
	private void doRegister() {
			
		if (!DeviceId.equals("") )
		{
			//get the edit field
			EditText editText = (EditText)findViewById(R.id.editText1);
			
			//the edit field used to register
			if (!editText.getText().toString().equals("")) {
				
				//the username of the parent dashboard account 
				String ParentID = editText.getText().toString();
				
				//register the device passing the AgeCheq Parent 
				AgeCheqApi.register(this, DevKey, DeviceId, "New Android Device", ParentID);
			}
		}
		else
		{
			infoBox("There is no device ID","No Device ID");
		}
		
	}
	
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
			
			AgeCheqApi.associateData(this, DevKey, DeviceId, AppId, txtDataKey.getText().toString(), txtDataValue.getText().toString());
			
		}
		else
		{
			infoBox("There is no device ID","No Device ID");
		}
		
	}
	
	private void doAgeGateData() {
		if (!DeviceId.equals("") )
		{
			//get the edit fields
			EditText txtYear = (EditText)findViewById(R.id.editText4);
			EditText txtMonth = (EditText)findViewById(R.id.editText5);
			EditText txtDay = (EditText)findViewById(R.id.editText6);
			
			AgeCheqApi.agegate(this, DevKey, DeviceId, txtYear.getText().toString(), txtMonth.getText().toString(), txtDay.getText().toString());
			
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
    public void onIsRegisteredResponse(String rtn, String rtnmsg, Boolean agecheq_deviceregistered,Boolean agegate_deviceregistered) {

		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
	    dlgAlert.setMessage("isRegistered Response = " + rtn +"  message=" + rtnmsg + " - " + "  agecheq_deviceregistered=" + 
	    		agecheq_deviceregistered.toString() + "  agegate_deviceregistered=" + agegate_deviceregistered.toString() );
	    dlgAlert.setTitle("ISREGISTERED");              
	    dlgAlert.setPositiveButton("OK", null);
	    dlgAlert.setCancelable(true);
	    dlgAlert.create().show();
    }
    
    @Override
    public void onRegisterResponse(String rtn, String rtnmsg) {

    	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
	    dlgAlert.setMessage("Register Response = " + rtn +"- message=" + rtnmsg );
	    dlgAlert.setTitle("REGISTER");              
	    dlgAlert.setPositiveButton("OK", null);
	    dlgAlert.setCancelable(true);
	    dlgAlert.create().show();
	    
    }    
    
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
    public void onAgegateResponse(String rtn, String rtnmsg) {
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
	    dlgAlert.setMessage("Agegate Response = " + rtn +"-" + rtnmsg);
	    dlgAlert.setTitle("AGEGATE");              
	    dlgAlert.setPositiveButton("OK", null);
	    dlgAlert.setCancelable(true);
	    dlgAlert.create().show();
    }
    
    @Override
    public void onAgeCheqServerError(String paramString) {

    }
    
    
	
	//-----------------------------------------------
    // Get Device ID
    //-----------------------------------------------
    private class GetDeviceId extends AsyncTask<String, Void, String> {
    	
    	@Override
    	protected String doInBackground(String... params) {
    		    	
    		Context c = getApplicationContext();
    		
    		Info adInfo = null;

    		try {
    			adInfo = AdvertisingIdClient.getAdvertisingIdInfo(c);
    		}
    		catch (Exception ex) {
    			Log.d("zz", "Exception: " + ex.getMessage());
    		}
    		
    		String id = adInfo.getId();
    		return id;
    	}
    	
    	@Override
    	protected void onPostExecute(String result) {
    		DeviceId = result;
    	}
    	
    	
    }
    

}
