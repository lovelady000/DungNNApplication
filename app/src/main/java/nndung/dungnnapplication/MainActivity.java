package nndung.dungnnapplication;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import nndung.dungnnapplication.entity.Region;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetData().execute();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    class GetData extends AsyncTask {
        @Override
        protected List<Region> doInBackground(Object[] params) {
            String urlInput = "http://ship9k.com/api/region/getallnopaging";
            try {
                URL url = new URL(urlInput);
                HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();

                urlConnect.setRequestProperty("Content-length", "0");
                urlConnect.setRequestMethod("GET");
                urlConnect.setRequestProperty("Content-type", "Application/JSON");

                int i = urlConnect.getResponseCode();

                System.out.println(i);


                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlConnect.getInputStream()));
                TypeToken<List<Region>> typeToken = new TypeToken<List<Region>>() {
                };
                List<Region> region = new Gson().fromJson(in.readLine(), typeToken.getType());
                in.close();
                return region;

            } catch (Exception ex) {
                System.out.print("demo");

                System.out.println(ex.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(Object o) {
            List<Region> lstRegion = (List<Region>) o;
            String arr1[] = new String[lstRegion.size()];
            int i = 0;
            for (Region item : lstRegion) {
                arr1[i] = item.getName();
                i++;
            }

            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>
                    (
                            MainActivity.this,
                            android.R.layout.simple_spinner_item,
                            arr1
                    );
            adapter.setDropDownViewResource
                    (android.R.layout.simple_list_item_single_choice);
            spinner.setAdapter(adapter);
            //spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,lstRegion));
        }
    }
}

