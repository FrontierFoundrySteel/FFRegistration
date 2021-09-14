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

public class PrefManager {

    public static final String TokkenId    = "TokkenId";
    public static final String FireStoreP    = "FireStoreP";
    public static final String FireStorePbu    = "FireStorePbu";
    public static final String FireStoreLink = "FireStoreLink";
    public static final String FireStoreLinkbu = "FireStoreLinkbu";
    public static final String PREF_NAME = "FF_Steel_shared_preferences";
    public static final String LoggingInfo = "LoginInfo";
    public static final String Name = "name";
    public static final String CurrentDay = "day";
    public static final String LastDay = "Lastday";
    public static final String Level="";
    public static final String ManagerMobileNumber = "ManagerNo";
    public static final String MobileNumber = "mobilenumber";
    public static final String Designation = "designation";
    public static final String U_SType = "U_stype";
    public static final String SlpCode = "SPLCode";
    public static final String ZC_Email_Address = "ZC_Email_Address";
    public static final String DSO_Email_Address = "DSO_Email_Address";
    public static final String Territory = "Territory";

    /*DataBase Credentials*/
    public static final String DataBaseName = "dbName";
    public static final String UserName = "UserName";
    public static final String Password = "Password";
    public static final String IPAddress = "IpAddress";
    public static final String Port = "Port";
    public static final String WarkamDangYo = "WaasdfgitrkaDang";

    /*App update check*/
    public static final String UpdatedAppCode = "appversioncode ";
    public static final String GotoNewOrOldRateCalculator = "GotoNewOrOldRateCalculator";

    /*
     * ChecklastQuotation
     * */
    public static final String CheckLastQoutationNo = "QuotationNo";
    public static final String CheckLastQoutationFromWhere = "FromDashBoard";

    public static final String EmpId="EmpID";
    public static final String DEPARTMENT = "DEPARTMENT";
    /*
     * ChecklastQuotation
     * */
    public static final String CheckLastGiveAwayNo = "GiveAwayNo";
    public static final String CheckLastGiveAwayFromWhere = "GiveAwayFromDashBoard";

    /*App Whats New Update check*/
    public static final String CheckWhatsNewUpdate = "1";

    public static final String POSITIONTYPE="DepartmentId";

    public static final String Position="Position";


    /*Website Images URL */
    public static final String WebsiteURL = "https://ff.com.pk/CRM";

    /*Payment Confirmation Slip Website Images URL */
    public static final String BCSFolder = "BCSImages";

    /*SaleOrder Images URL */
    public static final String SOFFolder = "SOFImages";

    /*Task And Visit Website Images URL */
    public static final String TVRFolder = "TVRImages";

    /*Credit Limit Images Website Images URL */
    public static final String CreditLimitImagesFolder = "CreditLimitImage";


    /* Audit Reports Date */
    public static final String AuditeDateForReports = "2019-07-01 00:00:00.000";

    public static final String JobCardCode="JobCardCode";
    public static final String JobCardEmpId="EId";
    public static final String JobCardEmployeName="JobCardEmployeeName";
    public static final String Location="Location";
    public static final String StartRange="StartRange";
    public static final String EndRange="EndRange";
    public static final String ContactNumber="contactNum";
    public static final String Memo="Memo";

    /* Linear Ads Data*/



    public static final String FIRSTSUPERVISOR = "FIRSTSUPERVISOR";
    public static final String SECONDSUPERVISOR = "SECONDSUPERVISOR";
    public static final String THIRDSUPERVISOR = "THIRDSUPERVISOR";
    public static final String FORTHSUPERVISOR = "FORTHSUPERVISOR";
    public static final String FIRFTHSUPERVISOR = "FIFTHSUPERVISOR";
    public static final String SIXSUPERVISOR = "SIXSUPERVISOR";
    public static final String SEVENSUPERVISOR = "SEVENSUPERVISOR";
    public static final String EIGHTSUPERVISOR = "EIGHTSUPERVISOR";
    public static final String PROCUREMENTENGINEER = "PROCUREMENTENGINEER";
    public static final String OWNERONWE = "OWNERONWE";
    public static final String PackageName = "PackageName";

