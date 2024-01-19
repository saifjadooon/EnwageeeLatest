package com.example.envagemobileapplication.Models.RequestModels


data class JobReqUpdateModel(
    val jobRequestId: Int,
    val statusId: Int,
    val jobRequestStatusName: String,
    val jobSkills: String,
    val remarks: String
)
