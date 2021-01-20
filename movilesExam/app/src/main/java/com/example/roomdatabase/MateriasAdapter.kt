package com.example.roomdatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.MateriasAdapter.MateriasAdapterVH

class MateriasAdapter(private val itemClicked1: ItemClicked) : RecyclerView.Adapter<MateriasAdapterVH>() {
    private var materiasList: List<Materias>? = null
    private var context: Context? = null
    fun setData(materiasList: List<Materias>?) {
        this.materiasList = materiasList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriasAdapterVH {
        context = parent.context
        return MateriasAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_mats, null))
    }

    override fun onBindViewHolder(holder: MateriasAdapterVH, position: Int) {
        val materias = materiasList!![position]
        val matname = materias.matname
        holder.mats.text = matname
        holder.imageOptions.setOnClickListener { view -> //popmenu
            showPopup(view, materias)
        }
    }

    fun showPopup(view: View?, materias: Materias?) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.menu_options)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId
            when (id) {
                R.id.delete -> itemClicked1.deleteClicked(materias)
                R.id.update -> itemClicked1.updateClicked(materias)
                R.id.details -> itemClicked1.detailsClicked(materias)
            }
            false
        }
        popupMenu.show()
    }

    interface ItemClicked {
        fun updateClicked(materias: Materias?)
        fun deleteClicked(materias: Materias?)
        fun detailsClicked(materias: Materias?)
    }

    override fun getItemCount(): Int {
        return materiasList!!.size
    }

    inner class MateriasAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mats: TextView
        var imageOptions: ImageView

        init {
            mats = itemView.findViewById(R.id.mats_row)
            imageOptions = itemView.findViewById(R.id.menuOption)
        }
    }

}