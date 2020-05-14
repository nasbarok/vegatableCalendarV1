package com.nasbarok.vegatablecalendarv1;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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

    public void initTable() {
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
        }

        public void loadData() {
            int leftRowMargin=0;
            int topRowMargin=0;
            int rightRowMargin=0;
            int bottomRowMargin = 0;
            int textSize = 0, smallTextSize =0, mediumTextSize = 0;
            textSize = (int) getResources().getDimension(R.dimen.font_size_verysmall);
            smallTextSize = (int) getResources().getDimension(R.dimen.font_size_small);
            mediumTextSize = (int)
                    getResources().getDimension(R.dimen.font_size_medium);
            Invoices invoices = new Invoices();
            InvoiceData[] data = invoices.getInvoices();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
            DecimalFormat decimalFormat = new DecimalFormat("0.00");

            List<VegetableCalendar> vegetableCalendars = vegetableCalendarDB.getVegetableCalendars();

            int rows = data.length;
            getSupportActionBar().setTitle("Invoices (" + String.valueOf(rows) + ")");
            TextView textSpacer = null;
            mTableLayout.removeAllViews();
            // -1 means heading row
            for(int i = -1; i > rows; i ++) {
                InvoiceData row = null;
                if (i > -1)
                    row = data[i];
                else {
                    textSpacer = new TextView(this);
                    textSpacer.setText("");
                }
                // data columns
                final TextView tv = new TextView(this);
                tv.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.LEFT);
                tv.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv.setText("Inv.#");
                    tv.setBackgroundColor(Color.parseColor("#f0f0f0"));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                }
                else {
                    tv.setBackgroundColor(Color.parseColor("#f8f8f8"));
                    tv.setText(String.valueOf(row.invoiceNumber));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                final TextView tv2 = new TextView(this);
                if (i == -1) {
                    tv2.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                }
                else {
                    tv2.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                tv2.setGravity(Gravity.LEFT);
                tv2.setPadding(5, 15, 0, 15);
                if (i == -1) {
                    tv2.setText("Date");
                    tv2.setBackgroundColor(Color.parseColor("#f7f7f7"));
                }
                else {
                    tv2.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv2.setTextColor(Color.parseColor("#000000"));
                    tv2.setText(dateFormat.format(row.invoiceDate));
                }
                final LinearLayout layCustomer = new LinearLayout(this);
                layCustomer.setOrientation(LinearLayout.VERTICAL);
                layCustomer.setPadding(0, 10, 0, 10);
                layCustomer.setBackgroundColor(Color.parseColor("#f8f8f8"));
                final TextView tv3 = new TextView(this);
                if (i == -1) {
                    tv3.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv3.setPadding(5, 5, 0, 5);
                    tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                }
                else {
                    tv3.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv3.setPadding(5, 0, 0, 5);
                    tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                tv3.setGravity(Gravity.TOP);
                if (i == -1) {
                    tv3.setText("Customer");
                    tv3.setBackgroundColor(Color.parseColor("#f0f0f0"));
                }
                else {
                    tv3.setBackgroundColor(Color.parseColor("#f8f8f8"));
                    tv3.setTextColor(Color.parseColor("#000000"));
                    tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                    tv3.setText(row.customerName);
                }
                layCustomer.addView(tv3);
                if (i > -1) {
                    final TextView tv3b = new TextView(this);
                    tv3b.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv3b.setGravity(Gravity.RIGHT);
                    tv3b.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    tv3b.setPadding(5, 1, 0, 5);
                    tv3b.setTextColor(Color.parseColor("#aaaaaa"));
                    tv3b.setBackgroundColor(Color.parseColor("#f8f8f8"));
                    tv3b.setText(row.customerAddress);
                    layCustomer.addView(tv3b);
                }
                final LinearLayout layAmounts = new LinearLayout(this);
                layAmounts.setOrientation(LinearLayout.VERTICAL);
                layAmounts.setGravity(Gravity.RIGHT);
                layAmounts.setPadding(0, 10, 0, 10);
                layAmounts.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                final TextView tv4 = new TextView(this);
                if (i == -1) {
                    tv4.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    tv4.setPadding(5, 5, 1, 5);
                    layAmounts.setBackgroundColor(Color.parseColor("#f7f7f7"));
                }
                else {
                    tv4.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv4.setPadding(5, 0, 1, 5);
                    layAmounts.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                tv4.setGravity(Gravity.RIGHT);
                if (i == -1) {
                    tv4.setText("Inv.Amount");
                    tv4.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallTextSize);
                }
                else {
                    tv4.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv4.setTextColor(Color.parseColor("#000000"));
                    tv4.setText(decimalFormat.format(row.invoiceAmount));
                    tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
                layAmounts.addView(tv4);
                if (i > -1) {
                    final TextView tv4b = new TextView(this);
                    tv4b.setLayoutParams(new
                            TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv4b.setGravity(Gravity.RIGHT);
                    tv4b.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    tv4b.setPadding(2, 2, 1, 5);
                    tv4b.setTextColor(Color.parseColor("#00afff"));
                    tv4b.setBackgroundColor(Color.parseColor("#ffffff"));
                    String due = "";
                    if (row.amountDue.compareTo(new BigDecimal(0.01)) == 1) {
                        due = "Due:" + decimalFormat.format(row.amountDue);
                        due = due.trim();
                    }
                    tv4b.setText(due);
                    layAmounts.addView(tv4b);
                }
                // add table row
                final TableRow tr = new TableRow(this);
                tr.setId(i + 1);
                TableLayout.LayoutParams trParams = new
                        TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParams.setMargins(leftRowMargin, topRowMargin, rightRowMargin,
                        bottomRowMargin);
                tr.setPadding(0,0,0,0);
                tr.setLayoutParams(trParams);
                tr.addView(tv);
                tr.addView(tv2);
                tr.addView(layCustomer);
                tr.addView(layAmounts);
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
                    tvSepLay.span = 4;
                    tvSep.setLayoutParams(tvSepLay);
                    tvSep.setBackgroundColor(Color.parseColor("#d9d9d9"));
                    tvSep.setHeight(1);
                    trSep.addView(tvSep);
                    mTableLayout.addView(trSep, trParamsSep);
                }
            }
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
