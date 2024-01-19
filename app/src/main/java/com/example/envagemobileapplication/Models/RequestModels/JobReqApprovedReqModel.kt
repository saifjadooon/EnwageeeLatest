package com.example.envagemobileapplication.Models.RequestModels

data class JobReqApprovedReqModel(
    val jobStatus: JobStatus,
    val jobPublishSetting: JobPublishSetting
)

data class JobStatus(
    val jobRequestId: Int,
    val statusId: Int,
    val jobRequestStatusName: String,
    val remarks: String,
    val jobSkills: String // Assuming jobSkills is a list of strings
)

data class JobPublishSetting(
    val applicationFormId: String,
    val showSalary: Boolean,
    val showNature: Boolean,
    val showClient: Boolean,
    val showIndustry: Boolean,
    val showAddress: Boolean,
    val showType: Boolean,
    val showSkills: Boolean,
    val showShift: Boolean,
    val isPublish: Boolean
)