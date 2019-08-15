package com.example.mathquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathquiz.Model.AnswerResult;
import com.example.mathquiz.Model.ResultUnit;
import com.example.mathquiz.entity.AlgebraExpression;
import com.example.mathquiz.entity.BinaryNode;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvAlgebraExp, tvTitle;
    private EditText etUserResult;
    private BinaryNode currentAlgebraExpTreeRoot;
    public static ArrayList<ResultUnit> resultList = new ArrayList<>();
    public final static int REQUEST_CODE_RESULTACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // StringBuffer s = new StringBuffer(AlgebraExpression.EXPRESSION_SYMBOL);


        tvAlgebraExp = (TextView)findViewById(R.id.idActMainAlgebraExp);
        etUserResult = (EditText)findViewById(R.id.idActMainEtResult);
        tvTitle = findViewById(R.id.idActMainTvTitle);



        updateButton();
    }
    private void updateButton() {
        ViewGroup vGroup = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        ViewGroup mRootView = (ViewGroup)vGroup.getChildAt(0);
        traversalView(mRootView);
    }
    private void traversalView(ViewGroup rootView) {
        for(int i = 0; i<rootView.getChildCount(); i++)
        {
            View childVg = rootView.getChildAt(i);
            if(childVg instanceof ViewGroup)
                traversalView((ViewGroup) childVg);
            else if(childVg instanceof Button) {
                Button b = ((Button) childVg);
                b.setOnClickListener(this);
                b.setTag(b.getText());
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            Button b = (Button)view;
            if (Character.isDigit(b.getTag().toString().charAt(0))
                    || b.getTag().toString().equals(this.getString(R.string.main_dot))
                    || b.getTag().toString().equals(this.getString(R.string.main_minus))) {
                String result = etUserResult.getText() + b.getTag().toString();
                etUserResult.setText(result);
            }
            else if (b.getTag().toString().equals(this.getString(R.string.main_score))) {
                doScore();
            }
            else if (b.getTag().toString().equals(this.getString(R.string.main_generate))) {
                doGenerate();
            }
            else if (b.getTag().toString().equals(this.getString(R.string.main_validate))) {
                doValidate();
            }
            else if (b.getTag().toString().equals(this.getString(R.string.main_clear))) {
                doClear();
            }
            else if (b.getTag().toString().equals(this.getString(R.string.main_finish))) {
                finish();
            }

        }
    }

    private void doGenerate() {
        currentAlgebraExpTreeRoot = AlgebraExpression.createRandomAgebraExpressionBinTree(null);
        tvAlgebraExp.setText(AlgebraExpression.getAlgebraExp(currentAlgebraExpTreeRoot));
    }

    private void doScore() {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        startActivityForResult(intent, MainActivity.REQUEST_CODE_RESULTACTIVITY);
    }
    private void doValidate() {

        if (etUserResult.getText().toString().trim().equals("")) {
            Toast.makeText(this, R.string.err_id_empty_input, Toast.LENGTH_LONG).show();
            return ;
        }
        if (!isValidNumeric(etUserResult.getText().toString().trim())) {
            Toast.makeText(this, R.string.err_id_invalide_number, Toast.LENGTH_LONG).show();
            return ;
        }
        if(currentAlgebraExpTreeRoot == null) {
            Toast.makeText(this, "Please generate algebra expression", Toast.LENGTH_LONG).show();
            return ;
        }
        BigDecimal userResult = new BigDecimal(etUserResult.getText().toString());
        BigDecimal calcResult = AlgebraExpression.calculateExpression(currentAlgebraExpTreeRoot);
        //tvAlgebraExp.setText(calcResult.toString());
        AnswerResult isCorrect;
        String showResult;
        if (userResult.compareTo(calcResult) == 0) {
            isCorrect = AnswerResult.RIGHT;
            showResult = isCorrect.toString();
        }
        else {
            isCorrect = AnswerResult.WRONG;
            showResult = isCorrect.toString() + " \nCorrect answer is: " + calcResult.toString();
        }
        Toast.makeText(this, showResult, Toast.LENGTH_LONG).show();
        saveCurrentUserOperation(new ResultUnit(tvAlgebraExp.getText().toString(), calcResult, userResult, isCorrect));
        doClear();
    }
    private void doClear() {
        etUserResult.setText("");
        tvAlgebraExp.setText("");
        tvTitle.setText(R.string.app_title);
    }
    private void saveCurrentUserOperation(ResultUnit resultUnit) {
        MainActivity.resultList.add(resultUnit);
    }
    public boolean isValidNumeric(String s) {
        return s.matches("-?[0-9]+.?[0-9]*");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MainActivity.REQUEST_CODE_RESULTACTIVITY) {
            super.onActivityResult(requestCode, resultCode, data);
            String newTitle = (String) data.getStringExtra(ResultActivity.EXTRA_KEY_NAME) + " " + (String) data.getStringExtra(ResultActivity.EXTRA_KEY_SCORE);
            tvTitle.setText(newTitle);
        }
    }
}
