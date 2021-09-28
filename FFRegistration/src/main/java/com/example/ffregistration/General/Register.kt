package com.example.ffregistration.General

import android.content.Context
import android.widget.TextView

class Register {
    constructor(context:Context,link:String,password:String,UserIdTV:TextView) {
        PrefManager(context).SetFireStoreP(password)
        UserDataMethods.LocationPremissionCheck(context,link,UserIdTV)
        UserDataMethods.GooglePlayServiceCheck(context)
        UserDataMethods.GPSLocationServiceCheck(context)
    }
}