package com.android.commonlibrary.adapter.item_adapter;

/**
 * Title:原生RecyclerView的adapter使用示例,仅做参考
 * description:
 * autor:pei
 * created on 2019/9/11
 */
//public class DefaultAdapter <T>extends RecyclerView.Adapter{
//
//    protected Context mContext;
//    protected View mLayoutView;
//    protected List<T> mData;
//
//    public DefaultAdapter(Context context,List<T>data){
//        this.mContext=context;
//        this.mData=data;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        mLayoutView= LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false);
//        ViewHolder viewHolder=new ViewHolder(mLayoutView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        String name=mData.get(position).toString();
//        ((ViewHolder)holder).mTvName.setText(name);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData==null?0:mData.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder{
//
//        TextView mTvName;
//
//        public ViewHolder(View view) {
//            super(view);
//            mTvName=(TextView)view.findViewById(R.id.tv_name);
//        }
//    }
//
//}
