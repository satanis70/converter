package com.ermilov.converter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ermilov.converter.databinding.FragmentCalculatorBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okio.IOException
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await


class Fragment_calculator : Fragment() {

    private var fragmentFirstBinding: FragmentCalculatorBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater.inflate(R.layout.fragment_calculator, container, false)
        fragmentFirstBinding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return fragmentFirstBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(IO).launch {
            val request = Request.Builder().url("http://www.cbr.ru/scripts/XML_daily.asp").build()
            val client = OkHttpClient().newCall(request).enqueue(object: Callback{
                override fun onFailure(call: Call, e: java.io.IOException) {
                    Log.i("error", e.message.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.i("tag", response.toString())
                }
            })
        }
    }
}