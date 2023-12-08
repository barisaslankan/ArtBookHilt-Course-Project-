package com.barisaslankan.artbookhilt.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.barisaslankan.artbookhilt.R
import com.barisaslankan.artbookhilt.api.RetrofitAPI
import com.barisaslankan.artbookhilt.repository.ArtBookRepository
import com.barisaslankan.artbookhilt.repository.ArtBookRepositoryInterface
import com.barisaslankan.artbookhilt.roomdb.ArtDao
import com.barisaslankan.artbookhilt.roomdb.ArtDatabase
import com.barisaslankan.artbookhilt.util.Util.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context : Context
    ) = Room.databaseBuilder(
        context,
        ArtDatabase::class.java,
        "ArtDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(
        database: ArtDatabase
    ) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofit() : RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectRepository(dao : ArtDao, api : RetrofitAPI) = ArtBookRepository(dao,api) as ArtBookRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context:Context) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
    )

}