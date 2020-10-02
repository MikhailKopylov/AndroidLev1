package com.amk.weatherforall.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.amk.weatherforall.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeleteCityDialog:BottomSheetDialogFragment() {

    companion object{
        fun newInstance(): DeleteCityDialog{
            return DeleteCityDialog()
        }
    }

    var onDialogListener:OnDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.delete_city_dialog_fragment, container, false)
        isCancelable = false

        view.findViewById<Button>(R.id.delete_city_button).setOnClickListener {
//            Snackbar.make(view, "Connect again", Snackbar.LENGTH_SHORT).show()
            dismiss()
            onDialogListener?.onDialogOK()
        }

        view.findViewById<Button>(R.id.cancel_delete_button).setOnClickListener {
//            Snackbar.make(view, "Connect again", Snackbar.LENGTH_SHORT).show()
            dismiss()
            onDialogListener?.onDialogCancel()
        }

        return view
    }
}