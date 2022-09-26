package com.panbadian.opiekun_lekow

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoseAdapter (
    private val doseList: MutableList<Dose>
): RecyclerView.Adapter<DoseAdapter.RankingViewHolder>() {

    var onItemClick: ((Dose)-> Unit)? = null

    inner class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvHour = itemView.findViewById<TextView>(R.id.tv_hour)
        private val tvQuantity = itemView.findViewById<TextView>(R.id.tv_quantity)
        private val tvNotes = itemView.findViewById<TextView>(R.id.tv_notes)
        private val chbGiven = itemView.findViewById<CheckBox>(R.id.chb_given)

        init{
            itemView.setOnClickListener{
                onItemClick?.invoke(doseList[adapterPosition])
            }
        }

        fun bind(dose: Dose){
            tvHour.text = dose.hour
            tvQuantity.text = dose.quantity.toString()
            tvNotes.text = dose.notes
            chbGiven.isChecked = dose.given

            chbGiven.setOnClickListener(){
                // change given to an opposite value here and in db
                dose.given = !dose.given
                val dbHelper = DBHelper(chbGiven.context)
                dbHelper.changeGiven(dose.id, dose.given)
                Log.d("debug", "check " + dose.hour + " " + dose.given)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_dose, parent, false)
        )
    }

    fun addDose(dose: Dose) {
        doseList.add(dose)
    }

    fun removeDose(position: Int, context: Context) {
        // delete dose from db and list
        val dbHelper = DBHelper(context)
        dbHelper.deleteDose(doseList[position].id)
        doseList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val dose = doseList[position]
        holder.bind(dose)
    }

    override fun getItemCount(): Int {
        return doseList.size
    }
}
