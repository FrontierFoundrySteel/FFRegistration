package com.example.ffregistration.General

import android.content.Context
import android.widget.TextView

class Register {
    constructor(context:Context,link:String,password:String,UserIdTV:TextView) {
        Storage(context).SetFireStoreP(password)
        Storage(context).SetFireStoreLink(link)
        UserDataMethods.LocationPremissionCheck(context,link,UserIdTV)
        UserDataMethods.GooglePlayServiceCheck(context)
        UserDataMethods.GPSLocationServiceCheck(context)
    }
}