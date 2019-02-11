package com.boostcamp.dreampicker.utils;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import me.gujun.android.taggroup.TagGroup;

public class BindingUtil {
    @SuppressWarnings("unchecked")
    @BindingAdapter({"items"})
    public static <T, VH extends RecyclerView.ViewHolder> void setItems(
            @NonNull final RecyclerView recyclerView,
            @Nullable final List<T> items) {
        final ListAdapter<T, VH> adapter = (ListAdapter<T, VH>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(items == null ? null : new ArrayList<>(items));
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"pagedItems"})
    public static <T, VH extends RecyclerView.ViewHolder> void setPagedItems(
            @NonNull final RecyclerView recyclerView,
            @Nullable final PagedList<T> items) {
        final PagedListAdapter<T, VH> adapter = (PagedListAdapter<T, VH>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(items);
        }
    }

    @BindingAdapter({"tagItems"})
    public static void setTagItems(@NonNull TagGroup tagGroup, @NonNull final List<String> items) {
        final List<String> tagItems = Arrays.asList(tagGroup.getTags());
        if(!tagItems.containsAll(items)) {
            tagGroup.setTags(items);
        }
    }

    @BindingAdapter({"vote"})
    public static void doVote(@NonNull CheckBox checkBox, @Nullable final String selectionId) {
        if(selectionId != null && !checkBox.isChecked()) {
            checkBox.performClick();
        }
    }

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter({"date"})
    public static void setDate(@NonNull final TextView textView, @NonNull final Date date) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-DD");
        textView.setText(format.format(date));
    }

    @BindingAdapter({"rcProgress"})
    public static void setRcProgress(RoundCornerProgressBar progressBar, int value) {
        progressBar.setProgress(value);
    }

    @BindingAdapter({"rcMax"})
    public static void setRcMax(RoundCornerProgressBar progressBar, int value) {
        progressBar.setMax(value);
    }

    @BindingAdapter({"visible"})
    public static void setVisibility(@NonNull final LinearLayout layout, @Nullable final String selectionId) {
        if(selectionId != null) {
            layout.setVisibility(View.VISIBLE);
        }
    }
}
