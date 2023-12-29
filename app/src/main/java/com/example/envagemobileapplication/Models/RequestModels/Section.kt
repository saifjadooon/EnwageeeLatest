package com.example.envagemobileapplication.Models.RequestModels

import android.os.Parcel
import android.os.Parcelable
 import com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetSpecificAssesmentRsp.ClientAssessmentFormQuestion


data class Section(
    val clientAssessmentFormSectionId: Int,
    val clientAssessmentFormId: Int,
    val name: String,
    val weightage: Double,
    val isDeleted: Boolean,
    val createdBy: Int,
    val createdDate: String,
    val modifiedBy: Int,
    val modifiedDate: String,
    val clientAssessmentFormQuestions: List<ClientAssessmentFormQuestion>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        mutableListOf<ClientAssessmentFormQuestion>().apply {
            parcel.readList(this, ClientAssessmentFormQuestion::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(clientAssessmentFormSectionId)
        parcel.writeInt(clientAssessmentFormId)
        parcel.writeString(name)
        parcel.writeDouble(weightage)
        parcel.writeByte(if (isDeleted) 1 else 0)
        parcel.writeInt(createdBy)
        parcel.writeString(createdDate)
        parcel.writeInt(modifiedBy)
        parcel.writeString(modifiedDate)
        parcel.writeList(clientAssessmentFormQuestions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Section> {
        override fun createFromParcel(parcel: Parcel): Section {
            return Section(parcel)
        }

        override fun newArray(size: Int): Array<Section?> {
            return arrayOfNulls(size)
        }
    }
}