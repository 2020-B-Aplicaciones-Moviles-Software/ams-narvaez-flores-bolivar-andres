package com.example.roomdatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.EstudiantesAdapter.UsersAdapterVH

class EstudiantesAdapter(private val itemClicked: ItemClicked) : RecyclerView.Adapter<UsersAdapterVH>() {
    private var estudiantesList: List<Estudiantes>? = null
    private var context: Context? = null
    fun setData(estudiantesList: List<Estudiantes>?) {
        this.estudiantesList = estudiantesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapterVH {
        context = parent.context
        return UsersAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_ests, null))
    }

    override fun onBindViewHolder(holder: UsersAdapterVH, position: Int) {
        val estudiantes = estudiantesList!![position]
        val username = estudiantes.username
        holder.users.text = username
        holder.imageOptions.setOnClickListener { view -> //popmenu
            showPopup(view, estudiantes)
        }
    }

    fun showPopup(view: View?, estudiantes: Estudiantes?) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.menu_options)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId
            when (id) {
                R.id.delete -> itemClicked.deleteClicked(estudiantes)
                R.id.update -> itemClicked.updateClicked(estudiantes)
                R.id.details -> itemClicked.detailsClicked(estudiantes)
            }
            false
        }
        popupMenu.show()
    }

    interface ItemClicked {
        fun updateClicked(estudiantes: Estudiantes?)
        fun deleteClicked(estudiantes: Estudiantes?)
        fun detailsClicked(estudiantes: Estudiantes?)
    }

    override fun getItemCount(): Int {
        return estudiantesList!!.size
    }

    inner class UsersAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var users: TextView
        var imageOptions: ImageView

        init {
            users = itemView.findViewById(R.id.users_row)
            imageOptions = itemView.findViewById(R.id.menuOption)
        }
    }

}