package com.example.saveit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saveit.model.Category;

import java.util.ArrayList;

/**
 * a class for the category adapter
 */
class CategoryAdapter extends RecyclerView.Adapter<CategoryItemHolder> {
    private ArrayList<Category> categories;
    private static final int[] iconImages = {R.drawable.money, R.drawable.tax, R.drawable.lipstick, R.drawable.id, R.drawable.house, R.drawable.garden, R.drawable.fish, R.drawable.fan, R.drawable.email, R.drawable.dog, R.drawable.car, R.drawable.cake, R.drawable.buy, R.drawable.cat, R.drawable.company};

    CategoryAdapter(ArrayList<Category> items) {
        categories = items;
    }

    @Override
    public CategoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View gridItem = inflater.inflate(R.layout.category_item, parent, false);
        return new CategoryItemHolder(gridItem);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemHolder holder, final int position) {
        Category catItem = categories.get(position);
        CategoryItemHolder catHolder = ((CategoryItemHolder) holder);
        catHolder.title.setText(catItem.getTitle());
        catHolder.image.setImageResource(iconImages[catItem.getImage()]);
    }

    @Override
    public int getItemCount() { return categories.size(); }

}

    /**
     * a class for a category holder
     */
    class CategoryItemHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public CategoryItemHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.category_img);
            title = itemView.findViewById(R.id.category_title);
        }
    }
