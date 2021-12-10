package com.shiyong.databinding;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andy.binding.BaseBindingAdapter;
import com.andy.binding.DataBindingActivity;
import com.andy.binding.vm.BaseViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shiyong.databinding.databinding.ActivityMainBinding;
import com.shiyong.databinding.databinding.ViewRecyclerviewItem1Binding;
import com.shiyong.databinding.databinding.ViewRecyclerviewItem2Binding;
import com.shiyong.databinding.databinding.ViewRecyclerviewItem3Binding;
import com.shiyong.databinding.databinding.ViewRecyclerviewItem4Binding;
import com.shiyong.databinding.databinding.ViewRecyclerviewItem5Binding;
import com.shiyong.databinding.databinding.ViewRecyclerviewItemBinding;
import com.shiyong.tagviews.TagContainerLayout;
import com.shiyong.tagviews.TagView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends DataBindingActivity <BaseViewModel, ActivityMainBinding>{

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    @Override
    protected void initData() {
        dataBinding.setActivity(this);
        setSupportActionBar(dataBinding.toolbar);
        List<List<String>> dataList = new ArrayList();
        List<String> list1 = new ArrayList<String>();
        list1.add("Java");
        list1.add("C++");
        list1.add("Python");
        list1.add("Swift");
        list1.add("你好，这是一个TAG。你好，这是一个TAG。你好，这是一个TAG。你好，这是一个TAG。");
        list1.add("PHP");
        list1.add("JavaScript");
        list1.add("Html");
        list1.add("Welcome to use AndroidTagView!");

        List<String> list2 = new ArrayList<String>();
        list2.add("China");
        list2.add("USA");
        list2.add("Austria");
        list2.add("Japan");
        list2.add("Sudan");
        list2.add("Spain");
        list2.add("UK");
        list2.add("Germany");
        list2.add("Niger");
        list2.add("Poland");
        list2.add("Norway");
        list2.add("Uruguay");
        list2.add("Brazil");

        List<String> list3 = new ArrayList<String>();
        list3.add("Persian");
        list3.add("波斯语");
        list3.add("فارسی");
        list3.add("Hello");
        list3.add("你好");
        list3.add("سلام");
        List<String> list4 = new ArrayList<String>();
        list4.add("Adele");
        list4.add("Whitney Houston");

        List<String> list5 = new ArrayList<String>();
        list5.add("Custom Red Color");
        list5.add("Custom Blue Color");
        dataList.add(list1);
        dataList.add(list2);
        dataList.add(list3);
        dataList.add(list4);
        dataList.add(list5);
        GridLayoutManager manager=new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL,false);
        dataBinding.include.recyclerView.setLayoutManager(manager);
        TagRecyclerViewAdapter mAdapter=new TagRecyclerViewAdapter();
        dataBinding.include.recyclerView.setAdapter(mAdapter);


        List<int[]> colors = new ArrayList<int[]>();
        //int[]color = {backgroundColor, tagBorderColor, tagTextColor, tagSelectedBackgroundColor}
        int[] col1 = {Color.parseColor("#ff0000"), Color.parseColor("#000000"), Color.parseColor("#ffffff"), Color.parseColor("#999999")};
        int[] col2 = {Color.parseColor("#0000ff"), Color.parseColor("#000000"), Color.parseColor("#ffffff"), Color.parseColor("#999999")};

        colors.add(col1);
        colors.add(col2);
        mAdapter.setDataList(dataList);



        // test in RecyclerView
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setVisibility(View.VISIBLE);
//        TagRecyclerViewAdapter adapter = new TagRecyclerViewAdapter(this, list3);
//        adapter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Click on TagContainerLayout", Toast.LENGTH_SHORT).show();
//            }
//        });
//        recyclerView.setAdapter(adapter);
    }

    /*private void loadImages(List<String> list) {
        String[] avatars = new String[]{"https://forums.oneplus.com/data/avatars/m/231/231279.jpg",
                "https://d1marr3m5x4iac.cloudfront.net/images/block/movies/17214/17214_aa.jpg",
                "https://lh3.googleusercontent.com/-KSI1bJ1aVS4/AAAAAAAAAAI/AAAAAAAAB9c/Vrgt6WyS5OU/il/photo.jpg"};

        for (int i=0; i<list.size(); i++) {
            final int index = i;
            Glide.with(mTagContainerLayout1.getContext())
                    .asBitmap()
                    .load(avatars[i % avatars.length])
                    .apply(new RequestOptions().override(85))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            mTagContainerLayout1.getTagView(index).setImage(resource);
                        }
                    });
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }*/

    class TagRecyclerViewAdapter extends BaseBindingAdapter<List<String>,ViewDataBinding> {

        @Override
        protected int getLayoutResId(int viewType) {
            int layoutId;
            switch (viewType){
                case 0:
                    layoutId=R.layout.view_recyclerview_item1;
                    break;
                case 1:
                    layoutId=R.layout.view_recyclerview_item2;
                    break;
                case 2:
                    layoutId=R.layout.view_recyclerview_item3;
                    break;
                case 3:
                    layoutId=R.layout.view_recyclerview_item4;
                    break;
                case 4:
                    layoutId=R.layout.view_recyclerview_item5;
                    break;
                default:
                    layoutId=R.layout.view_recyclerview_item;
                    break;
            }
            return layoutId;
        }
        @Override
        public int getItemViewType(int position) {
            return position;
//            return super.getItemViewType(position);
        }
        /**
         * RecyclerView.ViewHolder创建
         * @param holder
         */
        protected void onBindingHolderCreate(BaseBindingViewHolder holder) {
            //初始化
            if(holder.dbBinding instanceof ViewRecyclerviewItem1Binding){
                ViewRecyclerviewItem1Binding itemBinding= (ViewRecyclerviewItem1Binding) holder.dbBinding;
                itemBinding.tagcontainerLayout1.setDefaultImageDrawableID(R.drawable.yellow_avatar);
                itemBinding.btnAddTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemBinding.tagcontainerLayout1.addTag(itemBinding.textTag.getText().toString());
                        //Add tag in the specified position
                        //itemBinding.tagcontainerLayout1.addTag(itemBinding.textTag.getText().toString(), 4);
                    }
                });
                //itemBinding.tagcontainerLayout1.setMaxLines(1);
                itemBinding.tagcontainerLayout1.setOnTagClickListener(new TagView.OnTagClickListener() {
                    @Override
                    public void onTagClick(int position, String text) {
                        Toast.makeText(MainActivity.this, "click-position:" + position + ", text:" + text,
                                Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onTagLongClick(final int position, String text) {
                        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("long click")
                                .setMessage("You will delete this tag!")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (position <  itemBinding.tagcontainerLayout1.getChildCount()) {
                                            itemBinding.tagcontainerLayout1.removeTag(position);
                                        }
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        dialog.show();
                    }

                    @Override
                    public void onSelectedTagDrag(int position, String text) {
                    }

                    @Override
                    public void onTagCrossClick(int position) {
                        //itemBinding.tagcontainerLayout1.removeTag(position);
                        Toast.makeText(MainActivity.this, "Click TagView cross! position = " + position,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }else  if(holder.dbBinding instanceof ViewRecyclerviewItem3Binding){
                ViewRecyclerviewItem3Binding itemBinding= (ViewRecyclerviewItem3Binding) holder.dbBinding;
                itemBinding.tagcontainerLayout3.setOnTagClickListener(new TagView.OnTagClickListener() {
                    @Override
                    public void onTagClick(int position, String text) {
                        List<Integer> selectedPositions = itemBinding.tagcontainerLayout3.getSelectedTagViewPositions();
                        //deselect all tags when click on an unselected tag. Otherwise show toast.
                        if (selectedPositions.isEmpty() || selectedPositions.contains(position)) {
                            Toast.makeText(MainActivity.this, "click-position:" + position + ", text:" + text,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            //deselect all tags
                            for (int i : selectedPositions) {
                                itemBinding.tagcontainerLayout3.deselectTagView(i);
                            }
                        }

                    }
                    @Override
                    public void onTagLongClick(final int position, String text) {
                        itemBinding.tagcontainerLayout3.toggleSelectTagView(position);

                        List<Integer> selectedPositions = itemBinding.tagcontainerLayout3.getSelectedTagViewPositions();
                        Toast.makeText(MainActivity.this, "selected-positions:" + selectedPositions.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSelectedTagDrag(int position, String text) {
                        ClipData clip = ClipData.newPlainText("Text", text);
                        View view = itemBinding.tagcontainerLayout3.getTagView(position);
                        View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);
                        view.startDrag(clip, shadow, Boolean.TRUE, 0);
                    }
                    @Override
                    public void onTagCrossClick(int position) {
                    }
                });
            }
        }
        @Override
        protected void onBindItem(ViewDataBinding binding, List<String> items) {
            if(binding instanceof ViewRecyclerviewItemBinding){
                ViewRecyclerviewItemBinding itemBinding= (ViewRecyclerviewItemBinding) binding;
                itemBinding.tagcontainerLayout.setTags(items);
            }else if(binding instanceof ViewRecyclerviewItem1Binding){
                ViewRecyclerviewItem1Binding itemBinding= (ViewRecyclerviewItem1Binding) binding;
                itemBinding.tagcontainerLayout1.setTags(items);
                loadImages(itemBinding.tagcontainerLayout1,items);
            }else if(binding instanceof ViewRecyclerviewItem2Binding){
                ViewRecyclerviewItem2Binding itemBinding= (ViewRecyclerviewItem2Binding) binding;
                itemBinding.tagcontainerLayout2.setTags(items);
            }else if(binding instanceof ViewRecyclerviewItem3Binding){
                ViewRecyclerviewItem3Binding itemBinding= (ViewRecyclerviewItem3Binding) binding;
                itemBinding.tagcontainerLayout3.setTags(items);
            }else if(binding instanceof ViewRecyclerviewItem4Binding){
                ViewRecyclerviewItem4Binding itemBinding= (ViewRecyclerviewItem4Binding) binding;
                itemBinding.tagcontainerLayout4.setTags(items);
            }else if(binding instanceof ViewRecyclerviewItem5Binding){
                ViewRecyclerviewItem5Binding itemBinding= (ViewRecyclerviewItem5Binding) binding;
                List<int[]> colors = new ArrayList<int[]>();
                //int[]color = {backgroundColor, tagBorderColor, tagTextColor, tagSelectedBackgroundColor}
                int[] col1 = {Color.parseColor("#ff0000"), Color.parseColor("#000000"), Color.parseColor("#ffffff"), Color.parseColor("#999999")};
                int[] col2 = {Color.parseColor("#0000ff"), Color.parseColor("#000000"), Color.parseColor("#ffffff"), Color.parseColor("#999999")};

                colors.add(col1);
                colors.add(col2);

                itemBinding.tagcontainerLayout5.setTags(items, colors);
            }
        }
        private void loadImages(TagContainerLayout mTagContainerLayout1,List<String> list) {
            String[] avatars = new String[]{"https://forums.oneplus.com/data/avatars/m/231/231279.jpg",
                    "https://d1marr3m5x4iac.cloudfront.net/images/block/movies/17214/17214_aa.jpg",
                    "https://lh3.googleusercontent.com/-KSI1bJ1aVS4/AAAAAAAAAAI/AAAAAAAAB9c/Vrgt6WyS5OU/il/photo.jpg"};

            for (int i=0; i<list.size(); i++) {
                final int index = i;
                Glide.with(mTagContainerLayout1.getContext())
                        .asBitmap()
                        .load(avatars[i % avatars.length])
                        .apply(new RequestOptions().override(85))
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                mTagContainerLayout1.getTagView(index).setImage(resource);
                            }
                        });
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
