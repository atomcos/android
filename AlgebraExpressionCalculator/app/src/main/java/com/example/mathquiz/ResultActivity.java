package com.example.mathquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mathquiz.Model.AnswerResult;
import com.example.mathquiz.Model.ResultComparator;
import com.example.mathquiz.Model.ResultUnit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton rbAll, rbRight, rbWrong, rbSortA, rbSortB;
    private ListView lvResult;
    private EditText etName;
    private TextView tvScore;
    private Button btShow, btBack;
    private ArrayAdapter<ResultUnit> arrayAdapter;
    private ArrayList<ResultUnit> tmpResultList;
    public final static String EXTRA_KEY_NAME = "Extra_Key_Name";
    public final static String EXTRA_KEY_SCORE = "Extra_Key_Score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initData();
    }

    private void initData() {
        rbAll = findViewById(R.id.idActResultRdbShowAll);
        rbRight = findViewById(R.id.idActResultRdbShowRight);
        rbWrong = findViewById(R.id.idActResultRdbShowWrong);
        rbSortA = findViewById(R.id.idActResultRdbShowSortA);
        rbSortB = findViewById(R.id.idActResultRdbShowSortD);
        lvResult = findViewById(R.id.idActResultLvResult);
        etName = findViewById(R.id.idActResultEtName);
        tvScore = findViewById(R.id.idActResultTvScore);
        btShow = findViewById(R.id.idActResultBtnShow);
        btShow.setOnClickListener(this);
        btBack = findViewById(R.id.idActResultBtnBack);
        btBack.setOnClickListener(this);

        tvScore.setText(calcScore());
        initList();
    }

    private void initList() {
        tmpResultList = new ArrayList<>(MainActivity.resultList);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tmpResultList);
        lvResult.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idActResultBtnShow:
                doShow();
                break;
            case R.id.idActResultBtnBack:
                doBack();
                break;
        }
    }

    private String calcScore() {
        if (MainActivity.resultList.size() <= 0)
            return "0";
        int correctNum = 0;
        for (ResultUnit r : MainActivity.resultList)
            if (r.getAnswerResult() == AnswerResult.RIGHT)
                correctNum ++;
        return String.format("%.2f%%", (float)(correctNum * 100) / MainActivity.resultList.size());
    }
    private void manipulateListData(String strNotEqual, int sort) {//sort: -1/0/1  desc/none/asc sorting
        if (sort == 0) {
            arrayAdapter.clear();
            for (ResultUnit r : MainActivity.resultList)
                if (!r.getAnswerResult().toString().equals(strNotEqual))
                    tmpResultList.add(r);
        }
        if (sort > 0)
            Collections.sort(tmpResultList, new ResultComparator());
        else if (sort < 0)
            Collections.sort(tmpResultList, new ResultComparator.ResultComparatorDesc());
        arrayAdapter.notifyDataSetChanged();
    }
    private void doShow() {

        if (rbAll.isChecked()) {
            manipulateListData("",0);
        }
        else if (rbRight.isChecked()) {
            manipulateListData(AnswerResult.WRONG.toString(), 0);
        }
        else if (rbWrong.isChecked()) {
            manipulateListData(AnswerResult.RIGHT.toString(), 0);
        }
        else if (rbSortA.isChecked()) {
            manipulateListData(AnswerResult.RIGHT.toString(), 1);
        }
        else if (rbSortB.isChecked()) {
            manipulateListData(AnswerResult.RIGHT.toString(), -1);
        }

    }

    private void doBack() {
        if (!etName.getText().toString().trim().equals("")) {
            Intent intent = new Intent();
            intent.putExtra(ResultActivity.EXTRA_KEY_NAME, etName.getText().toString());
            intent.putExtra(ResultActivity.EXTRA_KEY_SCORE, tvScore.getText().toString());
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}
