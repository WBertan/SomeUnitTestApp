package com.wbertan.someunittestapp;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by william.bertan on 14/12/2016.
 */

public class AdapterContacts extends RecyclerView.Adapter<AdapterContacts.ViewHolder> {
    private List<Contact> mDataset;
    private ViewHolderAction mViewHolderAction;
    private int mSelectedPosition = -1;

    private int[] itemColors = new int[] {
            Color.parseColor("#1abc9c"),
            Color.parseColor("#2ecc71"),
            Color.parseColor("#3498db"),
            Color.parseColor("#9b59b6"),
            Color.parseColor("#34495e"),
            Color.parseColor("#f1c40f"),
            Color.parseColor("#e67e22"),
            Color.parseColor("#e74c3c"),
            Color.parseColor("#95a5a6"),
            Color.parseColor("#16a085"),
            Color.parseColor("#27ae60"),
            Color.parseColor("#2980b9"),
            Color.parseColor("#8e44ad"),
            Color.parseColor("#2c3e50"),
            Color.parseColor("#f39c12"),
            Color.parseColor("#d35400"),
            Color.parseColor("#c0392b"),
            Color.parseColor("#bdc3c7"),
            Color.parseColor("#7f8c8d")
    };

    public AdapterContacts() {
        mDataset = new ArrayList<>();
    }

    public AdapterContacts(ViewHolderAction viewHolderAction) {
        this();
        this.mViewHolderAction = viewHolderAction;
    }

    public void add(Contact contact) {
        if(mDataset.contains(contact)) {
            int index = mDataset.lastIndexOf(contact);
            mDataset.set(index, contact);
        } else {
            mDataset.add(contact);
        }
        notifyDataSetChanged();
    }

    public void addAll(Collection<Contact> collection) {
        mDataset.addAll(collection);
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public Contact getSelectedItem() {
        if(mSelectedPosition > -1) {
            return mDataset.get(mSelectedPosition);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : -1;
    }

    @Override
    public AdapterContacts.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_contact_item, parent, false);
        return new ViewHolder(view);
    }

    public interface ViewHolderAction {
        void executeRecyclerItemClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView mCardView;
        @BindView(R.id.imageViewContactName)
        ImageView mImageViewContactName;
        @BindView(R.id.textViewContactName)
        TextView mTextViewContactName;
        @BindView(R.id.textViewContactPhone)
        TextView mTextViewContactPhone;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Contact contact = mDataset.get(position);
        holder.mTextViewContactName.setText(contact.getName());
        holder.mTextViewContactPhone.setText(contact.getPhone());
        TextDrawable imageDrawableWithId = TextDrawable.builder()
                                                       .buildRound(contact.getName().trim().isEmpty() ? " " : contact.getName().trim().substring(0, 1).toUpperCase(), itemColors[position % itemColors.length]);
        holder.mImageViewContactName.setImageDrawable(imageDrawableWithId);
        holder.mImageViewContactName.setVisibility(View.VISIBLE);
        holder.mCardView.setCardBackgroundColor(mSelectedPosition == position ? Color.LTGRAY : Color.WHITE);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedPosition = position;
                notifyDataSetChanged();
                if(mViewHolderAction != null) {
                    mViewHolderAction.executeRecyclerItemClick(view, position);
                }
            }
        });
    }
}