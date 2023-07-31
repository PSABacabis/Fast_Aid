package com.pupdict.fastaid.data.medicalID.basicinfo

import kotlinx.coroutines.flow.Flow

class BasicInfoRepositoryImpl(
    private val biDAO: BasicInfoDAO
): BasicInfoRepository {

    override suspend fun upsertBasicInfo(basicInfo: BasicInfo) {
        biDAO.upsertBasicInfo(basicInfo)
    }

    override suspend fun deleteBasicInfo(basicInfo: BasicInfo) {
        biDAO.deleteBasicInfo(basicInfo)
    }

    override suspend fun getBasicInfobyID(id: Int): BasicInfo {
        return biDAO.getBasicInfobyID(id)
    }

    override fun getBasicInfo(): Flow<List<BasicInfo>> {
        return biDAO.getBasicInfo()
    }

}