    int PRIVATE_MODE;
    Context _context;
    SharedPreferences.Editor editor;
    String masterKeyAlias;
    //SharedPreferences pref;
    SharedPreferences pref;

    @SuppressLint({"CommitPrefEdits"})
    public PrefManager(Context context) {
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

        //this.pref = this._context.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
        this.editor = this.pref.edit();

    }


    public String GetPackageName()
    {
        return  this.pref.getString( PackageName,"nill" );
    }
    public void SetPackageName(String PackageName)
    {
        this.editor.putString( PrefManager.PackageName,PackageName );
        this.editor.commit();
    }

    public String GetFireStoreLinkbu()
    {
        return  this.pref.getString( FireStoreLinkbu,"https://ff.com.pk/FCM/" );
    }
    public void SetFireStoreLinkbuk(String FireStoreLinkbu)
    {
        this.editor.putString( PrefManager.FireStoreLinkbu,FireStoreLinkbu );
        this.editor.commit();
    }

    public String GetTokkenId()
    {
        return  this.pref.getString( TokkenId,"null" );
    }
    public void SetTokkenId(String TokkenId)
    {
        this.editor.putString( PrefManager.TokkenId,TokkenId );
        this.editor.commit();
    }

    public String GetFireStoreP()
    {
        return  this.pref.getString( FireStoreP,"null" );
    }
    public void SetFireStoreP(String FireStoreP)
    {
        this.editor.putString( PrefManager.FireStoreP,FireStoreP );
        this.editor.commit();
    }
    public String GetFireStorePbu()
    {
        return  this.pref.getString( FireStorePbu,"FF_Steel_!@#$%" );
    }
    public void GetFireStorePbu(String FireStorePbu)
    {
        this.editor.putString( PrefManager.FireStorePbu,FireStorePbu );
        this.editor.commit();
    }

    public String GetFireStoreLink()
    {
        return  this.pref.getString( FireStoreLink,"null" );
    }
    public void SetFireStoreLink(String FireStoreLink)
    {
        this.editor.putString( PrefManager.FireStoreLink,FireStoreLink );
        this.editor.commit();
    }

    public String GetEid()
    {
        return  this.pref.getString( JobCardEmpId,"" );
    }
    public void SetJobCardEmpId(String JobCardEmpId)
    {
        this.editor.putString( PrefManager.JobCardEmpId,JobCardEmpId );
        this.editor.commit();
    }

    public String GetJobCardCode()
    {
        return  this.pref.getString( JobCardCode,"" );
    }
    public void SetJobCardCode(String JobCardCode)
    {
        this.editor.putString( PrefManager.JobCardCode,JobCardCode );
        this.editor.commit();
    }

    public String GetEmpId() {
        return this.pref.getString( EmpId, "0" );
    }

    public void SetEmpId(String Emp) {
        this.editor.putString( EmpId, Emp );
        this.editor.commit();
    }

    public String GetJobCardEmployeName()
    {
        return  this.pref.getString( JobCardEmployeName,"" );
    }
    public void SetJobCardEmployeName(String JobCardEmployeName)
    {
        this.editor.putString( PrefManager.JobCardEmployeName,JobCardEmployeName );
        this.editor.commit();
    }

    public String GetLocation()
    {
        return  this.pref.getString( Location,"" );
    }
    public void SetLocation(String Location)
    {
        this.editor.putString( PrefManager.Location,Location );
        this.editor.commit();
    }


    public String GetStartRange()
    {
        return  this.pref.getString( StartRange,"" );
    }
    public void SetStartRange(String StartRange)
    {
        this.editor.putString( PrefManager.StartRange,StartRange );
        this.editor.commit();
    }

