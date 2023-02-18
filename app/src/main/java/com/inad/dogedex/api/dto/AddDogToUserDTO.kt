package com.inad.dogedex.api.dto

import com.squareup.moshi.Json

data class AddDogToUserDTO(@field:Json(name = "dog_id") val dogId: Long)
