package br.com.francivaldo.pagamentosdecartao.di

import android.content.Context
import br.com.confchat.mobile.data.database.AppDatabase
import br.com.confchat.mobile.data.database.repository.contract.IPaymentRepository
import br.com.confchat.mobile.data.database.repository.implementation.PaymentRepository
import br.com.confchat.mobile.data.network.repository.pagbank.ApiPagBankRepository
import br.com.confchat.mobile.data.network.repository.pagbank.IApiPagBankRepository
import br.com.francivaldo.pagamentosdecartao.data.network.ApiaPagBankService
import br.com.francivaldo.pagamentosdecartao.domain.repository.IPagBankDomainRepository
import br.com.francivaldo.pagamentosdecartao.domain.repository.PagBankDomainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StanceModule {
    private val authInterceptorPagBank = Interceptor{chain->
        val request = chain.request();
        val newHeaders = request.headers.newBuilder()
            .add("Authorization","Bearer 277C6113D1544B0A9F6887B1F1CCA136")
            .build()
        val newRequest = request.newBuilder().headers(newHeaders).build()
        chain.proceed(newRequest)
    }
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    @Provides
    @Singleton
    fun providerPagbankDomaiRepository(it: IApiPagBankRepository, db: IPaymentRepository): IPagBankDomainRepository {
        return PagBankDomainRepository(it,db)
    }
    @Provides
    @Singleton
    fun providerPayment(@ApplicationContext context: Context): IPaymentRepository {
        return PaymentRepository(AppDatabase.getInstance(context).paymentDao())
    }
    @Provides
    @Singleton
    fun providerPagbankApiRepository(it: ApiaPagBankService): IApiPagBankRepository {
        return ApiPagBankRepository(it)
    }
    fun getClientPagBank() = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(authInterceptorPagBank)
        .addInterceptor(loggingInterceptor)
        .build()
    @Provides
    @Singleton
    fun getRetrofitPagBank(
    ): ApiaPagBankService = Retrofit.Builder()
        .baseUrl("https://sandbox.api.pagseguro.com/")
        .client(getClientPagBank())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiaPagBankService::class.java)
}