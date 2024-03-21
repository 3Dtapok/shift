package com.shift.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shift.model.Dob
import com.shift.model.Location
import com.shift.model.Name
import com.shift.model.Picture
import com.shift.model.User


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "first_name") val firstName: String = "",
    @ColumnInfo(name = "last_name") val lastName: String = "",
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "phone") val phone: String = "",
    @ColumnInfo(name = "picture_large") val pictureMedium: String = "",
    @ColumnInfo(name = "gender") val gender: String = "",
    @ColumnInfo(name = "dob_age") val dobAge: String = "",
    @ColumnInfo(name = "street_number") val streetNumber: String = "",
    @ColumnInfo(name = "street_name") val streetName: String = "",
    @ColumnInfo(name = "city") val city: String = "",
    @ColumnInfo(name = "state") val state: String = "",
    @ColumnInfo(name = "country") val country: String = "",
    @ColumnInfo(name = "postcode") val postcode: String = "",
    @ColumnInfo(name = "latitude") val latitude: String = "",
    @ColumnInfo(name = "longitude") val longitude: String = "",
) {
    fun toUser(): User {
        return User(
            name = Name(title = title, first = firstName, last = lastName),
            email = email,
            phone = phone,
            picture = Picture(medium = pictureMedium, large = "", small = ""),
            location = Location(
                street = Location.Street(number = streetNumber.toIntOrNull() ?: 0, name = streetName),
                city = city,
                state = state,
                country = country,
                postcode = postcode,
                coordinates = Location.Cords(latitude = latitude, longitude = longitude),
            ),
            gender = gender,
            dob = Dob(age = dobAge)
        )
    }

    companion object {
        fun fromUser(user: User): UserEntity {
            return UserEntity(
                title = user.name.title,
                firstName = user.name.first,
                lastName = user.name.last,
                email = user.email,
                phone = user.phone,
                pictureMedium = user.picture.large,
                gender = user.gender,
                dobAge = user.dob.age,
                streetNumber = user.location.street.number.toString(),
                streetName = user.location.street.name,
                city = user.location.city,
                state = user.location.state,
                country = user.location.country,
                postcode = user.location.postcode,
                latitude = user.location.coordinates.latitude,
                longitude = user.location.coordinates.longitude,
            )
        }
    }
}
