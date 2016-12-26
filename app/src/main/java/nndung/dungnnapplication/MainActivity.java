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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import nndung.dungnnapplication.entity.*;
import nndung.dungnnapplication.entity.Region;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetData().execute();
        new Login().execute("");
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
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

    class Login extends AsyncTask<String, Integer, Token> {

        @Override
        protected Token doInBackground(String... params) {
            String urlInput = "http://ship9k.com/oauth/token";
            try {
                URL url = new URL(urlInput);
                HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
                String data = "grant_type=password&username=0983007974&password=123456";
                urlConnect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnect.setRequestMethod("POST");
                OutputStreamWriter writer = new OutputStreamWriter(urlConnect.getOutputStream());
                writer.write(data);
                writer.flush();
                //urlConnect.setRequestProperty("Content-type", "Application/JSON");
                int i = urlConnect.getResponseCode();

                System.out.println(i);


                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlConnect.getInputStream()));
                String line = in.readLine();
                Token token = new Gson().fromJson(line, Token.class);
                in.close();
                return token;

            } catch (Exception ex) {
                System.out.print("demo");

                System.out.println(ex.getMessage());
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Token s) {
            super.onPostExecute(s);
            if(s != null) {

            }
        }
    }

}

