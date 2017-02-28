package com.zgq.wokao.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.ContextUtil;
import com.zgq.wokao.Util.DrawableUtil;
import com.zgq.wokao.model.paper.info.ExamPaperInfo;
import com.zgq.wokao.ui.view.RotateTextView;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/24.
 */

public class HomePaperAdapter extends RecyclerView.Adapter {
    //recyclerView adapter
    private android.content.Context context = ContextUtil.getContext();
    private ArrayList<ExamPaperInfo> paperInfos = null;
    private ArrayList<Boolean> isItemSelectedList = new ArrayList<>();
    private ArrayList<Integer> colorlabelList = null;
    private final int GROUPTYPE = 1;
    private final int ITEMTYPE = 2;
    private int viewType = GROUPTYPE;

    private PaperAdapterListener listener;

    public HomePaperAdapter(ArrayList<ExamPaperInfo> paperInfos,PaperAdapterListener listener) {
        this.listener = listener;
        this.paperInfos = paperInfos;
        initData();
    }

    private void initData() {
        initIsItemSelectedList();
        initColorLabelList();
    }

    private void initIsItemSelectedList() {
        isItemSelectedList.clear();
        for (int i = 0; i < paperInfos.size(); i++) {
            isItemSelectedList.add(false);
        }
    }

    private void initColorLabelList() {
        colorlabelList = DrawableUtil.getCircleDrawableSet(paperInfos.size());
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (paperInfos.size() == 0 || paperInfos == null) return;

        final int positionTmp = position;

        final PaperInfoViewHolder holder1 = (PaperInfoViewHolder) holder;
        final ExamPaperInfo info = paperInfos.get(position);
        //设置paperlabel的正面以及反面
//        holder1.paperLabel.setSidesStyle(new RotateTextView.UpAndDownSideStyle() {
//            @Override
//            public void setUpSideStyle() {
//                holder.paperLabel.setText(info.getTitle().substring(0, 1));
//                holder1.paperLabel.setBackground(context.getDrawable(colorlabelList.get(position)));
//                holder1.item.setBackgroundColor(context.getColor(R.color.colorWhite));
//            }
//
//            @Override
//            public void setDownSideStyle() {
//                holder1.paperLabel.setText("");
//                holder1.paperLabel.setBackground(context.getDrawable(R.drawable.circle_background_downside_right_icon));
//                holder1.item.setBackgroundColor(context.getColor(R.color.colorRecyclerViewItemSelectedBackGround));
//            }
//        });
        //title 以及label的初始显示
        if (info.getTitle() != null) {
            holder1.paperName.setText(info.getTitle());
        }
//        if (!isItemSelectedList.get(position)) {
//            holder1.paperLabel.setCurrentSide(RotateTextView.UPSIDE);
//            holder1.paperLabel.setText(info.getTitle().substring(0, 1));
//            holder1.paperLabel.setBackground(context.getDrawable(colorlabelList.get(position)));
//            holder1.item.setBackgroundColor(context.getColor(R.color.colorWhite));
//        } else {
//            holder1.paperLabel.setCurrentSide(RotateTextView.DOWNSIDE);
//            holder1.paperLabel.setText("√");
//            holder1.paperLabel.setBackground(context.getDrawable(R.drawable.circle_background_downside));
//            holder1.item.setBackgroundColor(context.getColor(R.color.colorRecyclerViewItemSelectedBackGround));
//        }
        //作者信息初始显示
        if (info.getAuthor() != null) {
            holder1.paperAuthorAndDate.setText(getAuthorAndData(info));
        }
        //是否收藏信息显示
        if (!info.isStared()) {
            holder1.paperStar.setBackground(context.getDrawable(R.drawable.inactive_star));
        } else {
            holder1.paperStar.setBackground(context.getDrawable(R.drawable.active_star));
        }
        holder1.paperStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.isStared()) {
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            info.setStared(false);
//                        }
//                    });
                    holder1.paperStar.setBackground(context.getDrawable(R.drawable.inactive_star));
                    listener.onStared(position,false);
//                    Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_SHORT).show();
                } else {
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            info.setStared(true);
//                        }
//                    });
                    holder1.paperStar.setBackground(context.getDrawable(R.drawable.active_star));
                    listener.onStared(position,true);
//                    Toast.makeText(getActivity(), "已收藏", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //paperlabel的单击事件
        holder1.paperLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder1.paperLabel.changeSide();
//                if ((holder1.paperLabel).getCurrentSide() == RotateTextView.UPSIDE) {
//                    isItemSelectedList.set(position, false);
//                } else {
//                    isItemSelectedList.set(position, true);
//                }
            }
        });
        //recyclerView item单击事件
        holder1.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(positionTmp);
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), QuestionsListActivity.class);
//                intent.putExtra("paperTitle", info.getTitle());
//                intent.putExtra("paperAuthor", info.getAuthor());
//                startActivity(intent);
            }
        });
        //recyclerView item长按事件
        holder1.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(positionTmp);
                return true;
            }
        });

        switch (holder.getItemViewType()) {
            case ITEMTYPE:
                break;
            case GROUPTYPE:
                holder1.groupTitle.setVisibility(View.VISIBLE);
                if (position == 0) {
                    holder1.groupTitle.setText("最近");
                } else {
                    holder1.groupTitle.setText("其他");
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 3) {
            return GROUPTYPE;
        } else {
            return ITEMTYPE;
        }
    }

    @Override
    public int getItemCount() {
        return paperInfos.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main_recyclerview_item, parent,false);
        return new PaperInfoViewHolder(view);
    }

    public void removeSpecificPosition(int position) {
        paperInfos.remove(position);
        isItemSelectedList.remove(position);
        colorlabelList.remove(position);
        notifyItemRemoved(position);
    }

    private void releaseSelectedView() {
        for (int i = 0; i < isItemSelectedList.size(); i++) {
            isItemSelectedList.set(i, false);
        }
        notifyDataSetChanged();
    }

    private String getAuthorAndData(ExamPaperInfo info) {
        String author = info.getAuthor();
        String lastDate = info.getLastStudyDate();
        if (author == null || author.equals("")) author = "      ";
        if (lastDate == null || lastDate.equals("")) {
            lastDate = "未学习";
        } else {
        }
        return author;
    }


    public class PaperInfoViewHolder extends RecyclerView.ViewHolder {
        public TextView paperName;
        public RotateTextView paperLabel;
        public TextView paperAuthorAndDate;
        public RelativeLayout item;
        public TextView paperStar;
        public TextView groupTitle;

        public PaperInfoViewHolder(View itemView) {
            super(itemView);
            item = (RelativeLayout) itemView.findViewById(R.id.list_item);
            paperName = (TextView) itemView.findViewById(R.id.paper_name);
            paperLabel = (RotateTextView) itemView.findViewById(R.id.question_label);
            paperLabel.setClickable(true);
            paperAuthorAndDate = (TextView) itemView.findViewById(R.id.auther);
            paperStar = (TextView) itemView.findViewById(R.id.activity_main_paper_star_tv);
            groupTitle = (TextView) itemView.findViewById(R.id.group_title);
        }
    }

    public interface PaperAdapterListener{
        public void onStared(int position,boolean isStared);
        public void onItemClick(int position);
        public void onItemLongClick(int position);
    }

}
