package com.example.s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ManualExoenses_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView rcv;
    ArrayList<MessageModelClass> Message;
    RecyclerView.Adapter Messageadapter;
    RecyclerView.LayoutManager mgr;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    String msgData = "";
    Spinner month;
    ImageView add;


    ArrayList<String> lst1 = new ArrayList<>();
    static String amts = "";
    String avail = "";
    static String amtsfordisplay = "";
    TextView totaldebtv, totalcredtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_exoenses_);
        rcv = findViewById(R.id.rcv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        add = findViewById(R.id.add);

        totalcredtv = findViewById(R.id.total_cred);
        totaldebtv = findViewById(R.id.total_deb);

        drawerLayout = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigationbar, R.string.close_navigationbar);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);

        month = findViewById(R.id.month);
        setmonthspinner();
        setbottomnav();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(ManualExoenses_Activity.this);
                d.setContentView(R.layout.add_manual_expense);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();
                EditText title = d.findViewById(R.id.et_title);
                EditText Amount = d.findViewById(R.id.et_amount);
                Spinner category = d.findViewById(R.id.et_tag);
                EditText date = d.findViewById(R.id.et_when);
                Spinner transtype = d.findViewById(R.id.et_transactionType);
                Button save = d.findViewById(R.id.btn_save_transaction);
                ImageView cal = d.findViewById(R.id.cal);
                final Calendar c = Calendar.getInstance();
                date.setEnabled(false);

                ArrayList<String> transtypearraylist = new ArrayList<>();
                transtypearraylist.add("Select Transaction Type");
                transtypearraylist.add("Income");
                transtypearraylist.add("Expense");

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ManualExoenses_Activity.this, android.R.layout.simple_spinner_item, transtypearraylist);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                transtype.setAdapter(arrayAdapter);

                ArrayList<String> cattypearray = new ArrayList<>();
                cattypearray.add("Select a category");
                cattypearray.add("Housing");
                cattypearray.add("Transportation");
                cattypearray.add("Food");
                cattypearray.add("Entertainment");
                cattypearray.add("Health Care");
                cattypearray.add("Other");

                ArrayAdapter<String> catarrayAdapter = new ArrayAdapter<String>(ManualExoenses_Activity.this, android.R.layout.simple_spinner_item, cattypearray);
                catarrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(catarrayAdapter);

                cal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dp = new DatePickerDialog(ManualExoenses_Activity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                        dp.show();
                        dp.getDatePicker().setMaxDate(c.getTimeInMillis());

                    }
                });


            }
        });
    }

    private void setbottomnav() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bank:
                        startActivity(new Intent(ManualExoenses_Activity.this, MainActivity.class));
                        break;
                    case R.id.cash:
                        break;
                    case R.id.bill:
                        startActivity(new Intent(ManualExoenses_Activity.this, Bill_Activity.class));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void setmonthspinner() {
        ArrayList<String> months = new ArrayList<>();
        Calendar cc = new GregorianCalendar();
        cc.setTime(new Date());
        for (int i = 0; i < 24; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM YYYY");
            months.add(sdf.format(cc.getTime()));
            cc.add(Calendar.MONTH, -1);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(arrayAdapter);

        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String monthname = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + monthname, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        changenotificationcolor();
    }

    private void changenotificationcolor() {
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.notification));


    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.trip) {
            Toast.makeText(this, "trip", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SplitScreen.class));
        }
        if (item.getItemId() == R.id.chart) {
            Toast.makeText(this, "stats", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, stats.class));

        }
        if (item.getItemId() == R.id.privacy) {
            Toast.makeText(this, "privacy", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Privacy_Policy.class));
        }
        if (item.getItemId() == R.id.connect_us) {
            Toast.makeText(this, "connect us", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_SENDTO)
                    .setData(new Uri.Builder().scheme("mailto").build())
                    .putExtra(Intent.EXTRA_EMAIL, new String[]{"ritvikdubeyrk@gmail.com"});
            startActivity(intent);

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
    }


}