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

    @BindingAdapter({"vote"})
    public static void doVote(@NonNull CheckBox checkBox, @Nullable final String selectionId) {
        if(selectionId != null && !checkBox.isChecked()) {
            checkBox.performClick();
        }
    }

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter({"date"})
    public static void setDate(@NonNull final TextView textView, @NonNull final Date date) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        textView.setText(format.format(date));
    }

    @BindingAdapter({"rcProgress"})
    public static void setRcProgress(@NonNull final RoundCornerProgressBar progressBar, final int value) {
        progressBar.setProgress(value);
    }

    @BindingAdapter({"rcMax"})
    public static void setRcMax(@NonNull final RoundCornerProgressBar progressBar, final int value) {
        progressBar.setMax(value);
    }

    @BindingAdapter({"visible"})
    public static void setVisibility(@NonNull final ProgressBar pb, final boolean isVisible) {
        if(isVisible) {
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
        }
    }
}
