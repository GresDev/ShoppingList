package com.thortigen.orgabuy.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thortigen.orgabuy.data.models.CartItem

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_table")
    fun getAllItems(): LiveData<List<CartItem>>

    @Query("SELECT COUNT(*) FROM cart_table")
    fun getItemsNum(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(cartItem: CartItem)

    @Delete
    suspend fun deleteItem(cartItem: CartItem)

    @Query("DELETE FROM cart_table")
    suspend fun deleteAllItems()

    @Update
    suspend fun editItem(cartItem: CartItem)

    @Query("DELETE FROM cart_table WHERE id LIKE :id")
    suspend fun deleteItemById(id: Int)

}