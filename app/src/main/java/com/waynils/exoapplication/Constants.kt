package com.waynils.exoapplication

class Constants {

    companion object {
        const val PROD_URL = "https://dizeur.000webhostapp.com/"
        const val PROD_URL_IMAGES = "https://dizeur.000webhostapp.com/images/"
    }

    enum class HTTPStatus constructor(val code: Int) {
        BAD_REQUEST(400),
        FORBIDDEN(403),
        NOT_FOUND(404),
        INTERNAL_SERVER_ERROR(500);
    }


}