package com.hamsofts.startips;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Vip_Archive extends Fragment {


    View view;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    TextView loading;
    DatabaseReference mDatabaseReference;
    FirebaseRecyclerAdapter<Model, ItemViewHolder> firebaseRecyclerAdapter;


    TextView txtLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tips, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("jackpot").child("viparchive");

        txtLoading = (TextView) view.findViewById(R.id.jp);


        mLayoutManager = new LinearLayoutManager(getContext());
        //mLayoutManager.setReverseLayout(true);
        //mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        displayRecycler();


        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //  int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
       displayRecycler();
    }

    public void displayRecycler() {

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("jackpot")
                .child("viparchive");

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, Model.class)
                        .build();

        FirebaseRecyclerAdapter<Model, Daily_Tips.ItemViewHolder> adapter = new FirebaseRecyclerAdapter<Model, Daily_Tips.ItemViewHolder>(options) {

            @Override
            public Daily_Tips.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.post_row, parent, false);

                return new Daily_Tips.ItemViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(Daily_Tips.ItemViewHolder holder, int position, Model model) {
                // Bind the Chat object to the ChatHolder
                // ...
                final String item_key = getRef(position).getKey();
                holder.setTitle(model.getTitle());
                holder.setBody(model.getBody());
                holder.setTime(model.getTime());
                loading.setVisibility(View.GONE);
                //swipeRefreshLayout.setRefreshing(false);

                holder.mView.setOnClickListener(v -> {


                    Intent adDetails = new Intent(v.getContext(), Post_Details.class);
                    adDetails.putExtra("selection","viparchive");
                    adDetails.putExtra("postKey", item_key);
                    startActivity(adDetails);
                });
            }
            @Override
            public void onError(DatabaseError e) {
                // Called when there is an error getting data. You may want to update
                // your UI to display an error message to the user.
                // ...
                Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                //swipeRefreshLayout.setRefreshing(false);

            }
        };
        adapter.startListening();
        mRecyclerView.setAdapter(adapter);
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder {


        View mView;

        public ItemViewHolder(View v) {
            super(v);
            mView = v;

        }

        public void setTitle(String title) {
            TextView tvTitle = (TextView) mView.findViewById(R.id.postTitle);
            tvTitle.setText(title);
        }

        public void setBody(String price) {

            TextView txtPrice = (TextView) mView.findViewById(R.id.post);
            txtPrice.setText(price);

        }

        public void setTime(Long time) {

            TextView txtTime = (TextView) mView.findViewById(R.id.postTime);
            //long elapsedDays=0,elapsedWeeks = 0, elapsedHours=0,elapsedMin=0;
            long elapsedTime;
            long currentTime = System.currentTimeMillis();
            int elapsed = (int) ((currentTime - time) / 1000);
            if (elapsed < 60) {
                if (elapsed < 2) {
                    txtTime.setText("Just Now");
                } else {
                    txtTime.setText(elapsed + " sec ago");
                }
            } else if (elapsed > 604799) {
                elapsedTime = elapsed / 604800;
                if (elapsedTime == 1) {
                    txtTime.setText(elapsedTime + " week ago");
                } else {

                    txtTime.setText(elapsedTime + " weeks ago");
                }
            } else if (elapsed > 86399) {
                elapsedTime = elapsed / 86400;
                if (elapsedTime == 1) {
                    txtTime.setText(elapsedTime + " day ago");
                } else {
                    txtTime.setText(elapsedTime + " days ago");
                }
            } else if (elapsed > 3599) {
                elapsedTime = elapsed / 3600;
                if (elapsedTime == 1) {
                    txtTime.setText(elapsedTime + " hour ago");
                } else {
                    txtTime.setText(elapsedTime + " hours ago");
                }
            } else if (elapsed > 59) {
                elapsedTime = elapsed / 60;
                txtTime.setText(elapsedTime + " min ago");


            }

        }
    }
}



