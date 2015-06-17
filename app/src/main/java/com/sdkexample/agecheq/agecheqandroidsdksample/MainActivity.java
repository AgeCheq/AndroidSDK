package com.sdkexample.agecheq.agecheqandroidsdksample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.agecheq.agecheqlib.AgeCheqApi;

public class MainActivity extends Activity  {

    //global variables
    private String DevKey   = "ENTER_YOUR_DEVELOPER_KEY";
    private String AppId    = "ENTER_YOUR_APP_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void check_Click(View view){

        doCheck();

    }

    public void associate_Click(View view){

        doAssociate();

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

        //get the edit field
        EditText editText = (EditText)findViewById(R.id.txtAgeCheqPIN);

        if (!editText.getText().toString().equals("")) {

            //call the check API call
            AgeCheqApi.check(this,DevKey, AppId, editText.getText().toString(), new AgeCheqApi.OnCheckReturnListener() {

                @Override
                public void onCheckReturn(String rtn, String rtnmsg, int checkType, boolean appAuthorized, boolean appBlocked, int parentVerified, boolean under13, boolean under18, boolean underDevAge, int trials) {
                    Log.d("dbgAgeCheq","RETURN FROM CHECK COMMAND: " + rtn);
                    Log.d("dbgAgeCheq", "Authorized=" + appAuthorized + " | Blocked=" + appBlocked);

                    //strings to pass to UI
                    final String strMessage = "Authorized=" + appAuthorized + " | Blocked=" + appBlocked;
                    final String strTitle = "Check Command Response";

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            MainActivity.this.infoBox(strMessage,strTitle);
                        }
                    });


                }


            });


        }
        else
        {
            infoBox("Please enter a valid AgeCheq PIN","No AgeCheq PIN");
        }


    }


    private void doAssociate() {

        //get the edit field
        EditText editText = (EditText)findViewById(R.id.txtAgeCheqPIN);
        EditText dataText = (EditText)findViewById(R.id.txtAssociatedData);

        if (!editText.getText().toString().equals("")) {

            AgeCheqApi.associate(this, DevKey, AppId, editText.getText().toString(), dataText.getText().toString(), new AgeCheqApi.OnAssociateReturnListener() {
                @Override
                public void onAssociateReturn(String rtn, String rtnmsg) {
                    Log.d("dbgAgeCheq", "RETURN FROM ASSOCIATE COMMAND: " + rtn);

                    //strings to pass to UI
                    final String strMessage = "RETURN FROM ASSOCIATE COMMAND: " + rtn;
                    final String strTitle = "Associate Command Response";

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            MainActivity.this.infoBox(strMessage, strTitle);
                        }
                    });
                }

            });

        }
        else
        {
            infoBox("Please enter a valid AgeCheq PIN","No AgeCheq PIN");
        }



    }




}
