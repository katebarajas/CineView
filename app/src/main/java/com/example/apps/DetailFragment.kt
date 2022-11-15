package com.example.apps

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.apps.room_database.ToDo
import com.example.apps.room_database.ToDoDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmento=inflater.inflate(R.layout.fragment_detail,container,false )
        var tarea=requireArguments().getString("tarea")
        var hora=requireArguments().getString("hora")
        var lugar=requireArguments().getString("lugar")
        var id=requireArguments().getString("id")

        var tvTarea : TextView =fragmento.findViewById(R.id.tvTarea)
        var tvHora : TextView =fragmento.findViewById(R.id.tvHora)
        var tvLugar : TextView =fragmento.findViewById(R.id.tvLugar)
        var tvID : TextView =fragmento.findViewById(R.id.tvID)

        tvTarea.text=tarea
        tvHora.text=hora
        tvLugar.text=lugar
        tvID.text=id

        val btnEditar : Button= fragmento.findViewById(R.id.btnEdit)
        btnEditar.setOnClickListener {
            val principal= Intent(inflater.context, NewTaskActivity::class.java)
            principal.putExtra("tarea",tvTarea.text as String)
            principal.putExtra("hora",tvHora.text as String)
            principal.putExtra("lugar",tvLugar.text as String)
            principal.putExtra("id",tvID.text as String)
            startActivity(principal)
        }

        val btnBorrar : Button= fragmento.findViewById(R.id.btnDelete)
        btnBorrar.setOnClickListener {
            val db = ToDoDatabase.getDatabase(requireActivity())
            val  toDoDAO= db.todoDao()
            val dbFirebase= FirebaseFirestore.getInstance()
            val task = ToDo(id!!.toInt(), tarea!!.toString(),hora!!.toString(),lugar!!.toString())

            val negativeButton = { _: DialogInterface, _: Int -> }
            val positiveButton = { dialog: DialogInterface, which: Int ->
                runBlocking {
                    launch {
                        toDoDAO.deleteTask(task)
                        dbFirebase.collection("ToDo").document(id).delete()
                    }
                }
                val principal = Intent(requireActivity(),ToDoActivity::class.java)
                startActivity(principal)
            }
            val dialog=AlertDialog.Builder(requireActivity())
                .setTitle("Delete Task")
                .setMessage("You really want to delete the task?")
                .setPositiveButton("ok", positiveButton)
                .setNegativeButton("Cancel", negativeButton)
                .create()
                .show()


        }

        return fragmento
    }
}