package com.example.teamtrack.arch

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("per_page") var perPage: Int? = null,
    @SerializedName("total") var total: Int? = null,
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("data") var data: List<Data> = arrayListOf(),
    @SerializedName("support") var support: Support? = Support()
)

@Entity(tableName = "users")
data class Data(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") var id: Int? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("avatar") var avatar: String? = null,
    var country: String? = null
) {
    companion object {
         val countries = listOf(
            "China", "India", "Indonesia", "Pakistan", "Bangladesh",
            "Japan", "Philippines", "Vietnam"
        )

        fun getRandomCountry(): String {
            return countries.random()
        }
    }
}

data class Support(
    @SerializedName("url") var url: String? = null,
    @SerializedName("text") var text: String? = null
)

data class SingleUserDetails(
    @SerializedName("data") var data: Data? = Data(),
    @SerializedName("support") var support: Support? = Support()
)
