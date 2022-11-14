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
import com.example.apps.room_database.ToDoDatabase
import com.example.apps.room_database.repository.ToDoRepository
import com.example.apps.room_database.viewmodel.ToDoViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ToDoFragment : Fragment() {
    private lateinit var listRecyclerView: RecyclerView
    private lateinit var myAdapter : RecyclerView.Adapter<MyTaskListAdapter.MyViewHolder>
    var myTaskTitles : ArrayList<String> = ArrayList()
    var myTaskTimes : ArrayList<String> = ArrayList()
    var myTaskPlaces : ArrayList<String> = ArrayList()
    var myTaskIds : ArrayList<String> = ArrayList()
    var info: Bundle= Bundle()
    private lateinit var toDoViewModel: ToDoViewModel
    private lateinit var toDoRepository: ToDoRepository

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
        val toDoDAO = db.todoDao()
        /*runBlocking {
            launch {
                var result= toDoDAD.getAllTasks()
                var i=0
                myTaskTitles.clear()
                myTaskTimes.clear()
                myTaskPlaces.clear()
                myTaskIds.clear()
                while (i< result.size){
                    myTaskTitles.add(result[i].title.toString())
                    myTaskTimes.add(result[i].time.toString())
                    myTaskPlaces.add(result[i].place.toString())
                    myTaskIds.add(result[i].id.toString())
                    i++
                }
                myAdapter.notifyDataSetChanged()
            }
        }*/

        toDoRepository= ToDoRepository(toDoDAO)
        toDoViewModel= ToDoViewModel(toDoRepository)

        var result = toDoViewModel.getAllTask()
        result.invokeOnCompletion {
            var theTask = toDoViewModel.getTheTasks()
            if(theTask!!.size!=0){
                var i=0
                myTaskTitles.clear()
                myTaskTimes.clear()
                myTaskPlaces.clear()
                myTaskIds.clear()
                while (i< theTask.size){
                    myTaskTitles.add(theTask[i].title.toString())
                    myTaskTimes.add(theTask[i].time.toString())
                    myTaskPlaces.add(theTask[i].place.toString())
                    myTaskIds.add(theTask[i].id.toString())
                    i++
                }
                myAdapter.notifyDataSetChanged()

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