    public String GetEndRange()
    {
        return  this.pref.getString( EndRange,"" );
    }
    public void SetEndRange(String EndRange)
    {
        this.editor.putString( PrefManager.EndRange,EndRange );
        this.editor.commit();
    }

    public String GetContactNumber()
    {
        return  this.pref.getString( ContactNumber,"" );
    }
    public void SetContactNumber(String ContactNumber)
    {
        this.editor.putString( PrefManager.ContactNumber,ContactNumber );
        this.editor.commit();
    }

    public String GetMemo()
    {
        return  this.pref.getString( Memo,"" );
    }
    public void SetMemo(String Memo)
    {
        this.editor.putString( PrefManager.Memo,Memo );
        this.editor.commit();
    }


    public String GetCurrentDay() {
        return this.pref.getString( CurrentDay, "0" );
    }

    public void SetCurrentDay(String CurrentDay) {
        this.editor.putString( PrefManager.CurrentDay, CurrentDay );
        this.editor.commit();
    }


    public String GetStoreLastDay() {
        return this.pref.getString( LastDay, "2017-01-01" );
    }


    public void SetStoreLastDay(String LastDay) {
        this.editor.putString( PrefManager.LastDay, LastDay );
        this.editor.commit();
    }


    public String GetZcEmailAddress() {
        return this.pref.getString( ZC_Email_Address, "0" );
    }

    public void SetZcEmailAddress(String ZC_Email_Address) {
        this.editor.putString( PrefManager.ZC_Email_Address, ZC_Email_Address );
        this.editor.commit();
    }


    public String GetDsoEmailAddress() {
        return this.pref.getString( DSO_Email_Address, "0" );
    }

    public void SetDsoEmailAddress(String DSO_Email_Address) {
        this.editor.putString( PrefManager.DSO_Email_Address, DSO_Email_Address );
        this.editor.commit();
    }


    public String GetManagerMobileNumber() {
        return this.pref.getString( ManagerMobileNumber, "0" );
    }

    public void SetManagerMobileNumber(String ManagerMobileNumber) {
        this.editor.putString( PrefManager.ManagerMobileNumber, ManagerMobileNumber );
        this.editor.commit();
    }


    public String GetLoggingInfo() {
        return this.pref.getString( LoggingInfo, "0" );
    }

    public void SetLoggingInfo(String LoggingInfo) {
        this.editor.putString( PrefManager.LoggingInfo, LoggingInfo );
        this.editor.commit();
    }


    public String GetName() {
        return this.pref.getString( Name, "0" );
    }

    public void SetName(String name) {
        this.editor.putString( Name, name );
        this.editor.commit();
    }


    public String GetSlpCode() {
        return this.pref.getString( SlpCode, "0" );
    }

    public void SetSlpCode(String SlpCode) {
        this.editor.putString( PrefManager.SlpCode, SlpCode );
        this.editor.commit();
    }


    public String GetMobileNumber() {
        return this.pref.getString( MobileNumber, "0" );
    }

    public void SetMobileNumber(String MobileNumber) {
        this.editor.putString( PrefManager.MobileNumber, MobileNumber );
        this.editor.commit();
    }


    public String GetDesignation() {
        return this.pref.getString( Designation, "0" );
    }

    public void SetDesignation(String Designation) {
        this.editor.putString( PrefManager.Designation, Designation );
        this.editor.commit();
    }

    public String GetU_Stype() {
        return this.pref.getString( U_SType, "0" );
    }

    public void SetU_Stype(String U_Stype) {
        this.editor.putString( U_SType, U_Stype );
        this.editor.commit();
    }


    public String GetLevel() {
        return this.pref.getString( Level, "0" );
    }

    public void SetLevel(String Level) {
        this.editor.putString( PrefManager.Level, Level );
        this.editor.commit();
    }

    public String GetTerritory() {
        return this.pref.getString( Territory, "" );
    }

    public void SetTerritory(String Territory) {
        this.editor.putString( PrefManager.Territory, Territory );
        this.editor.commit();
    }


