package com.lifejodi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifejodi.home.data.ProfilesData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentAccepted extends Fragment {

    @BindView(R.id.recycler_request_status)
    RecyclerView recyclerRequestStatus;

    ProfilesData profilesData = ProfilesData.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_status, null);
        ButterKnife.bind(this, view);

        setRecyclerAdapter();

        return view;
    }

    public void setRecyclerAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerRequestStatus.setLayoutManager(linearLayoutManager);
        recyclerRequestStatus.setAdapter(new RequestStatusAdapter(getActivity(),profilesData.getAcceptedList()));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
