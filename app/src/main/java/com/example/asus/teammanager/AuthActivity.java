package com.example.asus.teammanager;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.teammanager.model.api_model.CustomerType;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.fetch_presenter.FetchPresenter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.pusher.pushnotifications.PushNotifications;

import java.util.ArrayList;


public class AuthActivity extends AppCompatActivity {

    TextView txt_mac;
    ArrayList<String> values = new ArrayList<>();
    TextView txt_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


//        Button btn_getmac = findViewById(R.id.btn_getmac);
////        txt_mac = findViewById(R.id.txt_mc_address);
////
////
////        btn_getmac.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String mcAdress = getMacAddress();
////                txt_mac.setText(mcAdress);
////            }
////        });

//        setYAxis();
//
//        BarChart chart = findViewById(R.id.chart);
//        BarDataSet dataset = new BarDataSet(getDataSet(), "hasil pencapaian");
////        dataset.setColors(Color.RED, Color.GREEN, Color.GRAY, Color.BLACK, Color.BLUE); //set color for each  bar
//        dataset.setColor(ContextCompat.getColor(this, R.color.colorAccent));
//
//        BarDataSet dataset2 = new BarDataSet(getNewDataSet(), "target pencapaian");
//        dataset2.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
//
//        BarData data = new BarData(dataset, dataset2);
//        data.setBarWidth(.4f);
//
////        data.setValueTextColor(R.color.colorAccent);
//        chart.setData(data);
//        chart.groupBars(0, 0.1f, 0.05f);
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
//
//        YAxis leftAxis = chart.getAxisLeft();
//        YAxis rightAxis = chart.getAxisRight();
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return values.get((int) value);
//            }
//        });
//        rightAxis.setEnabled(false);
//
//        Description description = new Description();
//        description.setText("");  //the position is on bottom right
//        chart.setDescription(description);
//        chart.animateXY(4000, 4000);
//        chart.invalidate();


//        PushNotifications.start(this, "8d1eb444-d7c9-45d6-95a3-cbe1ab9d7253");
//        PushNotifications.subscribe("retail_manager_2");

        txt_result = findViewById(R.id.txt_result);

        FetchPresenter presenter = new FetchPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object response) {
                ArrayList<CustomerType> customer_types = (ArrayList<CustomerType>) response;

                for(int i=0;i<customer_types.size();i++){
                    txt_result.setText(txt_result.getText().toString().concat("\n").concat(customer_types.get(i).getCustomerTypeName()));
                }
            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(AuthActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(AuthActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        presenter.getFetchPresenter();
    }

    private ArrayList<BarEntry> getDataSet() {

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(0,  110f); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(1, 40f); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(2, 60f); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(3, 30f); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(4, 90f); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(5, 100f); // Jun
        valueSet1.add(v1e6);

        return valueSet1;
    }

    private ArrayList<BarEntry> getNewDataSet(){
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(0,  50f); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(1, 70f); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(2, 50f); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(3, 20f); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(4, 50f); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(5, 85f); // Jun
        valueSet1.add(v1e6);

        return valueSet1;
    }

    private void setYAxis(){

        values.add("Jan");
        values.add("Feb");
        values.add("Mar");
        values.add("Apr");
        values.add("Mei");
        values.add("Jun");
    }
}