    /*DataBase Credentials setter and Getter Methods*/

    public String GetDataBaseName() {
        return this.pref.getString( DataBaseName, "" );
    }

    public void SetDataBaseName(String DataBaseName) {
        this.editor.putString( PrefManager.DataBaseName, DataBaseName );
        this.editor.commit();
    }

    public String GetUserName() {
        return this.pref.getString( UserName, "" );
    }

    public void SetUserName(String UserName) {
        this.editor.putString( PrefManager.UserName, UserName );
        this.editor.commit();
    }

    public String GetDataBasePassword() {
        return this.pref.getString( Password, "" );
    }

    public void SetDataBasePassword(String Password) {
        this.editor.putString( PrefManager.Password, Password );
        this.editor.commit();
    }

    public String GetIPAddress() {
        return this.pref.getString( IPAddress, "" );
    }

    public void SetIPAddress(String IPAddress) {
        this.editor.putString( PrefManager.IPAddress, IPAddress );
        this.editor.commit();
    }

    public String GetPort() {
        return this.pref.getString( Port, "1433" );
    }

    public void SetPort(String Port) {
        this.editor.putString( PrefManager.Port, Port );
        this.editor.commit();
    }


    public String GetNewOrOldRateCalculatorCheck() {
        return this.pref.getString( GotoNewOrOldRateCalculator, "0" );
    }

    public void SetNewOrOldRateCalculatorCheck(String BypassOverdueCheck) { // 0 for new RateCalculator and 1 for Old RateCalculator
        this.editor.putString( PrefManager.GotoNewOrOldRateCalculator, BypassOverdueCheck );
        this.editor.commit();
    }


