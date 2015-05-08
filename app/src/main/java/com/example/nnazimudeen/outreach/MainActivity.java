package com.example.nnazimudeen.outreach;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.TextView;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends ListActivity {

   // public final static int EXTRA_MESSAGE = "com.example.nnazimudeen.outreach.MESSAGE";
    public final static Drawable IMAGE = null;

    Intent menu = null;
    BufferedReader bReader = null;
    static JSONArray quesList = null;
  //  public final static SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        ListView MainActivity_LV = getListView();

        final SharedPreferences prefs = this.getSharedPreferences(
                "com.example.nnaimudeen.outreach", Context.MODE_PRIVATE);
        String uName = prefs.getString("com.example.nnaimudeen.outreach.uname", null);
        if(uName != null){
            getActionBar().setTitle("Categories" );
//       // getActionBar().setTitle("Hello "+value);
           // getActionBar().setSubtitle("Categories");
        }
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        alertDialog.setTitle("eBay Quiz");

        //alertDialog.setMessage("Answered out of ");
        alertDialog.setCanceledOnTouchOutside(false);

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setId(R.id.UserName);
        input.setHeight(150);
        input.setHint("User Name");
//        input.setMaxWidth(10);
//        input.setPadding(200,20,20,20);
        alertDialog.setView(input);

        alertDialog.setButton( DialogInterface.BUTTON_POSITIVE,"Log On", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
//
//               // setTitle(value);
                getActionBar().setTitle("Categories");
//               // getActionBar().setTitle("Hello "+value);
                //getActionBar().setSubtitle("Categories");

                prefs.edit().putString("com.example.nnaimudeen.outreach.uname", value).apply();
//                getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C3539")) );

                // Do something with value!

            }
        });

//        alertDialog.setButton( DialogInterface.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                // Canceled.
//            }
//        });

        if(uName == null) {
            alertDialog.show();
        }
        ListAdapter adapter = new ListAdapter(this, generateData());
        try {
            if(quesList == null) {
                loadQuestions();
                //loadQuestionsFromSvc();
            }
        } catch (Exception e){

        }


        MainActivity_LV.setAdapter(adapter);
        MainActivity_LV.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        // Do something in response to button

                        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);

                        String message = generateData().get(position).getName();// get the value from employeIdArray which corrosponds to the 'position' of the selected row

                        intent.putExtra("position", position);
                        //intent.putExtra(IMAGE, generateData().get(position).getImage());
                        startActivity(intent);

                    }
                }
        );
        setListAdapter(adapter);
    }


    public ArrayList<Item> generateData()
    {
        ArrayList<Item> items = new ArrayList<Item>();

        Bitmap gbxImg = BitmapFactory.decodeResource(getResources(), R.drawable.gbx);
        Bitmap gbxImg1  = Bitmap.createScaledBitmap(gbxImg, 300, 400, true);
        Bitmap l10nImg = BitmapFactory.decodeResource(getResources(), R.drawable.i10n);
        Bitmap l10nImg1  = Bitmap.createScaledBitmap(l10nImg, 300, 400, true);
        Bitmap i18nImg = BitmapFactory.decodeResource(getResources(), R.drawable.i18n);
        Bitmap i18nImg1  = Bitmap.createScaledBitmap(i18nImg, 300, 400, true);
        Bitmap mtImg = BitmapFactory.decodeResource(getResources(), R.drawable.mt);
        Bitmap mtImg1  = Bitmap.createScaledBitmap(mtImg, 300, 400, true);

        items.add(new Item("GBX", new BitmapDrawable(getResources(), gbxImg1)));
        items.add(new Item("L10N", new BitmapDrawable(getResources(), l10nImg1)));
        items.add(new Item("I18N",new BitmapDrawable(getResources(), i18nImg1)));
        items.add(new Item("MT",new BitmapDrawable(getResources(), mtImg1)));
        return items;
    }

    /** Called when the user clicks the Send button */
//    public void sendMessage(View view) {
//        try {
//            loadQuestions();
//        } catch (Exception e){
//
//        }
//        // Do something in response to button
//       Intent intent = new Intent(this, QuestionActivity.class);
//        TextView labelView = (TextView) findViewById(R.id.ActivityName);
//        String message = labelView.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//       startActivity(intent);
//    }

    private void loadQuestions() throws Exception {
        try {
            InputStream questions = this.getBaseContext().getResources()
                    .openRawResource(R.raw.questions);
            bReader = new BufferedReader(new InputStreamReader(questions));
            StringBuilder quesString = new StringBuilder();
            String aJsonLine = null;
            while ((aJsonLine = bReader.readLine()) != null) {
                quesString.append(aJsonLine);
            }
            //Log.d(this.getClass().toString(), quesString.toString());
            JSONObject quesObj = new JSONObject(quesString.toString());
            quesList = quesObj.getJSONArray("Questions");
            //Log.d(this.getClass().getName(),
           //         "Num Questions " + quesList.length());
        } catch (Exception e){

        } finally {
            try {
                bReader.close();
            } catch (Exception e) {
                Log.e("", e.getMessage().toString(), e.getCause());
            }

        }


    }

    private void loadQuestionsFromSvc() throws Exception {
        try {
            String queryString = GET("http://10.254.185.109:8080/SampleRestfulService/questions/gbh");
            //Log.d(this.getClass().toString(), quesString.toString());
            JSONObject quesObj = new JSONObject(queryString);
            quesList = quesObj.getJSONArray("Questions");
            //Log.d(this.getClass().getName(),
            //         "Num Questions " + quesList.length());
        } catch (Exception e){

        } finally {
            try {
                bReader.close();
            } catch (Exception e) {
                Log.e("", e.getMessage().toString(), e.getCause());
            }

        }


    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private void setTitle(String userName) {

        Intent intent = getIntent();
        this.setTitle(userName);
    }

    public static JSONArray getQuesList() {
        return quesList;
    }
}
