package com.example.nnazimudeen.outreach;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog.Builder;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;

import android.util.Log;
import android.view.View.OnClickListener;
import android.view.Window;

import android.view.View;
import android.util.Log;
import android.widget.TextView;

public class QuestionActivity extends Activity {
    /** Called when the activity is first created. */

    EditText question = null;
    RadioButton answer1 = null;
    RadioButton answer2 = null;
    RadioButton answer3 = null;
    RadioButton answer4 = null;
    RadioGroup answers = null;
    Button finish = null;
    int selectedAnswer = -1;
    int quesIndex = 0;
    int numEvents = 0;
    int selected[] = null;
    int correctAns[] = null;
    long score12 = 0;
    long timeLeft = 0;
    boolean review =false;
    Button prev, next = null;
    CountDownTimer mCountDownTimer = startCountDownTimer();
    boolean isCounterRunning  = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        TableLayout quizLayout = (TableLayout) findViewById(R.id.quizLayout);
        quizLayout.setVisibility(android.view.View.INVISIBLE);

        try {
            question = (EditText) findViewById(R.id.question);

            answer1 = (RadioButton) findViewById(R.id.a0);
            answer2 = (RadioButton) findViewById(R.id.a1);
            answer3 = (RadioButton) findViewById(R.id.a2);
            answer4 = (RadioButton) findViewById(R.id.a3);
            answers = (RadioGroup) findViewById(R.id.answers);
            RadioGroup questionLayout = (RadioGroup)findViewById(R.id.answers);
            //Button finish = (Button)findViewById(R.id.finish);
           // finish.setOnClickListener(finishListener);
            //questionLayout.setOnClickListener(nextListener1);
            questionLayout.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                      if(answer1.isChecked() || answer2.isChecked() || answer3.isChecked() || answer4.isChecked()) {
                          setAnswer();
                          quesIndex++;
                          if (quesIndex >= MainActivity.getQuesList().length())
                              quesIndex = MainActivity.getQuesList().length() - 1;

                          showQuestion(quesIndex, false);
                      }

                  }
              }
            );

            prev = (Button)findViewById(R.id.Prev);
            prev.setOnClickListener(prevListener);
            prev.setVisibility( View.GONE);
            next = (Button)findViewById(R.id.Next);
            next.setOnClickListener(nextListener);
            next.setVisibility( View.GONE);


            selected = new int[MainActivity.getQuesList().length()];
            java.util.Arrays.fill(selected, -1);
            correctAns = new int[MainActivity.getQuesList().length()];
            java.util.Arrays.fill(correctAns, -1);

            this.showQuestion(0, false);
            quizLayout.setVisibility(android.view.View.VISIBLE);




