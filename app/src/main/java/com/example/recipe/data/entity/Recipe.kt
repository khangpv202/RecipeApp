package com.example.recipe.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//Created by Khangpv on 11/12/2021.
@Entity
@Parcelize
data class Recipe(
    val name: String? = null,
    val category: String? = null,
    val imageUrl: String? = null,
    val ingredients: String? = null,
    val steps: String? = null
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}