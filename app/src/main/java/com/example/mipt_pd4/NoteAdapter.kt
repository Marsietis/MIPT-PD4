package com.example.mipt_pd4

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mipt_pd4.database.Note

// Custom ArrayAdapter for displaying Note objects
class NoteAdapter(context: Context, notes: List<Note>) :
    ArrayAdapter<Note>(context, 0, notes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d("NoteAdapter", "getView")
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            Log.d("NoteAdapter", "listItemView is null")
        }

        val note = getItem(position)

        val nameTextView = listItemView?.findViewById<TextView>(R.id.noteNameTextView)
        val contentTextView = listItemView?.findViewById<TextView>(R.id.noteContentTextView)

        nameTextView?.text = note?.name
        contentTextView?.text = note?.content

        return listItemView!!
    }
}
