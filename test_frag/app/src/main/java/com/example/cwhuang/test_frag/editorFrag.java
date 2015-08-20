package com.example.cwhuang.test_frag;

//import android.app.Fragment;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorInflater;

/**
 * Created by cwhuang on 2015/8/3.
 * Recommendation Fragment layout
 */
public class editorFrag extends Fragment{


    OnSelectListener mCallback;

    // Container Activity must implement this interface
    public interface OnSelectListener {
        public void onArticleSelected(String fragmentname);
        public void onArticleSelected(int num,Fragment f,int layout_index);
    }

    Fragment editorFrag;
    public float mPosX,mCurPosX,mPosY,mCurPosY;
    private Bundle obj = null;
    private String tag = "";
    private int layout_index;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnSelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSelectListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        obj = getArguments();

        int layout = obj.getInt("layout");
        tag = obj.getString("tag");
        layout_index = obj.getInt("layout_index");
        Log.d("TAG", "!!" + layout + "," + tag + "," + layout_index);
        editorFrag = editorFrag.this.getFragmentManager().findFragmentByTag(tag);
        return inflater.inflate(layout, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);  // 榜定按鈕事件
        //setGestureListener(view);
        /*
        if(getView().findViewById(R.id.icon_like)!=null && getView().findViewById(R.id.icon_dislike)!=null){
            ImageView img_like = (ImageView) getView().findViewById(R.id.icon_like);
            ImageView img_dislike = (ImageView) getView().findViewById(R.id.icon_dislike);
            img_like.setOnTouchListener(likeListener);
            img_dislike.setOnTouchListener(dislikeListener);
            Log.d("TAG", "find it!!!");
        }
        */

        //set click event on imageview
        if(getView().findViewById(R.id.icon_like)!=null &&
           getView().findViewById(R.id.icon_cross)!=null &&
           getView().findViewById(R.id.icon_info)!=null) {
            ImageView img_click_like = (ImageView) getView().findViewById(R.id.icon_like);
            img_click_like.setOnClickListener(click_like);
            ImageView img_click_dislike = (ImageView) getView().findViewById(R.id.icon_cross);
            img_click_dislike.setOnClickListener(click_dislike);
            ImageView img_click_info = (ImageView) getView().findViewById(R.id.icon_info);
            img_click_info.setOnClickListener(click_info);
        }

