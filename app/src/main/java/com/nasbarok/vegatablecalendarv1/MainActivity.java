package com.nasbarok.vegatablecalendarv1;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public VegetableCalendarDBHelper vegetableCalendarDB;
    private TableLayout mTableLayout;
    ProgressDialog mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = new ProgressDialog(this);
        mTableLayout = (TableLayout) findViewById(R.id.tableVegetables);
        mTableLayout.setStretchAllColumns(true);


        //initiates db
        InputStream inputStream = getResources().openRawResource(R.raw.vegetable_calendar_db);
        vegetableCalendarDB = new VegetableCalendarDBHelper(this.getApplicationContext());
        vegetableCalendarDB.createDB(inputStream);

        startLoadData();
    }

    public void startLoadData() {
        mProgressBar.setCancelable(false);
        mProgressBar.setMessage(getResources().getString(R.string.loading_msg));
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();
        new LoadDataTask().execute(0);
    }

/*    public void initTable() {
        TableLayout stk = (TableLayout) findViewById(R.id.calendar_table);
        List<VegetableCalendar> vegetableCalendars = vegetableCalendarDB.getVegetableCalendars();
        for (VegetableCalendar vegetableCalendar : vegetableCalendars) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(vegetableCalendar.getVegetableCalendarName());
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(vegetableCalendar.getVegetableCalendarSowing());
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(vegetableCalendar.getVegetableCalendarPlantations());
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText(vegetableCalendar.getVegetableCalendarHarvest());
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }*/

        public void loadData() {
            //List<VegetableCalendar> vegetableCalendars = vegetableCalendarDB.getVegetableCalendars();
            // getSupportActionBar().setTitle(getResources().getString(R.string.app_name) + "( " + String.valueOf(rows) + " )");
            int leftRowMargin = 0;
            int topRowMargin = 0;
            int rightRowMargin = 0;
            int bottomRowMargin = 0;
            int textSize = 0, smallTextSize = 0, mediumTextSize = 0;
            textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
            smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);
            mediumTextSize = (int)
                    getResources().getDimension(R.dimen.font_size_medium);

            List<VegetableCalendar> vegetableCalendars = vegetableCalendarDB.getVegetableCalendars();
            VegetableCalendar[] data = new VegetableCalendar[vegetableCalendars.size()];
            data = vegetableCalendars.toArray(data);

            int rows = data.length;
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name) + " (" + String.valueOf(rows) + ")");
            TextView textSpacer = null;
            mTableLayout.removeAllViews();
            // -1 means heading row
            for (int i = -1; i < rows; i++) {
                VegetableCalendar row = null;
                if (i > -1)
                    row = data[i];
                else {
                    textSpacer = new TextView(this);
                    textSpacer.setText("");
                }
                // data columns
                //tv vegetable name
                final TextView tv = new TextView(this);
                tv.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.LEFT);
                tv.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv.setText(getResources().getString(R.string.calendar_heading1));
                    tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv.setBackgroundColor(Color.parseColor("#f8f8f8"));
                    tv.setText(String.valueOf(row.getVegetableCalendarName()));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                //tv January
                final LinearLayout layJanuarySPH = new LinearLayout(this);
                layJanuarySPH.setOrientation(LinearLayout.VERTICAL);
                layJanuarySPH.setGravity(Gravity.RIGHT);
                layJanuarySPH.setPadding(0, 10, 0, 10);
                layJanuarySPH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                layJanuarySPH.setBackgroundColor(Color.parseColor("#ffffff"));

                final TextView tvSJanuary = new TextView(this);
                tvSJanuary.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvSJanuary.setPadding(5, 0, 1, 5);
                if (i == -1) {
                    tvSJanuary.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvSJanuary.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tvSJanuary.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tvSJanuary.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                tvSJanuary.setGravity(Gravity.LEFT);
                tvSJanuary.setPadding(5, 15, 0, 15);



                tvSJanuary.setGravity(Gravity.RIGHT);
                tvSJanuary.setBackgroundColor(Color.parseColor("#ffffff"));

                if (i == -1) {
                    tvSJanuary.setText(getResources().getString(R.string.calendar_heading2));
                    tvSJanuary.setBackgroundColor(Color.parseColor("#f7f7f7"));
                } else {
                    final TextView tvP = new TextView(this);
                    tvP.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvP.setPadding(5, 0, 1, 5);

                    tvP.setGravity(Gravity.RIGHT);
                    tvP.setBackgroundColor(Color.parseColor("#000000"));
                    laySPH.addView(tvP);

                    final TextView tvH = new TextView(this);
                    tvH.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvH.setPadding(5, 0, 1, 5);

                    tvH.setGravity(Gravity.RIGHT);
                    tvH.setBackgroundColor(Color.parseColor("#d9d9d9"));


                    //calendar_heading2 matching Sowing/Harvest/Plantation
                    tvS.setBackgroundColor(Color.parseColor("#ffffff"));
                    tvS.setTextColor(Color.parseColor("#000000"));
                    tvS.setText("toto");
                }
                laySPH.addView(tvS);

                //tv February
                final TextView tv3 = new TextView(this);
                if (i == -1) {
                    tv3.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv3.setPadding(5, 5, 0, 5);
                    tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv3.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv3.setPadding(5, 0, 0, 5);
                    tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                tv3.setGravity(Gravity.LEFT);
                tv3.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv3.setText(getResources().getString(R.string.calendar_heading3));
                    tv3.setBackgroundColor(Color.parseColor("#f0f0f0"));
                } else {
                    tv3.setBackgroundColor(Color.parseColor("#f8f8f8"));
                    tv3.setTextColor(Color.parseColor("#000000"));
                    tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tv3.setText("tata");
                }

                //tv March
                final TextView tv4 = new TextView(this);
                if (i == -1) {
                    tv4.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv4.setPadding(5, 5, 0, 5);
                } else {
                    tv4.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv4.setPadding(5, 0, 0, 5);
                }
                tv4.setGravity(Gravity.LEFT);
                tv4.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv4.setText(getResources().getString(R.string.calendar_heading4));
                    tv4.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv4.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv4.setTextColor(Color.parseColor("#000000"));
                    tv4.setText("titi");
                    tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }

                //tv April
                final TextView tv5 = new TextView(this);
                if (i == -1) {
                    tv5.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv5.setPadding(5, 5, 0, 5);
                } else {
                    tv5.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv5.setPadding(5, 0, 0, 5);
                }
                tv5.setGravity(Gravity.LEFT);
                tv5.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv5.setText(getResources().getString(R.string.calendar_heading5));
                    tv5.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv5.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv5.setTextColor(Color.parseColor("#000000"));
                    tv5.setText("tutu");
                    tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }

                //tv May
                final TextView tv6 = new TextView(this);
                if (i == -1) {
                    tv6.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv6.setPadding(5, 5, 0, 5);
                } else {
                    tv6.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv6.setPadding(5, 0, 0, 5);
                }
                tv6.setGravity(Gravity.LEFT);
                tv6.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv6.setText(getResources().getString(R.string.calendar_heading6));
                    tv6.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv6.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv6.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv6.setTextColor(Color.parseColor("#000000"));
                    tv6.setText("tutu");
                    tv6.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }

                //tv June
                final TextView tv7 = new TextView(this);
                if (i == -1) {
                    tv7.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv7.setPadding(5, 5, 0, 5);
                } else {
                    tv7.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv7.setPadding(5, 0, 0, 5);
                }
                tv7.setGravity(Gravity.LEFT);
                tv7.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv7.setText(getResources().getString(R.string.calendar_heading7));
                    tv7.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv7.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv7.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv7.setTextColor(Color.parseColor("#000000"));
                    tv7.setText("tutu");
                    tv7.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }

                //tv July
                final TextView tv8 = new TextView(this);
                if (i == -1) {
                    tv8.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv8.setPadding(5, 5, 0, 5);
                } else {
                    tv8.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv8.setPadding(5, 0, 0, 5);
                }
                tv8.setGravity(Gravity.LEFT);
                tv8.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv8.setText(getResources().getString(R.string.calendar_heading8));
                    tv8.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv8.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv8.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv8.setTextColor(Color.parseColor("#000000"));
                    tv8.setText("tutu");
                    tv8.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }

                //tv August
                final TextView tv9 = new TextView(this);
                if (i == -1) {
                    tv9.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv9.setPadding(5, 5, 0, 5);
                } else {
                    tv9.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv9.setPadding(5, 0, 0, 5);
                }
                tv9.setGravity(Gravity.LEFT);
                tv9.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv9.setText(getResources().getString(R.string.calendar_heading9));
                    tv9.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv9.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv9.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv9.setTextColor(Color.parseColor("#000000"));
                    tv9.setText("tutu");
                    tv9.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }

                //tv September
                final TextView tv10 = new TextView(this);
                if (i == -1) {
                    tv10.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv10.setPadding(5, 5, 0, 5);
                } else {
                    tv10.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv10.setPadding(5, 0, 0, 5);
                }
                tv10.setGravity(Gravity.LEFT);
                tv10.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv10.setText(getResources().getString(R.string.calendar_heading10));
                    tv10.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv10.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv10.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv10.setTextColor(Color.parseColor("#000000"));
                    tv10.setText("tutu");
                    tv10.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }

                //tv October
                final TextView tv11 = new TextView(this);
                if (i == -1) {
                    tv11.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv11.setPadding(5, 5, 0, 5);
                } else {
                    tv11.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv11.setPadding(5, 0, 0, 5);
                }
                tv11.setGravity(Gravity.LEFT);
                tv11.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv11.setText(getResources().getString(R.string.calendar_heading11));
                    tv11.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv11.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv11.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv11.setTextColor(Color.parseColor("#000000"));
                    tv11.setText("tutu");
                    tv11.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }

                //tv November
                final TextView tv12 = new TextView(this);
                if (i == -1) {
                    tv12.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv12.setPadding(5, 5, 0, 5);
                } else {
                    tv12.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv12.setPadding(5, 0, 0, 5);
                }
                tv12.setGravity(Gravity.LEFT);
                tv12.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv12.setText(getResources().getString(R.string.calendar_heading12));
                    tv12.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv12.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv12.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv12.setTextColor(Color.parseColor("#000000"));
                    tv12.setText("tutu");
                    tv12.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }

                //tv December
                final TextView tv13 = new TextView(this);
                if (i == -1) {
                    tv13.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv13.setPadding(5, 5, 0, 5);
                } else {
                    tv13.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv13.setPadding(5, 0, 0, 5);
                }
                tv13.setGravity(Gravity.LEFT);
                tv13.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv13.setText(getResources().getString(R.string.calendar_heading13));
                    tv13.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv13.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                } else {
                    tv13.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv13.setTextColor(Color.parseColor("#000000"));
                    tv13.setText("tutu");
                    tv13.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }

                // add table row
                final TableRow tr = new TableRow(this);
                tr.setId(i + 1);
                TableLayout.LayoutParams trParams = new
                        TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin,
                        bottomRowMargin);
                tr.setPadding(0, 0, 0, 0);
                tr.setLayoutParams(trParams);
                tr.addView(tv);
                tr.addView(laySPH);
                tr.addView(tv3);
                tr.addView(tv4);
                tr.addView(tv5);
                tr.addView(tv6);
                tr.addView(tv7);
                tr.addView(tv8);
                tr.addView(tv9);
                tr.addView(tv10);
                tr.addView(tv11);
                tr.addView(tv12);
                tr.addView(tv13);
                if (i > -1) {
                    tr.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            TableRow tr = (TableRow) v;
                        }
                    });
                }
                mTableLayout.addView(tr, trParams);
                if (i > -1) {
                    // add separator row
                    final TableRow trSep = new TableRow(this);
                    TableLayout.LayoutParams trParamsSep = new
                            TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);
                    trParamsSep.setMargins(leftRowMargin, topRowMargin,
                            rightRowMargin, bottomRowMargin);
                    trSep.setLayoutParams(trParamsSep);
                    TextView tvSep = new TextView(this);
                    TableRow.LayoutParams tvSepLay = new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT);
                    tvSepLay.span = 13;
                    tvSep.setLayoutParams(tvSepLay);
                    tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
                    tvSep.setHeight(1);
                    trSep.addView(tvSep);
                    mTableLayout.addView(trSep, trParamsSep);
                }
            }
        }

        public LinearLayout fillTableSHP(VegetableCalendar vCvalue,TextView tv){
            final LinearLayout laySPH = new LinearLayout(this);
            laySPH.setOrientation(LinearLayout.VERTICAL);
            laySPH.setGravity(Gravity.RIGHT);
            laySPH.setPadding(0, 10, 0, 10);
            laySPH.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            laySPH.setBackgroundColor(Color.parseColor("#ffffff"));

            final TextView tvS = new TextView(this);
            tvS.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tvS.setPadding(5, 0, 1, 5);


            tvS.setGravity(Gravity.RIGHT);
            tvS.setBackgroundColor(Color.parseColor("#ffffff"));
            laySPH.addView(tvS);

            final TextView tvP = new TextView(this);
            tvP.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tvP.setPadding(5, 0, 1, 5);

            tvP.setGravity(Gravity.RIGHT);
            tvP.setBackgroundColor(Color.parseColor("#000000"));
            laySPH.addView(tvP);

            final TextView tvH = new TextView(this);
            tvH.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tvH.setPadding(5, 0, 1, 5);

            tvH.setGravity(Gravity.RIGHT);
            tvH.setBackgroundColor(Color.parseColor("#d9d9d9"));
            laySPH.addView(tvS);
            return laySPH;
        }

        class LoadDataTask extends AsyncTask<Integer, Integer, String> {
            @Override
            protected String doInBackground(Integer... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Task Completed.";
            }
            @Override
            protected void onPostExecute(String result) {
                mProgressBar.hide();
                loadData();
            }
            @Override
            protected void onPreExecute() {
            }
            @Override
            protected void onProgressUpdate(Integer... values) {
            }
        }
    }
