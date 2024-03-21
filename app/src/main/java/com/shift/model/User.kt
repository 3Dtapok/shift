package com.shift.model

import java.io.Serializable

data class UserResponse(val results: List<User>)

data class User(

    val name: Name,
    val location: Location,
    val email: String = "",
    val phone: String = "",
    val picture: Picture,
    val gender: String = "",
    val dob: Dob,
) : Serializable

data class Name(
    val title: String = "",
    val first: String = "",
    val last: String = "",
) : Serializable{
    override fun toString(): String = "$title $first $last"
}

data class Location(
    val street: Street,
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val postcode: String = "",
    val coordinates: Cords,

) : Serializable{
    data class Street(
        val number: Int,
        val name: String = "",
    ) : Serializable

    data class Cords(
        val latitude: String = "",
        val longitude: String = "",
    ) : Serializable
}



data class Picture(
    val large: String,
    val medium: String,
    val small: String,
)

data class Dob(
    val age: String,
) : Serializable

