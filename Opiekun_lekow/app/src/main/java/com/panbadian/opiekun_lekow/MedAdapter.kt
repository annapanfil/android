package com.panbadian.opiekun_lekow
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.internal.ContextUtils.getActivity

class MedAdapter (
    private val register: MutableList<Med>
): RecyclerView.Adapter<MedAdapter.RankingViewHolder>() {

    inner class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvMedName = itemView.findViewById<TextView>(R.id.tv_med_name)
        val fabDelete = itemView.findViewById<FloatingActionButton>(R.id.fab_delete)
        val tvDuration = itemView.findViewById<TextView>(R.id.tv_duration)
        val doseList = itemView.findViewById<RecyclerView>(R.id.rv_doses)

        fun bind(med: Med, ){
            tvMedName.text = med.name
            tvDuration.text = med.duration

            val doseAdapter = DoseAdapter(mutableListOf())
            doseList.adapter = doseAdapter
            doseList.layoutManager = LinearLayoutManager(itemView.context)

            for(dose in med.dosages) {
                doseAdapter.addDose(dose)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_med, parent, false)
        )
    }

    fun addMed(med: Med) {
        register.add(med)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val med = register[position]
        holder.bind(med)
    }

    override fun getItemCount(): Int {
        return register.size
    }
}
