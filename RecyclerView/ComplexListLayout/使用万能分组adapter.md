# 使用万能分组adapter

# 1,复制SectionedRecyclerViewAdapter.java到自己的工程中



# 2,首先需要自己写一个adapter继承SectionedRecyclerViewAdapter这个抽象类

比如: 

	public class TestAdapter extends SectionedRecyclerViewAdapter<HeaderHolder,
	        TestSectionHeaderHolder, TestSectionBodyHolder,
	        TestSectionFooterHolder, FooterHolder> 

其中涉及到几个泛型有几个泛型

- 第一个泛型(HeaderHolder)是RecyclerView的整个列表的Header的ViewHolder,一般是广告,轮播图等等
- 第二个(TestSectionHeaderHolder)是分组的每个组的header的ViewHolder
- 第三个(TestSectionBodyHolder)是分组的每个组内的item的ViewHolder
- 第四个(TestSectionFooterHolder)是分组的每个组内的footer的ViewHolder
- 第五个(FooterHolder)是RecyclerView的整个列表的footer的ViewHolder

# 3,自己的adapter需要实现下面这些父类的抽象方法

	/**
     * 整个列表是否有Header
     */
    protected abstract boolean hasHeader();

    /**
     * 返回分组的数量
     */
    protected abstract int getSectionCount();

    /**
     * 返回当前分组的item数量
     *
     * @param section 组头的位置
     */
    protected abstract int getItemCountForSection(int section);

    /**
     * 当前分组是否有footer
     *
     * @param section 组头的位置
     */
    protected abstract boolean hasFooterInSection(int section);

    /**
     * 为分组header创建一个类型为H的ViewHolder
     */
    protected abstract H onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType);

    /**
     * 为分组footer创建一个类型为F的ViewHolder
     *
     */
    protected abstract F onCreateSectionFooterViewHolder(ViewGroup parent, int viewType);

    /**
     * 为分组内容创建一个类型为VH的ViewHolder
     *
     */
    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    /**
     * 为整个列表创建一个类型为RH的ViewHolder
     *
     */
    protected abstract RH onCreateHeaderViewHolder(ViewGroup parent, int viewType);

    /**
     * 为整个列表创建一个类型为FO的ViewHolder
     *
     */
    protected abstract FO onCreateFooterViewHolder(ViewGroup parent, int viewType);

    /**
     * 绑定分组的Header数据
     */
    protected abstract void onBindSectionHeaderViewHolder(H holder, int section);

    /**
     * 绑定分组数据
     *
     */
    protected abstract void onBindItemViewHolder(VH holder, int section, int position);

    /**
     * 绑定整个列表的Header数据
     *
     */
    protected abstract void onBindHeaderViewHolder(RH holder);

    /**
     * 绑定分组的footer数据
     *
     */
    protected abstract void onBindSectionFooterViewHolder(F holder, int section);
	/**
     * 当footer不是上拉刷新时，复写此方法，如：点击查看更多或者更复杂的布局等
     *
     */
    protected abstract void onBindFooterOtherViewHolder(FO holder);

然后自己去实现里面的方法就行了,简单方便.还可以通过setEmptyView(emptyView)设置没有数据时显示的空布局.

# 4,如果需要实现上拉加载

## 简单的使用 ,则可以用LoadMoreListener.java

添加上拉刷新监听

	loadMoreListener = new LoadMoreListener(mGridLayoutManager) {
            @Override
            public void onLoadMore() {
                isPull = false;
                isLoading = true;
                mAdapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_MORE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //mPresenter.loadData(1);
                        List<TestEntity.BodyBean.EListBean> datas = DatasUtil.createDatas();
                        mAdapter.addMoreData(datas);

                        mAdapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
                        isLoading = false;
                    }
                }, 1000);
            }
        };
        rv.addOnScrollListener(loadMoreListener);

## 稍微高级点的控制,则用RecycleViewScrollHelper.java

# 5,添加分割线,则需要用到SectionedGridDivider.java

使用方式:

	mDivider = new SectionedGridDivider(this, 50, Color.parseColor("#F5F5F5"));
        rv.addItemDecoration(mDivider);

# 总结

今天早上看到的郭霖大神的微信公众号上推送的文章,然后我将它理解了一下,然后写下来,谢谢原创.微信公众号分享的文章地址为:http://mp.weixin.qq.com/s/XbR9M2oeYcvrhG6GgjIgGA

