package com.example.ffregistration.General

import android.Manifest
import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Context.TELEPHONY_SERVICE
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.multidex.BuildConfig
import com.ffsteel.ffpractice_app1.Models.UserRegistrationResponseDataModel
import com.ffsteel.ffpractice_app1.Services.RetrofitInterfaces
import com.ffsteel.ffpractice_app1.Services.ServiceBuilder
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
//import ff.steel.procurement.BuildConfig
import com.example.ffregistration.General.PrefManager
import com.example.ffregistration.General.UserDataMethods.getAppName
import com.example.ffregistration.General.Util
import com.example.ffregistration.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.*
import retrofit2.*
import java.io.IOException
import java.sql.ResultSet
import java.util.*

object UserDataMethods {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    lateinit var locationManager: LocationManager
    var dialog: ProgressDialog? = null
    var string_city: String? = null
    var string_state: String? = null
    var string_country: String? = null
    var string_location: String? = null
    var stringLatitude: String? = null
    var stringLongitude: String? = null
    var stringLooking: String? = null
    lateinit var InsertionJobGlobal: Job

    fun currentVersion(): String? {
        val release = Build.VERSION.RELEASE.replace("(\\d+[.]\\d+)(.*)".toRegex(), "$1").toDouble()
        var codeName = "Unsupported" //below Jelly bean OR above Oreo
        if (release >= 4.1 && release < 4.4) codeName = "Jelly Bean" else if (release < 5) codeName = "Kit Kat" else if (release < 6) codeName = "Lollipop" else if (release < 7) codeName = "Marshmallow" else if (release < 8) codeName = "Nougat" else if (release < 9) codeName = "Oreo" else if (release < 10) codeName = "Pie" else if (release < 11) codeName = "Android Q"
        return codeName + " v" + release + ", API Level: " + Build.VERSION.SDK_INT
    }

