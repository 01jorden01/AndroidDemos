package com.j_productions.database.view;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.j_productions.database.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    BottomAppBar bottomAppBar;
    // FloatingActionButton fab;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    // mTextMessage.setText(R.string.title_home);
                    showProductsFragment();
                    return true;
                case R.id.navigation_add:
                    //  mTextMessage.setText(R.string.title_dashboard);
                    showNewProductFragment();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//                Toast.makeText(Context.getActivity(), "Switch van plaats",
//                        Toast.LENGTH_LONG).show();
//
        //veranderen
//                if (bottomAppBar.getFabAlignmentMode() == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER)
//                 //   moveToEnd();
//                else {
//                    moveToCenter();
//                }
//            }
//        });

        // bottomAppBar.replaceMenu(R.menu.bottom_menu);

        FrameLayout frameLayout = findViewById(R.id.content);
        if (savedInstanceState == null) {
            //showNewProductFragment();
            showProductsFragment();
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //kleur navigatiebar
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colourPrimaryDark));
        }
    }


    private void showNewProductFragment() {
        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewProductFragment newProductFragment = new NewProductFragment();
        fragmentTransaction.replace(R.id.content, newProductFragment, "newProductFragment");
        fragmentTransaction.commit();
    }

    private void showProductsFragment() {
        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProductFragment productsFragment = new ProductFragment();
        fragmentTransaction.replace(R.id.content, productsFragment, "showProductsFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            //ik zit terug in mijn eerste fragment.
            //gebruiker klikt nogmaals op back --> app afsluiten
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }


//DEPRICATED (FAB + bottomAppbar animeren)(alpha01)
    //region  FAB ACTIONS on BOTTOMAPPBAR

//    private void centerToEndAnimation() {
//        detachFab();
//        moveToEnd();
//    }
//
//    //fab animation from end to center
//    private void endToCenterAnimation() {
//        detachFab();
//        moveToCenter();
//    }
//
//    private void detachFab() {
//        bottomAppBar.setFabAttached(false);
//    }
//
//    private void moveToEnd() {
//        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
//        attachFab();
//    }
//
//    private void moveToCenter() {
//        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
//        attachFab();
//    }
//
//    private void attachFab() {
//        bottomAppBar.setFabAttached(true);
//    }

    //#endregion
}
