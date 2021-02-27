package com.ermilov.converter.chooseValute.data

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ermilov.converter.R


class ValuteHolderFrom(
    var listnameValute: List<String>,
    var _listcodeValute: List<String>,
    var view: View,
    var activity: Activity,
    var action: Int,
) : RecyclerView.Adapter<ValuteHolderFrom.MainHolder>() {
    var listValute = ArrayList<String>()
    var listCodeVlute = ArrayList<String>()
    lateinit var navController: NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteHolderFrom.MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holderFrom: ValuteHolderFrom.MainHolder, position: Int) {
        navController = Navigation.findNavController(view)
        holderFrom.bind(listnameValute[position], _listcodeValute[position])
        listValute.add(listnameValute[position])
        listCodeVlute.add(_listcodeValute[position])

        holderFrom.itemView.setOnClickListener {
            if (action == R.id.action_fragment_choose_currency_to_fragment_calculator){
                val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
                with (sharedPref.edit()) {
                    putString("codefrom", _listcodeValute[position])
                    apply()
                }
                navController.navigate(action)
            } else {
                val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
                with (sharedPref.edit()) {
                    putString("codeto", _listcodeValute[position])
                    apply()
                }
                navController.navigate(action)
            }


        }
    }

    override fun getItemCount(): Int {
      return listnameValute.size
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val nameValue = itemView.findViewById<TextView>(R.id.tvNameItem)
        private val codeValute = itemView.findViewById<TextView>(R.id.tvValuteCode)

        fun bind(valute: String, code: String){
            nameValue.text = valute
            codeValute.text = code
        }

    }
}