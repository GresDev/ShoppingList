package com.thortigen.orgabuy.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thortigen.orgabuy.data.models.CatalogItem

@Dao
interface CatalogDao {

    @Query("SELECT * FROM catalog_table ORDER BY name ASC")
    fun getAllItems(): LiveData<List<CatalogItem>>

    @Query("SELECT * FROM catalog_table WHERE id LIKE :itemId")
    fun getItemById(itemId: Int): CatalogItem

    @Query("SELECT * FROM catalog_table WHERE name LIKE :itemName")
    fun getItemByName(itemName: String): CatalogItem?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: CatalogItem)

    @Update
    suspend fun updateItem(item: CatalogItem)

    @Delete
    suspend fun deleteItem(item: CatalogItem)

    @Query("SELECT * FROM catalog_table WHERE name LIKE :searchQuery")
    fun searchDatabase(searchQuery: String) : LiveData<List<CatalogItem>>

}