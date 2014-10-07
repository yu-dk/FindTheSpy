package com.oatmeal.spy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.oatmeal.spy.game.State;
import java.util.ArrayList;


public class GameActivity extends Activity {

    public State mState;
    PlayerAdapter mPlayerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_play);
        int num_players = getIntent().getExtras().getInt("NUM_PLAYERS");
        ArrayList<String> player_names = new ArrayList<String>();
        for (int i = 0; i < num_players; i++ ) {
            player_names.add("name_" + Integer.toString(i));
        }
        this.mState = new State(num_players, player_names);

        //Log.e(Integer.toString(mState.spy_id), "## spy_id");

        GridView gridview = (GridView) findViewById(R.id.gridview);
        mPlayerAdapter = new PlayerAdapter(this, this.mState);
        gridview.setAdapter(mPlayerAdapter);
        gridview.setOnItemClickListener(new OnItemClickStatus());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_skip_round) {
            mState.newGame();
            mPlayerAdapter.notifyDataSetInvalidated();
        }
        return super.onOptionsItemSelected(item);
    }

    class OnItemClickStatus implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
            if (!mState.isPlayerStarted(position)) {
                mState.startPlayer(position);
                viewShowWord(v, position);
            } else {
                TextView tv = (TextView) v.findViewById(R.id.player_name);
                if (tv.getText().toString() != mState.getPlayerName(position)) {
                    tv.setText(mState.getPlayerName(position));
                    tv.setTextColor(getResources().getColor(R.color.black));
                } else {  // kill or show words
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                    builder.setTitle(mState.getPlayerName(position))
                            .setItems(R.array.playerAction, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        Toast.makeText(GameActivity.this, mState.getWord(position), Toast.LENGTH_LONG).show();
                                    } else {
                                        mState.killPlayer(position);
                                        if (mState.isGameOver()) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                                            if (!mState.wordListEmpty()) {
                                                builder.setTitle("Game over");
                                                builder.setMessage("The spy is " + mState.getPlayerName(mState.spy_id));
                                                builder.setCancelable(false);
                                                builder.setPositiveButton("Start new game", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    mState.newGame();
                                                    mPlayerAdapter.notifyDataSetInvalidated();
                                                }
                                            });
                                        } else {
                                                builder.setTitle("Game over");
                                                builder.setMessage("The spy is " + mState.getPlayerName(mState.spy_id));
                                                builder.setMessage("No word left!");
                                            }
                                        builder.create().show();
                                        }

                                        mPlayerAdapter.notifyDataSetInvalidated();
                                    }
                                }
                            });
                    builder.create().show();
                }
            }
        }

        private void viewShowWord(View v, int position){
            ((ImageView)(v.findViewById(R.id.player_image)) ).setImageResource(R.drawable.card1);
            TextView tv = (TextView)v.findViewById(R.id.player_name);
            tv.setText(mState.getWord(position));
            tv.setTextColor(getResources().getColor(R.color.blue));
        }
    }


}

