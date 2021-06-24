package com.example.bakingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private Context mContext;
    List<String> stepsList;
    public interface ViewStepInterface{
        void onClickStep(int id);
    }
    public static ViewStepInterface viewStepInterface;

    public StepsAdapter(Context mContext, ViewStepInterface stepInterface) {
        this.mContext = mContext;
        viewStepInterface = stepInterface;
        stepsList = new ArrayList<>();
        stepsList.add("Recipe Introduction");
        stepsList.add("Starting prep");
        stepsList.add("Prep the cookie crust.");
        stepsList.add("Press the crust into baking form.");
        stepsList.add("Start filling prep");

    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_steps_view_holder, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        String step = stepsList.get(position);
        holder.stepTextView.setText(step);
    }

    @Override
    public int getItemCount() {
        if (stepsList == null) return 0;
        else return stepsList.size();
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
}
