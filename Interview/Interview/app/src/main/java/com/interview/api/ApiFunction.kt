package com.kody.daawatcustomer.api


class ApiFunction {

    companion object{

        var strBaseUrl : String = "http://www.kokilabenhospital.com/"

        var OP_LOGIN : String = "login"


        //-- Api Success / Error
        var statusSuccess200 = "200"
        var statusSuccess201 = "201"
        var status401 = "401"


        //--- Parameters
        var PARM_PHONE_NUMBER : String = "phone_number"
        var PARM_PASSWORD : String = "password"
        var PARM_CONFIRM_PASSWORD : String = "confirm_password"
        var PARM_DEVICE_NAME : String = "device_name"
        var PARM_MODEL_NAME : String = "model_name"
        var PARM_DEVICE_TOKEN : String = "device_token"
        var PARM_OS_VERSION : String = "os_version"
        var PARM_UUID : String = "uuid"
        var PARM_IP : String = "ip"
        var PARM_DEVICE_TYPE : String = "device_type"
        var PARM_ : String = "phone_number"
    }

}