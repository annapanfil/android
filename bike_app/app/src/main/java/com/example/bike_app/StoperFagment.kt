package com.example.bike_app

import android.os.Bundle
import android.os.Handler
import android.os.Looper.getMainLooper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StoperFagment : Fragment(), View.OnClickListener{
    private var seconds: Int = 0
    private var running: Boolean = false
    private var wasRunning: Boolean = false //czy działał przed wstrzymaniem aktywności
    private var trackId: Long? = null
    private lateinit var recordAdapter: RecordAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
            trackId = savedInstanceState.getLong("trackId")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_stoper_fagment, container, false)
        runStoper(layout)

        val bPause = layout.findViewById<Button>(R.id.b_pause)
        val bReset = layout.findViewById<Button>(R.id.b_reset)
        val bStart = layout.findViewById<Button>(R.id.b_start)
        val bSave = layout.findViewById<Button>(R.id.b_save)
        bPause.setOnClickListener(this)
        bStart.setOnClickListener(this)
        bReset.setOnClickListener(this)
        bSave.setOnClickListener(this)

//        showResults(DBHelper(requireContext()))

        return layout
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.b_start -> onClickStart()
            R.id.b_reset -> onClickReset()
            R.id.b_pause -> onClickPause()
            R.id.b_save -> onClickSave()
        }
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if(wasRunning){
            running = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
        outState.putBoolean("wasRunning", wasRunning)
        outState.putLong("trackId", trackId!!)
    }


    private fun onClickStart(){
        Log.d("DEBUG", "Start")
        running = true
    }

    private fun onClickPause(){
        Log.d("DEBUG", "Pause")
        running = false
    }

    private fun onClickReset(){
        Log.d("DEBUG", "Reset")
        running = false
        seconds = 0
    }

    private fun onClickSave(){
        Log.d("DEBUG", "Save time $trackId $seconds")
        onClickPause()
        val dbHelper = DBHelper(requireContext())
        dbHelper.insertTime(trackId!!, seconds)
        showResults(dbHelper)
        onClickReset()
    }

    private fun runStoper(view: View){
        val timeView = view.findViewById<TextView>(R.id.tv_time)
        val handler = Handler(getMainLooper())
        handler.post {
            val hours = seconds/3600
            val minutes = (seconds % 3600)/60
            val secs = seconds % 60
            val time = String.format("%d:%02d:%02d", hours, minutes, secs)
            timeView.text = time
            if (running){
                seconds++
            }
            handler.postDelayed({runStoper(view)}, 1000)
        }
    }

    private fun showResults(dbHelper: DBHelper){
        val tvBestTime = view?.findViewById<TextView>(R.id.tv_best_time)
        val bestTime = dbHelper.getBestTime(trackId!!).time
        val strBestTime = bestTime.toString()
        Log.d("DEBUG", "best time ${bestTime.toString()}")
        tvBestTime?.text = bestTime.toString()

        val records: ArrayList<Record> = dbHelper.getTimes(trackId!!)

        recordAdapter = RecordAdapter(mutableListOf())

        val messageList = view?.findViewById<RecyclerView>(R.id.rv_records)
        messageList?.adapter = recordAdapter
        messageList?.layoutManager = LinearLayoutManager(requireContext())

        for(record in records) {
            recordAdapter.addRecord(record)
        }

        //TODO: dodać statystyki
    }

    fun setTrackId(_trackId: Long?){
        trackId = _trackId
    }
}