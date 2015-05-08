package com.example.nnazimudeen.outreach;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ScoreBoardActivity extends ListActivity {
    ArrayList<Item> items = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score_board);
        ListView ScoreActivity_LV = getListView();
        getActionBar().setDisplayHomeAsUpEnabled(true);

//        final SharedPreferences prefs = this.getSharedPreferences(
//                "com.example.nnaimudeen.outreach", Context.MODE_PRIVATE);
//        String scores = prefs.getString("com.example.nnaimudeen.outreach.scores", null);
//
//        prefs.edit().putStringSet("com.example.nnaimudeen.outreach.scores", value).apply();

        Intent intent = getIntent();
        String message = intent.getStringExtra("title");
        if(message.contains("GBX")){
            message = "GBX";
            this.getActionBar().setIcon(generateImageData().get(0).getImage());
        }else if(message.contains("L10N")){
            message = "L10N";
            this.getActionBar().setIcon(generateImageData().get(1).getImage());
        }else if(message.contains("I18N")){
            message = "I18N";
            this.getActionBar().setIcon(generateImageData().get(2).getImage());
        }else if(message.contains("MT")){
            message = "MT";
            this.getActionBar().setIcon(generateImageData().get(3).getImage());
        }

//        final SharedPreferences prefs = this.getSharedPreferences(
//                "com.example.nnaimudeen.outreach", Context.MODE_PRIVATE);
//        String uName = prefs.getString("com.example.nnaimudeen.outreach.uname", null);
        getActionBar().setTitle(message+ " Score Board ");
//        String scor = intent.getStringExtra("score");
//       Score sc= new Score(uName,Integer.valueOf(scor));
//
//        generateData().add(sc);
         ScoreAdaptor adapter = new ScoreAdaptor(this, generateData());



        ScoreActivity_LV.setAdapter(adapter);

        setListAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_board, menu);
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


    public ArrayList<Score> generateData()
    {
        ArrayList<Score> scores = new ArrayList<Score>();



        scores.add(new Score("Niaz",90));
        scores.add(new Score("John",80));
        scores.add(new Score("Mike",87));
        scores.add(new Score("Zayd",73));
        return scores;
    }

    public ArrayList<Item> generateImageData()
    {


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
}
