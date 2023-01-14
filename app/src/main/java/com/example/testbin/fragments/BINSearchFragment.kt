package com.example.testbin.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testbin.MainViewModel
import com.example.testbin.adapters.CardItemModel
import com.example.testbin.databinding.FragmentBINSearchBinding
import org.json.JSONObject

class BINSearchFragment : Fragment() {
    private lateinit var binding: FragmentBINSearchBinding
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBINSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            if (binding.textInputEditText.text?.isEmpty() == true)
                Toast.makeText(context, "Заполни", Toast.LENGTH_SHORT).show()
            else {
                requestBIN(binding.textInputEditText.text.toString().toInt())
                updateCard()
                binding.cardView.visibility = View.VISIBLE
            }
        }
    }

    private fun updateCard() = with(binding) {
        model.liveDataCurrent.observe(viewLifecycleOwner) { it ->
            val tempCountry = "${it.countryEmoji} ${it.countryName}"
            val tempCord = "(latitude: ${it.countryLatitude}, longitude: ${it.countryLongitude})"
            tvScheme.text = it.scheme.replaceFirstChar { it.uppercase() }
            tvBrand.text = it.brand
            tvType.text = it.type.replaceFirstChar { it.uppercase() }
            tvPrepaid.text = it.prepaid.replace("true","Yes").replace("false","No")
            tvBank.text = it.bankName
            tvCity.text = it.bankCity
            tvUrl.text = it.bankUrl
            tvPhone.text = it.bankPhone
            tvLenght.text = it.cardNumberLength
            tvLuhn.text = it.cardNumberLuhn.replace("true","Yes").replace("false","No")
            textCountry.text = tempCountry
            tvCord.text = tempCord
        }
    }

    private fun requestBIN(bin: Int) {
        val url = "https://lookup.binlist.net/$bin"
        val queue = Volley.newRequestQueue(context)
        val requestString = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                parseBINResult(result)
                //Log.d("MyLog", "Result: $result")
            },
            { error ->
                Log.d("MyLog", "Error: $error")
            }
        )
        queue.add(requestString)
    }

    private fun parseBINResult(result: String) {
        val mainObject = JSONObject(result)
        val item = CardItemModel(
            mainObject.getString("scheme"),
            mainObject.getString("type"),
            mainObject.getString("brand"),
            mainObject.getString("prepaid"),
            mainObject.getJSONObject("bank").getString("name"),
            mainObject.getJSONObject("bank").getString("city"),
            mainObject.getJSONObject("bank").getString("url"),
            mainObject.getJSONObject("bank").getString("phone"),
            mainObject.getJSONObject("number").getString("length"),
            mainObject.getJSONObject("number").getString("luhn"),
            mainObject.getJSONObject("country").getString("emoji"),
            mainObject.getJSONObject("country").getString("name"),
            mainObject.getJSONObject("country").getString("latitude"),
            mainObject.getJSONObject("country").getString("longitude"),
        )
        model.liveDataCurrent.value = item
    }

    companion object {
        @JvmStatic
        fun newInstance() = BINSearchFragment()
    }
}