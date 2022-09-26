package com.panbadian.opiekun_lekow
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PrescriptionAdapter (
    private val register: MutableList<Prescription>
): RecyclerView.Adapter<PrescriptionAdapter.RankingViewHolder>() {

    var onItemClick: ((Prescription)-> Unit)? = null

    inner class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvMedName = itemView.findViewById<TextView>(R.id.tv_med_name)
        val fabDelete = itemView.findViewById<FloatingActionButton>(R.id.fab_delete)
        val tvDuration = itemView.findViewById<TextView>(R.id.tv_duration)
        val doseList = itemView.findViewById<RecyclerView>(R.id.rv_doses)

        // click medicine
        init{
            itemView.setOnClickListener{
                onItemClick?.invoke(register[adapterPosition])
            }
        }

        private fun onDoseClick(dose: Dose) {
            Log.d("debug", "click " + dose.hour + " " + dose.notes)
        }

        fun bind(prescription: Prescription){
            tvMedName.text = prescription.medicine
            tvDuration.text = prescription.date_from + " - " + prescription.date_to

            // show doses
            val doseAdapter = DoseAdapter(mutableListOf())
            doseList.adapter = doseAdapter
            doseList.layoutManager = LinearLayoutManager(itemView.context)

            for(dose in prescription.dosages) {
                doseAdapter.addDose(dose)
            }

            doseAdapter.onItemClick = {dose -> onDoseClick(dose)}

            // delete prescription
            fabDelete.setOnClickListener(){
                Log.d("debug", "delete " + prescription.medicine)
                removeMed(prescription, fabDelete.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_med, parent, false)
        )
    }

    fun addMed(prescription: Prescription) {
        // add med to list
        register.add(prescription)
        notifyDataSetChanged()
    }

    fun removeMed(prescription: Prescription, context: Context) {
        // remove med from list and db
        Log.d("debug", register.toString())
        register.remove(prescription)
        val dbHelper = DBHelper(context)
        dbHelper.deletePrescription(prescription.id)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val prescription = register[position]
        holder.bind(prescription)
    }

    override fun getItemCount(): Int {
        return register.size
    }
}