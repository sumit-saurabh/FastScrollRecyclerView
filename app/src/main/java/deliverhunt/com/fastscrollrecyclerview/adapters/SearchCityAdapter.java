package deliverhunt.com.fastscrollrecyclerview.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import deliverhunt.com.fastkroll.RecyclerViewFastScroller;
import deliverhunt.com.fastscrollrecyclerview.R;
import deliverhunt.com.fastscrollrecyclerview.listeners.RecyclerViewClickListener;

/**
 * Created by sumit.saurabh on 26/1/16.
 */

public final class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.ViewHolder>
    implements RecyclerViewFastScroller.BubbleTextGetter
{
    private final List<String> items;
    private static RecyclerViewClickListener mItemClickListener;

    public SearchCityAdapter(List<String> items)
    {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view =
            LayoutInflater.from(
                parent.getContext()).inflate(R.layout.recylerview_list_row,
                                             parent, false
            );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        String text = items.get(position);
        holder.setText(text);
    }

    public void SetOnItemClickListener(final RecyclerViewClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public String getTextToShowInBubble(final int pos)
    {
        return Character.toString(items.get(pos).charAt(0));
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        private final TextView textView;

        private ViewHolder(View itemView)
        {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        public void setText(CharSequence text)
        {
            textView.setText(text);
        }

        @Override
        public void onClick(View view)
        {
            if (null != mItemClickListener)
            {
                mItemClickListener.onItemClick(view, this.getLayoutPosition());
            }
        }
    }
}
