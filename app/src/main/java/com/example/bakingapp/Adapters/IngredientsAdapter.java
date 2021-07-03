package com.example.bakingapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Entries.IngredientEntry;
import com.example.bakingapp.JsonRef.JsonConstants;
import com.example.bakingapp.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {
    private static final String TAG = IngredientsAdapter.class.getSimpleName();
    private Context mContext;
    private List<IngredientEntry> ingredientsList;
    public IngredientsAdapter(Context mContext, List<IngredientEntry> ingredientsList) {
        this.mContext = mContext;
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.polished_ingredient_view_holder, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        String ingredient = ingredientsList.get(position).getIngredient();
        String measure = ingredientsList.get(position).getMeasure();
        float quantity = ingredientsList.get(position).getQuantity();
        holder.ingredientName.setText(ingredient);
        holder.unitNumber.setText(String.valueOf(quantity));
        holder.ingredientNumber.setText(String.valueOf(position +1));
        int unitNo = 0;

        for(int i = 0; i < JsonConstants.units.length; i++){
            if(measure.equals(JsonConstants.units[i])){
                unitNo = i;
                break;
            }
        }
        int unitIcon = JsonConstants.unitIcons[unitNo];
        Log.d("UNIT_NO: ", String.valueOf(unitIcon));
        String unitLongName = JsonConstants.unitName[unitNo];

        holder.unitIcon.setImageResource(unitIcon);
        holder.unitLongName.setText(unitLongName);


        holder.itemView.setOnClickListener(v -> {
            if(holder.ingredientChecked.getVisibility() == View.GONE){
                holder.ingredientChecked.setVisibility(View.VISIBLE);
            }
            else{
                holder.ingredientChecked.setVisibility(View.GONE);
            }


        });
    }

    @Override
    public int getItemCount() {
        if (ingredientsList == null) return 0;
        else return ingredientsList.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder{
        private TextView ingredientName;
        private TextView ingredientNumber;
        private TextView unitNumber;
        private ImageView unitIcon;
        private TextView unitLongName;
        private ImageView ingredientChecked;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.tv_ingredient_name);
            unitNumber = itemView.findViewById(R.id.tv_unit_number);
            unitIcon = itemView.findViewById(R.id.iv_unit_icon);
            unitLongName = itemView.findViewById(R.id.tv_unit_long_name);
            ingredientNumber = itemView.findViewById(R.id.tv_ingredient_number);
            ingredientChecked = itemView.findViewById(R.id.iv_ingredient_checked);
        }
    }

    public void setIngredientsList(List<IngredientEntry> ingredientsList) {
        if (ingredientsList!= null) {
            this.ingredientsList = ingredientsList;
            notifyDataSetChanged();
            Log.d(TAG, "set data successfully");
        }
    }
}
