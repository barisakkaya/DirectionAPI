package com.example.mobilsorgular.model.google.directions

import com.google.gson.annotations.SerializedName

data class Polyline(@SerializedName("points")
                    val points: String = "")