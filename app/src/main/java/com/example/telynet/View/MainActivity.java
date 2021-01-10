package com.example.telynet.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.telynet.Presenter.Interface.MainActivityPresenterInterface;
import com.example.telynet.Presenter.MainActivityPresenter;
import com.example.telynet.R;

public class MainActivity extends AppCompatActivity {
    private MainActivityPresenterInterface mainActivityPresenter;
    private Button btnAscendingSort;
    private Button btnDescendingSort;
    private RadioButton rdVisitYes;
    private RadioButton rdVisitNo;
    private RadioButton rdVisitAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityPresenter = new MainActivityPresenter(getApplicationContext(), this);

        btnAscendingSort = (Button) findViewById(R.id.btn_ascending_sort);
        btnDescendingSort = (Button) findViewById(R.id.btn_descending_sort);
        rdVisitYes = (RadioButton) findViewById(R.id.rb_visit_yes);
        rdVisitNo = (RadioButton) findViewById(R.id.rb_visit_no);
        rdVisitAll = (RadioButton) findViewById(R.id.rb_visit_all);

        btnAscendingSortAction();

        btnDescendingSortAction();

        rdVisitAction();
    }

    private void btnAscendingSortAction() {
        btnAscendingSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityPresenter.applyAscendingSort();
            }
        });
    }

    private void btnDescendingSortAction() {
        btnDescendingSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityPresenter.applyDescendingSort();
            }
        });
    }

    private void rdVisitAction() {
        rdVisitYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyVisitFilter();
            }
        });

        rdVisitNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyVisitFilter();
            }
        });

        rdVisitAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyVisitFilter();
            }
        });
    }

    private void applyVisitFilter() {
        mainActivityPresenter.applyVisitFilter(rdVisitYes.isChecked(), rdVisitNo.isChecked());
    }
}