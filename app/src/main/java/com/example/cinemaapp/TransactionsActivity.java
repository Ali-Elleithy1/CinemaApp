package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionsActivity extends AppCompatActivity {
    String sortString="created DESC";
    String currentUserId;

    ListView transactionsLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        Backendless.initApp(this,"4192D836-B383-B50A-FF05-5E5F30870300","542D62D0-F6C7-4D5F-A32A-157220FFC408");
        transactionsLV=findViewById(R.id.transactionsLV);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        currentUserId = Backendless.UserService.CurrentUser().getUserId();

        DataQueryBuilder builder=DataQueryBuilder.create();
        builder.setSortBy(sortString);
        builder.setWhereClause("ownerId = '" +currentUserId+"'");
        Backendless.Data.of(Transactions.class).find(builder, new AsyncCallback<List<Transactions>>() {
            @Override
            public void handleResponse(List<Transactions> response) {
//                List<String> names = response
//                        .parallelStream()
//                        .map(Transactions::getName)
//                        .collect(Collectors.toList());


                //ArrayAdapter adapter= new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,names);

                TransactionsAdapter adapter = new TransactionsAdapter(TransactionsActivity.this, response);
                transactionsLV.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(TransactionsActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    class TransactionsAdapter extends ArrayAdapter<Transactions> {
        public TransactionsAdapter(@NonNull Context context, List<Transactions> transactions) {
            super(context, 0, transactions);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //inflate
            convertView = getLayoutInflater().inflate(R.layout.custom_transaction, parent, false);
            TextView nameTV = convertView.findViewById(R.id.ctNameTV);
            TextView dateTV = convertView.findViewById(R.id.ctDateTV);
            TextView ShowTimeTV = convertView.findViewById(R.id.ctShowTimeTV);
            TextView TicketTypeTV = convertView.findViewById(R.id.ctTicketTypeTV);
            TextView movieTV = convertView.findViewById(R.id.ctMovieTV);
            TextView seatsTV = convertView.findViewById(R.id.ctSeatsTV);
            TextView quantityTV = convertView.findViewById(R.id.ctQuantityTV);
            TextView totalTV = convertView.findViewById(R.id.ctTotalTV);


            nameTV.setText(getItem(position).getName());
            String newDate=parseDateToddMMyyyy(getItem(position).getCreated().toString());
            dateTV.setText(newDate);
            ShowTimeTV.setText(getItem(position).getShowTime());
            TicketTypeTV.setText(getItem(position).getType());
            movieTV.setText(getItem(position).getMovieName());
            seatsTV.setText(getItem(position).getSeats());
            quantityTV.setText(""+getItem(position).getQuantity());
            totalTV.setText(""+getItem(position).getTotal());

            return convertView;
        }
    }
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