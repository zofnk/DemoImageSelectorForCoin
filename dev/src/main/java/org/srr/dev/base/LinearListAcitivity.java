package org.srr.dev.base;


import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;

import org.srr.dev.R;

import org.srr.dev.util.IML;
import org.srr.dev.view.xrecyclerview.XRecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class LinearListAcitivity extends BaseActivity {


    private XRecyclerView mList;
    private SwipeRefreshLayout srl;
    ViewStub empty;
    public LinearListAcitivity() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fr_linear_list;
    }

    @Override
    public void initView() {
        srl = findView(R.id.srl_fr_linear_list);
        empty=findView(R.id.vs_base_empty);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onPullRefresh();
            }
        });
        mList = (XRecyclerView)findViewById(R.id.rv_fr_linear_list);
        mList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                onXPullrefresh();
            }

            @Override
            public void onLoadMore() {
                onLoadingMore();
            }
        });
        View header = initHeader();
        View footer = initFooter();
        if (header != null) {
            mList.addHeaderView(header);
        }
        if (footer != null) {
            mList.addFootView(footer);
        }

        final RecyclerView.LayoutManager layoutManager = setManager();
        if (layoutManager != null) {
            mList.setLayoutManager(layoutManager);
        } else {
            mList.setLayoutManager(new LinearLayoutManager(this));
        }

        final RecyclerView.ItemDecoration itemDecoration = setDivide();
        if (itemDecoration != null) {
            mList.addItemDecoration(itemDecoration);
        }
        final RecyclerView.ItemAnimator itemAnimator = setAnimator();
        if (itemAnimator != null) {
            mList.setItemAnimator(itemAnimator);
        } else {
            mList.setItemAnimator(new DefaultItemAnimator());
        }
        mList.setAdapter(setAdapter());
    }

    public RecyclerView getmList() {
        return mList;
    }
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return srl;
    }

    public void setSrlayoutEnable(boolean enabled) {
        srl.setEnabled(enabled);
    }

    public void showSwiRefreshLayout() {
        if (srl != null) {
            srl.setRefreshing(true);
        }
    }

    public void dismissSwiRefreshLayout() {
        if (srl != null) {
            srl.setRefreshing(false);
        }
    }
    public void loadMoreComplete() {
        mList.loadMoreComplete();
    }

    public void noMoreComplete() {
        mList.noMoreLoading();
    }
    public void initComplete() {
        mList.initComplete();
    }

    public abstract RecyclerView.Adapter<RecyclerView.ViewHolder> setAdapter();

    public abstract void onPullRefresh();

    public void onLoadingMore() {
    }
    public void onXPullrefresh() {
    }

    public View initHeader() {
        return null;
    }

    public View initFooter() {
        return null;
    }

    public RecyclerView.LayoutManager setManager() {
        return null;
    }

    public RecyclerView.ItemDecoration setDivide() {
        return null;
    }

    public RecyclerView.ItemAnimator setAnimator() {
        return null;
    }
    public ViewStub getEmptyView() {
        return empty;
    }
}
