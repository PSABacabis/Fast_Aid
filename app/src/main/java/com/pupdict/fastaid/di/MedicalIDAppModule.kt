package com.pupdict.fastaid.di

import android.app.Application
import androidx.room.Room
import com.pupdict.fastaid.data.medicalID.MedicalIDDatabase
import com.pupdict.fastaid.data.medicalID.basicinfo.BasicInfoRepository
import com.pupdict.fastaid.data.medicalID.basicinfo.BasicInfoRepositoryImpl
import com.pupdict.fastaid.data.medicalID.medicalnotes.MedicalNotesRepository
import com.pupdict.fastaid.data.medicalID.medicalnotes.MedicalNotesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MedicalIDAppModule {

    @Provides
    @Singleton
    fun provideMedicalIDDatabase(app: Application): MedicalIDDatabase {
        return Room.databaseBuilder(
            app,
            MedicalIDDatabase::class.java,
            "medicalID_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBasicInfoRepository(db: MedicalIDDatabase): BasicInfoRepository {
//        return BasicInfoRepositoryImpl(db.biDAO,db.alDAO,db.mdDAO)
        return BasicInfoRepositoryImpl(db.biDAO)
    }

    @Provides
    @Singleton
    fun provideMedicalNotesRepository(db: MedicalIDDatabase): MedicalNotesRepository {
        return MedicalNotesRepositoryImpl(db.mnDAO)
    }
}

