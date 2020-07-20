package com.rakuten.assessment.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.rakuten.assessment.R;
import com.rakuten.assessment.model.ImageModel;
import com.rakuten.assessment.viewmodel.ImageListViewModel;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

  private ImageListViewModel imageListViewModel;

  private ImageView imageView;
  private TextView titleView;
  private TextView ownerView;
  private TextView secretView;
  private TextView serverView;
  private TextView jsonView;

  final private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = this::startObserveUI;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    imageView = view.findViewById(R.id.image);
    titleView = view.findViewById(R.id.title);
    ownerView = view.findViewById(R.id.owner);
    secretView = view.findViewById(R.id.secret);
    serverView = view.findViewById(R.id.server);
    jsonView = view.findViewById(R.id.json);

    imageListViewModel = new ViewModelProvider(requireActivity()).get(ImageListViewModel.class);

    view.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
  }

  private void startObserveUI() {
    if (getView() != null) {
      getView().getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
      if (!imageListViewModel.getActiveImage().hasActiveObservers()) {
        imageListViewModel.getActiveImage().observe(getViewLifecycleOwner(), this::updateUI);
      }
    }
  }

  private void updateUI(ImageModel image) {
    Picasso.get()
      .load(image.getUrl())
      .resize(imageView.getMeasuredWidth(), 0)
      .centerInside()
      .into(imageView);
    titleView.setText(image.title);
    ownerView.setText(image.owner);
    secretView.setText(image.secret);
    serverView.setText(image.server);
    jsonView.setText(new Gson().toJson(image));
  }
}
