package et.aait.itsc.abduselam.ussdregistration

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import et.aait.itsc.abduselam.ussdregistration.data.RegionalVoter
import et.aait.itsc.abduselam.ussdregistration.data.SharedData
import et.aait.itsc.abduselam.ussdregistration.viewmodel.CityVoterViewModel
import et.aait.itsc.abduselam.ussdregistration.viewmodel.RegionalVoterViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.dialog_success.view.*
import java.lang.IllegalStateException

class StatusAndPinDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val statusPinDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_pin_status, null)
            val regionalVoterViewModel = ViewModelProviders.of(this).get(RegionalVoterViewModel::class.java)
            val cityVoterViewModel = ViewModelProviders.of(this).get(CityVoterViewModel::class.java)
            Log.e("city or region", SharedData.statusPin[0])
            if(SharedData.statusPin[0] == "Region") {
                regionalVoterViewModel.getRegionalVoterByPhoneNumber(SharedData.statusPin[1])
                regionalVoterViewModel.getResponse.observe(this, androidx.lifecycle.Observer {
                        response -> response.body()?.run {
                    statusPinDialogView.pin.text = this.voterPIN.toString()
                }
                })

            }
            else {

                cityVoterViewModel.getCityVoterByPhoneNumber(SharedData.statusPin[1])
                cityVoterViewModel.getResponse.observe(this, androidx.lifecycle.Observer {
                        response -> response.body()?.run {
                    statusPinDialogView.pin.text = this.voterPIN.toString()
                }
                })

            }
            builder.setView(statusPinDialogView)

                .setTitle("Your Info!")
                .setPositiveButton(R.string.next, DialogInterface.OnClickListener{ dialog, id ->
                    dismiss()

                })

            builder.create()


        } ?: throw IllegalStateException("Actitvity Cannot be null")
    }

}
