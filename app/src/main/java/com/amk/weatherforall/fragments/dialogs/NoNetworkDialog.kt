package com.amk.weatherforall.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.amk.weatherforall.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class NoNetworkDialog: BottomSheetDialogFragment() {


    companion object{
        fun newInstance(): NoNetworkDialog{
            return NoNetworkDialog()
        }
    }

    var onDialogListener:OnDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.no_network_dialog_fragment, container, false)
        isCancelable = false

        view.findViewById<Button>(R.id.connect_again_button).setOnClickListener {
//            Snackbar.make(view, "Connect again", Snackbar.LENGTH_SHORT).show()
            dismiss()
            onDialogListener?.onDialogReconnect()
        }

        view.findViewById<Button>(R.id.cancel_network_button).setOnClickListener {
//            Snackbar.make(view, "Connect again", Snackbar.LENGTH_SHORT).show()
            dismiss()
            onDialogListener?.onDialogCancel()
        }

        return view
    }


}