    public String GetAppVersionCode() {
        String VersionCode = "0";
        try {
            VersionCode = Integer.toString( _context.getPackageManager().getPackageInfo( _context.getPackageName(), 0 ).versionCode );
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return this.pref.getString( UpdatedAppCode, "" + VersionCode );
    }

    public void SetAppVersionCode(String AppVersionCode) {
        this.editor.putString( UpdatedAppCode, AppVersionCode );
        this.editor.commit();
    }

    public String GetWhatsNew() {
        return this.pref.getString( CheckWhatsNewUpdate, "1" );
    }

    public void SetWhatsNew(String CheckWhatsNewUpdate) {
        this.editor.putString( PrefManager.CheckWhatsNewUpdate, CheckWhatsNewUpdate );
        this.editor.commit();
    }


    public String GetCheckLastQoutationFromWhere() {
        return this.pref.getString( CheckLastQoutationFromWhere, "FromDashBoard" );
    }

    public void SetCheckLastQoutationFromWhere(String CheckLastQoutationFromWhere) {
        this.editor.putString( PrefManager.CheckLastQoutationFromWhere, CheckLastQoutationFromWhere );
        this.editor.commit();
    }


    public String GetCheckLastQoutationNo() {
        return this.pref.getString( CheckLastQoutationNo, "0" );
    }

    public void SetCheckLastQoutationNo(String CheckLastQoutationNo) {
        this.editor.putString( PrefManager.CheckLastQoutationNo, CheckLastQoutationNo );
        this.editor.commit();
    }


    public String GetCheckGiveAwayFromWhere() {
        return this.pref.getString( CheckLastGiveAwayFromWhere, "GiveAwayFromDashBoard" );
    }

    public void SetCheckGiveAwayFromWhere(String CheckLastGiveAwayFromWhere) {
        this.editor.putString( PrefManager.CheckLastGiveAwayFromWhere, CheckLastGiveAwayFromWhere );
        this.editor.commit();
    }


    public String GetCheckLastGiveAwaynNo() {
        return this.pref.getString( CheckLastGiveAwayNo, "0" );
    }

    public void SetCheckLastGiveAwayNo(String CheckLastGiveAwayNo) {
        this.editor.putString( PrefManager.CheckLastGiveAwayNo, CheckLastGiveAwayNo );
        this.editor.commit();
    }


    public String GetDepartment() {
        return this.pref.getString( DEPARTMENT, "0" );
    }

    public void SetDepartment(String Department) {
        this.editor.putString( PrefManager.DEPARTMENT, Department );
        this.editor.commit();
    }

    public String GetPositionType() {
        return this.pref.getString( POSITIONTYPE, "0" );
    }

    public void SetPositionType(String PType) {
        this.editor.putString( PrefManager.POSITIONTYPE, PType );
        this.editor.commit();
    }

    public String GetPosition() {
        return this.pref.getString( Position, "0" );
    }

    public void SetPosition(String position) {
        this.editor.putString( PrefManager.Position, position );
        this.editor.commit();
    }



    public String GetSecondSuperVisor() {
        return this.pref.getString( SECONDSUPERVISOR, "0" );
    }

    public void SetSecondSuperVisor(String SecondSuperVisor) {
        this.editor.putString( PrefManager.SECONDSUPERVISOR, SecondSuperVisor );
        this.editor.commit();
    }
    public String GetThirdSuperVisor() {
        return this.pref.getString( THIRDSUPERVISOR, "0" );
    }

    public void SetThirdSuperVisor(String ThirdSuperVisor) {
        this.editor.putString( PrefManager.THIRDSUPERVISOR, ThirdSuperVisor );
        this.editor.commit();
    }


    public String GetForthSuperVisor() {
        return this.pref.getString( FORTHSUPERVISOR, "0" );
    }

    public void SetForthSuperVisor(String ForthSuperVisor) {
        this.editor.putString( PrefManager.FORTHSUPERVISOR, ForthSuperVisor );
        this.editor.commit();
    }


    public String GetFifthSuperVisor() {
        return this.pref.getString( FIRFTHSUPERVISOR, "0" );
    }

    public void SetFifthSuperVisor(String FifthSuperVisor) {
        this.editor.putString( PrefManager.FIRFTHSUPERVISOR, FifthSuperVisor );

        this.editor.commit();
    }

    public String GetFirstSuperVisor() {
        return this.pref.getString( FIRSTSUPERVISOR, "0" );
    }

    public void SetFirstSuperVisor(String FirstSuperVisor) {
        this.editor.putString( PrefManager.FIRSTSUPERVISOR, FirstSuperVisor );

        this.editor.commit();
    }

    public String GetSixthSuperVisor() {
        return this.pref.getString( SIXSUPERVISOR, "0" );
    }

    public void SetSixthSuperVisor(String SixSuperVisor) {
        this.editor.putString( PrefManager.SIXSUPERVISOR, SixSuperVisor );
        this.editor.commit();
    }

    public String GetSevenSuperVisor() {
        return this.pref.getString( SEVENSUPERVISOR, "0" );
    }

    public void SetSevenSuperVisor(String SevenSuperVisor) {
        this.editor.putString( PrefManager.SEVENSUPERVISOR, SevenSuperVisor );
        this.editor.commit();
    }

    public String GetEightSuperVisor() {
        return this.pref.getString( EIGHTSUPERVISOR, "0" );
    }

    public void SetEightSuperVisor(String EightSuperVisor) {
        this.editor.putString( PrefManager.EIGHTSUPERVISOR, EightSuperVisor );
        this.editor.commit();
    }


    public String GetProcurementEng() {
        return this.pref.getString( PROCUREMENTENGINEER, "0" );
    }

    public void SetProcurementEng(String ProcurementEng) {
        this.editor.putString( PrefManager.PROCUREMENTENGINEER, ProcurementEng );
        this.editor.commit();
    }


    public String GetFirstOwner() {
        return this.pref.getString( OWNERONWE, "0" );
    }

    public void SetFirstOwner(String fIRSToWNER) {
        this.editor.putString( PrefManager.OWNERONWE, fIRSToWNER );
        this.editor.commit();
    }


    public void clearSession() {
        this.editor.clear();
        this.editor.commit();
    }
}