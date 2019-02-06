package com.boostcamp.dreampicker.utils;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
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
    public static void setTagItems(@NonNull TagGroup tagGroup, @NonNull final List<String> items) {
        final List<String> tagItems = Arrays.asList(tagGroup.getTags());
        if(!tagItems.containsAll(items)) {
            tagGroup.setTags(items);
        }
    }

    @BindingAdapter({"rcProgress"})
    public static void setRcProgress(RoundCornerProgressBar progressBar, int value) {
        progressBar.setProgress(value);
    }

    @BindingAdapter({"rcMax"})
    public static void setRcMax(RoundCornerProgressBar progressBar, int value) {
        progressBar.setMax(value);
    }
}
