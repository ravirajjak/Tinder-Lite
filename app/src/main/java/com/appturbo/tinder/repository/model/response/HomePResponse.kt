package com.appturbo.tinder.repository.model.response

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomePResponse(
    val results: List<Result>
) : Parcelable

@Parcelize
data class Result(
    val seed: String,
    val user: User,
    val version: String,
    var uInfo: String?,
    var uHeader: String?
) : BaseObservable(), Parcelable {
    public var userInfo: String?
        @Bindable get() = uInfo
        set(value) {
            uInfo = value
            notifyChange()
        }
    public var userHeader: String?
        @Bindable get() = uHeader
        set(value) {
            uHeader = value
            notifyChange()
        }
}


@Parcelize
data class User(
    val SSN: String,
    val cell: String,
    val dob: String,
    val email: String,
    val gender: String,
    val location: Location,
    val md5: String,
    val name: Name,
    val password: String,
    val phone: String,
    val picture: String,
    val registered: String,
    val salt: String,
    val sha1: String,
    val sha256: String,
    val username: String
) : Parcelable

@Parcelize
data class Location(
    val city: String,
    val state: String,
    val street: String,
    val zip: String
) : Parcelable

@Parcelize
data class Name(
    val first: String,
    val last: String,
    val title: String
) : Parcelable