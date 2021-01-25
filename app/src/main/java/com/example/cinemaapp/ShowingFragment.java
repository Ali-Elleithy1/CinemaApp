package com.example.cinemaapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ShowingFragment extends Fragment {

    ListView moviesLV;
    List<String> Max= Arrays.asList("01:15pm","04:30pm","07:00pm","09:30pm");
    List<String> Gold= Arrays.asList("01:30pm","02:00pm","04:00pm","04:30pm","06:30pm","07:00pm","09:00pm","09:30pm");
    List<String> FDX= Arrays.asList("03:00pm","05:30pm","08:00pm");
    List<Integer> prices = Arrays.asList(110,180,180);


    ArrayList<Movies> mResponse = new ArrayList<>();
    Uri uri;

    String sortString="rdate ASC";

    public ShowingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showing, container, false);
        moviesLV=view.findViewById(R.id.moviesLV);

        //Backendless.initApp(getContext(),"4192D836-B383-B50A-FF05-5E5F30870300","542D62D0-F6C7-4D5F-A32A-157220FFC408");


        //currentUserId = Backendless.UserService.CurrentUser().getUserId();

        DataQueryBuilder builder=DataQueryBuilder.create();
        builder.setSortBy(sortString);
        //builder.setWhereClause("ownerId = '" +currentUserId+"' OR type = '"+orderString+"'");
        Backendless.Data.of(Movies.class).find(builder, new AsyncCallback<List<Movies>>() {
            @Override
            public void handleResponse(List<Movies> response) {
                List<String> names = response
                        .parallelStream()
                        .map(Movies::getName)
                        .collect(Collectors.toList());


                //ArrayAdapter adapter= new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,names);
                for(Movies movie:response)
                {
                    mResponse.add(movie);
                }

                MovieAdapter adapter = new MovieAdapter(getContext(), mResponse);
                moviesLV.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getActivity(), "problem loading movies", Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
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


    public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.MyViewHolder> {
        private List<String> showsList;
        private int i;
        private String s;


        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView showTime;
            ImageButton ticketIB;

            MyViewHolder(View view) {
                super(view);
                showTime = view.findViewById(R.id.showTimeTV);
                ticketIB = view.findViewById(R.id.ticketIB);
            }
        }
        public ShowAdapter(List<String> list, int i, String s) {
            this.showsList = list;
            this.i = i;
            this.s = s;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_shows, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String show = showsList.get(position);
            holder.showTime.setText(show);
            holder.ticketIB.setTag(showsList.size()+show);
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
                    Intent in = new Intent(getActivity(),BookingActivity.class);
                    in.putExtra("type",i);
                    in.putExtra("show",show);
                    in.putExtra("name",s);
                    startActivity(in);
                }
            });
        }
        @Override
        public int getItemCount() {
            return showsList.size();
        }
    }

    //MOVIE ADAPTER
    class MovieAdapter extends ArrayAdapter<Movies> implements View.OnClickListener {
        public MovieAdapter(@NonNull Context context, List<Movies> movies) {
            super(context, 0,movies);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //inflate
            convertView=getLayoutInflater().inflate(R.layout.custom_movie,parent,false);
            ImageView cmShaderIV=convertView.findViewById(R.id.cmShaderIV);
            ImageView poster=convertView.findViewById(R.id.miPosterIV);
            ImageView trailerBTN=convertView.findViewById(R.id.trailerBTN);
            poster.setAdjustViewBounds(true);
            poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
            TextView name=convertView.findViewById(R.id.miNameTV);
            TextView rate=convertView.findViewById(R.id.miRateTV);
            TextView language=convertView.findViewById(R.id.miLanguageTV);
            language.setBackgroundColor(Color.rgb(255,255,255));
            TextView duration=convertView.findViewById(R.id.miDurationTV);
            duration.setBackgroundColor(Color.rgb(255,255,255));
            RecyclerView maxRV = convertView.findViewById(R.id.miMaxRV);
            RecyclerView goldRV = convertView.findViewById(R.id.miGoldRV);
            RecyclerView fdxRV = convertView.findViewById(R.id.miFdxRV);

            //poster.
            name.setText(getItem(position).getName());
            rate.setText(getItem(position).getRate());
            language.setText(getItem(position).getLanguage());
            duration.setText(getItem(position).getDuration().toString()+" min");
            Picasso.get().load(getItem(position).getPoster()).into(poster);
            trailerBTN.setTag(getItem(position).getTrailer());
            cmShaderIV.setTag(position);




            //event
            cmShaderIV.setOnClickListener(this);
            trailerBTN.setOnClickListener(this);


            ShowAdapter sMAdapter = new ShowAdapter(Max,1,getItem(position).getName());
            LinearLayoutManager sMLayoutManager = new LinearLayoutManager(getContext());
            sMLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            maxRV.setLayoutManager(sMLayoutManager);
            maxRV.setItemAnimator(new DefaultItemAnimator());
            maxRV.setClipToPadding(false);
            maxRV.setAdapter(sMAdapter);

            ShowAdapter sGAdapter = new ShowAdapter(Gold,2,getItem(position).getName());
            LinearLayoutManager sGLayoutManager = new LinearLayoutManager(getContext());
            sGLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            goldRV.setLayoutManager(sGLayoutManager);
            goldRV.setItemAnimator(new DefaultItemAnimator());
            goldRV.setClipToPadding(false);
            goldRV.setAdapter(sGAdapter);

            ShowAdapter sFAdapter = new ShowAdapter(FDX,3,getItem(position).getName());
            LinearLayoutManager sFLayoutManager = new LinearLayoutManager(getContext());
            sFLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            fdxRV.setLayoutManager(sFLayoutManager);
            fdxRV.setItemAnimator(new DefaultItemAnimator());
            fdxRV.setClipToPadding(false);
            fdxRV.setAdapter(sFAdapter);

            return convertView;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.trailerBTN) {
//                trailerVV.setVisibility(View.VISIBLE);
//                MediaController mediaController = new MediaController(getActivity());
//                mediaController.setAnchorView(trailerVV);
//                trailerVV.setMediaController(mediaController);
//                trailerVV.setVideoPath(v.getTag().toString());
//                trailerVV.requestFocus();
//                trailerVV.start();
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(v.getTag().toString())));
            } else if (v.getId() == R.id.cmShaderIV) {
                int position = (int) v.getTag();
                //INTENT OBJ
                Intent i = new Intent(getActivity().getBaseContext(),
                        MovieInfoActivity.class);

                //PACK DATA
                i.putExtra("name", mResponse.get(position).getName());
                i.putExtra("language", mResponse.get(position).getLanguage());
                i.putExtra("subtitles", mResponse.get(position).getSubtitles());
                i.putExtra("rate", mResponse.get(position).getRate());
                i.putExtra("rdate", mResponse.get(position).getRdate().toString());
                i.putExtra("genre", mResponse.get(position).getGenre());
                i.putExtra("duration", mResponse.get(position).getDuration().toString());
                i.putExtra("plot", mResponse.get(position).getPlot());
                i.putExtra("poster", mResponse.get(position).getPoster());


                //START ACTIVITY
                getActivity().startActivity(i);
            }
        }
    }
}