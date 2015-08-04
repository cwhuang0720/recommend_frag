package com.example.cwhuang.test_frag;

//import android.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cwhuang on 2015/8/3.
 */
public class editorFrag extends Fragment{
        //implements View.OnClickListener {

    OnSelectListener mCallback;

    // Container Activity must implement this interface
    public interface OnSelectListener {
        public void onArticleSelected(String fragmentname);
        public void onArticleSelected(int num,Fragment f);
    }

    Fragment editorFrag;
    private float mPosX,mCurPosX,mPosY,mCurPosY;
    private String name = "";
    private String LatLnt = "";
    private String address = "";
    private Bundle obj = null;
    private String tag = "";

    public editorFrag(){

    }
    public void set_location(String name, String LatLnt, String address){
        this.name = name;
        this.LatLnt = LatLnt;
        this.address = address;
    }

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
        /*String name = obj.getString("name");
        String address = obj.getString("address");
        String LatLnt = obj.getString("latlnt");
        String url = obj.getString("url");
        set_location(name, LatLnt, address);
        return inflater.inflate(R.layout.fragment_2, container, false);*/
        int layout = obj.getInt("layout");
        tag = obj.getString("tag");
        Log.d("TAG","!!"+layout+","+tag);
        editorFrag = editorFrag.this.getFragmentManager().findFragmentByTag(tag);
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);  // 榜定按鈕事件
        setGestureListener(view);

        //view.findViewById(R.id.fragment1).setOnClickListener(this);
    }
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
                            && (Math.abs(mCurPosY - mPosY) < 25)){
                        //向下滑動
                        mCallback.onArticleSelected(1,editorFrag);

                        Log.d("TAG", "up !!u dont like it!!!"+mCurPosY+","+mPosY);
                    } else if (mCurPosX - mPosX < 0
                            && (Math.abs(mCurPosX - mPosX) > 25)
                            && (Math.abs(mCurPosY - mPosY) < 25)) {
                        //向上滑動
                        mCallback.onArticleSelected(2,editorFrag);
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
