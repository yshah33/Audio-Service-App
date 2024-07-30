/**********************************************************
 * Program 3 - Activity-1

 Course: CS 478, Spring 2024.
 Author: Yashwi S

 Description: This file contains the ChicagoLandmark class, which is a ListFragment representing
 a list of Chicago landmarks. It utilizes a ViewModel to manage the list data and item selection.
 ***********************************************************/
package com.example.project3;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

public class LandmarkFragment extends ListFragment {
    private ListViewModel mModel;

    // Fragment is attached to the activity
    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
    }

    // Create Fragment's view
    @Override
    public void onViewCreated(View view, Bundle savedState) {
        super.onViewCreated(view, savedState);

        // Initialize ViewModel and set adapter for the ListView
        mModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        setListAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.chi_landmark, MainActivity.landmark));
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Set item click listener for the ListView
        getListView().setOnItemClickListener((parent, view1, position, id) -> {
            getListView().setItemChecked(position, true);
            mModel.selectItem(position);
        });
    }

    // Save instance state to restore selected item position
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}