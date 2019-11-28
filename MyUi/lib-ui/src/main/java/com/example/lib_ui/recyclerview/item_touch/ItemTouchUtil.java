package com.example.lib_ui.recyclerview.item_touch;

import android.graphics.Color;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchUtil<T> {

    private RecyclerView.Adapter mChannelAdapter;
    private List<T> mChannelBeanArrayList;
    private RecyclerView mRecyclerView;
    private ItemTouchCallback callback;
    private boolean isOrder = false;
    private int currentPagePosition;
    private int currentPageNewPosition;
    private ItemTouchHelper itemTouchHelper;

    public ItemTouchUtil(ItemTouchCallback callback, RecyclerView.Adapter mChannelAdapter, List<T> mChannelBeanArrayList, RecyclerView mRecyclerView){
        this.mChannelBeanArrayList = mChannelBeanArrayList;
        this.mChannelAdapter = mChannelAdapter;
        this.mRecyclerView = mRecyclerView;
        this.callback = callback;
    }

    public void initItemTouchHelper(){
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback(){

            //开启长按拖拽功能，默认为true【暂时用不到】
            //如果需要我们自定义拖拽和滑动，可以设置为false，然后调用itemTouchHelper.startDrag(ViewHolder)方法来开启！
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            //开始滑动功能，默认为true【暂时用不到】
            //如果需要我们自定义拖拽和滑动，可以设置为false，然后调用itemTouchHelper.startSwipe(ViewHolder)方法来开启！
            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            /*用于设置是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向
            比如如果是列表类型的RecyclerView，拖拽只有UP、DOWN两个方向
            而如果是网格类型的则有UP、DOWN、LEFT、RIGHT四个方向
            */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0;//dragFlags 是拖拽标志
                int swipeFlags = 0;//swipeFlags是侧滑标志，我们把swipeFlags 都设置为0，表示不处理滑动操作
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    swipeFlags = 0;
                } else {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    swipeFlags = 0;
                }
                Log.w("ItemTouchHelper","{getMovementFlags}dragFlags="+dragFlags+";swipeFlags="+swipeFlags);
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            /*如果我们设置了非0的dragFlags ，那么当我们长按item的时候就会进入拖拽并在拖拽过程中不断回调onMove()方法
            我们就在这个方法里获取当前拖拽的item和已经被拖拽到所处位置的item的ViewHolder，
            有了这2个ViewHolder，我们就可以交换他们的数据集并调用Adapter的notifyItemMoved方法来刷新item*/
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                moved();

                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                Log.w("ItemTouchHelper","{onMove}fromPosition="+fromPosition+";toPosition="+toPosition);
                //这里可以添加判断，实现某一项不可交换
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mChannelBeanArrayList, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mChannelBeanArrayList, i, i - 1);
                    }
                }
                mChannelAdapter.notifyItemMoved(fromPosition, toPosition);

                return true;
            }

            /*同理如果我们设置了非0的swipeFlags，我们在侧滑item的时候就会回调onSwiped的方法，我们不处理这个事件，空着就行了。*/
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
            //我们希望拖拽的Item在拖拽的过程中发生震动或者颜色变深，这样就需要继续重写下面两个方法
            //当长按选中item的时候（拖拽开始的时候）调用
            //ACTION_STATE_IDLE：闲置状态
            //ACTION_STATE_SWIPE：滑动状态
            //ACTION_STATE_DRAG：拖拽状态
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                Log.w("ItemTouchHelper","{onSelectedChanged}actionState="+actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    //获取系统震动服务

                    //震动70毫秒

                    viewHolder.itemView.setPressed(true);
                    viewHolder.itemView.setBackgroundColor(Color.parseColor("#ff0000"));//演示拖拽的时候item背景颜色加深（实际情况中去掉）
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            //当手指松开的时候（拖拽或滑动完成的时候）调用，这时候我们可以将item恢复为原来的状态（相对于背景颜色加深来说的）
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                Log.w("ItemTouchHelper","{clearView}viewHolder.getAdapterPosition="+viewHolder.getAdapterPosition());
                viewHolder.itemView.setPressed(false);
                currentPageNewPosition = viewHolder.getAdapterPosition();
                Log.w("ItemTouchHelper","{clearView}currentPagePosition="+currentPagePosition);
                Log.w("ItemTouchHelper","{clearView}currentPageNewPosition="+currentPageNewPosition);
                if(!(currentPagePosition == currentPageNewPosition)){
                    isOrder = true;

                    //执行其他方法，比如设置拖拽后的item为选中状态
                }

                viewHolder.itemView.setBackgroundColor(Color.parseColor("#c5c5c5"));//演示拖拽的完毕后item背景颜色恢复原样（实际情况中去掉）
                mChannelAdapter.notifyDataSetChanged();//解决重叠问题
            }
        });
        //设置是否可以排序
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }


    private void moved(){

        if (callback != null) {
            callback.onMove();
        }



    }

    public List<T> getChangedData(){
        return mChannelBeanArrayList;
    }

}
