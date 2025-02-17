package com.example.appenglish.Presentation.Ui.Main;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appenglish.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private LottieAnimationView home, profile, flashcard, question, exam;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = findViewById(R.id.homeLottie);
        home.playAnimation();

        profile = findViewById(R.id.profileLottie);
        profile.playAnimation();

        flashcard = findViewById(R.id.flashCardLottie);
        flashcard.playAnimation();

        question = findViewById(R.id.questionLottie);
        question.playAnimation();
        exam = findViewById(R.id.exam);
        exam.playAnimation();

        bottomNavigationView = findViewById(R.id.bottomNav);


        viewPager = findViewById(R.id.viewPager);

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.homemenu).setChecked(true);
                        home.playAnimation();
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.flashCardMenu).setChecked(true);
                        flashcard.playAnimation();
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.examMenu).setChecked(true);
                        exam.playAnimation();
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.questionMenu).setChecked(true);
                        question.playAnimation();
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.profileMenu).setChecked(true);
                        profile.playAnimation();
                        break;


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homemenu) {
                    home.playAnimation();
                    viewPager.setCurrentItem(0);
                    return true;

                }
                if (item.getItemId() == R.id.flashCardMenu) {
                    flashcard.playAnimation();
                    viewPager.setCurrentItem(1);
                    return true;

                }
                if (item.getItemId() == R.id.examMenu) {
                    exam.playAnimation();
                    viewPager.setCurrentItem(2);
                    return true;

                }
                if (item.getItemId() == R.id.questionMenu) {
                    question.playAnimation();
                    viewPager.setCurrentItem(3);
                    return true;

                }
                if (item.getItemId() == R.id.profileMenu) {
                    profile.playAnimation();
                    viewPager.setCurrentItem(4);
                    return true;

                }
                return false;
            }
        });

    }
}