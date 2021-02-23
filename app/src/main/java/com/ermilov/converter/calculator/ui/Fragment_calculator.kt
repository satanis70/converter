package com.ermilov.converter.calculator.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ermilov.converter.R
import com.ermilov.converter.databinding.FragmentCalculatorBinding
import com.ermilov.converter.calculator.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class Fragment_calculator : Fragment() {

    lateinit var navController: NavController
    private lateinit var fragmentCalculatorBinding: FragmentCalculatorBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflater.inflate(R.layout.fragment_calculator, container, false)
        fragmentCalculatorBinding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return fragmentCalculatorBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val spfrom = sharedPref.getString("codefrom", getString(R.string.ru))
        val spto = sharedPref.getString("codeto", getString(R.string.usd))
        if (spfrom==null){
            fragmentCalculatorBinding.tvfrom.text = resources.getText(R.string.ru)
        } else {
            fragmentCalculatorBinding.tvfrom.text = spfrom
        }
        if (spto==null){
            fragmentCalculatorBinding.tvto.text = resources.getText(R.string.usd)
        } else {
            fragmentCalculatorBinding.tvto.text = spto
        }



        fragmentCalculatorBinding.tvfrom.setOnClickListener {
            navController.navigate(R.id.action_fragment_calculator_to_fragment_choose_currency)
        }
        fragmentCalculatorBinding.tvto.setOnClickListener {
            navController.navigate(R.id.action_fragment_calculator_to_chooseToFragment)
        }
        fragmentCalculatorBinding.buttonConvert.setOnClickListener {
            if (spfrom != null && spto!=null) {
                viewModel.convert(
                    fragmentCalculatorBinding.etsumm.text.toString(),
                    spfrom,
                    spto
                )
                view.hideKeyboard()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect{ event->
                when(event){
                    is MainViewModel.CurrencyEvent.Succes -> {
                        fragmentCalculatorBinding.tvOut.text = event.resultText
                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        fragmentCalculatorBinding.tvOut.text = event.errorText
                    }
                    else -> Unit
                }
            }
        }
    }


    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}