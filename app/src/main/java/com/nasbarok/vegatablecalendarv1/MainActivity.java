package com.nasbarok.vegatablecalendarv1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nasbarok.vegatablecalendarv1.db.VegetableCalendarDBHelper;
import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public VegetableCalendarDBHelper vegetableCalendarDB;
    private TableLayout mTableLayout;
    ProgressDialog mProgressBar;
    List<VegetableCalendar> vegetableCalendars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = new ProgressDialog(this);
        mTableLayout = (TableLayout) findViewById(R.id.tableVegetables);
        mTableLayout.setStretchAllColumns(true);

        //initiate db
        InputStream inputStream = getResources().openRawResource(R.raw.vegetable_calendar_db);
        vegetableCalendarDB = new VegetableCalendarDBHelper(this.getApplicationContext());
        vegetableCalendarDB.createDB(inputStream);

        startLoadData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = (MenuItem) menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                VegetableCalendar vegetableCalendar = null;
                List <VegetableCalendar> vegetableCalendarList;
                vegetableCalendarList = vegetableCalendarDB.findVegetableCalendar(query);
                if(vegetableCalendarList.size()>1){
                    vegetableCalendars = vegetableCalendarList;
                    startLoadData();
                }else{
                    vegetableCalendars = new ArrayList<VegetableCalendar>();
                    startLoadData();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void startLoadData() {
        mProgressBar.setCancelable(false);
        mProgressBar.setMessage(getResources().getString(R.string.loading_msg));
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();
        new LoadDataTask().execute(0);
    }
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

        //get dimensions in pixels
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widthTotalDisplay = size.x - 40;
        int widthColumn = widthTotalDisplay/14;
        int widthColumnVegetables = widthColumn + widthColumn/2;
        if(vegetableCalendars==null){
            vegetableCalendars = vegetableCalendarDB.getVegetableCalendars();
        }

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
            //LinearLayout vegetable Name
            final TextView tv = new TextView(this);
            tv.setLayoutParams(new
                    TableRow.LayoutParams(widthColumnVegetables,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.LEFT);
            tv.setPadding(5, 15, 0, 15);

            if (i == -1) {
                tv.setText(getResources().getString(R.string.calendar_heading1));
                tv.setBackgroundColor(getResources().getColor(R.color.table_color_vegetable_head));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
            } else {
                tv.setBackgroundColor(getResources().getColor(R.color.table_color_vegetable_column));
                tv.setText(String.valueOf(row.getVegetableCalendarName()));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            //LinearLayout January
            LinearLayout layJanuarySTH = new LinearLayout(this);
            layJanuarySTH.setOrientation(LinearLayout.VERTICAL);
            layJanuarySTH.setGravity(Gravity.LEFT);
            layJanuarySTH.setPadding(0, 0, 0, 10);
            layJanuarySTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layJanuarySTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            final TextView tvJanuary = new TextView(this);
            tvJanuary.setGravity(Gravity.LEFT);
            tvJanuary.setPadding(5, 15, 0, 15);

            if (i == -1) {
                tvJanuary.setText(getResources().getString(R.string.calendar_heading2));
                tvJanuary.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
                tvJanuary.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvJanuary.setSingleLine(true);
                layJanuarySTH.addView(tvJanuary);
            } else {
                layJanuarySTH = fillCurrentMonth(row.getVegetableCalendarJanuary(),layJanuarySTH);
            }

            //LinearLayout February
            LinearLayout layFebruarySTH = new LinearLayout(this);
            layFebruarySTH.setOrientation(LinearLayout.VERTICAL);
            layFebruarySTH.setGravity(Gravity.LEFT);
            layFebruarySTH.setPadding(0, 0, 0, 10);
            layFebruarySTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layFebruarySTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));

            final TextView tvFebruary = new TextView(this);
            tvFebruary.setGravity(Gravity.LEFT);
            tvFebruary.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));
            tvFebruary.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvFebruary.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvFebruary.setText(getResources().getString(R.string.calendar_heading3));
                tvFebruary.setSingleLine(true);
                layFebruarySTH.addView(tvFebruary);
            } else {
                layFebruarySTH = fillCurrentMonth(row.getVegetableCalendarFebruary(),layFebruarySTH);
            }

            //LinearLayout March
            LinearLayout layMarchSTH = new LinearLayout(this);
            layMarchSTH.setOrientation(LinearLayout.VERTICAL);
            layMarchSTH.setGravity(Gravity.LEFT);
            layMarchSTH.setPadding(0, 0, 0, 10);
            layMarchSTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layMarchSTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMarch = new TextView(this);
            tvMarch.setGravity(Gravity.LEFT);
            tvMarch.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMarch.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvMarch.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvMarch.setText(getResources().getString(R.string.calendar_heading4));
                layMarchSTH.addView(tvMarch);
            } else {
                layMarchSTH = fillCurrentMonth(row.getVegetableCalendarMarch(),layMarchSTH);
            }

            //LinearLayout April
            LinearLayout layAprilSTH = new LinearLayout(this);
            layAprilSTH.setOrientation(LinearLayout.VERTICAL);
            layAprilSTH.setGravity(Gravity.LEFT);
            layAprilSTH.setPadding(0, 0, 0, 10);
            layAprilSTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layAprilSTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));

            final TextView tvApril = new TextView(this);
            tvApril.setGravity(Gravity.LEFT);
            tvApril.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));
            tvApril.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvApril.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvApril.setText(getResources().getString(R.string.calendar_heading5));
                tvApril.setSingleLine(true);
                layAprilSTH.addView(tvApril);
            } else {
                layAprilSTH = fillCurrentMonth(row.getVegetableCalendarApril(),layAprilSTH);
            }

            //LinearLayout May
            LinearLayout layMaySTH = new LinearLayout(this);
            layMaySTH.setOrientation(LinearLayout.VERTICAL);
            layMaySTH.setGravity(Gravity.LEFT);
            layMaySTH.setPadding(0, 0, 0, 10);
            layMaySTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.WRAP_CONTENT));
            layMaySTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvMay = new TextView(this);
            tvMay.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tvMay.setGravity(Gravity.LEFT);
            tvMay.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvMay.setPadding(5, 15, 0, 15);

            if (i == -1) {
                tvMay.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvMay.setText(getResources().getString(R.string.calendar_heading6));
                tvMay.setSingleLine(true);
                layMaySTH.addView(tvMay);
            } else {
                layMaySTH = fillCurrentMonth(row.getVegetableCalendarMay(),layMaySTH);
            }

            //LinearLayout June
            LinearLayout layJuneSTH = new LinearLayout(this);
            layJuneSTH.setOrientation(LinearLayout.VERTICAL);
            layJuneSTH.setGravity(Gravity.LEFT);
            layJuneSTH.setPadding(0, 0, 0, 10);
            layJuneSTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.MATCH_PARENT));
            layJuneSTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));

            final TextView tvJune = new TextView(this);
            tvJune.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tvJune.setGravity(Gravity.LEFT);
            tvJune.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));
            tvJune.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvJune.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvJune.setText(getResources().getString(R.string.calendar_heading7));
                tvJune.setSingleLine(true);
                layJuneSTH.addView(tvJune);
            } else {
                layJuneSTH = fillCurrentMonth(row.getVegetableCalendarJune(),layJuneSTH);
            }

            //LinearLayout July
            LinearLayout layJulySTH = new LinearLayout(this);
            layJulySTH.setOrientation(LinearLayout.VERTICAL);
            layJulySTH.setGravity(Gravity.LEFT);
            layJulySTH.setPadding(0, 0, 0, 10);
            layJulySTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.WRAP_CONTENT));
            layJulySTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvJuly = new TextView(this);
            tvJuly.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tvJuly.setGravity(Gravity.LEFT);
            tvJuly.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvJuly.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvJuly.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvJuly.setText(getResources().getString(R.string.calendar_heading8));
                tvJuly.setSingleLine(true);
                layJulySTH.addView(tvJuly);
            } else {
                layJulySTH = fillCurrentMonth(row.getVegetableCalendarJuly(),layJulySTH);
            }

            //LinearLayout August
            LinearLayout layAugustSTH = new LinearLayout(this);
            layAugustSTH.setOrientation(LinearLayout.VERTICAL);
            layAugustSTH.setGravity(Gravity.LEFT);
            layAugustSTH.setPadding(0, 0, 0, 10);
            layAugustSTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.WRAP_CONTENT));
            layAugustSTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));

            final TextView tvAugust = new TextView(this);
            tvAugust.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tvAugust.setGravity(Gravity.LEFT);
            tvAugust.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));
            tvAugust.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvAugust.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvAugust.setText(getResources().getString(R.string.calendar_heading9));
                tvAugust.setSingleLine(true);
                layAugustSTH.addView(tvAugust);
            } else {
                tvAugust.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                layAugustSTH = fillCurrentMonth(row.getVegetableCalendarAugust(),layAugustSTH);
            }

            //LinearLayout September
            LinearLayout laySeptemberSTH = new LinearLayout(this);
            laySeptemberSTH.setOrientation(LinearLayout.VERTICAL);
            laySeptemberSTH.setGravity(Gravity.LEFT);
            laySeptemberSTH.setPadding(0, 0, 0, 10);
            laySeptemberSTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.WRAP_CONTENT));
            laySeptemberSTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvSeptember = new TextView(this);
            tvSeptember.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tvSeptember.setGravity(Gravity.LEFT);
            tvSeptember.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvSeptember.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvSeptember.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvSeptember.setText(getResources().getString(R.string.calendar_heading10));
                tvSeptember.setSingleLine(true);
                laySeptemberSTH.addView(tvSeptember);
            } else {
                laySeptemberSTH = fillCurrentMonth(row.getVegetableCalendarSeptember(),laySeptemberSTH);
            }

            //LinearLayout October
            LinearLayout layOctoberSTH = new LinearLayout(this);
            layOctoberSTH.setOrientation(LinearLayout.VERTICAL);
            layOctoberSTH.setGravity(Gravity.LEFT);
            layOctoberSTH.setPadding(0, 0, 0, 10);
            layOctoberSTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.WRAP_CONTENT));
            layOctoberSTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));

            final TextView tvOctober = new TextView(this);
            tvOctober.setGravity(Gravity.LEFT);
            tvOctober.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));
            tvOctober.setPadding(5, 15, 0, 15);
            tvOctober.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            if (i == -1) {

                tvOctober.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvOctober.setText(getResources().getString(R.string.calendar_heading11));
                tvOctober.setSingleLine(true);
                layOctoberSTH.addView(tvOctober);
            } else {
                layOctoberSTH = fillCurrentMonth(row.getVegetableCalendarOctober(),layOctoberSTH);
            }

            //LinearLayout November
            LinearLayout layNovemberSTH = new LinearLayout(this);
            layNovemberSTH.setOrientation(LinearLayout.VERTICAL);
            layNovemberSTH.setGravity(Gravity.LEFT);
            layNovemberSTH.setPadding(0, 0, 0, 10);
            layNovemberSTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.WRAP_CONTENT));
            layNovemberSTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));

            final TextView tvNovember = new TextView(this);
            tvNovember.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tvNovember.setGravity(Gravity.LEFT);
            tvNovember.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark));
            tvNovember.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvNovember.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvNovember.setText(getResources().getString(R.string.calendar_heading12));
                tvNovember.setSingleLine(true);
                layNovemberSTH.addView(tvNovember);
            } else {
                layNovemberSTH = fillCurrentMonth(row.getVegetableCalendarNovember(),layNovemberSTH);
            }

            //LinearLayout December
            LinearLayout layDecemberSTH = new LinearLayout(this);
            layDecemberSTH.setOrientation(LinearLayout.VERTICAL);
            layDecemberSTH.setGravity(Gravity.LEFT);
            layDecemberSTH.setPadding(0, 0, 0, 10);
            layDecemberSTH.setLayoutParams(new
                    TableRow.LayoutParams(widthColumn,
                    TableRow.LayoutParams.WRAP_CONTENT));
            layDecemberSTH.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));

            final TextView tvDecember = new TextView(this);
            tvDecember.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tvDecember.setGravity(Gravity.LEFT);
            tvDecember.setBackgroundColor(getResources().getColor(R.color.table_color_default_bright2));
            tvDecember.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvDecember.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                tvDecember.setText(getResources().getString(R.string.calendar_heading13));
                tvDecember.setSingleLine(true);
                layDecemberSTH.addView(tvDecember);
            } else {
                layDecemberSTH = fillCurrentMonth(row.getVegetableCalendarNovember(),layDecemberSTH);
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
                tvSep.setBackgroundColor(getResources().getColor(R.color.table_color_default_dark2));
                tvSep.setHeight(1);
                trSep.addView(tvSep);
                mTableLayout.addView(trSep, trParamsSep);
            }
        }
    }


        public LinearLayout fillCurrentMonth(String monthInformation, LinearLayout linearLayout){

            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.RIGHT);
            linearLayout.setPadding(0, 5, 0, 5);
            linearLayout.setWeightSum(3f);
            linearLayout.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            if(monthInformation.equals("NONE")){
                return linearLayout;
            }
            //IS OS ISAS ISAE
            final TextView tvS = new TextView(this);
            tvS.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,1.0f)
                    );
            tvS.setPadding(5, 0, 1, 5);
            tvS.setGravity(Gravity.RIGHT);

            final TextView tvOS = new TextView(this);
            tvOS.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,1.0f));
            tvOS.setPadding(5, 0, 1, 5);
            tvOS.setGravity(Gravity.RIGHT);

            final TableRow trS = new TableRow(this);
            final TableRow trT = new TableRow(this);
            final TableRow trH = new TableRow(this);
            trS.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,1.0f));
            trT.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,1.0f));
            trH.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,1.0f));
            trS.setEnabled(false);
            trT.setEnabled(false);
            trH.setEnabled(false);

            final TextView tvT = new TextView(this);
            tvT.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,1.0f));
            tvT.setPadding(5, 0, 1, 5);
            tvT.setGravity(Gravity.RIGHT);


            final TextView tvH = new TextView(this);
            tvH.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,1.0f));
            tvH.setPadding(5, 0, 1, 5);
            tvH.setGravity(Gravity.RIGHT);

            if(monthInformation==null){
                monthInformation="";
            }

            if(monthInformation.contains("IS")&&!monthInformation.contains("ISAS")&&!monthInformation.contains("ISAE")) {
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_iseeding));
                trS.setEnabled(false);
            }
            if(monthInformation.contains("ISAS")&&monthInformation.contains("OSAE")) {
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_iseeding));
                tvOS.setBackgroundColor(getResources().getColor(R.color.table_color_oseeding));
                trS.addView(tvS);
                trS.addView(tvOS);
                trS.setEnabled(true);
            }
            if(monthInformation.contains("ISAS")&&!monthInformation.contains("OSAE")) {
                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1.0f));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_iseeding));
                trS.addView(tvS);
                trS.addView(tvAEmpty);
                trS.setEnabled(true);
            }
            if(monthInformation.contains("ISAE")) {
                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1.0f));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_iseeding));
                trS.addView(tvAEmpty);
                trS.addView(tvS);
                trS.setEnabled(true);
            }
            if(monthInformation.contains("OS")&&!monthInformation.contains("OSAS")&&!monthInformation.contains("OSAE")) {
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_oseeding));
                trS.setEnabled(false);
            }
            if(monthInformation.contains("OSAE")&&!monthInformation.contains("ISAS")) {
                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1.0f));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tvS.setBackgroundColor(getResources().getColor(R.color.table_color_oseeding));
                trS.addView(tvAEmpty);
                trS.addView(tvS);
                trS.setEnabled(true);
            }

            //T

            if(monthInformation.contains("T")&&!monthInformation.contains("TAS")&&!monthInformation.contains("TAE")) {
                tvT.setBackgroundColor(getResources().getColor(R.color.table_color_transplantation));
                trT.setEnabled(false);
            }
            if(monthInformation.contains("TAS")) {
                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1.0f));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tvT.setBackgroundColor(getResources().getColor(R.color.table_color_transplantation));
                trT.addView(tvT);
                trT.addView(tvAEmpty);
                trT.setEnabled(true);
            }
            if(monthInformation.contains("TAE")) {
                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1.0f));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tvT.setBackgroundColor(getResources().getColor(R.color.table_color_transplantation));
                trT.addView(tvAEmpty);
                trT.addView(tvT);
                trT.setEnabled(true);
            }

            //H
            if(monthInformation.contains("H")&&!monthInformation.contains("HAS")&&!monthInformation.contains("HAE")) {
                tvH.setBackgroundColor(getResources().getColor(R.color.table_color_harvest));
                trH.setEnabled(false);
            }
            if(monthInformation.contains("HAS")) {
                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1.0f));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tvH.setBackgroundColor(getResources().getColor(R.color.table_color_harvest));
                trH.addView(tvH);
                trH.addView(tvAEmpty);
                trH.setEnabled(true);
            }
            if(monthInformation.contains("HAE")) {
                final TextView tvAEmpty = new TextView(this);
                tvAEmpty.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1.0f));
                tvAEmpty.setPadding(5, 0, 1, 5);
                tvAEmpty.setGravity(Gravity.RIGHT);
                tvH.setBackgroundColor(getResources().getColor(R.color.table_color_harvest));
                trH.addView(tvH);
                trH.addView(tvAEmpty);
                trH.setEnabled(true);
            }

            //fill linearLayout
            if(trS.isEnabled()){
                linearLayout.addView(trS);
            }else{
                linearLayout.addView(tvS);
            }
            if(trT.isEnabled()){
                linearLayout.addView(trT);
            }else{
                linearLayout.addView(tvT);
            }
            if(trH.isEnabled()){
                linearLayout.addView(trH);
            }else{
                linearLayout.addView(tvH);
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
