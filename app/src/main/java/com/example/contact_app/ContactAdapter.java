package com.example.contact_app;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> implements Filterable {
    private List<Contact> contactsAll;
    private List<Contact> contacts;
    private static ItemClickListener clickListener;


    public ContactAdapter(List<Contact> contacts) {
        super();
        this.contacts = contacts;
        this.contactsAll = new ArrayList<>(contacts);
    }
    private static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getPhone().equals(newItem.getPhone()) &&
                    oldItem.getEmail().equals(newItem.getEmail());
        }
    };

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        holder.textView.setText(contacts.get(position).getName());
        byte[] byteImage = contacts.get(position).getImage();
        holder.ivAvatar.setImageBitmap(BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Contact> filterContacts = new ArrayList<>();
                if(constraint.toString().isEmpty()){
                    System.out.println("run");
                    filterContacts.addAll(contactsAll);

                }else{
                    System.out.println("here");
                    for(Contact record : contactsAll) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (record.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filterContacts.add(record);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterContacts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contacts.clear();
                contacts.addAll((Collection<? extends Contact>) results.values);
                notifyDataSetChanged();
            }
        };
    }


    static class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView;
        public ImageView ivAvatar;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
}
