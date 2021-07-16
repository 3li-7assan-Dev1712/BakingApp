package com.example.bakingapp.Adapters;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Entries.StepsEntry;
import com.example.bakingapp.R;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private Context mContext;
    private List<StepsEntry> stepsEntries;
    private boolean mLandscapeModed;
    private int rowNo = 0;

    public void setmLandscapeModed(boolean mLandscapeModed) {
        this.mLandscapeModed = mLandscapeModed;
    }

    public interface ViewStepInterface{
        void onClickStep(int id);
    }
    public static ViewStepInterface viewStepInterface;

    public StepsAdapter(Context mContext, ViewStepInterface stepInterface) {
        this.mContext = mContext;
        viewStepInterface = stepInterface;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_steps_view_holder, parent, false);
        return new StepsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
       if (stepsEntries != null){
           String shortDes = stepsEntries.get(position).getShortDescription();
           holder.stepTextView.setText(shortDes);
           if (mLandscapeModed) {
               holder.itemView.findViewById(R.id.fast_food_background).setVisibility(View.INVISIBLE);
               if (position == rowNo) holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
               else  holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.lightStepColor));
               holder.itemView.setOnClickListener(v -> {
                   viewStepInterface.onClickStep(position);
                   rowNo = position;
                   if (Util.SDK_INT >= 22) {
                       int finalRadius = (int) Math.hypot(holder.itemView.getWidth() / 2f, holder.itemView.getHeight() / 2f);
                       Animator animator = ViewAnimationUtils.createCircularReveal(holder.itemView,
                               holder.itemView.getWidth() / 2,
                               holder.itemView.getHeight() / 2,
                               0,
                               finalRadius);
                       holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                       animator.start();
                   }
                   notifyDataSetChanged();
               });
           }
       }
    }

    @Override
    public int getItemCount() {
        if (stepsEntries == null) return 0;
        else return stepsEntries.size();
    }

    static class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView stepTextView;
        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            stepTextView = itemView.findViewById(R.id.recipeStepsTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            viewStepInterface.onClickStep(getBindingAdapterPosition());
        }
    }
    public void setStepsList(List<StepsEntry> stepsEntries){
        if (stepsEntries != null){
            this.stepsEntries = stepsEntries;
            notifyDataSetChanged();
            Log.d("TAG", "added the data successfully");
        }
    }
    public StepsEntry getStepById(int id){
        return stepsEntries.get(id);
    }
}
