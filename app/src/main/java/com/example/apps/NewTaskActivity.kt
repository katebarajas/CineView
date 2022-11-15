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

    private lateinit var binding: ActivityNewTaskBinding
    lateinit var imagenId : ImageView
    //lateinit var btnCargarImg: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_new_task)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCargarImg.setOnClickListener { requestPermissions() }

        editTextTitle=findViewById(R.id.editTextTitle)
        editTextTime=findViewById(R.id.editTextTime)
        editTextPlace=findViewById(R.id.editTextPlace)
        editTextId= findViewById(R.id.editTextID2)
        btnSavetask=findViewById(R.id.btnSavetask)


        imagenId= findViewById(R.id.imagenId)
        //btnCargarImg=findViewById(R.id.btnCargarImg)

        if(this.intent.getStringExtra("id")!=null){
            editTextTitle.setText(this.intent.getStringExtra("tarea"))
            editTextTime.setText(this.intent.getStringExtra("hora"))
            editTextPlace.setText(this.intent.getStringExtra("lugar"))
            editTextId.setText(this.intent.getStringExtra("id"))
            btnSavetask.setText("Edit Task")
        }

    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    pickPhotoFromGallery()
                }
                else-> requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            pickPhotoFromGallery()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted->

        if (isGranted){
            pickPhotoFromGallery()
        }else{
            Toast.makeText(this,"You need to enable the permission", Toast.LENGTH_LONG)
                .show()
        }

    }

    private val startForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result->
        if(result.resultCode == Activity.RESULT_OK){
            val data = result.data?.data
            imagenId.setImageURI(data)
        }
    }

    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForActivityGallery.launch(intent)
    }

    fun onSavesTask(view: View) {
        var title:String = editTextTitle.text.toString()
        var time:String = editTextTime.text.toString()
        var place: String = editTextPlace.text.toString()
        var id: String = editTextId.text.toString()
        var image:String = imagenId. toString()

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