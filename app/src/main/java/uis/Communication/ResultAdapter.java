package uis.Communication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import uis.festival.R;


/* Adapter – 기존의 ListView에서 사용하는 Adapter와 같은 개념으로 데이터와 아이템에 대한 View생성
    ViewHolder – 재활용 View에 대한 모든 서브 뷰를 보유
    LayoutManager – 아이템의 항목을 배치
    ItemDecoration – 아이템 항목에서 서브뷰에 대한 처리
    ItemAnimation – 아이템 항목이 추가, 제거되거나 정렬될때 애니메이션 처리 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private ArrayList<String> sttData;
    private ArrayList<String> translateData;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView sttText, translateText;

        public ViewHolder(View view, int viewType) {
            super(view);

            if (viewType == 0) {
                sttText = (TextView) itemView.findViewById(R.id.right_text);
            } else {
                translateText = (TextView) itemView.findViewById(R.id.left_text);
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ResultAdapter(ArrayList<String> sttData, ArrayList<String> translateData) {
        this.sttData = sttData;
        this.translateData = translateData;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        ViewHolder vh;
        // create a new view
        if (viewType == 0) {
            View rightView = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_right, parent, false);
            vh = new ViewHolder(rightView, viewType);
        } else {
            View leftView = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_left, parent, false);
            vh = new ViewHolder(leftView, viewType);
        }

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if (holder.getItemViewType() == 0) {
            holder.sttText.setText(sttData.get(position / 2));
        } else {

            holder.translateText.setText(translateData.get((position) / 2));

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return sttData.size() + translateData.size();
    }

    // 각 아이템에 유니크한 아이드를 부여 -> setHasStableItd(true)
    @Override
    public long getItemId(int position) {
        return position;
    }

    void addResult(int position, String result) {
        if (!result.isEmpty()) {
            sttData.set(position, result);
            notifyDataSetChanged(); // sttData 안의 Data 변경 알림
        }
    }

    void addString() {
        sttData.add(new String());
        notifyItemInserted(sttData.size() - 1);
    }

    void errorRemove(int position) {
        if(!sttData.isEmpty()) {
            sttData.remove(position);
            notifyItemRemoved(position);
        }

    }

    void remove(int position) {
        if (position % 2 == 0) { // sttData를 삭제하라 선택
            int removePosition = position / 2;
            sttData.remove(removePosition);
            notifyItemRemoved(position);
            translateData.remove(removePosition);
            notifyItemRemoved(position + 1);
        } else { // translateData를 삭제하라 선택
            int removePosition = (position - 1) / 2;
            sttData.remove(removePosition);
            notifyItemRemoved(position - 1);
            translateData.remove(removePosition);
            notifyItemRemoved(position);
        }
    }

}