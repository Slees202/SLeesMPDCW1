//Stuart Lees S1821982
package org.stuart.gcu.cw1;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawDataDisplay;
    private Button startButton;
    private String result = "";
    private String url1="";
    private SitesAdapter mAdapter;
    private IncidentsList incidentsList;
    // Traffic Scotland Planned Roadworks XML link
    private String urlSource="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
// Set up the raw links to the graphical components
        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        Log.e("MyTag","after startButton");


        sitesList = (ListView)findViewById(R.id.IncidentsList);


        sitesList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
                String url = mAdapter.getItem(pos).getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url))
    }
    public void startProgress()
    {
// Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //
    @Override
    public void onClick(View v)
    {
        Log.e("MyTag","in onClick");
        startProgress();
        Log.e("MyTag","after startProgress");
    }
    // Need separate thread to access the internet resource over network
// Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;
        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            Log.e("MyTag","in run");
            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new
                        InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");
//
// Now read the data. Make sure that there are no specific
//headers
// in the data file that you need to ignore.
// The useful data that you need is in each of the itementries
//
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);
                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }
//
// Now that you have the xml data you can parse it
//
// Now update the TextView to display raw XML data
// Probably not the best way to update TextView
// but we are just getting started !
            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    rawDataDisplay.setText(result);
                }
            });


            if(!isNetworkAvailable()==true)
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).show();
            }
            else if(isNetworkAvailable()==true)
            {
                Toast.makeText(MainActivity.this,
                        "Welcome", Toast.LENGTH_LONG).show();
            }

        }

        public boolean isNetworkAvailable() {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {


                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                    if (capabilities != null) {
                        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                            return true;
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                            return true;
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {

                            return true;
                        }
                    }
                }
            }

            return false;

        }


}