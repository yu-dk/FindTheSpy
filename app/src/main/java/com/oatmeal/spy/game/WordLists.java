package com.oatmeal.spy.game;
import java.util.ArrayList;
import java.util.Random;

public class WordLists {
    public ArrayList<WordPair> lwp;
    public WordLists() {
        lwp = new ArrayList<WordPair>();
        lwp.add(new WordPair("strawberry", "raspberry", 0.5f));
        lwp.add(new WordPair("apple", "tomato", 0.5f));
        lwp.add(new WordPair("watermelon", "cucumber", 0.5f));
        //lwp.add(new WordPair("Norway", "Sweden", 0.5f));
        lwp.add(new WordPair("train", "flight", 0.5f));
        lwp.add(new WordPair("Tokyo", "Seoul", 0.5f));
        lwp.add(new WordPair("Iceland", "Greenland", 0.5f));
        lwp.add(new WordPair("vampire", "zoombie", 0.5f));
        lwp.add(new WordPair("chicken", "duck", 0.5f));
        lwp.add(new WordPair("running", "jogging", 0.5f));
    }

    public void addWordPair(WordPair ws) {
        // fixme: check if wordPair exists
        lwp.add(ws);
    }

    public String[] getWordPair(){
        if ( lwp.size() <= 0 ) return null;
        Random ri = new Random();
        int rin= ri.nextInt(lwp.size());
        WordPair wp = this.lwp.remove(rin);
        double p = Math.random();
        if ( p <= wp.f1 )  return new String[] {wp.w1, wp.w2};
        return new String[] {wp.w2, wp.w1};
    }

}
