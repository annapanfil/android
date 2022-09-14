package com.panbadian.opiekun_lekow
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoseAdapter (
    private val register: MutableList<Dose>
): RecyclerView.Adapter<DoseAdapter.RankingViewHolder>() {

    inner class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvHour = itemView.findViewById<TextView>(R.id.tv_hour)
        val tvQuantity = itemView.findViewById<TextView>(R.id.tv_quantity)
        val tvNotes = itemView.findViewById<TextView>(R.id.tv_notes)
        val chbGiven = itemView.findViewById<CheckBox>(R.id.chb_given)

        fun bind(dose: Dose){
            tvHour.text = dose.hour
            tvQuantity.text = dose.quantity.toString()
            tvNotes.text = dose.notes
            chbGiven.isChecked = dose.given
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_dose, parent, false)
        )
    }

    fun addDose(dose: Dose) {
        register.add(dose)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val dose = register[position]
        holder.bind(dose)
    }

    override fun getItemCount(): Int {
        return register.size
    }
}
