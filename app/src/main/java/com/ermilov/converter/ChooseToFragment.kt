package com.ermilov.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ermilov.converter.chooseValute.data.ValuteHolderFrom
import com.ermilov.converter.databinding.FragmentChooseToBinding


class ChooseToFragment : Fragment() {

    private lateinit var fragmentChooseToFragmentChooseToBinding: FragmentChooseToBinding
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflater.inflate(R.layout.fragment_choose_to, container, false)
        fragmentChooseToFragmentChooseToBinding = FragmentChooseToBinding.inflate(inflater, container, false)
        return fragmentChooseToFragmentChooseToBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val adapter =
            activity?.let {
                ValuteHolderFrom(
                    resources.getStringArray(R.array.valutes_name_array).toList(),
                    resources.getStringArray(R.array.valutes_code_array).toList(),
                    fragmentChooseToFragmentChooseToBinding.root,
                    it, R.id.action_chooseToFragment_to_fragment_calculator)
            }
        fragmentChooseToFragmentChooseToBinding.recyclerValuteTo.layoutManager = LinearLayoutManager(requireContext())
        fragmentChooseToFragmentChooseToBinding.recyclerValuteTo.adapter = adapter
    }
}
