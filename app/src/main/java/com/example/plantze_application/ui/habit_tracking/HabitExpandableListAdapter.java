package com.example.plantze_application.ui.habit_tracking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.plantze_application.R;

import java.util.List;
import java.util.Map;

public class HabitExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> categories; // List of categories (e.g., Transportation, Food)
    private Map<String, List<String>> habitsByCategory; // Map of category to its habits

    public HabitExpandableListAdapter(Context context, List<String> categories, Map<String, List<String>> habitsByCategory) {
        this.context = context;
        this.categories = categories;
        this.habitsByCategory = habitsByCategory;
    }

    @Override
    public int getGroupCount() {
        return categories.size(); // Number of categories
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String category = categories.get(groupPosition);
        return habitsByCategory.get(category).size(); // Number of habits in a category
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categories.get(groupPosition); // Category name
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String category = categories.get(groupPosition);
        return habitsByCategory.get(category).get(childPosition); // Habit name
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true; // IDs are stable
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String category = (String) getGroup(groupPosition);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(category); // Set category name
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String habit = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(habit); // Set habit name
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true; // Allow child items to be selectable
    }

    /**
     * Updates the adapter data and notifies it to refresh.
     *
     * @param categories        The updated list of categories.
     * @param habitsByCategory  The updated map of habits by category.
     */
    public void updateData(List<String> categories, Map<String, List<String>> habitsByCategory) {
        this.categories = categories;
        this.habitsByCategory = habitsByCategory;
        notifyDataSetChanged();
    }
}
