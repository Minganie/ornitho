package com.ornithoaloreille.ornitho.view;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ornithoaloreille.ornitho.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizListFragment extends Fragment {
    private static final String TAG = QuizListFragment.class.getSimpleName();

    public static String TITLE;

    private Context context;


    public QuizListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_list, container, false);
    }

    public void setContext(Context context) {
        this.context = context;
        TITLE = context.getString(R.string.quizz);
    }
}
