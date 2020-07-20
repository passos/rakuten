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
import com.rakuten.assessment.component.QuickReturnLayout;
import com.rakuten.assessment.model.ImageModel;
import com.rakuten.assessment.utils.Log;
import com.rakuten.assessment.view.adapter.ImageListAdapter;
import com.rakuten.assessment.viewmodel.ImageListViewModel;

public class MainFragment extends Fragment {

  private static final String TAG = Log.tag(MainFragment.class);
  private TextView textView;
  private ImageListViewModel imageListViewModel;
  private QuickReturnLayout quickReturnLayout;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_main, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    textView = view.findViewById(R.id.text_view);
    quickReturnLayout = view.findViewById(R.id.quick_return_action_bar);

    ImageListAdapter imageListAdapter = new ImageListAdapter((v, position, imageModel) -> {
      imageListViewModel.setActiveImage(imageModel);
      Navigation.findNavController(view).navigate(R.id.action_main_to_details);
    });

    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    recyclerView.setAdapter(imageListAdapter);

    imageListViewModel = new ViewModelProvider(requireActivity()).get(ImageListViewModel.class);
    imageListViewModel.getImages().observe(getViewLifecycleOwner(), imageModels -> {
      Log.d(TAG, "images updated %d", imageModels.size());
      imageListAdapter.submitList(imageModels);
    });
  }

  @Override
  public void onResume() {
    super.onResume();

    if (getActivity() != null) {
      quickReturnLayout.setTitle(R.id.title, (String) getActivity().getTitle());
      quickReturnLayout.setOnReturnClick(v -> {
        getParentFragmentManager().popBackStack();
      });
    }

    // update the titleView to active image's title
    ImageModel image = imageListViewModel.getActiveImage().getValue();
    if (image != null && textView != null) {
      textView.setText(image.title);
    }
  }
}
