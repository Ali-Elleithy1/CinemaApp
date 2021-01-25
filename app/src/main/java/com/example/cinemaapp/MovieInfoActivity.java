package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MovieInfoActivity extends AppCompatActivity {
    List<String> Max= Arrays.asList("01:15pm","04:30pm","07:00pm","09:30pm");
    List<String> Gold= Arrays.asList("01:30pm","02:00pm","04:00pm","04:30pm","06:30pm","07:00pm","09:00pm","09:30pm");
    List<String> FDX= Arrays.asList("03:00pm","05:30pm","08:00pm");
    List<Integer> prices = Arrays.asList(110,180,180);

    String name,rate,language,subtitles,duration,rDate,poster,genre,plot;
    long d;

    ImageView miPosterIV;
    TextView miRateTV,miLanguageTV,miDurationTV,miNameTV,genreTV,rDateTV,subtitlesTV;
    RecyclerView miMaxRV,miGoldRV,miFdxRV;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail = new HashMap<>();
    List<String> plots = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        setTitle("Movies");
        miMaxRV=findViewById(R.id.miMaxRV);
        miGoldRV=findViewById(R.id.miGoldRV);
        miFdxRV=findViewById(R.id.miFdxRV);
        miRateTV=findViewById(R.id.miRateTV);
        miLanguageTV=findViewById(R.id.miLanguageTV);
        miDurationTV=findViewById(R.id.miDurationTV);
        miNameTV=findViewById(R.id.miNameTV);
        genreTV=findViewById(R.id.genreTV);
        rDateTV=findViewById(R.id.rDateTV);
        subtitlesTV=findViewById(R.id.subtitlesTV);
        miPosterIV=findViewById(R.id.miPosterIV);
        miPosterIV.setAdjustViewBounds(true);
        miPosterIV.setScaleType(ImageView.ScaleType.CENTER_CROP);
        miLanguageTV.setBackgroundColor(Color.rgb(255,255,255));
        miDurationTV.setBackgroundColor(Color.rgb(255,255,255));

        //RECEIVE DATA VIA INTENT
        Intent i = getIntent();
        name = i.getStringExtra("name");
        rate = i.getStringExtra("rate");
        genre = i.getStringExtra("genre");
        language = i.getStringExtra("language");
        rDate = i.getStringExtra("rdate");
        duration = i.getStringExtra("duration");
        subtitles = i.getStringExtra("subtitles");
        plot = i.getStringExtra("plot");
        poster = i.getStringExtra("poster");


        miRateTV.setText(rate);
        miLanguageTV.setText(language);
        miDurationTV.setText(duration+" min");
        miNameTV.setText(name);
        genreTV.setText(genre);
        rDateTV.setText(parseDateToddMMyyyy(rDate));


        subtitlesTV.setText(subtitles);
        Picasso.get().load(poster).into(miPosterIV);

        plots.add(plot);
        expandableListDetail.put("TAP HERE TO READ THE PLOT",plots);


        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(MovieInfoActivity.this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //expandableListTitle.set(groupPosition,"TAP HERE TO CLOSE THE PLOT");
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();
                return false;
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

        ShowAdapter sMAdapter = new ShowAdapter(Max,i);
        LinearLayoutManager sMLayoutManager = new LinearLayoutManager(this);
        sMLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        miMaxRV.setLayoutManager(sMLayoutManager);
        miMaxRV.setItemAnimator(new DefaultItemAnimator());
        miMaxRV.setClipToPadding(false);
        miMaxRV.setAdapter(sMAdapter);

        ShowAdapter sGAdapter = new ShowAdapter(Gold,i);
        LinearLayoutManager sGLayoutManager = new LinearLayoutManager(this);
        sGLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        miGoldRV.setLayoutManager(sGLayoutManager);
        miGoldRV.setItemAnimator(new DefaultItemAnimator());
        miGoldRV.setClipToPadding(false);
        miGoldRV.setAdapter(sGAdapter);

        ShowAdapter sFAdapter = new ShowAdapter(FDX,i);
        LinearLayoutManager sFLayoutManager = new LinearLayoutManager(this);
        sFLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        miFdxRV.setLayoutManager(sFLayoutManager);
        miFdxRV.setItemAnimator(new DefaultItemAnimator());
        miFdxRV.setClipToPadding(false);
        miFdxRV.setAdapter(sFAdapter);
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    //ADAPTER FOR THE SHOW TIMES
    public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.MyViewHolder> {
        private List<String> showsList;
        private Intent i;

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView showTime;
            ImageButton ticketIB;
            MyViewHolder(View view) {
                super(view);
                showTime = view.findViewById(R.id.showTimeTV);
                ticketIB = view.findViewById(R.id.ticketIB);
            }
        }
        public ShowAdapter(List<String> list, Intent i) {
            this.showsList = list;
            this.i = i;
        }
        @NonNull
        @Override
        public ShowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_shows, parent, false);
            return new ShowAdapter.MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(ShowAdapter.MyViewHolder holder, int position) {
            String show = showsList.get(position);
            holder.showTime.setText(show);
            if(checkTimings(showsList.get(position))==false)
            {
                holder.ticketIB.setEnabled(false);
                holder.showTime.setEnabled(false);
            }
            else
            {
                holder.ticketIB.setEnabled(true);
                holder.showTime.setEnabled(true);
            }
            holder.ticketIB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(MovieInfoActivity.this,BookingActivity.class);
                    in.putExtra("type",i);
                    in.putExtra("show",show);
                    startActivity(in);
                }
            });
        }
        @Override
        public int getItemCount() {
            return showsList.size();
        }
    }

    public boolean checkTimings(String endtime) {

        String endTime="";
        if(endtime.endsWith("am"))
        {
            for(int i=0; i<5; i++)
            {
                endTime+=endtime.charAt(i);
            }
            endTime+=" AM";
        }
        else if(endtime.endsWith("pm"))
        {
            for(int i=0; i<5; i++)
            {
                endTime+=endtime.charAt(i);
            }
            endTime+=" PM";
        }
        String pattern = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        try {
            Date now = sdf.parse(currentTime);
            Date end = sdf.parse(endTime);
            if(now.before(end))
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String parseDateToTime(String time) {
        String inputPattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        String outputPattern = "HH:mm:ss aa";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    //FUNCTION FOR CHANGING DATE FORMAT AND RETURNING THE UPDATED
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}