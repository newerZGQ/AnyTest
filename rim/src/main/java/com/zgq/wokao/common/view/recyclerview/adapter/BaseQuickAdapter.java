package com.zgq.wokao.common.view.recyclerview.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.zgq.wokao.R;
import com.zgq.wokao.common.view.recyclerview.helper.ItemTouchHelperAdapter;
import com.zgq.wokao.common.view.recyclerview.helper.OnStartDragListener;
import com.zgq.wokao.common.view.recyclerview.helper.SimpleItemTouchHelperCallback;
import com.zgq.wokao.common.view.recyclerview.listener.OnItemClickListener;
import com.zgq.wokao.common.view.recyclerview.listener.OnItemLongClickListener;
import com.zgq.wokao.common.view.recyclerview.listener.OnItemMoveListener;
import com.zgq.wokao.common.view.recyclerview.listener.OnRemoveListener;
import com.zgq.wokao.common.view.recyclerview.listener.OnRequestDataListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by long on 2016/4/21.
 * 适配器基类
 */
public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemTouchHelperAdapter {

    public static final int HEADER_VIEW = 0x00000111;
    public static final int LOADING_VIEW = 0x00000222;
    public static final int FOOTER_VIEW = 0x00000333;
    public static final int EMPTY_VIEW = 0x00000555;
    public static final int FULL_VIEW = 0x00000666;
    public static final int FULL_VIEW_2 = 0x00000777;
    public static final int FULL_VIEW_3 = 0x00000888;

    protected Context context;
    protected int layoutResId;
    protected LayoutInflater layoutInflater;
    protected List<T> data;
    private View parentView;
    // head and footer
    private View headerView;
    private View footerView;
    // listener
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    private OnRequestDataListener onRequestDataListener;
    private OnRemoveListener removeDataListener;
    // drag and swipe
    private OnStartDragListener dragStartListener;
    private OnItemMoveListener itemMoveListener;
    private SimpleItemTouchHelperCallback dragCallback;
    private int dragFixCount = 0;  // 固定数量，从0开始算
    private Drawable dragFixDrawable;
    // load more
    private boolean isLoadMoreEnable;
    private boolean isLoadingNow;
    private boolean isNoMoreData;
    private String loadingStr;
    private View loadingView;
    private TextView loadingDesc;
    private SpinKitView loadingIcon;
    // empty
    private View emptyView;


    public BaseQuickAdapter(Context context) {
        this(context, null);
    }

    public BaseQuickAdapter(Context context, List<T> data) {
        layoutResId = attachLayoutRes();
        if (layoutResId == 0) {
            throw new IllegalAccessError("Layout resource ID must be valid!");
        }
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 绑定布局
     *
     * @return
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param holder A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(BaseViewHolder holder, T item);

    @Override
    public int getItemCount() {
        int count = data.size() + getHeaderViewsCount() + getFooterViewsCount();
        if (count == 0 && emptyView != null) {
            return 1;
        }
        if (isLoadMoreEnable && data.size() != 0) {
            count++;
        }
        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView != null && position == 0) {
            return HEADER_VIEW;
        } else if ((data.size() + getHeaderViewsCount()
                + getFooterViewsCount()) == 0 && emptyView != null) {
            return EMPTY_VIEW;
        } else if (isLoadMoreEnable) {
            if (position == (getItemCount() - 1)) {
                return LOADING_VIEW;
            } else if (footerView != null && position == (getItemCount() - 2)) {
                return FOOTER_VIEW;
            }
        } else if (footerView != null && position == (getItemCount() - 1)) {
            return FOOTER_VIEW;
        }
        return getDefItemViewType(position - getHeaderViewsCount());
    }

    /**
     * 获取 ItemView 类型，对于多种布局的 RecyclerView 有用
     *
     * @param position
     * @return
     */
    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * Called when a view created by this adapter has been attached to a window.
     * simple to solve item will layout using all
     * {@link #_setFullSpan(RecyclerView.ViewHolder)}
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW
                || type == LOADING_VIEW || type == FULL_VIEW
                || type == FULL_VIEW_2 || type == FULL_VIEW_3) {
            _setFullSpan(holder);
        }
    }

    /**
     * When set to true, the item will layout using all span area. That means, if orientation
     * is vertical, the view will have full width; if orientation is horizontal, the view will
     * have full height.
     * if the hold view use StaggeredGridLayoutManager they should using all span area
     *
     * @param holder True if this item should traverse all spans.
     */
    protected void _setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params
                    = (StaggeredGridLayoutManager.LayoutParams)
                    holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    return (type == EMPTY_VIEW
                            || type == HEADER_VIEW
                            || type == FOOTER_VIEW
                            || type == LOADING_VIEW
                            || type == FULL_VIEW
                            || type == FULL_VIEW_2
                            || type == FULL_VIEW_3)
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (parentView == null) {
            parentView = parent;
        }
        BaseViewHolder baseViewHolder;
        switch (viewType) {
            case LOADING_VIEW:
                baseViewHolder = new BaseViewHolder(loadingView);
                break;
            case HEADER_VIEW:
                baseViewHolder = new BaseViewHolder(headerView);
                break;
            case EMPTY_VIEW:
                baseViewHolder = new BaseViewHolder(emptyView);
                break;
            case FOOTER_VIEW:
                baseViewHolder = new BaseViewHolder(footerView);
                break;
            default:
                baseViewHolder = onCreateDefViewHolder(parent, viewType);
                // 设置用于单项刷新的tag标识
                baseViewHolder.itemView.setTag(R.id.view_holder_tag, baseViewHolder);
                _initItemClickListener(baseViewHolder);
                break;
        }
        return baseViewHolder;
    }

    /**
     * 创建 ViewHolder
     *
     * @param parent   parent
     * @param viewType ItemView 类型，对于多种布局的 RecyclerView 有用
     * @return BaseViewHolder
     */
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, layoutResId);
    }

    protected BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        View view = layoutInflater.inflate(layoutResId, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LOADING_VIEW:
                _loadMore();
                break;
            case HEADER_VIEW:
                break;
            case EMPTY_VIEW:
                break;
            case FOOTER_VIEW:
                break;
            default:
                convert((BaseViewHolder) holder, data.get(
                        holder.getLayoutPosition() - getHeaderViewsCount()));
                _setDragFixBackground(holder, position);
                break;
        }
    }


    /************************************* 加载更多 ****************************************/

    public void setRequestDataListener(OnRequestDataListener listener) {
        this.onRequestDataListener = listener;
        if (!isLoadMoreEnable) {
            this.enableLoadMore(true);
        }
    }

    public void enableLoadMore(boolean isEnable) {
        this.isLoadMoreEnable = isEnable;
        _initLoadingView();
    }

    public void setLoadStyle(Style style) {
        Sprite sprite = SpriteFactory.create(style);
        _initLoadingView();
        loadingIcon.setIndeterminateDrawable(sprite);
    }

    public void setLoadDesc(String desc) {
        _initLoadingView();
        loadingStr = desc;
        loadingDesc.setText(loadingStr);
    }

    public void setLoadColor(int color) {
        loadingDesc.setTextColor(color);
        loadingIcon.getIndeterminateDrawable().setColor(color);
    }

    /**
     * 加载完成
     */
    public void loadComplete() {
        isLoadingNow = false;
    }

    /**
     * 没有更多数据，后面不再加载数据
     */
    public void noMoreData() {
        isLoadingNow = false;
        isNoMoreData = true;
        loadingIcon.setVisibility(View.GONE);
        loadingDesc.setText(R.string.no_more_data);
    }

    /**
     * 加载数据异常，重新进入可再加载数据
     */
    public void loadAbnormal() {
        isLoadingNow = false;
        loadingIcon.setVisibility(View.GONE);
        loadingDesc.setText(R.string.load_abnormal);
    }

    private void _initLoadingView() {
        if (loadingView == null) {
            loadingView = layoutInflater.inflate(R.layout.layout_load_more, null);
            loadingView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            loadingDesc = (TextView) loadingView.findViewById(R.id.tv_loading_desc);
            loadingIcon = (SpinKitView) loadingView.findViewById(R.id.iv_loading_icon);
            loadingStr = context.getResources().getString(R.string.loading_desc);
            loadingDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isLoadingNow && !isNoMoreData) {
                        _loadMore();
                    }
                }
            });
        }
    }

    private void _loadMore() {
        if (!isLoadingNow && onRequestDataListener != null && !isNoMoreData) {
            if (loadingIcon.getVisibility() == View.GONE) {
                loadingIcon.setVisibility(View.VISIBLE);
                loadingDesc.setText(loadingStr);
            }
            isLoadingNow = true;
            onRequestDataListener.onLoadMore();
        }
    }

    /************************************* 头尾视图 ****************************************/

    public View getHeaderView() {
        return headerView;
    }

    public void addHeaderView(View headerView) {
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.headerView = headerView;
        notifyDataSetChanged();
    }

    public View getFooterView() {
        return footerView;
    }

    public void addFooterView(View footerView) {
        footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.footerView = footerView;
        notifyDataSetChanged();
    }

    public int getHeaderViewsCount() {
        return headerView == null ? 0 : 1;
    }

    public int getFooterViewsCount() {
        return footerView == null ? 0 : 1;
    }

    /************************************空数据****************************************/

    public View getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public int getEmptyViewCount() {
        return emptyView == null ? 0 : 1;
    }

    /************************************数据操作****************************************/

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    public T getItem(int position) {
        return data.get(position);
    }

    /**
     * Get the data of list
     *
     * @return
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 更新数据，替换原有数据
     *
     * @param items
     */
    public void updateItems(List<T> items) {
        data = new ArrayList<>(items);
        notifyDataSetChanged();
        isNoMoreData = false;
    }

    private void _addItem(int position, T item) {
        if (data == null || data.size() == 0) {
            data = new ArrayList<>();
            data.add(item);
        } else {
            data.add(position, item);
        }
    }

    private void _addItemList(int position, List<T> items) {
        if (data == null || data.size() == 0) {
            data = new ArrayList<>();
        }
        data.addAll(position, items);
    }

    /**
     * 首部插入一条数据
     *
     * @param item 数据
     */
    public void addItem(T item) {
        _addItem(0, item);
        notifyItemInserted(0);
    }

    /**
     * 插入一条数据
     *
     * @param item     数据
     * @param position 插入位置
     */
    public void addItem(T item, int position) {
        position = Math.min(position, data.size());
        _addItem(position, item);
        notifyItemInserted(_calcPosition(position));
    }

    /**
     * 尾部插入一条数据
     *
     * @param item 数据
     */
    public void addLastItem(T item) {
        _addItem(data.size(), item);
        notifyItemInserted(_calcPosition(data.size()));
    }

    /**
     * 在列表尾添加一串数据
     *
     * @param items
     */
    public void addItems(List<T> items) {
        _addItemList(data.size(), items);
        int position = _calcPosition(data.size());
        for (T item : items) {
            notifyItemInserted(position++);
        }
    }

    /**
     * 在列表尾添加一串数据
     *
     * @param items
     */
    public void addItems(List<T> items, int position) {
        position = Math.min(position, data.size());
        _addItemList(position, items);
        int pos = _calcPosition(position);
        for (T item : items) {
            notifyItemInserted(pos++);
        }
    }

    /**
     * 移除一条数据
     *
     * @param position 位置
     */
    public void removeItem(int position) {
        if (position > data.size() - 1) {
            return;
        }
        int pos = _calcPosition(position);
        if (removeDataListener != null) {
            // 放在 mData.remove(pos) 前，不然外面获取不到数据
            removeDataListener.onItemRemove(pos);
        }
        data.remove(position);
        notifyItemRemoved(pos);
    }

    /**
     * 移除一条数据
     *
     * @param item 数据
     */
    public void removeItem(T item) {
        int pos = 0;
        for (T info : data) {
            if (item.hashCode() == info.hashCode()) {
                removeItem(pos);
                break;
            }
            pos++;
        }
    }

    /**
     * 清除所有数据
     */
    public void cleanItems() {
        data.clear();
        notifyDataSetChanged();
    }

    /**
     * 计算位置，算上头部
     *
     * @param position
     * @return
     */
    private int _calcPosition(int position) {
        if (headerView != null) {
            position++;
        }
        return position;
    }

    /************************************监听****************************************/

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked and held
     *
     * @param listener The callback that will run
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    /**
     * 设置移除监听
     *
     * @param removeDataListener
     */
    public void setRemoveDataListener(OnRemoveListener removeDataListener) {
        this.removeDataListener = removeDataListener;
    }

    /**
     * init the baseViewHolder to register onRecyclerViewItemClickListener
     * and onRecyclerViewItemLongClickListener
     *
     * @param baseViewHolder
     */
    private void _initItemClickListener(final BaseViewHolder baseViewHolder) {
        if (itemClickListener != null) {
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, baseViewHolder.getLayoutPosition());
                }
            });
        }
        if (itemLongClickListener != null) {
            baseViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return itemLongClickListener.onItemLongClick(v, baseViewHolder.getLayoutPosition());
                }
            });
        }
        if (dragCallback != null && dragFixCount > 0) {
            baseViewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (baseViewHolder.getLayoutPosition() < dragFixCount) {
                        dragCallback.setEnable(false);
                    } else {
                        dragCallback.setEnable(true);
                    }
                    return false;
                }
            });
        }
    }

    /************************************拖拽滑动****************************************/

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition >= dragFixCount && toPosition >= dragFixCount) {
            Collections.swap(data, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            if (itemMoveListener != null) {
                itemMoveListener.onItemMove(fromPosition, toPosition);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        removeItem(position);
    }

    public void setDragStartListener(OnStartDragListener dragStartListener) {
        this.dragStartListener = dragStartListener;
    }

    public void setDragCallback(SimpleItemTouchHelperCallback dragCallback) {
        this.dragCallback = dragCallback;
    }

    protected void startDrag(RecyclerView.ViewHolder viewHolder) {
        if (dragStartListener != null) {
            dragStartListener.onStartDrag(viewHolder);
        }
    }

    public void setItemMoveListener(OnItemMoveListener itemMoveListener) {
        this.itemMoveListener = itemMoveListener;
    }

    /**
     * 该方法在添加列表数据前调用
     *
     * @param dragFixCount 固定的数量
     */
    public void setDragFixCount(int dragFixCount) {
        this.dragFixCount = dragFixCount;
        if (dragFixDrawable == null) {
            dragFixDrawable = ContextCompat.getDrawable(context, R.drawable.shape_drag_default);
        }
    }

    public void setDragFixDrawable(int fixColor) {
        dragFixDrawable = new ColorDrawable(fixColor);
    }

    public void setDragFixDrawable(Drawable drawable) {
        dragFixDrawable = drawable;
    }

    public void setDragColor(int dragColor) {
        BaseViewHolder.setDragColor(dragColor);
    }

    public void setDragDrawable(Drawable drawable) {
        BaseViewHolder.setDragDrawable(drawable);
    }

    /**
     * 设置固定项的背景色
     *
     * @param holder
     * @param position
     */
    private void _setDragFixBackground(RecyclerView.ViewHolder holder, int position) {
        if (position < dragFixCount) {
            holder.itemView.setBackgroundDrawable(dragFixDrawable);
        }
    }

    /**
     * 设置拖拽控制标志位
     * eg: ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
     * ItemTouchHelper.RIGHT | ItemTouchHelper.START | ItemTouchHelper.END
     *
     * @param dragFlags
     */
    public void setDragFlags(int dragFlags) {
        if (dragCallback != null) {
            dragCallback.setDragFlags(dragFlags);
        }
    }

    /**
     * 设置滑动控制标志位
     * eg: ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
     * ItemTouchHelper.RIGHT | ItemTouchHelper.START | ItemTouchHelper.END
     *
     * @param swipeFlags
     */
    public void setSwipeFlags(int swipeFlags) {
        if (dragCallback != null) {
            dragCallback.setSwipeFlags(swipeFlags);
        }
    }

    /************************************* Tag标志 ****************************************/

    /**
     * 给BaseViewHolder设置Tag
     *
     * @param viewHolder 目标BaseViewHolder
     * @param tag        tag标志
     */
    public void setTag(BaseViewHolder viewHolder, Object tag) {
        viewHolder.itemView.setTag(tag);
    }

    /**
     * 根据tag标志获取BaseViewHolder
     *
     * @param tag tag标志
     * @return 目标BaseViewHolder
     */
    public BaseViewHolder getTag(Object tag) {
        View view = parentView.findViewWithTag(tag);
        if (view == null) {
            return null;
        }
        return (BaseViewHolder) view.getTag(R.id.view_holder_tag);
    }
}
