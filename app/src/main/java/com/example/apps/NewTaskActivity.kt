package com.example.apps

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.apps.databinding.ActivityNewTaskBinding
import com.example.apps.room_database.ToDo
import com.example.apps.room_database.ToDoDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NewTaskActivity : AppCompatActivity() {
    lateinit var editTextTitle:EditText
    lateinit var editTextTime:EditText
    lateinit var editTextPlace:EditText
    lateinit var editTextId: EditText
    lateinit var btnSavetask: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        editTextTitle=findViewById(R.id.editTextTitle)
        editTextTime=findViewById(R.id.editTextTime)
        editTextPlace=findViewById(R.id.editTextPlace)
        editTextId= findViewById(R.id.editTextID2)
        btnSavetask=findViewById(R.id.btnSavetask)

        if(this.intent.getStringExtra("id")!=null){
            editTextTitle.setText(this.intent.getStringExtra("tarea"))
            editTextTime.setText(this.intent.getStringExtra("hora"))
            editTextPlace.setText(this.intent.getStringExtra("lugar"))
            editTextId.setText(this.intent.getStringExtra("id"))
            btnSavetask.setText("Edit Task")
        }

    }

    fun onSavesTask(view: View) {
        var title:String = editTextTitle.text.toString()
        var time:String = editTextTime.text.toString()
        var place: String = editTextPlace.text.toString()
        var id: String = editTextId.text.toString()


        val db = ToDoDatabase.getDatabase(this)
        val todoDAO = db.todoDao()
        val dbFirebase = FirebaseFirestore.getInstance()
        val task = ToDo(id.toInt(),title, time, place)
        runBlocking {
            launch {

                if(id.equals("0")){
                    var result = todoDAO.insertTask(task)
                    if (result!=1L)
                    {
                        dbFirebase.collection("ToDo").document(result.toString())
                            .set(
                                hashMapOf(
                                    "title" to title,
                                    "time" to time,
                                    "place" to place
                                )

                            )

                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }else{
                    dbFirebase.collection("ToDo").document(id)
                        .set(
                            hashMapOf(
                                "title" to title,
                                "time" to time,
                                "place" to place
                            )

                        )
                    todoDAO.updateTask(task)
                    finish()
                }
            }
        }
        val principal= Intent(this, ToDoActivity::class.java)
        startActivity(principal)

    }

}