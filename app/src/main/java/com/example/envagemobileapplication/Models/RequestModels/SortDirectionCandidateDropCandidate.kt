package com.example.envagemobileapplication.Models.RequestModels


data class SortDirectionCandidateDropCandidate(
    val candidateFilters: List<Any>,
    val jobGUID: String,
    val pageIndex: Int,
    val pageSize: Int,
    val searchText: String,
    val sortBy: String,
    val sortDirection: Int,
    val tileStatusID: Int
)

