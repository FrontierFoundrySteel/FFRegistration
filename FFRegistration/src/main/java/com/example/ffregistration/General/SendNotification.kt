package com.example.ffregistration.General

import android.content.Context
import com.example.ffregistration.General.UserDataMethods.sendNotificationRetroCorou


class SendNotification {
    constructor(context: Context, OHEMUserID: String, AppID: String, Body:String,IsItPosition:String){
        sendNotificationRetroCorou(context,OHEMUserID,AppID,Body,IsItPosition)
    }
}