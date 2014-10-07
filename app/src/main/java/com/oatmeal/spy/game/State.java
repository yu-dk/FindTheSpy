package com.oatmeal.spy.game;
import java.util.*;

class WordPair{
    String w1, w2;
    float f1;
    public WordPair(String s1, String s2, float f1) {
        int flag = s1.compareTo(s2);
        if (flag == 0)
            new IllegalArgumentException("two strings should be different\n");
        if ( flag < 0 ) {
            this.w1 = s1;
            this.w2 = s2;
            this.f1 = f1;
        } else {
            this.w1 = s2;
            this.w2 = s1;
            this.f1 = 1 - f1;
        }
    }
}

class PlayerInfo{
  public String name;
  public boolean is_active;
  public boolean is_started;

  public PlayerInfo(String n) {
    this.name = n;
    this.is_active = true;
    this.is_started = false;
  }

  public PlayerInfo killP(){
    this.is_active = false;
    return this;
  }

  public PlayerInfo startP(){
    this.is_started = true;
    return this;
  }

  public PlayerInfo renewP(){
      this.is_active = true;
      this.is_started = false;
      return this;
  }

}

public class State {
  public int num_players;
  public HashMap mplayers;
  public String spy_word;
  public String nonspy_word;
  public int spy_id;
  private WordLists wl;

  public State(int np, ArrayList<String> player_names) {
     this.num_players = np;
     this.wl = new WordLists();
     this.mplayers = new HashMap();
     String[] wp = this.wl.getWordPair();
     this.spy_word = wp[0];
     this.nonspy_word = wp[1];
     this.spy_id = (new Random()).nextInt(np);
     for (int i = 0; i < np; i ++ )
       mplayers.put(i, new PlayerInfo(player_names.get(i)));
  }

  public void newGame(){
     String[] wp = this.wl.getWordPair();
     spy_word = wp[0];
     nonspy_word = wp[1];
     spy_id = (new Random()).nextInt(num_players);
     for (int i = 0; i < num_players; i ++ )
        renewPlayer(i);
  }

  public void renewPlayer(int position){
      mplayers.put(position, ((PlayerInfo)(mplayers.get(position))).renewP());
  }

  public void startPlayer(int position){
      mplayers.put(position, ((PlayerInfo)(mplayers.get(position))).startP());
  }

  public void killPlayer(int position){
      mplayers.put(position, ((PlayerInfo)(mplayers.get(position))).killP());
  }

  public boolean isPlayerAlive(int position){
        return ((PlayerInfo)(this.mplayers).get(position)).is_active;
  }
  public boolean isPlayerStarted(int position){
        return ((PlayerInfo)(this.mplayers).get(position)).is_started;
  }
  public String getPlayerName(int position){
      return ((PlayerInfo)(this.mplayers).get(position)).name;
  }

  public String getWord(int position){
      return position == spy_id ? spy_word : nonspy_word;
  }

  public boolean wordListEmpty() {
    return wl.lwp.size() <= 0;
  }

  public boolean isGameOver(){
     int ln = 0;
     for (int i = 0; i < num_players; i++) {
         boolean ia = ((PlayerInfo) (this.mplayers).get(i)).is_active;
         if (!ia && i == spy_id ) return true;
         if (ia) ln++;
     }
     if (ln <= 2) return true;
  return false;
  }

}
