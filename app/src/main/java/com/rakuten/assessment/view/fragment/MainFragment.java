package com.rakuten.assessment.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rakuten.assessment.R;
import com.rakuten.assessment.model.ImageModel;
import com.rakuten.assessment.view.adapter.ImageListAdapter;
import com.rakuten.assessment.viewmodel.ImageListViewModel;

public class MainFragment extends Fragment {

  private TextView titleView;
  private ImageListViewModel imageListViewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_main, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    titleView = view.findViewById(R.id.title);

    ImageListAdapter imageListAdapter = new ImageListAdapter((v, position, imageModel) -> {
      imageListViewModel.setActiveImage(imageModel);
      Navigation.findNavController(view).navigate(R.id.action_main_to_details);
    });

    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    recyclerView.setAdapter(imageListAdapter);
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }

      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
      }
    });

    imageListViewModel = new ViewModelProvider(requireActivity()).get(ImageListViewModel.class);
    imageListViewModel.getImages().observe(getViewLifecycleOwner(), imageListAdapter::submitList);
  }

  @Override
  public void onResume() {
    super.onResume();

    // update the titleView to active image's title
    ImageModel image = imageListViewModel.getActiveImage().getValue();
    if (image != null && titleView != null) {
      titleView.setText(image.title);
    }
  }
}
