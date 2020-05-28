package com.teach.teach1907;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.teach.data.TestInfo;
import com.teach.frame.ApiConfig;
import com.teach.frame.CommonPresenter;
import com.teach.frame.ICommonModel;
import com.teach.frame.ICommonPresenter;
import com.teach.frame.ICommonView;
import com.teach.frame.LoadTypeConfig;
import com.teach.frame.TestModel;
import com.teach.frame.utils.ParamHashMap;
import com.teach.teach1907.adapter.TestAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ICommonView {

    private RecyclerView mRecyclerView;
    private TestAdapter mTestAdapter;
    private int pageId = 0;
    private List<TestInfo.DataInfo> datas = new ArrayList<>();
    private ICommonPresenter mPresenter;
    private SmartRefreshLayout mRefreshLayout;
    private ICommonModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModel = new TestModel();
        mPresenter = new CommonPresenter(this,mModel);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRefreshLayout = findViewById(R.id.refreshLayout);

        final Map<String, Object> params = new ParamHashMap().add("c", "api").add("a", "getList");
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageId = 0;
                mPresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH,params,pageId);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageId++;
                mPresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.MORE,params,pageId);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTestAdapter = new TestAdapter(datas,this);
        mRecyclerView.setAdapter(mTestAdapter);

        mPresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.NORMAL,params,pageId);
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object[] pD) {
        switch (whichApi){
            case ApiConfig.TEST_GET:
                if (loadType == LoadTypeConfig.MORE){
                    mRefreshLayout.finishLoadMore();
                } else if (loadType == LoadTypeConfig.REFRESH){
                    mRefreshLayout.finishRefresh();
                    datas.clear();
                }
                List<TestInfo.DataInfo> datas = ((TestInfo)pD[0]).datas;
                MainActivity.this.datas.addAll(datas);
                mTestAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        Toast.makeText(MainActivity.this, pThrowable.getMessage()!=null ? pThrowable.getMessage():"网络请求发生错误", Toast.LENGTH_SHORT).show();
    }
}
