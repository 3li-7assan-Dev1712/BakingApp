package com.example.bakingapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Entries.RecipeEntry;
import com.example.bakingapp.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private Context mContext;
    // now I'll just use a temporary list to provide the data for the adapter
    private List<RecipeEntry> entries;
    public interface ChooseRecipeInterface{
        void onChooseRecipe(int id);
    }
    public static ChooseRecipeInterface chooseRecipeInterface;

    public RecipeAdapter(Context mContext, ChooseRecipeInterface recipeInterface) {
        this.mContext = mContext;
        chooseRecipeInterface = recipeInterface;
    }

    public void setRecipesName(List<RecipeEntry> entries) {
        if (entries != null) {
            this.entries = entries;
            // to make sure that the adapter will use out updated data I'll:
            Log.d(TAG, "added the data successfully");
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "Inflating views");
        /* here I'll inflate the xml file (the view holder) to java objects so I can manipulate
         the views programmatically like setting the recipe name for each item in the list in the MainActivity :) */
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_view_holder, parent, false);

        // after I inflate the view (create java object -view-) I use it to create my custom view holder *_*
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (entries != null){
            Log.d(TAG, "binding in onBind");
            String recipeName = entries.get(position).getRecipeName();
            holder.recipeName.setText(recipeName);
        }
        Log.d(TAG, "data == null, cannot bind");
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getting count");
        if (entries != null)
            return entries.size();
        else
            return 0;
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeName;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeNameTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            chooseRecipeInterface.onChooseRecipe(getBindingAdapterPosition());
        }
    }
}
