package com.currentaffairs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonthlyFragment extends Fragment {

    private RecyclerView rcv;
    private List<CaModel> eBookList;
    private CaAdapter mAdapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monthly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.monthly_rcv);
        rcv.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.monthly_progress_eBar);
        progressBar.setVisibility(View.VISIBLE);
        eBookList = new ArrayList<>();
        rcv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

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
                    }
                }else{
                    assert response.errorBody() != null;
                    Toast.makeText(getActivity(),response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<CaResponse> call, @NotNull Throwable t) {

            }
        });
    }

}
