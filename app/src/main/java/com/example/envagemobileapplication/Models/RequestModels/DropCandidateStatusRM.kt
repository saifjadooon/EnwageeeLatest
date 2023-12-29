package com.example.envagemobileapplication.Models.RequestModels

import okhttp3.MultipartBody

class DropCandidateStatusRM(
    val description: MultipartBody.Part,
    val candidateId: Int,
    val candidateNotesId: String?, // Use the appropriate type based on your actual data
    val jobId: Int,
    val reason: String
)