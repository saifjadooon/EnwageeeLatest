package com.example.envagemobileapplication.Models.RequestModels


data class SortDirectionJobRequisition(
    val clientId: String?,
    val jobRequestFilters: List<Any>,
    val pageIndex: Int,
    val pageSize: Int,
    val searchText: String,
    val sortBy: String,
    val sortDirection: Int,
    val tileStatusId: Int
 )