//            ProgressBar pb = (ProgressBar)findViewById(R.id.progressBarToday);
//
//            Animation an = new RotateAnimation(0.0f, 90.0f, 250f, 273f);
//            an.setFillAfter(true);
//            pb.startAnimation(an);

        } catch (Exception e) {
            Log.e("", e.getMessage().toString(), e.getCause());
        }

    }

    private CountDownTimer startCountDownTimer() {
       return new CountDownTimer(11000, 1000) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                TextView timer = (TextView) findViewById(R.id.timer);
                TextView score = (TextView) findViewById(R.id.score);
                score.setText("Score: "+score12+" pts");
                if(leftTimeInMilliseconds < 1000){
                    timer.setText("Time Left: " + 0+"s");
                }else {
                    timer.setText("Time Left: " + leftTimeInMilliseconds / 1000 +"s");
                }
                timeLeft = leftTimeInMilliseconds/1000;
            }


            @Override
            public void onFinish() {
                TextView timer = (TextView) findViewById(R.id.timer);
                // if(timer.getText().toString().equalsIgnoreCase("0")) {
                timer.setText("Time Left: " + 0 +"s");


                int score = 0;
                for (int i = 0; i < correctAns.length; i++) {
                    if ((correctAns[i] != -1) && (correctAns[i] == selected[i]))
                        score++;
                }

                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(QuestionActivity.this).create();
                alertDialog.setTitle("Score: "+score12+" pts");
                alertDialog.setMessage("Answered "+(score) + " out of " + (MainActivity.getQuesList().length()));

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Review", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        review = true;
                        QuestionActivity.this.quesIndex = 0;
                        QuestionActivity.this.showQuestion(0, true);
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Score Board", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(QuestionActivity.this, ScoreBoardActivity.class);



                        intent.putExtra("title", getActionBar().getTitle().toString());

                        intent.putExtra("score", String.valueOf(score12));

                        startActivity(intent);
                       // review = true;
                      //  quesIndex = 0;
                     //   QuestionActivity.this.showQuestion(0);
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Home", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                       // review = false;
                        finish();
                    }
                });
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                isCounterRunning  = false;
            }
            // }
        };
    }


    private void showQuestion(int qIndex, boolean review) {
        try {
            JSONObject aQues = MainActivity.getQuesList().getJSONObject(qIndex);
            String quesValue = aQues.getString("Question");
            if (correctAns[qIndex] == -1) {
                String correctAnsStr = aQues.getString("CorrectAnswer");
                correctAns[qIndex] = Integer.parseInt(correctAnsStr);
            }

            question.setText(quesValue.toCharArray(), 0, quesValue.length());
            answers.check(-1);
            answer1.setTextColor(Color.BLACK);
            answer2.setTextColor(Color.BLACK);
            answer3.setTextColor(Color.BLACK);
            answer4.setTextColor(Color.BLACK);
            JSONArray ansList = aQues.getJSONArray("Answers");
            String aAns = ansList.getJSONObject(0).getString("Answer");
            answer1.setText(aAns.toCharArray(), 0, aAns.length());
            aAns = ansList.getJSONObject(1).getString("Answer");
            answer2.setText(aAns.toCharArray(), 0, aAns.length());
            aAns = ansList.getJSONObject(2).getString("Answer");
            answer3.setText(aAns.toCharArray(), 0, aAns.length());
            aAns = ansList.getJSONObject(3).getString("Answer");
            answer4.setText(aAns.toCharArray(), 0, aAns.length());
//            Log.d("",selected[qIndex]+"");
            if (selected[qIndex] == 0)
                answers.check(R.id.a0);
            if (selected[qIndex] == 1)
                answers.check(R.id.a1);
            if (selected[qIndex] == 2)
                answers.check(R.id.a2);
            if (selected[qIndex] == 3)
                answers.check(R.id.a3);

            setScoreTitle();
            if(review) {
                TextView timer = (TextView) findViewById(R.id.timer);
                timer.setVisibility( View.GONE);
                TextView score = (TextView) findViewById(R.id.score);
                score.setVisibility( View.GONE);

                prev = (Button)findViewById(R.id.Prev);
                prev.setVisibility( View.VISIBLE);
                next = (Button)findViewById(R.id.Next);
                next.setVisibility( View.VISIBLE);
                if (quesIndex == (MainActivity.getQuesList().length() - 1))
                    next.setEnabled(false);

                if (quesIndex == 0)
                    prev.setEnabled(false);

                if (quesIndex > 0)
                    prev.setEnabled(true);

                if (quesIndex < (MainActivity.getQuesList().length() - 1))
                    next.setEnabled(true);
            }
//
//
            if (review) {
                if (selected[qIndex] != correctAns[qIndex]) {
                    if (selected[qIndex] == 0)
                        answer1.setTextColor(Color.RED);
                    if (selected[qIndex] == 1)
                        answer2.setTextColor(Color.RED);
                    if (selected[qIndex] == 2)
                        answer3.setTextColor(Color.RED);
                    if (selected[qIndex] == 3)
                        answer4.setTextColor(Color.RED);
                }
                if (correctAns[qIndex] == 0) {
                    answer1.setTextColor(Color.parseColor("#3ADF00"));
                    //answer1.setTextSize(30);
                }
                if (correctAns[qIndex] == 1) {
                    answer2.setTextColor(Color.parseColor("#3ADF00"));
                   // answer2.setTextSize(30);
                }
                if (correctAns[qIndex] == 2) {
                    answer3.setTextColor(Color.parseColor("#3ADF00"));
                   // answer3.setTextSize(30);
                }
                if (correctAns[qIndex] == 3) {
                    answer4.setTextColor(Color.parseColor("#3ADF00"));
                   // answer4.setTextSize(30);
                }
           }
            if(review){
                isCounterRunning = false;
                mCountDownTimer.cancel();
            }
            if(!review) {
                if (!isCounterRunning) {
                    isCounterRunning = true;
                    mCountDownTimer.start();
                } else {
                    mCountDownTimer.cancel(); // cancel
                    mCountDownTimer.start();  // then restart
                }

            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage(), e.getCause());
        }
    }


