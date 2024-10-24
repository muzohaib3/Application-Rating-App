package com.tcf.schoolprofileapp.services

object SoapRequestBody {

    val soapRequestBody = """
    <?xml version="1.0" encoding="utf-8"?>
    <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                   xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
        <soap:Body>
            <GetAllActiveSchools xmlns="http://tempuri.org/" />
        </soap:Body>
    </soap:Envelope>
""".trimIndent()

}