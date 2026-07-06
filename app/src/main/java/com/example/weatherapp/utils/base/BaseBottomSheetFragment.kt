package com.example.weatherapp.utils.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.weatherapp.R
import com.example.weatherapp.utils.network.NetworkChecker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseBottomSheetFragment<T : ViewBinding> : BottomSheetDialogFragment() {
    //Binding
    protected abstract val bindingInflater: (inflater: LayoutInflater) -> T
    private var _binding: T? = null
    protected val binding: T get() = requireNotNull(_binding)

    @Inject
    lateinit var networkChecker: NetworkChecker

    //Other
    var isNetworkAvailable = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Check network
        lifecycleScope.launch {
            networkChecker.checkNetwork().collect {
                isNetworkAvailable = it
                if (!it) {
                    Toast.makeText(requireContext(), R.string.checkYourNetwork, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getTheme(): Int = R.style.RemoveDialogBackground

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            dialog?.window?.setBackgroundDrawableResource(R.color.backShadow)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}