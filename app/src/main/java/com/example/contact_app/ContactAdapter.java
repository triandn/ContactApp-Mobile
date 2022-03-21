package com.example.contact_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> implements Filterable {
    private List<Contact> contactsAll;
    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
        this.contactsAll = new ArrayList<>(contacts);

    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        holder.textView.setText(contacts.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
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


    static class ContactHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name);
        }
    }
}
