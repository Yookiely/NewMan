package com.example.asus.newman;

/**
 * Created by asus on 2017/11/9.
 */
 import java.util.List;
 import android.support.v4.view.PagerAdapter;
 import android.view.View;
 import android.view.ViewGroup;
public class MyPagerAdapter extends PagerAdapter {
    private List<View> pageList;
    public MyPagerAdapter(List<View> pageList) {
            this.pageList = pageList;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        // 返回要展示的图片数量
         return pageList.size();
            }
    @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub

               return arg0 == arg1;
    }
    @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub

                View view = pageList.get(position);
            // 将指定位置的View加入到ViewGroup
                container.addView(view);
            // 将View作为key返回
                 return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        // 将当前位置的View移除
        container.removeView(pageList.get(position));
    }
}

