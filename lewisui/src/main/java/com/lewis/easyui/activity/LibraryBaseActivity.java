package com.lewis.easyui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lewis.easyui.R;
import com.lewis.easyui.event.BaseEvent;
import com.lewis.easyui.util.RelayoutViewTool;
import com.lewis.easyui.util.StatusBarTool;
import com.lewis.easyui.widget.swipebacklayout.SwipeBackActivity;
import com.lewis.easyui.widget.swipebacklayout.SwipeBackLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 *
 */
public abstract class LibraryBaseActivity extends SwipeBackActivity {

    private View baseActView;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getFragment() != null) {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.findFragmentById(android.R.id.content) == null) {
                fm.beginTransaction().add(android.R.id.content, getFragment()).commit();
            }
        }
    }

    public final Toolbar getToolbar() {
        return toolbar;
    }

    public void setContentView(int layoutResID, @ColorInt int systemBarColor) {
        View view = View.inflate(this, layoutResID, null);
        if (isRelayout()) {
            RelayoutViewTool.relayoutViewWithScale(view, getApplicationContext().getResources().getDisplayMetrics().widthPixels);
        }
        this.setContentView(view);
        if (systemBarColor > 0 && Build.VERSION.SDK_INT >= 21) {
            StatusBarTool.systemBarColor(this, systemBarColor);
        }
    }

    @Override
    public void setContentView(View view) {
        this.baseActView = view;
        super.setContentView(view);
        ButterKnife.bind(this);
        int edge = getEdgeTrackingEnabled();
        setSwipeBackEnable(edge > -1);
        if (edge > -1) {
            getSwipeBackLayout().setEdgeTrackingEnabled(edge);
        }

        toolbar = (Toolbar) findViewById(R.id.easy_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        initHeader();
        setWidgetState();
        initData();
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = View.inflate(this, layoutResID, null);
        if (isRelayout()) {
            RelayoutViewTool.relayoutViewWithScale(view, getApplicationContext().getResources().getDisplayMetrics().widthPixels);
        }
        this.setContentView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEvent event) {
    }

    protected View getBaseActView() {
        return baseActView;
    }

    protected void setBackgroundColor(int color) {
        baseActView.setBackgroundColor(color);
    }

    protected void setBackgroundResource(int resid) {
        baseActView.setBackgroundResource(resid);
    }


    protected boolean isRelayout() {
        return false;
    }

    protected LibraryBaseFragment getFragment() {
        return null;
    }

    protected int getEdgeTrackingEnabled() {
        return SwipeBackLayout.EDGE_LEFT;
    }

    protected abstract void initHeader();// 初始化头部

    protected abstract void setWidgetState();// 设置控件状态（注册监听or设置设配器）

    protected abstract void initData();// 获取数据

    protected <T> T getExtra(String key, T value) {
        Object o = null;
        if (value instanceof String) {
            o = this.getIntent().getStringExtra(key);
        } else if (value instanceof Boolean) {
            o = this.getIntent().getBooleanExtra(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Integer) {
            o = this.getIntent().getIntExtra(key, ((Integer) value).intValue());
        } else if (value instanceof Float) {
            o = this.getIntent().getFloatExtra(key, ((Float) value).floatValue());
        } else if (value instanceof Long) {
            o = this.getIntent().getLongExtra(key, ((Long) value).longValue());
        } else if (value instanceof Serializable) {
            o = this.getIntent().getSerializableExtra(key);
        }
        T t = (T) o;
        return t;
    }

    public Activity getTopActivity() {
        Activity top = this;
        while (top.getParent() != null) {
            top = top.getParent();
        }
        return top;
    }

    public void startActivity(Intent it) {
        super.startActivity(it);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
