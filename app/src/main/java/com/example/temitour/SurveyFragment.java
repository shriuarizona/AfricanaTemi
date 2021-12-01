package com.example.temitour;

import androidx.fragment.app.Fragment;

public class SurveyFragment extends Fragment {

    private static int fragmentId = R.layout.survey_fragment_2;

    public SurveyFragment() {
        super(fragmentId);
    }

    /**
     * Sets the ID of the fragment layout to use next time this fragment is used
     * @param newFragmentId is the ID of the survey question fragment layout
     */
    public static void setFragmentId(int newFragmentId) {
        fragmentId = newFragmentId;
    }

}
