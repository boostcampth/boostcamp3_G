package com.boostcamp.dreampicker.databinding;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
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

    @BindingAdapter({"tagItems"})
    public static void setTagItems(@NonNull final TagGroup tagGroup, @Nullable final List<String> tagList) {
        if(tagList != null) {
            tagGroup.setTags(tagList);
        }
    }

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter({"date"})
    public static void setDate(@NonNull final TextView textView, @NonNull final Date date) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        textView.setText(format.format(date));
    }

    @BindingAdapter(value = {"rcProgress", "rcMax"}, requireAll = true)
    public static void setProgress(@NonNull final RoundCornerProgressBar progressBar, final int progress, final int max) {
        progressBar.setMax(max);
        progressBar.setProgress(progress);
    }
}
