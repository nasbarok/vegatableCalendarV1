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
                //LinearLayout vegetable name
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
                //LinearLayout January
                LinearLayout layJanuarySTH = new LinearLayout(this);
                layJanuarySTH.setOrientation(LinearLayout.VERTICAL);
                layJanuarySTH.setGravity(Gravity.LEFT);
                layJanuarySTH.setPadding(0, 10, 0, 10);
                layJanuarySTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                final TextView tvJanuary = new TextView(this);
                tvJanuary.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvJanuary.setGravity(Gravity.LEFT);
                tvJanuary.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvJanuary.setText(getResources().getString(R.string.calendar_heading2));
                    tvJanuary.setBackgroundColor(Color.parseColor("#f0f0f0"));
                    tvJanuary.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    layJanuarySTH.addView(tvJanuary);
                } else {
                    tvJanuary.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    layJanuarySTH = fillCurrentMonth(tvJanuary,row.getVegetableCalendarJanuary(),layJanuarySTH);
                }

                //LinearLayout February
                LinearLayout layFebruarySTH = new LinearLayout(this);
                layFebruarySTH.setOrientation(LinearLayout.VERTICAL);
                layFebruarySTH.setGravity(Gravity.RIGHT);
                layFebruarySTH.setPadding(0, 10, 0, 10);
                layFebruarySTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                final TextView tvFebruary = new TextView(this);
                tvFebruary.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvFebruary.setGravity(Gravity.LEFT);
                tvFebruary.setBackgroundColor(Color.parseColor("#ffffff"));
                tvFebruary.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvFebruary.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvFebruary.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tvFebruary.setText(getResources().getString(R.string.calendar_heading3));
                    tvFebruary.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    layFebruarySTH.addView(tvFebruary);
                } else {
                    tvFebruary.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    layFebruarySTH = fillCurrentMonth(tvFebruary,row.getVegetableCalendarFebruary(),layFebruarySTH);
                }

                //LinearLayout March
                LinearLayout layMarchSTH = new LinearLayout(this);
                layMarchSTH.setOrientation(LinearLayout.VERTICAL);
                layMarchSTH.setGravity(Gravity.RIGHT);
                layMarchSTH.setPadding(0, 10, 0, 10);
                layMarchSTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                final TextView tvMarch = new TextView(this);
                tvMarch.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvMarch.setGravity(Gravity.LEFT);
                tvMarch.setBackgroundColor(Color.parseColor("#ffffff"));
                tvMarch.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvMarch.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvMarch.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tvMarch.setText(getResources().getString(R.string.calendar_heading4));
                    tvMarch.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    layMarchSTH.addView(tvMarch);
                } else {
                    tvMarch.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    layMarchSTH = fillCurrentMonth(tvMarch,row.getVegetableCalendarMarch(),layMarchSTH);
                }

                //LinearLayout April
                LinearLayout layAprilSTH = new LinearLayout(this);
                layAprilSTH.setOrientation(LinearLayout.VERTICAL);
                layAprilSTH.setGravity(Gravity.RIGHT);
                layAprilSTH.setPadding(0, 10, 0, 10);
                layAprilSTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                final TextView tvApril = new TextView(this);
                tvApril.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvApril.setGravity(Gravity.LEFT);
                tvApril.setBackgroundColor(Color.parseColor("#ffffff"));
                tvApril.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvApril.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvApril.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tvApril.setText(getResources().getString(R.string.calendar_heading5));
                    tvApril.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    layAprilSTH.addView(tvApril);
                } else {
                    tvApril.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    layAprilSTH = fillCurrentMonth(tvApril,row.getVegetableCalendarApril(),layAprilSTH);
                }

                //LinearLayout May
                LinearLayout layMaySTH = new LinearLayout(this);
                layMaySTH.setOrientation(LinearLayout.VERTICAL);
                layMaySTH.setGravity(Gravity.RIGHT);
                layMaySTH.setPadding(0, 10, 0, 10);
                layMaySTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                final TextView tvMay = new TextView(this);
                tvMay.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvMay.setGravity(Gravity.LEFT);
                tvMay.setBackgroundColor(Color.parseColor("#ffffff"));
                tvMay.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvMay.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvMay.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tvMay.setText(getResources().getString(R.string.calendar_heading6));
                    tvMay.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    layMaySTH.addView(tvMay);
                } else {
                    tvMay.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    layMaySTH = fillCurrentMonth(tvMay,row.getVegetableCalendarMay(),layMaySTH);
                }

                //LinearLayout June
                LinearLayout layJuneSTH = new LinearLayout(this);
                layJuneSTH.setOrientation(LinearLayout.VERTICAL);
                layJuneSTH.setGravity(Gravity.RIGHT);
                layJuneSTH.setPadding(0, 10, 0, 10);
                layJuneSTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                final TextView tvJune = new TextView(this);
                tvJune.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvJune.setGravity(Gravity.LEFT);
                tvJune.setBackgroundColor(Color.parseColor("#ffffff"));
                tvJune.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvJune.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvJune.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tvJune.setText(getResources().getString(R.string.calendar_heading7));
                    tvJune.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    layJuneSTH.addView(tvJune);
                } else {
                    tvJune.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    layJuneSTH = fillCurrentMonth(tvJune,row.getVegetableCalendarJune(),layJuneSTH);
                }

                //LinearLayout July
                LinearLayout layJulySTH = new LinearLayout(this);
                layJulySTH.setOrientation(LinearLayout.VERTICAL);
                layJulySTH.setGravity(Gravity.RIGHT);
                layJulySTH.setPadding(0, 10, 0, 10);
                layJulySTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                final TextView tvJuly = new TextView(this);
                tvJuly.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvJuly.setGravity(Gravity.LEFT);
                tvJuly.setBackgroundColor(Color.parseColor("#ffffff"));
                tvJuly.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvJuly.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvJuly.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tvJuly.setText(getResources().getString(R.string.calendar_heading8));
                    tvJuly.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    layJulySTH.addView(tvJuly);
                } else {
                    tvJuly.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    layJulySTH = fillCurrentMonth(tvJuly,row.getVegetableCalendarJuly(),layJulySTH);
                }

                //LinearLayout August
                LinearLayout layAugustSTH = new LinearLayout(this);
                layAugustSTH.setOrientation(LinearLayout.VERTICAL);
                layAugustSTH.setGravity(Gravity.RIGHT);
                layAugustSTH.setPadding(0, 10, 0, 10);
                layAugustSTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                final TextView tvAugust = new TextView(this);
                tvAugust.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvAugust.setGravity(Gravity.RIGHT);
                tvAugust.setBackgroundColor(Color.parseColor("#ffffff"));
                tvAugust.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvAugust.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvAugust.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tvAugust.setText(getResources().getString(R.string.calendar_heading9));
                    tvAugust.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    layAugustSTH.addView(tvAugust);
                } else {
                    tvAugust.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    layAugustSTH = fillCurrentMonth(tvAugust,row.getVegetableCalendarAugust(),layAugustSTH);
                }

                //LinearLayout September
                LinearLayout laySeptemberSTH = new LinearLayout(this);
                laySeptemberSTH.setOrientation(LinearLayout.VERTICAL);
                laySeptemberSTH.setGravity(Gravity.RIGHT);
                laySeptemberSTH.setPadding(0, 10, 0, 10);
                laySeptemberSTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                final TextView tvSeptember = new TextView(this);
                tvSeptember.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvSeptember.setGravity(Gravity.RIGHT);
                tvSeptember.setBackgroundColor(Color.parseColor("#ffffff"));
                tvSeptember.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvSeptember.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvSeptember.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tvSeptember.setText(getResources().getString(R.string.calendar_heading10));
                    tvSeptember.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    laySeptemberSTH.addView(tvSeptember);
                } else {
                    tvSeptember.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    laySeptemberSTH = fillCurrentMonth(tvSeptember,row.getVegetableCalendarSeptember(),laySeptemberSTH);
                }

                //LinearLayout October
                LinearLayout layOctoberSTH = new LinearLayout(this);
                layOctoberSTH.setOrientation(LinearLayout.VERTICAL);
                layOctoberSTH.setGravity(Gravity.RIGHT);
                layOctoberSTH.setPadding(0, 10, 0, 10);
                layOctoberSTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                final TextView tvOctober = new TextView(this);
                tvOctober.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvOctober.setGravity(Gravity.RIGHT);
                tvOctober.setBackgroundColor(Color.parseColor("#ffffff"));
                tvOctober.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvOctober.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvOctober.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tvOctober.setText(getResources().getString(R.string.calendar_heading11));
                    tvOctober.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    layOctoberSTH.addView(tvOctober);
                } else {
                    tvOctober.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    layOctoberSTH = fillCurrentMonth(tvOctober,row.getVegetableCalendarOctober(),layOctoberSTH);
                }

                //LinearLayout November
                LinearLayout layNovemberSTH = new LinearLayout(this);
                layNovemberSTH.setOrientation(LinearLayout.VERTICAL);
                layNovemberSTH.setGravity(Gravity.RIGHT);
                layNovemberSTH.setPadding(0, 10, 0, 10);
                layNovemberSTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                final TextView tvNovember = new TextView(this);
                tvNovember.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvNovember.setGravity(Gravity.RIGHT);
                tvNovember.setBackgroundColor(Color.parseColor("#ffffff"));
                tvNovember.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvNovember.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvNovember.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tvNovember.setText(getResources().getString(R.string.calendar_heading12));
                    tvNovember.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    layNovemberSTH.addView(tvNovember);
                } else {
                    tvNovember.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    layNovemberSTH = fillCurrentMonth(tvNovember,row.getVegetableCalendarNovember(),layNovemberSTH);
                }

                //LinearLayout December
                LinearLayout layDecemberSTH = new LinearLayout(this);
                layDecemberSTH.setOrientation(LinearLayout.VERTICAL);
                layDecemberSTH.setGravity(Gravity.RIGHT);
                layDecemberSTH.setPadding(0, 10, 0, 10);
                layDecemberSTH.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));

                final TextView tvDecember = new TextView(this);
                tvDecember.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvDecember.setGravity(Gravity.RIGHT);
                tvDecember.setBackgroundColor(Color.parseColor("#ffffff"));
                tvDecember.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tvDecember.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tvDecember.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tvDecember.setText(getResources().getString(R.string.calendar_heading13));
                    tvDecember.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    layDecemberSTH.addView(tvDecember);
                } else {
                    tvDecember.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    layDecemberSTH = fillCurrentMonth(tvDecember,row.getVegetableCalendarNovember(),layDecemberSTH);
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
                tr.addView(layJanuarySTH);
                tr.addView(layFebruarySTH);
                tr.addView(layMarchSTH);
                tr.addView(layAprilSTH);
                tr.addView(layMaySTH);
                tr.addView(layJuneSTH);
                tr.addView(layJulySTH);
                tr.addView(layAugustSTH);
                tr.addView(laySeptemberSTH);
                tr.addView(layOctoberSTH);
                tr.addView(layNovemberSTH);
                tr.addView(layDecemberSTH);
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


        public LinearLayout fillCurrentMonth(TextView tv, String monthInformation, LinearLayout linearLayout){
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.RIGHT);
            linearLayout.setPadding(0, 10, 0, 10);
            linearLayout.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));

            //IS OS ISAS ISAE
            final TextView tvS = new TextView(this);
            tvS.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tvS.setPadding(5, 0, 1, 5);
            tvS.setGravity(Gravity.RIGHT);

            if(monthInformation==null){
                monthInformation="";
            }

            if(monthInformation.contains("IS")&&!monthInformation.contains("ISAS")&&!monthInformation.contains("ISAE")) {
                tvS.setText("IS");
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_iseeding));
                linearLayout.addView(tvS);
            }
            if(monthInformation.contains("ISAE")&&monthInformation.contains("OSAE")) {
                final TableRow tr = new TableRow(this);
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_iseeding));

                final TextView tvOS = new TextView(this);
                tvOS.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvOS.setPadding(5, 0, 1, 5);
                tvOS.setGravity(Gravity.RIGHT);
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_oseeding));
                tvOS.setText(" ");
                tr.addView(tvS);
                tr.addView(tvOS);
                linearLayout.addView(tr);
            }
            if(monthInformation.contains("ISAS")&&!monthInformation.contains("OSAE")) {
                final TableRow tr = new TableRow(this);
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_iseeding));

                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tvAEmpty.setText(" ");
                tr.addView(tvS);
                tr.addView(tvAEmpty);
                linearLayout.addView(tr);
            }
            if(monthInformation.contains("ISAE")) {
                final TableRow tr = new TableRow(this);
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_iseeding));

                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tvAEmpty.setText(" ");
                tr.addView(tvAEmpty);
                tr.addView(tvS);
                linearLayout.addView(tr);
            }
            if(monthInformation.contains("OS")&&!monthInformation.contains("OSAS")&&!monthInformation.contains("OSAE")) {
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_oseeding));
                linearLayout.addView(tvS);
            }
            if(monthInformation.contains("OSAS")) {
                final TableRow tr = new TableRow(this);
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_oseeding));

                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tvAEmpty.setText(" ");
                tr.addView(tvS);
                tr.addView(tvAEmpty);
                linearLayout.addView(tr);
            }
            if(monthInformation.contains("OSAE")&&monthInformation.contains("ISAS")) {
                final TableRow tr = new TableRow(this);
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_oseeding));

                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tvAEmpty.setText(" ");
                tr.addView(tvAEmpty);
                tr.addView(tvS);
                linearLayout.addView(tr);
            }

            //T
            final TextView tvT = new TextView(this);
            tvT.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tvT.setPadding(5, 0, 1, 5);
            tvT.setGravity(Gravity.RIGHT);
            tvT.setText(" ");
            if(monthInformation.contains("T")&&!monthInformation.contains("TAS")&&!monthInformation.contains("TAE")) {
                tvT.setBackgroundColor(getResources().getColor(R.color.table_color_transplantation));
                linearLayout.addView(tvT);
            }
            if(monthInformation.contains("TAS")) {
                final TableRow tr = new TableRow(this);
                tvT.setBackgroundColor(getResources().getColor(R.color.table_color_transplantation));

                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tr.addView(tvT);
                tr.addView(tvAEmpty);
                linearLayout.addView(tr);
            }
            if(monthInformation.contains("TAE")) {
                final TableRow tr = new TableRow(this);
                tvT.setBackgroundColor(getResources().getColor(R.color.table_color_transplantation));

                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tr.addView(tvT);
                tr.addView(tvAEmpty);
                linearLayout.addView(tr);
            }
            //H
            final TextView tvH = new TextView(this);
            tvH.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tvH.setPadding(5, 0, 1, 5);
            tvH.setGravity(Gravity.RIGHT);
            tvH.setText(" ");

            if(monthInformation.contains("H")&&!monthInformation.contains("HAS")&&!monthInformation.contains("HAE")) {
                tvH.setBackgroundColor(getResources().getColor(R.color.table_color_harvest));
                linearLayout.addView(tvH);
            }
            if(monthInformation.contains("HAS")) {
                final TableRow tr = new TableRow(this);
                tvH.setBackgroundColor(getResources().getColor(R.color.table_color_harvest));

                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tr.addView(tvH);
                tr.addView(tvAEmpty);
                linearLayout.addView(tr);
            }
            if(monthInformation.contains("HAE")) {
                final TableRow tr = new TableRow(this);
                tvH.setBackgroundColor(getResources().getColor(R.color.table_color_harvest));

                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tr.addView(tvH);
                tr.addView(tvAEmpty);
                linearLayout.addView(tr);
            }
            return linearLayout;
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
