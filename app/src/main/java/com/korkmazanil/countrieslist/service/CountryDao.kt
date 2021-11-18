package com.korkmazanil.countrieslist.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.korkmazanil.countrieslist.model.Country

@Dao
interface CountryDao {

    // Insert -> INSERT INTO
    // suspend -> coroutine, pause & resume
    // vararg -> multiple country objects
    // List<Long> -> primary keys
    @Insert
    suspend fun insertAll(vararg countries : Country) : List<Long>

    @Query("SELECT * FROM Country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM Country WHERE uuid =:countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM Country")
    suspend fun deleteAllCountries()
}