        /*getView().findViewById(R.id.restaurant).setOnTouchListener(new MyTouchListener());
        getView().findViewById(R.id.restaurant).setOnDragListener(new MyDragListener());*/
        getView().findViewById(R.id.restaurant).setOnTouchListener(_MyTouchListener);
        getView().findViewById(R.id.fragment).setOnDragListener(_MyDragListener);

    }
    ///////////////////////
    private View.OnTouchListener _MyTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d("TAG", "onTouch!!");
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                mPosX = motionEvent.getX();
                mPosY = motionEvent.getY();
                Log.d("TAG", "onTouch!!" + mPosX + "," + mPosY);
                return true;
            } else {
                Log.d("TAG", "noTouch!!");
                return false;
            }

        }
    };
    private View.OnDragListener _MyDragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {

                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    Log.d("TAG", "ACTION_DRAG_STARTED!!");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("TAG", "ACTION_DRAG_ENTERED!!");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("TAG", "ACTION_DRAG_EXITED!!");
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    //Log.d("TAG", "ACTION_DRAG_LOCATION!!"+event.getX()+","+event.getY());
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup

                    View view = (View) event.getLocalState();
                    View view1 = getView().findViewById(R.id.button);
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    owner.removeView(view1);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    container.addView(view1);
                    view.setVisibility(View.VISIBLE);
                    mCurPosX = event.getX();
                    mCurPosY = event.getY();
                    if (mCurPosX - mPosX < 0 && (Math.abs(mCurPosX - mPosX) > 300)) {
                        //向上滑動
                        mCallback.onArticleSelected(2,editorFrag,layout_index);
                        Log.d("TAG", "up !!u dont like it!!!"+mCurPosY+","+mPosY);
                    }else if (mCurPosX - mPosX > 0 && (Math.abs(mCurPosX - mPosX) > 300)) {
                        //向上滑動
                        mCallback.onArticleSelected(2,editorFrag,layout_index);
                        Log.d("TAG", "up !!u like it!!!"+mCurPosY+","+mPosY);
                    }

                    Log.d("TAG","ACTION_DROP!!"+mCurPosX+","+mCurPosY);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundDrawable(normalShape);
                    Log.d("TAG", "ACTION_DRAG_ENDED!!");
                default:
                    Log.d("TAG", "no work!!");
                    break;
            }
            return true;
        }
    };



    ///////////////////////

    private View.OnClickListener click_like = new View.OnClickListener() {

        public void onClick(View view){
            //宣告要作的行為
            final Toast toast = Toast.makeText(view.getContext(),
                    "Like",
                    Toast.LENGTH_SHORT);
            toast.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 200);
        }
    };
    private View.OnClickListener click_dislike = new View.OnClickListener() {

        public void onClick(View view){
            //宣告要作的行為
            final Toast toast = Toast.makeText(view.getContext(),
                    "Dislike",
                    Toast.LENGTH_SHORT);
            toast.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 200);
        }
    };
    private View.OnClickListener click_info = new View.OnClickListener() {

        public void onClick(View view){
            //宣告要作的行為
            final Toast toast = Toast.makeText(view.getContext(),
                    "info",
                    Toast.LENGTH_SHORT);
            toast.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 200);
        }
    };

    private View.OnTouchListener likeListener = new View.OnTouchListener() {

        private int mx,my;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    mPosY = event.getY();
                    mPosX = event.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurPosX = event.getX();
                    mCurPosY = event.getY();

                    break;

                case MotionEvent.ACTION_UP:
                    if (mCurPosX - mPosX < 0
                            && (Math.abs(mCurPosX - mPosX) > 25)
                            && (Math.abs(mCurPosY - mPosY) < 75)) {
                        //向上滑動
                        //mCallback.onArticleSelected("two");
                        Log.d("TAG", "up !!u like it!!!"+mCurPosY+","+mPosY);
                        mx = (int)(event.getRawX()-mPosX);
                        my = (int)(event.getRawY()-mPosY);
                        v.layout(mx, my, mx + v.getWidth(), my + v.getHeight());

                        mCallback.onArticleSelected(2, editorFrag, layout_index);
                    }
                    break;
            }
            Log.e("address", String.valueOf(mx) + "~~" + String.valueOf(my));
            return true;
        }

    };
    private View.OnTouchListener dislikeListener = new View.OnTouchListener() {

        private int mx,my;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    mPosX = event.getX();
                    mPosY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurPosX = event.getX();
                    mCurPosY = event.getY();

                    break;

                case MotionEvent.ACTION_UP:
                    if (mCurPosX - mPosX > 0
                            && (Math.abs(mCurPosX - mPosX) > 25)
                            && (Math.abs(mCurPosY - mPosY) < 75)){
                        //向上滑動
                        Log.d("TAG", "up !!u dont like it!!!"+mCurPosY+","+mPosY);
                        mx = (int)(event.getRawX()-mPosX);
                        my = (int)(event.getRawY()-mPosY);
                        v.layout(mx,my,mx+v.getWidth(),my+v.getHeight());

                        mCallback.onArticleSelected(2, editorFrag, layout_index);
                    }
                    break;
            }
            Log.e("address", String.valueOf(mx) + "~~" + String.valueOf(my));
            return true;
        }

    };

    private void setGestureListener(View v){

        v.setOnTouchListener(new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    mPosX = event.getX();
                    mPosY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurPosX = event.getX();
                    mCurPosY = event.getY();

                    break;
                /*case MotionEvent.ACTION_UP:
                    if (mCurPosY - mPosY > 0
                            && (Math.abs(mCurPosY - mPosY) > 25)) {

                        Log.d("TAG", "up !!u dont like it!!!");
                    } else if (mCurPosY - mPosY < 0
                            && (Math.abs(mCurPosY - mPosY) > 25)) {
                        //向上滑动
                        Log.d("TAG", "up !!u like it!!!");
                    }

                    break;
                    */
                case MotionEvent.ACTION_UP:
                    if (mCurPosX - mPosX > 0
                            && (Math.abs(mCurPosX - mPosX) > 25)
                            && (Math.abs(mCurPosY - mPosY) < 75)){
                        //向下滑動
                        mCallback.onArticleSelected(1,editorFrag,layout_index);
                        //mCallback.onArticleSelected("one");
                        Log.d("TAG", "up !!u dont like it!!!"+mCurPosY+","+mPosY);
                    } else if (mCurPosX - mPosX < 0
                            && (Math.abs(mCurPosX - mPosX) > 25)
                            && (Math.abs(mCurPosY - mPosY) < 75)) {
                        //向上滑動
                        mCallback.onArticleSelected(2,editorFrag,layout_index);
                        //mCallback.onArticleSelected("two");
                        Log.d("TAG", "up !!u like it!!!"+mCurPosY+","+mPosY);
                    }

                    break;
            }
            return true;
        }

    });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



}


/*
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d("TAG", "onTouch!!");
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);

                return true;
            } else {
                Log.d("TAG", "noTouch!!");
                return false;
            }


        }

    }

    private final class MyDragListener implements View.OnDragListener{

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {

                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    Log.d("TAG", "ACTION_DRAG_STARTED!!");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("TAG", "ACTION_DRAG_ENTERED!!");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("TAG", "ACTION_DRAG_EXITED!!");
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    //Log.d("TAG", "ACTION_DRAG_LOCATION!!"+event.getX()+","+event.getY());
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup

                    View view = (View) event.getLocalState();
                    View view1 = getView().findViewById(R.id.button);
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    owner.removeView(view1);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    container.addView(view1);
                    view.setVisibility(View.VISIBLE);

                    Log.d("TAG","ACTION_DROP!!");
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundDrawable(normalShape);
                    Log.d("TAG", "ACTION_DRAG_ENDED!!");
                default:
                    Log.d("TAG", "no work!!");
                    break;
            }
            return true;
        }
    }*/

