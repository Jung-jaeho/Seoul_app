package uis.Question;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uis.Manage.AppManager;
import uis.Manage.AppQuestion;
import uis.festival.R;
import uis.introduce.ExpAdapter;

public class QuestionFragment extends Fragment {

    private TextView question;

    ListView mExpListview;
    ExpAdapter adapter;
    Button back_btn;
    ArrayList<String> groupitems;
    ArrayList<String> childitems;

    AppQuestion appQuestion = new AppQuestion(AppManager.getInstance().getLanguage());

    // 빈 생성자
    public QuestionFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.festival, container, false );

        question = (TextView) view.findViewById(R.id.category);
        question.setText("FAQ");
        back_btn = (Button)view.findViewById(R.id.festival_backbtn);
        back_btn.setVisibility(View.INVISIBLE);
        mExpListview = (ListView) view.findViewById(R.id.listView);
        initdata();

        return view;
    }

    private void initdata(){
        groupitems = new ArrayList<>();
        childitems = new ArrayList<>();

        String[] question = appQuestion.getQuestion();
        groupitems.add(question[0]);
        groupitems.add(question[1]);
        groupitems.add(question[2]);
        groupitems.add(question[3]);

        String[] answer = appQuestion.getQusetion1();
        childitems.add(answer[0] + "\n\n" + answer[1] + "\n\n" + answer[2] + "\n\n" + answer[3]);
        answer = appQuestion.getQuestion2();
        childitems.add(answer[0] + "\n\n" + answer[1] + "\n\n" + answer[2] + "\n\n" + answer[3]);
        answer = appQuestion.getQuestion3();
        childitems.add(answer[0] + "\n\n" + answer[1] + "\n\n" + answer[2] + "\n\n" + answer[3]);
        answer = appQuestion.getQuestion4();
        childitems.add(answer[0] + "\n\n" + answer[1] + "\n\n" + answer[2] + "\n\n" + answer[3]);

        initList();
    }
    public void initList(){
        adapter = new ExpAdapter(groupitems,childitems, getActivity());
        if (mExpListview.getAdapter() == null){
            mExpListview.setAdapter(adapter);
        }else
            adapter.notifyDataSetChanged();
    }

}
