package com.example.envagemobileapplication.Models.RequestModels


data class sortDirection(
    val pageIndex: Int,
    val pageSize: Int,
    val sortBy: String,
    val sortDirection: Int,
    val searchText: String,
    val tileStatusId: Int

)