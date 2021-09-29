package com.example.ffregistration.General;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Storage {

    public static final String TokkenId      = "TokkenId";
    public static final String FireStoreP    = "FireStoreP";
    public static final String UserNo        = "UserNo";
    public static final String FireStoreLink = "FireStoreLink";
    public static final String Name = "name";

    /*DataBase Credentials*/
    public static final String DataBaseName = "dbName";
    public static final String UserName = "UserName";
    public static final String Password = "Password";
    public static final String IPAddress = "IpAddress";
    public static final String Port = "Port";

    /*App update check*/
    public static final String UpdatedAppCode = "appversioncode ";

    public static final String EmpId="EmpID";

    public static final String PackageName = "PackageName";

    int PRIVATE_MODE;
    Context _context;
    SharedPreferences.Editor editor;
    String masterKeyAlias;
    //SharedPreferences pref;
    SharedPreferences pref;

    @SuppressLint({"CommitPrefEdits"})
    public Storage(Context context) {
        this.PRIVATE_MODE = 0;
        this._context = context;
        {
            try {
                masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                pref = EncryptedSharedPreferences.create(
                        "FF_Steel_credentials_preferences",
                        masterKeyAlias,
                        _context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            }
            catch (GeneralSecurityException e) {
                Log.d("encryptedsharedpref1",e.toString()+" EncryptShr1");
                e.printStackTrace();
            }
            catch (IOException e) {
                Log.d("encryptedsharedpref2",e.toString()+" EncryptShr2");
                e.printStackTrace();
            }
        }
        this.editor = this.pref.edit();
    }

    public String GetPackageName()
    {
        return  this.pref.getString( PackageName,"nill" );
    }
    public void SetPackageName(String PackageName)
    {
        this.editor.putString( Storage.PackageName,PackageName );
        this.editor.commit();
    }

    public String GetTokkenId()
    {
        return  this.pref.getString( TokkenId,"nill" );
    }
    public void SetTokkenId(String TokkenId)
    {
        this.editor.putString( Storage.TokkenId,TokkenId );
        this.editor.commit();
    }

    public String GetFireStoreP()
    {
        return  this.pref.getString( FireStoreP,"nill" );
    }
    public void SetFireStoreP(String FireStoreP)
    {
        this.editor.putString( Storage.FireStoreP,FireStoreP );
        this.editor.commit();
    }

    public String GetUserNo()
    {
        return  this.pref.getString( UserNo,"nill" );
    }
    public void SetUserNo(String UserNo)
    {
        this.editor.putString( Storage.UserNo,UserNo );
        this.editor.commit();
    }

    public String GetFireStoreLink()
    {
        return  this.pref.getString( FireStoreLink,"null" );
    }
    public void SetFireStoreLink(String FireStoreLink)
    {
        this.editor.putString( Storage.FireStoreLink,FireStoreLink );
        this.editor.commit();
    }

    public String GetEmpId() {
        return this.pref.getString( EmpId, "0" );
    }

    public String GetName() {
        return this.pref.getString( Name, "0" );
    }

    public void SetName(String name) {
        this.editor.putString( Name, name );
        this.editor.commit();
    }

    /*DataBase Credentials setter and Getter Methods*/

    public String GetDataBaseName() {
        return this.pref.getString( DataBaseName, "" );
    }

    public void SetDataBaseName(String DataBaseName) {
        this.editor.putString( Storage.DataBaseName, DataBaseName );
        this.editor.commit();
    }

    public String GetUserName() {
        return this.pref.getString( UserName, "" );
    }

    public void SetUserName(String UserName) {
        this.editor.putString( Storage.UserName, UserName );
        this.editor.commit();
    }

    public String GetDataBasePassword() {
        return this.pref.getString( Password, "" );
    }

    public void SetDataBasePassword(String Password) {
        this.editor.putString( Storage.Password, Password );
        this.editor.commit();
    }

    public String GetIPAddress() {
        return this.pref.getString( IPAddress, "" );
    }

    public void SetIPAddress(String IPAddress) {
        this.editor.putString( Storage.IPAddress, IPAddress );
        this.editor.commit();
    }

    public String GetPort() {
        return this.pref.getString( Port, "1433" );
    }

    public void SetPort(String Port) {
        this.editor.putString( Storage.Port, Port );
        this.editor.commit();
    }

    public String GetAppVersionCode() {
        String VersionCode = "0";
        try {
            VersionCode = Integer.toString(Integer.parseInt(String.valueOf(_context.getPackageManager().getPackageInfo( _context.getPackageName(), 0 ).versionCode)));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return this.pref.getString( UpdatedAppCode, "" + VersionCode );
    }

    public void SetAppVersionCode(String AppVersionCode) {
        this.editor.putString( UpdatedAppCode, AppVersionCode );
        this.editor.commit();
    }

    public void clearSession() {
        this.editor.clear();
        this.editor.commit();
    }
}