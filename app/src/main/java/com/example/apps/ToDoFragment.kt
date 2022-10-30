package com.example.apps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class ToDoFragment : Fragment() {
    private lateinit var listRecyclerView: RecyclerView
    private lateinit var myAdapter : RecyclerView.Adapter<MyTaskListAdapter.MyViewHolder>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmento=inflater.inflate(R.layout.fragment_to_do,container,false)
        /*val btnDetail1: Button =fragmento.findViewById(R.id.btn_detail_1)
        val btnDetail2: Button =fragmento.findViewById(R.id.btn_detail_2)
        val btnDetail3: Button =fragmento.findViewById(R.id.btn_detail_3)
        btnDetail1.setOnClickListener(View.OnClickListener {
            val datos = Bundle()
            datos.putString("tarea","ir al supermercado")
            datos.putString("hora","6:13 am")
            datos.putString("lugar","superx")
            activity?.getSupportFragmentManager()?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.replace(R.id.fcvToDo,DetailFragment::class.java,datos,"detail")
                ?.addToBackStack("")
                ?.commit()
        })
        btnDetail2.setOnClickListener(View.OnClickListener {
            val datos = Bundle()
            datos.putString("tarea","llevar carro al mantenimiento")
            datos.putString("hora","9:13 am")
            datos.putString("lugar","taller")
            activity?.getSupportFragmentManager()?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.replace(R.id.fcvToDo,DetailFragment::class.java,datos,"detail")
                ?.addToBackStack("")
                ?.commit()
        })
        btnDetail3.setOnClickListener(View.OnClickListener {
            val datos = Bundle()
            datos.putString("tarea","ir al supermercado")
            datos.putString("hora","6:13 am")
            datos.putString("lugar","superx")
            activity?.getSupportFragmentManager()?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.replace(R.id.fcvToDo,DetailFragment::class.java,datos,"detail")
                ?.addToBackStack("")
                ?.commit()
        })*/
        return fragmento
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var myTaskTitles : ArrayList<String> = ArrayList()
        var myTaskTimes : ArrayList<String> = ArrayList()
        var myTaskPlaces : ArrayList<String> = ArrayList()
        myTaskTitles.add("ir al super")
        myTaskTitles.add("ir al super 2")
        myTaskTitles.add("ir al super 3")
        myTaskTitles.add("ir al super 4")

        myTaskTimes.add("10:12 pm")
        myTaskTimes.add("12:12 pm")
        myTaskTimes.add("22:12 pm")
        myTaskTimes.add("21:12 pm")

        myTaskPlaces.add("lugar 1")
        myTaskPlaces.add("lugar 2")
        myTaskPlaces.add("lugar 3")
        myTaskPlaces.add("lugar 4")

        var info : Bundle = Bundle()
        info.putStringArrayList("titles", myTaskTitles)
        info.putStringArrayList("times", myTaskTimes)
        info.putStringArrayList("places", myTaskPlaces)
        listRecyclerView=requireView().findViewById(R.id.recyclerToDoList)
        myAdapter = MyTaskListAdapter(activity as AppCompatActivity,info)
        listRecyclerView.setHasFixedSize(true)
        listRecyclerView.adapter=myAdapter
        listRecyclerView.addItemDecoration(
            DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL)
        )

    }
}