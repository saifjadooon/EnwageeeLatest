package com.example.envagemobileapplication.Models.RequestModels


data class CandidateAssesmentRequestModel(
    val pageIndex: Int,
    val pageSize: Int,
    val sortBy: String,
    val sortDirection: Int,
    val searchText: String,
    val candidateGUID: String,
    val candidateFilters: List<String>
)
