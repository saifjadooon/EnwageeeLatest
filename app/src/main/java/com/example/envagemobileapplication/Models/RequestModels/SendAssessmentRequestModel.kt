package com.example.envagemobileapplication.Models.RequestModels

class SendAssessmentRequestModel (
    val clientId: Int,
    val jobId: Int,
    val clientAssessmentFormId: Int,
    val candidateId: Int,
    val status: String,
    val baseUrl: String
)