//    private OnClickListener finishListener = new OnClickListener() {
//        public void onClick(View v) {
//            setAnswer();
//            //Calculate Score
//            int score = 0;
//            for(int i=0; i<correctAns.length; i++){
//                if ((correctAns[i] != -1) && (correctAns[i] == selected[i]))
//                    score++;
//            }
//            AlertDialog alertDialog;
//            alertDialog = new AlertDialog.Builder(QuestionActivity.this).create();
//            alertDialog.setTitle("Score");
//            alertDialog.setMessage((score) +" out of " + (MainActivity.getQuesList().length()));
//
////            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Retake", new DialogInterface.OnClickListener(){
////
////                public void onClick(DialogInterface dialog, int which) {
////                   // review = false;
////                    quesIndex=0;
////                    QuestionActivity.this.showQuestion(0);
////                }
////            });
//
//            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Review", new DialogInterface.OnClickListener(){
//
//                public void onClick(DialogInterface dialog, int which) {
//                  //  review = true;
//                    quesIndex=0;
//                    QuestionActivity.this.showQuestion(0);
//                }
//            });
//
//            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Quit", new DialogInterface.OnClickListener(){
//
//                public void onClick(DialogInterface dialog, int which) {
//                   // review = false;
//                    finish();
//                }
//            });
//
//            alertDialog.show();
//
//        }
//    };

    private void setAnswer() {
        if (answer1.isChecked())
            selected[quesIndex] = 0;
        if (answer2.isChecked())
            selected[quesIndex] = 1;
        if (answer3.isChecked())
            selected[quesIndex] = 2;
        if (answer4.isChecked())
            selected[quesIndex] = 3;

//        Log.d("",Arrays.toString(selected));
//        Log.d("",Arrays.toString(correctAns));


        if ((correctAns[quesIndex] != -1) && (correctAns[quesIndex] == selected[quesIndex]))
            score12 = score12 + timeLeft;


    }

    private OnClickListener nextListener = new OnClickListener() {
        public void onClick(View v) {
            setAnswer();
            quesIndex++;
            if (quesIndex >= MainActivity.getQuesList().length())
                quesIndex = MainActivity.getQuesList().length() - 1;

            showQuestion(quesIndex, true);
        }
    };
//
//    private OnClickListener nextListener1 = new OnClickListener() {
//        public void onClick(View v) {
//            setAnswer();
//            quesIndex++;
//            if (quesIndex >= MainActivity.getQuesList().length())
//                quesIndex = MainActivity.getQuesList().length() - 1;
//
//            showQuestion(quesIndex);
//        }
//    };
//
//
//
    private OnClickListener prevListener = new OnClickListener() {
        public void onClick(View v) {
            setAnswer();
            quesIndex--;
            if (quesIndex < 0)
                quesIndex = 0;

            showQuestion(quesIndex, true);
        }
    };

    private void setScoreTitle() {
         Intent intent = getIntent();
        int message = intent.getIntExtra("position", 0);
        this.getActionBar().setIcon(generateData().get(message).getImage());
        this.setTitle(generateData().get(message).getName()+ "       " + (quesIndex+1)+ "/" + MainActivity.getQuesList().length());
      //  this.getActionBar().setSubtitle("Answered     " + (quesIndex)+ "/" + MainActivity.getQuesList().length());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
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

}