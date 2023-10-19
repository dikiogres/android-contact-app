package com.example.simplecontactapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ContactAdapter(context: Context, private val contacts: List<Contact>) : ArrayAdapter<Contact>(context, R.layout.contact_item, contacts) {

    private class ViewHolder {
        lateinit var tvName: TextView
        lateinit var tvNumber: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false)
            viewHolder = ViewHolder()
            viewHolder.tvName = view.findViewById(R.id.tvName)
            viewHolder.tvNumber = view.findViewById(R.id.tvNumber)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val currentContact = contacts[position]
        viewHolder.tvName.text = currentContact.name
        viewHolder.tvNumber.text = currentContact.number

        return view!!
    }
}
