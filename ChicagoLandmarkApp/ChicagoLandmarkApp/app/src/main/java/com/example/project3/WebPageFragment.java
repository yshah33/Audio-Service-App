package com.example.project3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class WebPageFragment extends Fragment {
    private WebView mWebView;
    private int mCurrIdx = -1;
    private int webPageNum;
    private ListViewModel mModel;

    // Public constructor
    public WebPageFragment() {
        super();
    }

    // Inflates the layout for the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.web_page,
                container, false);
    }

    // Create Fragment's view
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        mWebView = view.findViewById(R.id.web_pageView);

        // Initialize ViewModel
        mModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);
        // Observe the selected item in the ViewModel
        mModel.getSelectedItem().observe(getViewLifecycleOwner(), item -> {
            if (isValidPageIndex(item)) {
                mCurrIdx = item;
                mWebView.loadUrl(MainActivity.webPage[mCurrIdx]);   // Load URL in WebView
            }
        });
        webPageNum = MainActivity.webPage.length;
    }

    // Check if the page index is valid
    private boolean isValidPageIndex(int item) {
        return item >= 0 && item < webPageNum;
    }
}
