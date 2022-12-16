package com.link.astroquiz;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    TextView question;
    public RadioGroup rb;
    RadioButton rb1, rb2, rb3;
    Button next;

    List<String> questionList;
    List<String> rb1List;
    List<String> rb2List;
    List<String> rb3List;

    List<String> userAnswers;
    List<String> correctAnswers;
    View fragmentContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questionList = Arrays.asList(getResources().getStringArray(R.array.questions));
        rb1List = Arrays.asList(getResources().getStringArray(R.array.rb1));
        rb2List = Arrays.asList(getResources().getStringArray(R.array.rb2));
        rb3List = Arrays.asList(getResources().getStringArray(R.array.rb3));
        correctAnswers = Arrays.asList((getResources().getStringArray(R.array.correctAnswers)));

        userAnswers = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, container, false);
        rb = view.findViewById(R.id.radiogroup);
        rb1 = view.findViewById(R.id.answer1RB);
        rb2 = view.findViewById(R.id.answer2RB);
        rb3 = view.findViewById(R.id.answer3RB);
        question = view.findViewById(R.id.question);
        rotateLists();
        next = view.findViewById(R.id.nextBtn);
        next.setOnClickListener(this);
        fragmentContainer = view.findViewById(R.id.fragment_container);
        return view;
    }

    //method used to add the user's answers in a list for a later comparison with the correct answers list
    public void collectAnswer() {

        int checkedAnswer = rb.getCheckedRadioButtonId();
        if (R.id.answer1RB == checkedAnswer) {
            userAnswers.add(rb1.getText().toString());
        } else if (R.id.answer2RB == checkedAnswer) {
            userAnswers.add(rb2.getText().toString());
        } else if (R.id.answer3RB == checkedAnswer) {
            userAnswers.add(rb3.getText().toString());
        } else {
            userAnswers.add("");
        }
    }

    //comparing the user's answers with the correct answers and giving points accordingly
    int score = 0;
    public void checkAnswers() {
        for (int i = 0; i < userAnswers.size() ; i++) {
            if (userAnswers.get(i).equals(correctAnswers.get(i))) {
                score++;
            }
        }
    }

    int clickCount = 1;
    @Override
    public void onClick(View view) {

        clickCount++;

        if (next.getText().toString().equals("Submit")) {
            collectAnswer();
            checkAnswers();

            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle("Your Score is : " + score);
            builder.setMessage("You answered " + score + " out of 5 questions correct.");
            builder.setPositiveButton("Ok", (dialog, which) -> {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        collectAnswer();
        if (userAnswers.size() == correctAnswers.size()-1) {
            next.setText(R.string.submit);
        }


        rb.clearCheck();

        rotateLists();
    }

    //method used for displaying on screen the next question and answers when user clicks next button
    public void rotateLists (){
        question.setText(questionList.get(0));
        Collections.rotate(questionList, -1);

        rb1.setText(rb1List.get(0));
        Collections.rotate(rb1List, -1);

        rb2.setText(rb2List.get(0));
        Collections.rotate(rb2List, -1);

        rb3.setText(rb3List.get(0));
        Collections.rotate(rb3List, -1);
    }

}