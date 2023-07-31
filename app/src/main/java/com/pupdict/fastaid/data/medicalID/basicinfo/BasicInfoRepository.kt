package com.pupdict.fastaid.data.medicalID.basicinfo

import kotlinx.coroutines.flow.Flow

interface BasicInfoRepository {

    suspend fun upsertBasicInfo(basicInfo: BasicInfo)

    suspend fun deleteBasicInfo(basicInfo: BasicInfo)

    suspend fun getBasicInfobyID(id: Int): BasicInfo

    fun getBasicInfo(): Flow<List<BasicInfo>>

}