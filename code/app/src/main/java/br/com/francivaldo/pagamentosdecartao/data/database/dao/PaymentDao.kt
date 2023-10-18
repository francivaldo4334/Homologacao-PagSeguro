package br.com.francivaldo.pagamentosdecartao.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.francivaldo.pagamentosdecartao.data.database.model.Payment
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payment ORDER BY create_at DESC")
    fun getAll(): Flow<List<Payment>>
    @Query("SELECT * FROM payment WHERE id = :id  LIMIT 1")
    fun getById(id:Int): Payment
    @Insert
    abstract fun insert(it: Payment)
    @Query("SELECT * FROM payment ORDER BY create_at DESC LIMIT 1")
    fun getlast(): Payment
    @Update
    abstract fun update(payment: Payment)
}