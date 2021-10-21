package com.example.ffregistration.General

import android.content.Context
import com.example.ffregistration.General.UserDataMethods.sendNotificationRetroCorou


class SendNotification {
    constructor(context: Context, OHEMUserID: String, Title: String, Body:String,IsItPosition:String,Email:String){
        sendNotificationRetroCorou(context,OHEMUserID,Title,Body,IsItPosition,Email)
    }

    constructor(context: Context, OHEMUserID: String, Title: String, Body:String,IsItPosition:String){
        sendNotificationRetroCorou(context,OHEMUserID,Title,Body,IsItPosition)
    }
}