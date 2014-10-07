package com.oatmeal.spy;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oatmeal.spy.game.*;


public class PlayerAdapter extends BaseAdapter {
    private Context mContext;
    public State mState;

    public PlayerAdapter(Context c, State s) {
        this.mContext = c;
        this.mState = s;
    }

    public int getCount() {
        return mState.num_players;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ImageView imageView;
        TextView labelView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(210, 210));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setId(R.id.player_image);
            labelView = new TextView(mContext);
            labelView.setGravity(Gravity.CENTER_HORIZONTAL);
            labelView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            labelView.setId(R.id.player_name);
            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER);
            layout.addView(imageView);
            layout.addView(labelView);
            view = layout;
        } else {
            LinearLayout layout = (LinearLayout)convertView;
            imageView = (ImageView)layout.findViewById(R.id.player_image);
            labelView = (TextView)layout.findViewById(R.id.player_name);
            view = convertView;
        }

        if (mState.isPlayerStarted(position)) {
            if (mState.isPlayerAlive(position)) {
              imageView.setImageResource(R.drawable.card1);
              labelView.setTextColor(mContext.getResources().getColor(R.color.black));
            } else {
              imageView.setImageResource(R.drawable.card2);
              labelView.setTextColor(mContext.getResources().getColor(R.color.red));
            }
        } else {
          imageView.setImageResource(R.drawable.card0);
        }

        labelView.setText(mState.getPlayerName(position));

        return view;
    }

}