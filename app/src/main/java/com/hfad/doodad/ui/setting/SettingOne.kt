package com.hfad.doodad.ui.setting


import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.hfad.doodad.R
import kotlinx.android.synthetic.main.fragment_setting_one.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.math.BigInteger


const val PENDING_RESULT = "pending_result"
const val RESULT = "result"
val RESULT_CODE = "nth_prime".hashCode()
const val REQUEST_CODE = 0

class SettingOne : Fragment() {

    lateinit var intent : Intent
    val br = BR()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_one, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE){
            Toast.makeText(requireContext(), "onActivityResult ", Toast.LENGTH_SHORT).show()
            val a = (data?.getSerializableExtra(RESULT) as BigInteger)

        }
    }

    class BR : BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent?) {
            CoroutineScope(Main).launch {
                Toast.makeText(context, "ALARM ALARM", Toast.LENGTH_LONG).show()
                Log.d("TAG", "onReceive: ALARM ALARM")
            }
//            exitProcess(424)
        }
    }


    override fun onStop() {
        requireContext().unregisterReceiver(br)
        super.onStop()
    }
    override fun onResume() {
        super.onResume()
        val IF = IntentFilter("abc")
        requireActivity().registerReceiver(br, IF)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        intent = Intent( requireContext() , MyService::class.java)
        intent.putExtra(MyService.param, 500)
        val pendingIntent =  requireActivity(). createPendingResult(REQUEST_CODE, Intent(),
            0 )
        intent.putExtra( PENDING_RESULT, pendingIntent)


        start.setOnClickListener{
            val tp = TimePickerVoo(TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->

                Toast.makeText(requireContext(), "$hourOfDay,  $minute", Toast.LENGTH_SHORT).show()
            })
            tp.show( this.childFragmentManager, "TIme Picker")

            val builder =
                NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setContentTitle("Reminder!")
                    .setVibrate(longArrayOf(0, 100))
                    .setContentText("intent.getStringExtra(MSG)")

            val nm = requireContext().getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            nm.notify(intent.hashCode(), builder.build())

            


//            PugNotification.with(requireContext())
//                .load().color(R.color.colorAccent)
//                .title("a;skdfj")
//                .vibrate(longArrayOf(0, 150))
//                .message("aksdjf")
//                .simple().build()

        }

        stop.setOnClickListener{
//            requireActivity().stopService(intent)
        }
    }
}

class MyService : IntentService("IntentService"){

    companion object{
        val param = "extraInt"
    }
    val job = Job()
    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(this, "onBInd", Toast.LENGTH_SHORT).show()
        return null
    }



    override fun onHandleIntent(intent: Intent) {
        //Toast.makeText(this, "Finished, ${Thread.currentThread().name}", Toast.LENGTH_SHORT).show()
        val n = intent.getIntExtra(param, -1) ?: -1
        var bi: BigInteger = BigInteger.valueOf(2)
        for ( a in 0 ..n)
            bi = bi.nextProbablePrime()
        Thread.sleep(5000)

        val result = Intent()
        result.putExtra(RESULT, bi)
        val pendingIntent = intent.getParcelableExtra<PendingIntent>( PENDING_RESULT )
        pendingIntent.send(this, RESULT_CODE, result )
    }

    suspend fun work() = withContext( job + IO){
        delay(10000)

        withContext(Main){
            Toast.makeText( this@MyService, "LOOOOONG", Toast.LENGTH_SHORT).show()
        }
        this@MyService.stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (job.isActive)
            job.cancel()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show()
    }

}