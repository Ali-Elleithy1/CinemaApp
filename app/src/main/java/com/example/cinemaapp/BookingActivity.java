package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener{
    List<String> Max= Arrays.asList("01:15pm","04:30pm","07:00pm","09:30pm");
    List<String> Gold= Arrays.asList("01:30pm","02:00pm","04:00pm","04:30pm","06:30pm","07:00pm","09:00pm","09:30pm");
    List<String> FDX= Arrays.asList("03:00pm","05:30pm","08:00pm");
    List<Integer> prices = Arrays.asList(110,180,180);

    String showTime;
    int type;
    int quantity=0,total=0;
    String sType,name;

    ViewGroup layout;
    TableLayout tableLayout2;
    Button finishBTN;
    CardView cardView;
    ImageButton plusIB,minusIB;
    TextView qtyTV,priceTV,totalTV,ticketTypeTV;
    int price,numOfSeats;
    String time;
    String sortString="created DESC";
    String objectID;

    ArrayList<Integer> chosenSeats=new ArrayList<>();

    LinearLayout layoutSeat;

//    seats = "_UUUUUUAAAAARRRR_/"
//            + "_________________/"
//            + "UU__AAAARRRRR__RR/"
//            + "UU__UUUAAAAAA__AA/"
//            + "AA__AAAAAAAAA__AA/"
//            + "AA__AARUUUURR__AA/"
//            + "UU__UUUA_RRRR__AA/"
//            + "AA__AAAA_RRAA__UU/"
//            + "AA__AARR_UUUU__RR/"
//            + "AA__UUAA_UURR__RR/"
//            + "_________________/"
//            + "UU_AAAAAAAUUUU_RR/"
//            + "RR_AAAAAAAAAAA_AA/"
//            + "AA_UUAAAAAUUUU_AA/"
//            + "AA_AAAAAAUUUUU_AA/"
//            + "_________________/";

    String newSeats;



    String seats="";


    StringBuilder sb = new StringBuilder();

    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    String selectedIds = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setTitle("Booking Page");
        tableLayout2=findViewById(R.id.tableLayout2);
        finishBTN=findViewById(R.id.finishBTN);
        cardView=findViewById(R.id.cardView);
        plusIB=findViewById(R.id.plusIB);
        minusIB=findViewById(R.id.minusIB);
        qtyTV=findViewById(R.id.qtyTV);
        priceTV=findViewById(R.id.priceTV);
        totalTV=findViewById(R.id.totalTV);
        ticketTypeTV=findViewById(R.id.ticketTypeTV);
        layout = findViewById(R.id.layoutSeat);
        minusIB.setVisibility(View.INVISIBLE);
        cardView.setCardElevation(0);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();
        showTime = i.getStringExtra("show");
        type = i.getIntExtra("type",0);
        name=i.getStringExtra("name");
        //Toast.makeText(this, "Name: "+name, Toast.LENGTH_SHORT).show();
        if(type==1)
        {
            price=prices.get(0);
            sType="Max";
            ticketTypeTV.setText("MAX TICKET");
        }
        else if(type==2)
        {
            price=prices.get(1);
            sType="Gold";
            ticketTypeTV.setText("GOLD TICKET");
        }
        else
        {
            price=prices.get(2);
            sType="4DX";
            ticketTypeTV.setText("4DX TICKET");
        }

        priceTV.setText(price+" EGP");

//        String inputPattern = "EEE MMM dd HH:mm:ss zzz yyyy";
//        String outputPattern = "dd MMM yyyy";
//        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
//        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        DataQueryBuilder builder=DataQueryBuilder.create();
        builder.setSortBy(sortString);
        builder.setWhereClause("type = '"+sType+"' AND showTime = '"+showTime+"' AND movieName = '"+name+"'");
        Backendless.Data.of(Shows.class).find(builder, new AsyncCallback<List<Shows>>() {
            @Override
            public void handleResponse(List<Shows> response) {
                List<String> S = response
                        .parallelStream()
                        .map(Shows::getSeats)
                        .collect(Collectors.toList());

                Date now = Calendar.getInstance().getTime();
                if(S.size()>0) {
                    if (isAfter(response.get(0).getCreated().toString(), now.toString()) == true) {
                        //Toast.makeText(BookingActivity.this, "THIS RECORD IS OLD, YOU NEED TO CREATE NEW ONE", Toast.LENGTH_SHORT).show();
                        //INSERT HERE
                        seats = "_AAAAAAAAAAAAAAA_/"
                                + "_________________/"
                                + "AA__AAAAAAAAA__AA/"
                                + "AA__AAAAAAAAA__AA/"
                                + "AA__AAAAAAAAA__AA/"
                                + "AA__AAAAAAAAA__AA/"
                                + "AA__AAAA_AAAA__AA/"
                                + "AA__AAAA_AAAA__AA/"
                                + "AA__AAAA_AAAA__AA/"
                                + "AA__AAAA_AAAA__AA/"
                                + "_________________/"
                                + "AA_AAAAAAAAAAA_AA/"
                                + "AA_AAAAAAAAAAA_AA/"
                                + "AA_AAAAAAAAAAA_AA/"
                                + "AA_AAAAAAAAAAA_AA/"
                                + "_________________/";

                        saveNewShow();
                        render();
                    }
                    else
                    {
                        seats=response.get(0).getSeats();
                        objectID=response.get(0).getObjectId();
                        render();
                    }
                }
                else
                {
                    seats = "_AAAAAAAAAAAAAAA_/"
                            + "_________________/"
                            + "AA__AAAAAAAAA__AA/"
                            + "AA__AAAAAAAAA__AA/"
                            + "AA__AAAAAAAAA__AA/"
                            + "AA__AAAAAAAAA__AA/"
                            + "AA__AAAA_AAAA__AA/"
                            + "AA__AAAA_AAAA__AA/"
                            + "AA__AAAA_AAAA__AA/"
                            + "AA__AAAA_AAAA__AA/"
                            + "_________________/"
                            + "AA_AAAAAAAAAAA_AA/"
                            + "AA_AAAAAAAAAAA_AA/"
                            + "AA_AAAAAAAAAAA_AA/"
                            + "AA_AAAAAAAAAAA_AA/"
                            + "_________________/";
                    saveNewShow();
                    render();
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(BookingActivity.this, "problem loading seats", Toast.LENGTH_SHORT).show();
            }
        });
        finishBTN.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void render()
    {
        if(!seats.startsWith("/"))
        {
            seats = "/" + seats;
        }
        layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;

        for (int index = 0; index < seats.length(); index++) {
            if (seats.charAt(index) == '/') {
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            } else if (seats.charAt(index) == 'U') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_booked);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_BOOKED);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'A') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.BLACK);
                view.setTag(STATUS_AVAILABLE);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'R') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_reserved);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_RESERVED);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == '_') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }
        }
        layoutSeat.setVisibility(View.INVISIBLE);
    }

    public void saveNewShow()
    {
        HashMap show = new HashMap();
        show.put( "movieName", name );
        show.put( "showTime", showTime );
        show.put( "seats", seats );
        show.put( "type", sType );

        // save object asynchronously
        Backendless.Data.of( "Shows" ).save( show, new AsyncCallback<Map>() {
            public void handleResponse( Map response )
            {
                // new Contact instance has been saved
                Toast.makeText(BookingActivity.this, "New Show has been added", Toast.LENGTH_SHORT).show();
            }

            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(BookingActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveNewTransaction()
    {
        String currentUserFName = Backendless.UserService.CurrentUser().getProperty("fname").toString();
        String currentUserLName = Backendless.UserService.CurrentUser().getProperty("lname").toString();

        HashMap show = new HashMap();
        show.put( "movieName", name );
        show.put( "name", currentUserFName+" "+currentUserLName );
        show.put( "quantity", quantity );
        show.put( "type", sType );
        show.put("seats",chosenSeats.toString());
        show.put("showTime",showTime);
        show.put("total",total);

        // save object asynchronously
        Backendless.Data.of( "Transactions" ).save( show, new AsyncCallback<Map>() {
            public void handleResponse( Map response )
            {
                Toast.makeText(BookingActivity.this, "New Transaction has been added", Toast.LENGTH_SHORT).show();
                // new Contact instance has been saved
                Intent in = new Intent(BookingActivity.this,TransactionsActivity.class);
                BookingActivity.this.finish();
                startActivity(in);
            }

            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(BookingActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateShow()
    {
        HashMap show = new HashMap();
        show.put( "movieName", name );
        show.put( "showTime", showTime );
        show.put( "seats", seats );
        show.put( "type", sType );
        show.put("objectId",objectID);
        Backendless.Data.of( "Shows" ).save( show, new AsyncCallback<Map>() {
            public void handleResponse( Map savedShow )
            {
                // New contact object has been saved, now it can be updated.
                // The savedContact map now has a valid "objectId" value.
                savedShow.put( "seats", seats );

                Backendless.Data.of( "Shows" ).save( savedShow, new AsyncCallback<Map>() {
                    @Override
                    public void handleResponse( Map response )
                    {
                        // Contact objecthas been updated
                        Toast.makeText(BookingActivity.this, "Seats have been updated", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                        Toast.makeText(BookingActivity.this, "Oops!", Toast.LENGTH_SHORT).show();
                    }
                } );
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
    }

    public Boolean isAfter(String time,String now) {
        String inputPattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date dateT = null;
        Date dateN = null;
        String strT = null;
        String strN = null;

        try {
            dateT = inputFormat.parse(time);
            strT = outputFormat.format(dateT);
            dateT=outputFormat.parse(strT);
            dateN = inputFormat.parse(now);
            strN = outputFormat.format(dateN);
            dateN=outputFormat.parse(strN);

            if(dateN.after(dateT))
            {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        if(numOfSeats>0) {
            if ((int) view.getTag() == STATUS_AVAILABLE) {
                if (selectedIds.contains(view.getId() + ",")) {
                    selectedIds = selectedIds.replace(+view.getId() + ",", "");
                    view.setBackgroundResource(R.drawable.ic_seats_book);
                    chosenSeats.remove(chosenSeats.size()-1);
                    numOfSeats++;
                } else {
                    selectedIds = selectedIds + view.getId() + ",";
                    view.setBackgroundResource(R.drawable.ic_seats_selected);
                    chosenSeats.add(view.getId());
                    numOfSeats--;
                }
            } else if ((int) view.getTag() == STATUS_BOOKED) {
                Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
            } else if ((int) view.getTag() == STATUS_RESERVED) {
                Toast.makeText(this, "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this, "you have "+numOfSeats+" seats remaining!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "you have "+numOfSeats+" seats to book!", Toast.LENGTH_SHORT).show();
        }
    }


    public void increment(View view) {


        if (!qtyTV.getText().toString().equals("")) {
            quantity = Integer.parseInt(qtyTV.getText().toString());
        }

            if(view.getId()==R.id.plusIB)
            {
                minusIB.setVisibility(View.VISIBLE);
                if(quantity<10) {
                    quantity++;
                    total=(quantity*price);
                    totalTV.setText(""+total+" EGP");
                }
            }
            else if(view.getId()==R.id.minusIB)
            {
                if(quantity>0) {
                    quantity--;
                    total=(quantity*price);
                    totalTV.setText(""+total);
                    if (quantity == 0) {
                        minusIB.setVisibility(View.INVISIBLE);
                    }
                }
            }
        qtyTV.setText(""+quantity);
    }

    public void confirm(View view) {
        numOfSeats=Integer.parseInt(qtyTV.getText().toString());
        tableLayout2.setEnabled(false);
        plusIB.setEnabled(false);
        minusIB.setEnabled(false);
        view.setEnabled(false);
        layoutSeat.setVisibility(View.VISIBLE);
        finishBTN.setVisibility(View.VISIBLE);
    }

    public void finish(View view) {
        layoutSeat.setVisibility(View.INVISIBLE);
        StringBuilder sb = new StringBuilder(seats);
        char seat;
        int count=0;
        for(int j=0; j<chosenSeats.size(); j++) {
            for (int i = 0; i < seats.length(); i++) {
                seat = seats.charAt(i);
                if (seat=='U' || seat == 'A' || seat=='R') {
                    if (count == chosenSeats.get(j))
                    {
                        sb.replace(i,i+1,"U");
                        count=0;
                        break;
                    }
                        count++;
                }
            }
        }
        seats=sb.toString();
        updateShow();
        saveNewTransaction();
        //Toast.makeText(this, ""+chosenSeats, Toast.LENGTH_SHORT).show();
    }
}