package com.mcpcustomer_post_new_ps_n.android.ui.activities.sales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mcpcustomer_post_new_ps_n.android.R;
import com.mcpcustomer_post_new_ps_n.android.data.Utilities;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiClient;
import com.mcpcustomer_post_new_ps_n.android.domain.ApiInterface;
import com.mcpcustomer_post_new_ps_n.android.ui.models.SearchSuggestions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    RecyclerView searchRCID;
    ProgressBar searchPB;
    AppCompatTextView noData;
    RelativeLayout backRLID;
    AppCompatEditText searchETID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Utilities.startAnimation(this);

        searchRCID = findViewById(R.id.searchRCID);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        searchRCID.setLayoutManager(layoutManager);
        searchPB = findViewById(R.id.searchPB);
        noData = findViewById(R.id.noData);
        backRLID = findViewById(R.id.backRLID);
        searchETID = findViewById(R.id.searchETID);
        searchPB.setVisibility(View.GONE);
        backRLID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.finishAnimation(SearchActivity.this);
            }
        });

        searchETID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String suggestions = s.toString();
                loadSuggestions(suggestions);
            }
        });
    }

    private void loadSuggestions(String suggestions) {
        searchPB.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        searchRCID.setVisibility(View.GONE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<SearchSuggestions>> call = apiInterface.searchApi(suggestions);
        call.enqueue(new Callback<ArrayList<SearchSuggestions>>() {
            @Override
            public void onResponse(Call<ArrayList<SearchSuggestions>> call, Response<ArrayList<SearchSuggestions>> response) {
                searchPB.setVisibility(View.GONE);
                searchRCID.setVisibility(View.VISIBLE);
                if (response.body() != null && response.code() == 200) {
                    ArrayList<SearchSuggestions> searchSuggestions = response.body();
                    if (searchSuggestions.size() > 0) {
                        SearchSuggestionsAdapter adapter = new SearchSuggestionsAdapter(SearchActivity.this, searchSuggestions);
                        searchRCID.setAdapter(adapter);
                    } else {
                        noData.setVisibility(View.VISIBLE);
                        searchRCID.setVisibility(View.GONE);
                    }
                } else {
                    noData.setVisibility(View.VISIBLE);
                    searchRCID.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SearchSuggestions>> call, Throwable t) {
                searchPB.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                searchRCID.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utilities.finishAnimation(this);
    }

    public class SearchSuggestionsAdapter extends RecyclerView.Adapter<SearchSuggestionsAdapter.SearchVH> {

        private Activity activity;
        private ArrayList<SearchSuggestions> searchSuggestions = new ArrayList<>();

        public SearchSuggestionsAdapter(Activity activity, ArrayList<SearchSuggestions> searchSuggestions) {
            this.activity = activity;
            this.searchSuggestions = searchSuggestions;
        }

        @NonNull
        @Override
        public SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SearchVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SearchVH holder, final int position) {

            holder.searchSuggestionTVID.setText(searchSuggestions.get(position).getTitle());
            holder.searchItemLLID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, SubCategoriesActivity.class);
                    intent.putExtra("menu_id", "" + searchSuggestions.get(position).getMenu_id());
                    intent.putExtra("menu_name", "" + searchSuggestions.get(position).getTitle());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return searchSuggestions.size();
        }

        public class SearchVH extends RecyclerView.ViewHolder {

            AppCompatTextView searchSuggestionTVID;
            LinearLayout searchItemLLID;

            public SearchVH(@NonNull View itemView) {
                super(itemView);
                searchSuggestionTVID = itemView.findViewById(R.id.searchSuggestionTVID);
                searchItemLLID = itemView.findViewById(R.id.searchItemLLID);
            }
        }
    }
}
