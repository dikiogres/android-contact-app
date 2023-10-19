package com.example.simplecontactapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var fab: FloatingActionButton
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var contacts: MutableList<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        fab = findViewById(R.id.fab)
        dbHelper = DatabaseHelper(this)

        fab.setOnClickListener {
            showContactDialog(-1)
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            showContactDialog(position)
        }

        loadContacts()
    }

    private fun loadContacts() {
        contacts = dbHelper.getAllContacts()
        val adapter = ContactAdapter(this, contacts)
        listView.adapter = adapter
    }

    private fun showContactDialog(position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_contact, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.editTextName)
        val numberEditText = dialogView.findViewById<EditText>(R.id.editTextNumber)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = nameEditText.text.toString()
                val number = numberEditText.text.toString()

                if (position == -1) {
                    dbHelper.insertContact(name, number)
                } else {
                    val contact = contacts[position]
                    dbHelper.updateContact(contact.id, name, number)
                }

                loadContacts()
            }

        if (position != -1) {
            builder.setNegativeButton("Delete") { _, _ ->
                dbHelper.deleteContact(contacts[position].id)
                loadContacts()
            }
        }

        builder.show()
    }

}
