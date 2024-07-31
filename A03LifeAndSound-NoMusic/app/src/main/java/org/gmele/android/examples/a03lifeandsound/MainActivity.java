package org.gmele.android.examples.a03lifeandsound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText EtSomeText;
    EditText EtMultiLine;
    Button BtNextAct;
    int EventCount;
    String Messages;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        EtSomeText = (EditText) findViewById (R.id.EtSomeText);
        EtMultiLine = (EditText) findViewById (R.id.EtMultiline);
        BtNextAct = (Button) findViewById (R.id.BtNext);
        BtNextAct.setOnClickListener (this);
        if (savedInstanceState == null)
        {
            System.out.println ("*** OnCreate Null***");
            EventCount = 0;
            Messages = "-------------";
        }
        else
        {
            System.out.println ("*** OnCreate ***");
            EventCount = savedInstanceState.getInt ("Counter");
            Messages = "-------------\n" + savedInstanceState.getString ("Msg");
        }
        ShowMessage ("On Create...");

    }

    @Override
    protected void onStart ()
    {
        super.onStart ();
        ShowMessage ("On Start...");
    }

    @Override
    protected void onRestart ()
    {
        super.onRestart ();
        ShowMessage ("On ReStart...");
    }

    @Override
    protected void onResume ()
    {
        super.onResume ();
        ShowMessage ("On Resume...");
    }

    @Override
    protected void onPause ()
    {
        super.onPause ();      //Πρέπει να κληθεί; Αν ναι πριν ή μετά τις υπόλοιπες εντολές;
        ShowMessage ("On Pause...");
    }

    @Override
    protected void onStop ()
    {
        super.onStop ();
        ShowMessage ("On Stop...");
    }

    @Override
    protected void onDestroy ()
    {
        super.onDestroy ();
        ShowMessage ("On Destroy...");
    }

    @Override
    protected void onSaveInstanceState (Bundle Bun)
    {
        super.onSaveInstanceState (Bun);
        ShowMessage ("On Save Instance State");
        Bun.putString ("Msg", Messages);
        Bun.putInt ("Counter", EventCount);
    }


    /*
    @Override
    protected void onRestoreInstanceState (Bundle Bun)
    {
        super.onRestoreInstanceState (Bun);
        System.out.println ("*** OnRestore ***");
        Messages = Bun.getString ("Msg");
        EventCount = Bun.getInt ("Counter");
        ShowMessage ("On Restore Instance State");
    }
    */



    @Override
    public void onBackPressed ()
    {
        finish ();
        ShowMessage ("Pressed Back");
    }

    void ShowMessage (String Mess)
    {
        Mess = String.format ("[%4d] %s\n", ++EventCount, Mess);
        Messages = Mess + Messages;
        EtMultiLine.setText (Messages);
        Toast Tst = Toast.makeText (this, Mess, Toast.LENGTH_SHORT);
        Tst.setGravity (Gravity.CENTER, 0, 0);
        Tst.show ();
    }


    @Override
    public void onClick (View v)
    {
        Intent In = new Intent (this, PlayerActivity.class);
        startActivity (In);
    }
}
