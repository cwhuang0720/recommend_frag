package com.example.cwhuang.test_frag;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity
                implements editorFrag.OnSelectListener{

    private ArrayList<Integer> layout_num = new ArrayList<Integer>();
    private Bundle args = null;


    public void onArticleSelected(String frag_name) {
        Log.d("TAG",frag_name+"!!!");
    }
    public void onArticleSelected(int num,Fragment old_frag,int layout_index) {

        Log.d("TAG","yo!!!"+num);

        //layout_index
        layout_index +=1;

        //how many layout
        if(layout_index <9) {
            //tag_name
            String tag_str = String.valueOf(layout_index);
            //set Bundle
            args = replace_layout(layout_num.get(layout_index), tag_str, layout_index);
            //set new fragment
            editorFrag new_frag = new editorFrag();
            new_frag.setArguments(args);

            //method1 : replace fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
                    .replace(R.id.fragment_container, new_frag)
                    .commit();

            /*
            //method2 : hide old fragment and add new fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(old_frag)
                    .add(R.id.fragment_container, new_frag, tag_str)
                    .addToBackStack(null)
                    .commit();*/
            Log.d("TAG", "hide:" + (layout_index - 1) + "add:" + layout_index+"!!");
        }else{
            Log.d("TAG","out of limit layout!!!");
        }


    }

    private Bundle replace_place(String name, String address,String latlnt){
        Bundle args = new Bundle();
        args.putString("name",name);
        args.putString("address", address);
        args.putString("latlnt", latlnt);
        return args;
    }

    private Bundle replace_layout(Integer layout,String tag,int layout_index){
        Bundle args = new Bundle();
        args.putInt("layout", layout);
        args.putInt("layout_index", layout_index);
        args.putString("tag", tag);

        return args;
    }

    private void add_layout(){
        layout_num.add(R.layout.fragment_1);
        layout_num.add(R.layout.fragment_2);
        layout_num.add(R.layout.fragment_3);
        layout_num.add(R.layout.fragment_4);
        layout_num.add(R.layout.fragment_5);
        layout_num.add(R.layout.fragment_6);
        layout_num.add(R.layout.fragment_7);
        layout_num.add(R.layout.fragment_8);
        layout_num.add(R.layout.fragment_9);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_layout();

        // Check that the activity is using the layout version with the fragment_container FrameLayout
        if(findViewById(R.id.fragment_container) != null)
        {
            // if we are being restored from a previous state, then we dont need to do anything and should
            // return or else we could end up with overlapping fragments.
            if(savedInstanceState != null)
                return;

            // Create an instance of editorFrag
            editorFrag firstFrag = new editorFrag();
            editorFrag secondFrag = new editorFrag();

            String tag_str = "0";
            args = replace_layout(layout_num.get(0), tag_str,Integer.parseInt(tag_str));
            firstFrag.setArguments(args);
            // add fragment to the fragment container layout
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, firstFrag, tag_str)
                    .addToBackStack(null)
                    .commit();
            /*
            tag = "second";
            args = replace_layout(layout_num.get(1),tag);
            firstFrag.setArguments(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, secondFrag,tag)
                    .hide(firstFrag)
                    .addToBackStack(null)
                    .commit();

            Log.d("TAG", "add commit!!");*/
            /*
            args = replace_layout(layout_num.get(1));
            firstFrag.setArguments(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,firstFrag)
                    .commit();
            Log.d("TAG", "replace!!");
            */
            //remove
            /*getSupportFragmentManager()
                    .beginTransaction()
                    .remove(firstFrag)
                    .commit();
            Log.d("TAG", "!!remove");*/

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