    /** Returns the consumer friendly device name  */
    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }

    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true
        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }
        return phrase.toString()
    }

    fun Context.getAppName(): String = applicationInfo.loadLabel(packageManager).toString()

    fun getEmail(context: Context?): String? {
        val accountManager = AccountManager.get(context)
        val account = getAccount(accountManager)
        return account?.name
    }

    private fun getAccount(accountManager: AccountManager): Account? {
        val accounts = accountManager.getAccountsByType("com.google")
        val account: Account?
        account = if (accounts.size > 0) {
            Log.d("emailastam", accounts.size.toString() + " #1")
            accounts[0]
        }
        else {
            Log.d("emailastam", accounts.size.toString() + " #2")
            null
        }
        return account
    }

    fun isDeviceRooted(): Boolean {
        return Util.checkRootMethod1() || Util.checkRootMethod2() || Util.checkRootMethod3()
    }

    fun getIMEI(context: Context): String {
        val tm = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        lateinit var IMEIS: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            IMEIS= Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            }
            return IMEIS;
        }
        else
        {
            if (tm.deviceId != null) {
                IMEIS=tm.deviceId
            } else {
                IMEIS= Settings.Secure.getString(
                        context.contentResolver,
                        Settings.Secure.ANDROID_ID)
            }
            return IMEIS
        }
    }

    private fun LocationRequest(context: Context,url:String) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PermissionChecker.PERMISSION_GRANTED) {
            fusedLocationProviderClient = FusedLocationProviderClient(context)
            fusedLocationProviderClient?.getLastLocation()?.addOnSuccessListener(OnSuccessListener<Location?> { location ->
                if (location != null) {
                    val locationLatitude = location.latitude
                    val locationLongitude = location.longitude
                    stringLatitude = locationLatitude.toString()
                    stringLongitude = locationLongitude.toString()
                    if (stringLatitude != "0.0" && stringLongitude != "0.0") {
                        LocationRetreive(locationLatitude, locationLongitude, context,url)
                    }
                    else {
                        Toast.makeText(context, "Please turn on any GPS or location service and restart to use the app", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(context, "Please turn on any GPS or location service and restart to use the app", Toast.LENGTH_SHORT).show()
                }
            }
            )
        }
        else {
            LocationPremissionCheck(context,url)
        }
    }

    private fun LocationRetreive(locationLatitude: Double, locationLongitude: Double, context: Context,url:String) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(locationLatitude, locationLongitude, 1)
            if (addresses != null && addresses.size > 0) {
                string_city = addresses[0].locality
                string_state = addresses[0].adminArea
                string_country = addresses[0].countryName
                string_location = addresses[0].getAddressLine(0)
                if (string_country == null) {
                    if (string_state != null) {
                        string_country = string_state
                    }
                    else if (string_city != null) {
                        string_country = string_city
                    }
                    else {
                        string_country = "null"
                    }
                }
                if (string_city == null) {
                    if (string_state != null) {
                        string_city = string_state
                    }
                    else {
                        string_city = string_country
                    }
                }
                if (string_state == null) {
                    if (string_city != null) {
                        string_state = string_city
                    }
                    else {
                        string_state = string_country
                    }
                }
                if (string_location == null) {
                    string_location = "Null"
                }
                InitialRegistrationAT(context,url).execute()

            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun GooglePlayServiceCheck(context: Context): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(context)
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(context as Activity?, status, 2404).show()
            }
            return false
        }
        return true
    }

    fun GPSLocationServiceCheck(context: Context):Boolean {
        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return true
        }
        return false
    }

    fun LocationPremissionCheck(contexts: Context,url: String) {
        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        val rationale = "Please provide location permission so that you can ..."
        val options: Permissions.Options = Permissions.Options()
                .setRationaleDialogTitle("Location Permission")
                .setSettingsDialogTitle("Warning")
        Permissions.check(contexts /*context*/, permissions, null /*rationale*/, null /*options*/, object : PermissionHandler() {
            override fun onGranted() {
                LocationRequest(contexts,url)
            }

            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String?>?) {
                super.onDenied(context, deniedPermissions)
                LocationPremissionCheck(contexts,url)
            }
        })
    }

    fun updateHCMUserIDRetroCorou(context: Context,url: String) = CoroutineScope(Dispatchers.IO).launch {
        try {

            val updateHCMUserIDInterfaceService= ServiceBuilder(url).buildService(RetrofitInterfaces::class.java)
            val response=updateHCMUserIDInterfaceService.updateHCMUserID(PrefManager(context).GetFireStoreP()+"",PrefManager(context).GetEmpId()+"", getIMEI(context)+"")

            if(response.isSuccessful) {
                var updateHCMUserIDrespobody = response.body() // Use it or ignore it

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, response.body()?.response + "!", Toast.LENGTH_LONG).show()
                }
                Log.d("successfultoupdateHCMUserID", "successfultoupdateHCMUserID  ${response.body()?.response}")
            }
            else {

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Failed to update HCM UserID", Toast.LENGTH_LONG).show()
                }
                Log.d("unsuccessfulupdateHCMUserID", "unsuccessfulupdateHCMUserID  ")
            }
        }
        catch(e: Exception) {

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Failed to update HCM UserID $e", Toast.LENGTH_LONG).show()
            }
            Log.d("FailedtoupdateHCMUserID", "FailedtoupdateHCMUserID  $e")
        }
    }

    fun sendNotificationRetroCorou(context: Context, HCMUserID: String, jobcardNo: String,url:String) = CoroutineScope(Dispatchers.IO).launch {
        try {

            val updateHCMUserIDInterfaceService= ServiceBuilder(url).buildService(RetrofitInterfaces::class.java)
            val response=updateHCMUserIDInterfaceService.sendNotification(PrefManager(context).GetFireStoreP()+"",HCMUserID+"", "Jobcard ($jobcardNo) needs approval!",PrefManager(context).GetName()+"")

            if(response.isSuccessful) {
                var notificationRespobody = response.body() // Use it or ignore it

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, response.body()?.response + "!", Toast.LENGTH_LONG).show()
                }
                Log.d("successfultoupdateHCMUserID", "successfultoupdateHCMUserID  ${response.body()?.response}")
            }
            else {

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "unsuccessful to send notification", Toast.LENGTH_LONG).show()
                }
                Log.d("unsucessfulltoSendNotification", "unsucessfulltoSendNotification  ")
            }
        }
        catch(e: Exception) {

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Failed to send notification $e", Toast.LENGTH_LONG).show()
            }
            Log.d("FailedtosendNotification", "FailedtosendNotification  $e")
        }
    }

    class InitialRegistrationAT(cntxt: Context,URL:String) : CoroutineAsyncTask<String?, String?, String?>() {
        var z = ""
        var isSuccess = false
        var pd: ProgressDialog? = null
        var context: Context
        var url: String

        init {
            context=cntxt
            url=URL
        }

        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog(context)
            pd?.setMessage("Initial Registration....")
            pd?.show()
        }

        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)
            pd?.dismiss()
            if(isSuccess){
                Toast.makeText(context,"$z.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"$z", Toast.LENGTH_SHORT).show()
            }
        }

        override fun doInBackground(vararg params: String?): String? {
            try {
                val ShortTokkenID = PrefManager(context).GetTokkenId().toString().split(":".toRegex()).toTypedArray()
                ShortTokkenID[0]
                val result = when(isDeviceRooted()) {
                    true -> "YES"
                    false -> "NO"}
                val destinationService = ServiceBuilder(url).buildService(RetrofitInterfaces::class.java)
                val requestCall = destinationService.addUser(PrefManager(context).GetTokkenId() + "", ShortTokkenID[0] + "", PrefManager(context).GetPackageName() + "", context.getAppName() + "", BuildConfig.VERSION_CODE.toString() + "",
                    getEmail(context) + "", context.getAppName() + " User", getDeviceName() + "",  "", getIMEI(context) + "",
                    "0", string_location + "", string_city + "", string_state + "", string_country + "", stringLatitude + "", stringLongitude + "", currentVersion() + "", result + "", PrefManager(context).GetFireStoreP() + "")

                //context.resources.getString(R.string.screen_type)
                requestCall.enqueue(object : Callback<List<UserRegistrationResponseDataModel>> {
                    override fun onResponse(call: Call<List<UserRegistrationResponseDataModel>>, response: Response<List<UserRegistrationResponseDataModel>>) {
                        if (response.isSuccessful) {
                            //finish() // Move back to DestinationListActivity
                            Log.d("Credentials007", "  ${response.body()?.get(0)?.response}")
                            PrefManager(context).SetDataBaseName(response.body()?.get(0)?.database)
                            PrefManager(context).SetUserName(response.body()?.get(0)?.user)
                            PrefManager(context).SetDataBasePassword(response.body()?.get(0)?.password)
                            PrefManager(context).SetIPAddress(response.body()?.get(0)?.ip)
                            PrefManager(context).SetPort(response.body()?.get(0)?.port)

                            val ip = PrefManager(context).GetIPAddress()
                            val db = PrefManager(context).GetDataBaseName()
                            val un = PrefManager(context).GetUserName()
                            val password = PrefManager(context).GetDataBasePassword()
                            val port = PrefManager(context).GetPort()

                            Log.d("Credentials", " $ip $db $un $password $port ${response.body()?.get(0)?.response}")

                            var newlyCreatedDestination = response.body() // Use it or ignore it
                            Toast.makeText(context, response.body()?.get(0)?.response + "", Toast.LENGTH_LONG).show()
                            z=response.body()?.get(0)?.response.toString()

                        } else {
                            Toast.makeText(context, "Failed to Register!", Toast.LENGTH_LONG).show()
                            z="Failed to Register!"
                            isSuccess=false
                            return
                        }
                    }

                    override fun onFailure(call: Call<List<UserRegistrationResponseDataModel>>, t: Throwable) {
                        Toast.makeText(context, "Failed to Register!! " + t, Toast.LENGTH_LONG).show()
                        z="Failed to Register!! "
                        isSuccess=false
                        return
                    }
                })
                isSuccess=true
            }
            catch (ex: Exception) {
                isSuccess = false
                z = "Exceptions$ex"
                Log.d("InitialRegistration", "IR "+z)
            }
            return z
        }
    }

}