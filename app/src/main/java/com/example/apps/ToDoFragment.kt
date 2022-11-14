package com.example.apps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.apps.room_database.ToDo
import com.example.apps.room_database.ToDoDatabase
import com.example.apps.room_database.repository.ToDoRepository
import com.example.apps.room_database.viewmodel.ToDoViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ToDoFragment : Fragment() {
    private lateinit var listRecyclerView: RecyclerView
    private lateinit var myAdapter : RecyclerView.Adapter<MyTaskListAdapter.MyViewHolder>
    var myTaskTitles : ArrayList<String> = ArrayList()
    var myTaskTimes : ArrayList<String> = ArrayList()
    var myTaskPlaces : ArrayList<String> = ArrayList()
    var myTaskIds : ArrayList<String> = ArrayList()

    private lateinit var todoviewmodel:ToDoViewModel
    private lateinit var todorepository:ToDoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmento= inflater.inflate(R.layout.fragment_to_do,container,false)
        return fragmento
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab : View = requireActivity().findViewById(R.id.fabToDo)
        fab.setOnClickListener{view->
            val intent= Intent(activity,NewTaskActivity::class.java)
            val recursiveScope = 0
            startActivityForResult(intent,recursiveScope)
        }

        var info : Bundle = Bundle()
        info.putStringArrayList("titles", myTaskTitles)
        info.putStringArrayList("times", myTaskTimes)
        info.putStringArrayList("places", myTaskPlaces)
        info.putStringArrayList("id", myTaskIds)
        listRecyclerView=requireView().findViewById(R.id.recyclerToDoList)
        myAdapter = MyTaskListAdapter(activity as AppCompatActivity,info)
        listRecyclerView.setHasFixedSize(true)
        listRecyclerView.adapter=myAdapter
        listRecyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        updateList()

    }

    fun updateList(){
        val db = ToDoDatabase.getDatabase(requireActivity())
        val todoDAO = db.todoDao()
        val dbFirebase = FirebaseFirestore.getInstance()

        todorepository = ToDoRepository(todoDAO)
        todoviewmodel = ToDoViewModel(todorepository)

        var result= todoviewmodel.getAllTasks()
        result.invokeOnCompletion {
            var theTasks = todoviewmodel.getTheTasks()
        if (theTasks!!.size!=0){
            var i=0
            myTaskTitles.clear()
            myTaskTimes.clear()
            myTaskPlaces.clear()

            while (i< theTasks!!.size){
                myTaskTitles.add(theTasks[i].title)
                myTaskTimes.add(theTasks[i].time)
                myTaskPlaces.add(theTasks[i].place)
                i++
            }
            myAdapter.notifyDataSetChanged()
        } else {

            var tasks = mutableListOf<ToDo>()
            dbFirebase.collection(("ToDo")).get().addOnSuccessListener {
                var docs = it.documents
                if(docs.size != 0){
                    var i=0
                    while (i<docs.size)
                        myTaskTitles.add(docs[i].get("title") as String)
                    myTaskTimes.add(docs[i].get("time") as String)
                    myTaskPlaces.add(docs[i].get("place") as String)
                    i++
                }
                todoviewmodel.insertTasks(tasks)
                myAdapter.notifyDataSetChanged()
            }

            }
        }

        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==0){
            if(resultCode==Activity.RESULT_OK){
                updateList()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    }
