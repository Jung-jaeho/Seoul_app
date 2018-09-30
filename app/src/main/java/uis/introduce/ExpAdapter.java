package uis.introduce;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import uis.festival.R;


public class ExpAdapter extends BaseAdapter {

    ArrayList<String> groupitems;
    ArrayList<String> childitems;
    LayoutInflater mInflater = null;


    public ExpAdapter(ArrayList<String> groupitems, ArrayList<String> childitems, Context mInflater) {
        this.groupitems = groupitems;
        this.childitems = childitems;
        this.mInflater = (LayoutInflater)mInflater.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return groupitems.size();
    }

    @Override
    public Object getItem(int position) {
        return groupitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder;
        if(v == null){
            viewHolder = new ViewHolder();
            v = mInflater.inflate(R.layout.list_group_row, null);
            viewHolder.tv_question = (TextView) v.findViewById(R.id.question);
            viewHolder.iv_icon= (ImageView) v.findViewById(R.id.group_icon);
            viewHolder.rows = v.findViewById(R.id.title_row);
            viewHolder.expandableLayout = (ExpandableLayout) v.findViewById(R.id.exp_layout);
            viewHolder.tv_answer = (TextView) v.findViewById(R.id.answer);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.tv_question.setText(groupitems.get(position));
        viewHolder.tv_answer.setText(childitems.get(position));
        final ExpandableLayout list = viewHolder.expandableLayout;
        final ImageView image = viewHolder.iv_icon;
        final TextView question = viewHolder.tv_question;
        viewHolder.rows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.isExpanded()){
                    list.collapse();
                    image.setImageResource(R.drawable.right_arrow);
                    question.setTextColor(Color.parseColor("#4c4c4c"));

                }else{
                    list.expand();
                    image.setImageResource(R.drawable.down_arrow);
                    question.setTextColor(Color.parseColor("#084964"));
                }
            }
        });
        return v;
    }
    class ViewHolder {
        public TextView tv_answer;
        public TextView tv_question;
        public ImageView iv_icon;
        public LinearLayout rows;
        public ExpandableLayout expandableLayout;
    }
}
