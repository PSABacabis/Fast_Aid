package com.pupdict.fastaid.data.medicalID.basicinfo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BasicInfoDAO {
    @Upsert
    suspend fun upsertBasicInfo(basicInfo: BasicInfo)

    @Delete
    suspend fun deleteBasicInfo(basicInfo: BasicInfo)

    @Query("SELECT * FROM basic_info WHERE id = :id")
    suspend fun getBasicInfobyID(id: Int): BasicInfo

    @Query("SELECT * FROM basic_info")
    fun getBasicInfo(): Flow<List<BasicInfo>>

}


