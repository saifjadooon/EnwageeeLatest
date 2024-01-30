package com.example.envagemobileapplication.Models.RequestModels


data class GetFltrDtaBulkMsgRm(
    val pageIndex: Int,
    val pageSize: Int,
    val sortBy: String,
    val sortDirection: Int,
    val searchText: String,
    val tileStatusIds: String,
    val candidateFilters: List<Any>, // Replace 'Any' with the actual type if you have a specific type for candidateFilters
    val jobId: Int
)

