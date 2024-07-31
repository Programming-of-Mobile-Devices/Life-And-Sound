package org.gmele.android.examples.a03lifeandsound;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener
{

    ImageButton BtStart;
    ImageButton BtStop;
    ImageButton BtPrev;
    ImageButton BtNext;
    TextView TvSong;
    Button BtRing;
    String[] SongTitles = {"Νοσταλγός του Rock N Roll", "Μάλιστα Κύριε", "Nothing Else Matters"};
    int[] SongIDs;
    int CurSong;
    MediaPlayer MP;
    SoundPool SoundP;
    int RingId;
    boolean Act;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.playlay);
        BtStart = (ImageButton) findViewById (R.id.BtStart);
        BtStop = (ImageButton) findViewById (R.id.BtStop);
        BtPrev = (ImageButton) findViewById (R.id.BtPrev);
        BtNext = (ImageButton) findViewById (R.id.BtNext);
        TvSong = (TextView) findViewById (R.id.TvSong);
        BtRing = (Button) findViewById (R.id.BtBeep);
        BtStart.setOnClickListener (this);
        BtStop.setOnClickListener (this);
        BtPrev.setOnClickListener (this);
        BtNext.setOnClickListener (this);
        BtRing.setOnClickListener (this);
        SongIDs = new int[3];
        SongIDs[0] = R.raw.nostalgosrocknroll;
        SongIDs[1] = R.raw.malistakyrie;
        SongIDs[2] = R.raw.nothingelsematters;
        CurSong = 0;
        TvSong.setText (SongTitles[CurSong]);
        MP = MediaPlayer.create (this, SongIDs[CurSong]);
        MP.setOnCompletionListener (this);
        Act = false;
        SoundP = new SoundPool.Builder ().setMaxStreams (3).build();
        RingId = SoundP.load (this, R.raw.ringbell, 1);

    }

    @Override
    public void onClick (View v)
    {
        if (v == BtStart && !Act)
            DoStart ();
        else
            if (v == BtStart && Act)
                DoPause ();
            else
                if (v == BtStop)
                    DoStop ();
                else
                    if (v == BtPrev)
                        DoPrev ();
                    else
                        if (v == BtNext)
                            DoNext ();
                        else
                            if (v == BtRing)
                                SoundP.play (RingId, 0.5f, 0.5f, 1, 0, 1);
    }

    void DoStart ()
    {
        ShowMessage ("DoStart...");
        Act = true;
        MP.start ();
        BtStart.setImageResource (R.drawable.pauseb);
    }

    void DoPause ()
    {
        ShowMessage ("DoPause");
        Act = false;
        MP.pause ();
        BtStart.setImageResource (R.drawable.playb);
    }

    void DoStop ()
    {
        ShowMessage ("DoStop");
        Act = false;
        MP.stop ();
        try
        {
            MP.prepare ();
        }
        catch (IOException E)
        {
            ShowMessage ("Error in repreparing: " + E.getMessage ());
        }
        BtStart.setImageResource (R.drawable.playb);
    }

    void DoNext ()
    {
        ShowMessage ("DoNext....");
        if (MP.isPlaying ())
            MP.stop ();
        if (++CurSong == 3)
            CurSong = 0;
        TvSong.setText (SongTitles[CurSong]);
        MP = MediaPlayer.create (this, SongIDs[CurSong]);
        MP.setOnCompletionListener (this);
        if (Act)
            DoStart ();
    }

    void DoPrev ()
    {
        ShowMessage ("DoPrev");
        if (MP.isPlaying ())
            MP.stop ();
        if (--CurSong == -1)
            CurSong = 2;
        TvSong.setText (SongTitles[CurSong]);
        MP = MediaPlayer.create (this, SongIDs[CurSong]);
        if (Act)
            DoStart ();
    }

    void ShowMessage (String Mess)
    {
        Toast Tst = Toast.makeText (this, Mess, Toast.LENGTH_LONG);
        Tst.setGravity (Gravity.CENTER, 0, 0);
        Tst.show ();
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        ShowMessage (SongTitles[CurSong] + " Completed..." );
        DoNext ();
    }
}
