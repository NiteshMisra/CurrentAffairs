package com.currentaffairs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class MonthlyFragment extends Fragment {

    private RecyclerView rcv;
    private List<CaModel> eBookList;
    private CaAdapter mAdapter;
    private TextView noInternet;
    private TextView noData;
    private SwipeRefreshLayout eBookRefresh;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monthly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.monthly_rcv);
        rcv.setHasFixedSize(true);
        noInternet = view.findViewById(R.id.monthlyNoInternet);
        noData = view.findViewById(R.id.monthlyNoData);
        rcv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        eBookRefresh = view.findViewById(R.id.monthlyRefresh);
        loadLayout();
        eBookRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadLayout();
            }
        });
    }

    private void loadLayout(){
        if (isInternet()){
            eBookRefresh.setRefreshing(true);
            noData.setText("Please wait...");
            rcv.setVisibility(View.GONE);
            noInternet.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            eBookList = new ArrayList<>();
            Map<String,String> headers = new HashMap<>();
            headers.put("User-ID","1");
            headers.put("Authorization","67nPxwLC/yyGc");

            RetrofitClient.getInstance().getApi().getCurrentAffairs(headers,"-1").enqueue(new Callback<CaResponse>() {
                @Override
                public void onResponse(@NotNull Call<CaResponse> call, @NotNull Response<CaResponse> response) {
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        if (response.body().getData() != null){
                            List<CaModel> list = response.body().getData();
                            for (CaModel item : list){
                                if (Integer.parseInt(item.getType()) == 2){
                                    eBookList.add(item);
                                }
                            }
                            mAdapter = new CaAdapter(getActivity(), eBookList);
                            rcv.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            noData.setVisibility(View.GONE);
                            noInternet.setVisibility(View.GONE);
                            rcv.setVisibility(View.VISIBLE);
                        }else {
                            noData.setText("No Data is available");
                            noData.setVisibility(View.VISIBLE);
                            noInternet.setVisibility(View.GONE);
                            rcv.setVisibility(View.GONE);
                        }
                    }else{
                        assert response.errorBody() != null;
                        noData.setText("No Response from Server.\nRefresh to try again");
                        noData.setVisibility(View.VISIBLE);
                        noInternet.setVisibility(View.GONE);
                        rcv.setVisibility(View.GONE);
                    }
                    eBookRefresh.setRefreshing(false);
                }

                @Override
                public void onFailure(@NotNull Call<CaResponse> call, @NotNull Throwable t) {
                    eBookRefresh.setRefreshing(false);
                    noData.setText("Server not Responding\nRefresh to try again");
                    noData.setVisibility(View.VISIBLE);
                    noInternet.setVisibility(View.GONE);
                    rcv.setVisibility(View.GONE);
                }
            });

        }else{
            eBookRefresh.setRefreshing(false);
            noInternet.setText("No Internet Connection\nRefresh to try again");
            noData.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
            rcv.setVisibility(View.GONE);
        }
    }

    private Boolean isInternet(){
        ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }

}
