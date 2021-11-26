package com.example.computermanage.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.computermanage.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class AdapterSlider extends SliderViewAdapter<AdapterSlider.Holder> {
    int[] images;

    public AdapterSlider(int[] images) {
        this.images = images;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider,null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        viewHolder.imageView.setImageResource(images[position]);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class Holder extends SliderViewAdapter.ViewHolder{
        ImageView imageView;
        public Holder(View view){
            super(view);
            imageView = view.findViewById(R.id.image_view);
        }

    }
}
