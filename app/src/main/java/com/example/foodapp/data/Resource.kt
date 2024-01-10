package com.example.foodapp.data

sealed class Resource <out R>{
    data class Success<out R : Any>(val result: R) : Resource<R>()
    data class Failure<out R : Any>(val errorMessage: String) : Resource<R>()
}