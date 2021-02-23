package com.ermilov.converter.chooseValute.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ermilov.converter.R
import com.ermilov.converter.chooseValute.data.ValuteHolderFrom
import com.ermilov.converter.databinding.FragmentChooseCurrencyBinding


class Fragmentchoose : Fragment() {

    private lateinit var fragmentChooseBinding: FragmentChooseCurrencyBinding
    lateinit var navController: NavController
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        inflater.inflate(R.layout.fragment_choose_currency, container, false)
        fragmentChooseBinding = FragmentChooseCurrencyBinding.inflate(inflater, container, false)
        return fragmentChooseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
            val adapter =
                activity?.let {
                    ValuteHolderFrom(
                        resources.getStringArray(R.array.valutes_name_array).toList(),
                        resources.getStringArray(R.array.valutes_code_array).toList(),
                        fragmentChooseBinding.root,
                        it,
                        R.id.action_fragment_choose_currency_to_fragment_calculator
                    )
                }
            fragmentChooseBinding.recyclerValute.layoutManager = LinearLayoutManager(requireContext())
            fragmentChooseBinding.recyclerValute.adapter = adapter



    }


}