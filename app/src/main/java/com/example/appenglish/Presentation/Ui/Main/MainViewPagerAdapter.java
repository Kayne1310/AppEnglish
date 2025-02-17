package com.example.appenglish.Presentation.Ui.Main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appenglish.Presentation.Ui.Main.Exam.ExamFragment;
import com.example.appenglish.Presentation.Ui.Main.FlashCard.FlashCardFragment;
import com.example.appenglish.Presentation.Ui.Main.Home.HomeFragment;
import com.example.appenglish.Presentation.Ui.Main.Profile.ProfileFragment;
import com.example.appenglish.Presentation.Ui.Main.Question.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {


    private final List<Fragment> fragmentArrayList =new ArrayList<>();
    public MainViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new FlashCardFragment());
        fragmentArrayList.add(new ExamFragment());
        fragmentArrayList.add(new QuestionFragment());
        fragmentArrayList.add(new ProfileFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
